package com.wearewaes.hashcomparator.service;

import com.wearewaes.hashcomparator.domain.Hash;
import com.wearewaes.hashcomparator.exceptions.HashPositionAlreadyExistException;
import com.wearewaes.hashcomparator.exceptions.InvalidBase64Exception;
import com.wearewaes.hashcomparator.exceptions.MissingHashException;
import com.wearewaes.hashcomparator.repository.HashRepository;
import com.wearewaes.hashcomparator.service.dto.CompareResultDTO;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashService {
    private final HashRepository hashRepository;
    private final HashCompareService hashCompareService;

    public HashService(HashRepository hashRepository, HashCompareService hashCompareService) {
        this.hashRepository = hashRepository;
        this.hashCompareService = hashCompareService;
    }

    /**
     *
     * @param hashVersion The hash version.
     * @param hashString  The base64 hash.
     * @param position    The position (left or right).
     *
     * @throws HashPositionAlreadyExistException Thrown when the hash for the position + version already exist.
     */
    public void saveHash(String hashVersion, String hashString, String position) throws HashPositionAlreadyExistException {

        if (hashRepository.findByHashVersionAndPosition(hashVersion, position) != null) {
            throw new HashPositionAlreadyExistException("The " + position + " hash with id " + hashVersion + " already exist");
        }

        if (!Base64.isBase64(hashString.getBytes())) {
            throw new InvalidBase64Exception("hash is not a valid base 64");
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
     * @param hashVersion The hash version.
     */
    public CompareResultDTO getHashDiffResult(String hashVersion) {
        List<Hash> hashes = this.hashRepository.findByHashVersion(hashVersion);

        if (hashes.size() < 2) {
            throw new MissingHashException("Not all hashes for id " + hashVersion + " are set");
        }

        return hashCompareService.compare(hashes.get(0).getHash(), hashes.get(1).getHash());
    }
}
