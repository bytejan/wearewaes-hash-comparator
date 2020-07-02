package com.wearewaes.hashcomparator.service;

import com.wearewaes.hashcomparator.exceptions.InvalidBase64Exception;
import com.wearewaes.hashcomparator.service.dto.CompareResultDTO;
import com.wearewaes.hashcomparator.service.dto.DiffErrorDTO;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;

@Service
public class HashCompareService {

    public CompareResultDTO compare(String leftHash, String rightHash) throws InvalidBase64Exception {
        List<DiffErrorDTO> errors = new ArrayList<>();
        char[] hash1chars;
        char[] hash2chars;

        if (!Base64.isBase64(leftHash.getBytes())) {
            throw new InvalidBase64Exception("Left hash is not a valid base 64");
        }

        if (!Base64.isBase64(rightHash.getBytes())) {
            throw new InvalidBase64Exception("Right hash is not a valid base 64");
        }

        // Take hash with most characters to compare.
        if (leftHash.length() > rightHash.length()) {
            hash1chars = leftHash.toCharArray();
            hash2chars = rightHash.toCharArray();
        } else {
            hash1chars = rightHash.toCharArray();
            hash2chars = leftHash.toCharArray();
        }


        for (int i = 0; i < hash1chars.length; i++) {
            // Check if the char does not match.
            if (checkIfChartInListIsNotEqual(hash1chars, hash2chars, i)) {

                // Check if the previous char did not match, else create a new error object (only if index is not 0).
                if (i != 0  && checkIfChartInListIsNotEqual(hash1chars, hash2chars, i -1)) {
                    // Add +1 to the offset from the latest error.
                    int index = errors.size() -1;
                    DiffErrorDTO lastError = errors.get(index);
                    lastError.setOffset(lastError.getOffset() + 1);
                    errors.set(index, lastError);
                } else {
                    int position = i + 1;
                    errors.add(new DiffErrorDTO(position,1));
                }
            }
        }

        return new CompareResultDTO(errors);
    }

    static boolean checkIfChartInListIsNotEqual(char[] list1, char[] list2, int position) {
        return list2.length <= position || list1[position] != list2[position];
    }
}
