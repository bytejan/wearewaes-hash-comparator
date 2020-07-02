package com.wearewaes.hashcomparator.service;

import com.wearewaes.hashcomparator.domain.Hash;
import com.wearewaes.hashcomparator.exceptions.HashPositionAlreadyExistException;
import com.wearewaes.hashcomparator.exceptions.MissingHashException;
import com.wearewaes.hashcomparator.repository.HashRepository;
import com.wearewaes.hashcomparator.service.dto.CompareResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashService {
    private HashRepository hashRepository;
    private HashCompareService hashCompareService;

    public HashService(HashRepository hashRepository, HashCompareService hashCompareService) {
        this.hashRepository = hashRepository;
        this.hashCompareService = hashCompareService;
    }

    /**
     *
     * @param hashVersion
     * @param hashString
     * @param position
     *
     * @throws HashPositionAlreadyExistException
     */
    public void saveHash(String hashVersion, String hashString, String position) throws HashPositionAlreadyExistException {

        if (hashRepository.findByHashVersionAndPosition(hashVersion, position) != null) {
            throw new HashPositionAlreadyExistException("The " + position + " hash with id " + hashVersion + " already exist");
        }

        Hash hash = new Hash();
        hash.setHash(hashString);
        hash.setPosition(position);
        hash.setHashVersion(hashVersion);
        hashRepository.save(hash);
    }

    /**
     * Get the result from the 2 hashes which are stored in the databased based on the hash version.
     *
     * @param hashVersion
     */
    public CompareResultDTO getHashDiffResult(String hashVersion) {
        List<Hash> hashes = this.hashRepository.findByHashVersion(hashVersion);

        if (hashes.size() < 2) {
            throw new MissingHashException("Not all hashes for id " + hashVersion + " are set");
        }

        return hashCompareService.compare(hashes.get(0).getHash(), hashes.get(1).getHash());
    }
}
