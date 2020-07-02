package com.wearewaes.hashcomparator.web.dto;

import javax.validation.constraints.NotNull;

public class HashDTO {

    @NotNull
    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
