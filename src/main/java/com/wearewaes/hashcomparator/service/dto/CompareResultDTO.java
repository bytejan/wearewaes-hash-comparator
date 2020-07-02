package com.wearewaes.hashcomparator.service.dto;

import java.util.List;

public class CompareResultDTO {

    public CompareResultDTO(List<DiffErrorDTO> errors) {
        this.errors = errors;

        if (errors.size() == 0) {
            this.isEqual = true;
        } else {
            this.isEqual = false;
        }
    }

    private boolean isEqual;

    public boolean isEqual() {
        return isEqual;
    }

    public void setEqual(boolean equal) {
        isEqual = equal;
    }

    private List<DiffErrorDTO> errors;

    public List<DiffErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<DiffErrorDTO> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "CompareResultDTO{" +
                "isEqual=" + isEqual +
                ", errors=" + errors +
                '}';
    }
}