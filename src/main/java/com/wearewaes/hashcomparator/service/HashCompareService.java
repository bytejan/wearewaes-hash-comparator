package com.wearewaes.hashcomparator.service;

import com.wearewaes.hashcomparator.exceptions.InvalidBase64Exception;
import com.wearewaes.hashcomparator.service.dto.CompareResultDTO;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

@Service
public class HashCompareService {

    public CompareResultDTO compare(String leftHash, String rightHash) throws InvalidBase64Exception {
        if (!Base64.isBase64(leftHash.getBytes())) {
            throw new InvalidBase64Exception("Left hash is not a valid base 64");
        }

        if (!Base64.isBase64(rightHash.getBytes())) {
            throw new InvalidBase64Exception("Right hash is not a valid base 64");
        }

        return new CompareResultDTO();
    }
}
