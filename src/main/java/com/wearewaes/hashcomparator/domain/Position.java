package com.wearewaes.hashcomparator.domain;

public enum Position {
    LEFT("left"),
    RIGHT("right");

    private String positionName;

    Position(String positionName) {
        this.positionName = positionName;

    }

    @Override
    public String toString(){
        return positionName;
    }
}
