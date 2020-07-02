package com.wearewaes.hashcomparator.service;

import com.wearewaes.hashcomparator.exceptions.InvalidBase64Exception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HashCompareServiceTest {

    private static String VALID_BASE64 = "d2VhcmV3YWVz";
    private static String NON_VALID_BASE64 = "weare#$ weas";

    private static String LEFT_HASH_NOT_VALID_ERROR_MSG = "Left hash is not a valid base 64";
    private static String RIGHT_HASH_NOT_VALID_ERROR_MSG = "Right hash is not a valid base 64";

    private HashCompareService hashCompareService = new HashCompareService();

    @Test
    void whenLeftHashNotBase64_thenShouldThrowError() {
        // Given.
        Exception exception = assertThrows(InvalidBase64Exception.class, () ->
                hashCompareService.compare(NON_VALID_BASE64, VALID_BASE64));

        // Then.
        assertEquals(LEFT_HASH_NOT_VALID_ERROR_MSG, exception.getMessage());
    }

    @Test
    public void whenRightHashNotBase64_thenShouldThrowError() {
        // Given.
        Exception exception = assertThrows(InvalidBase64Exception.class, () ->
                hashCompareService.compare(VALID_BASE64, NON_VALID_BASE64));

        // Then.
        assertEquals(RIGHT_HASH_NOT_VALID_ERROR_MSG, exception.getMessage());
    }
}
