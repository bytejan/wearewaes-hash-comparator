package com.wearewaes.hashcomparator.service.dto;

public class DiffErrorDTO {
    int position;
    int offset;

    public DiffErrorDTO(int position, int offset) {
        this.position = position;
        this.offset = offset;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "DiffError{" +
                "position=" + position +
                ", offset=" + offset +
                '}';
    }
}
