package com.wearewaes.hashcomparator.exceptions;

public class HashPositionAlreadyExistException extends RuntimeException {
    public HashPositionAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
