package com.wearewaes.hashcomparator.exceptions;

public class InvalidBase64Exception extends RuntimeException {
    public InvalidBase64Exception(String errorMessage) {
        super(errorMessage);
    }
}
