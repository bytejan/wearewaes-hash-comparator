package com.wearewaes.hashcomparator.service;

import com.wearewaes.hashcomparator.exceptions.InvalidBase64Exception;
import com.wearewaes.hashcomparator.service.dto.CompareResultDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashCompareServiceTest {

    private static final String VALID_BASE64 = "d2VhcmV3YWVz";
    private static final String VALID_BASE64_NR2 = "d2GhcmV3YWVgd";

    private static final String NON_VALID_BASE64 = "weare#$ weas";

    private static final String LEFT_HASH_NOT_VALID_ERROR_MSG = "Left hash is not a valid base 64";
    private static final String RIGHT_HASH_NOT_VALID_ERROR_MSG = "Right hash is not a valid base 64";

    private final HashCompareService hashCompareService = new HashCompareService();

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

    @Test
    public void whenBothHashesAreTheSame_thenResultShouldShowEqual() {
        // Given.
        CompareResultDTO result =  hashCompareService.compare(VALID_BASE64, VALID_BASE64);

        // Then.
        assertEquals(result.getErrors().size(), 0);
        assertTrue(result.isEqual());
    }

    @Test
    public void whenLeftHashIsLonger_thenResultShouldShowErrors() {
        // Given.
        CompareResultDTO result =  hashCompareService.compare(VALID_BASE64_NR2, VALID_BASE64);

        // Then.
        assertEquals(result.getErrors().size(), 2);

        // Error 1 should have an offset from 1.
        assertEquals(result.getErrors().get(0).getOffset(), 1);

        // Error 2 should have an offset from 2.
        assertEquals(result.getErrors().get(1).getOffset(), 2);
        assertFalse(result.isEqual());
    }

    @Test
    public void whenRightHashIsLonger_thenResultShouldShowErrors() {
        // Given.
        CompareResultDTO result =  hashCompareService.compare(VALID_BASE64, VALID_BASE64_NR2);

        // Then.
        assertEquals(result.getErrors().size(), 2);

        // Error 1 should have an offset from 1.
        assertEquals(result.getErrors().get(0).getOffset(), 1);

        // Error 2 should have an offset from 2.
        assertEquals(result.getErrors().get(1).getOffset(), 2);
        assertFalse(result.isEqual());
    }
}
