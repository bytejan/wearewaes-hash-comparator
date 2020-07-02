package com.wearewaes.hashcomparator.exceptions;

public class MissingHashException extends RuntimeException {
    public MissingHashException(String errorMessage) {
        super(errorMessage);
    }
}
