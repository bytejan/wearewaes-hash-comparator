package com.wearewaes.hashcomparator.service;

import com.wearewaes.hashcomparator.domain.Hash;
import com.wearewaes.hashcomparator.domain.Position;
import com.wearewaes.hashcomparator.repository.HashRepository;
import org.springframework.stereotype.Service;

@Service
public class HashService {

    private HashRepository hashRepository;

    public HashService(HashRepository hashRepository) {
        this.hashRepository = hashRepository;
    }

    public void saveHash(String hashId, String hashString, Position position) {
        Hash hash = new Hash();
        hash.setHash(hashString);
        hash.setPosition(position.toString());
        hash.setHashVersion(hashId);
        hashRepository.save(hash);
    }
}
