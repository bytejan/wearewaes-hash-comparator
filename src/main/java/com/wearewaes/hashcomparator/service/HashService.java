package com.wearewaes.hashcomparator.service;

import com.wearewaes.hashcomparator.domain.Hash;
import com.wearewaes.hashcomparator.exceptions.HashPositionAlreadyExistException;
import com.wearewaes.hashcomparator.repository.HashRepository;
import org.springframework.stereotype.Service;

@Service
public class HashService {
    private HashRepository hashRepository;

    public HashService(HashRepository hashRepository) {
        this.hashRepository = hashRepository;
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
            throw new HashPositionAlreadyExistException("The " + position + " hash with id " + hashVersion + " does already exist");
        }

        Hash hash = new Hash();
        hash.setHash(hashString);
        hash.setPosition(position);
        hash.setHashVersion(hashVersion);
        hashRepository.save(hash);
    }
}
