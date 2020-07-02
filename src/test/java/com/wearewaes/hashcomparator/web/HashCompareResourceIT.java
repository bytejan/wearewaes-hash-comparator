package com.wearewaes.hashcomparator.web;

import com.wearewaes.hashcomparator.HashComparatorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = HashComparatorApplication.class)
public class HashCompareResourceIT {

    @Autowired
    private MockMvc restAccountMockMvc;

    @Test
    public void whenHashNotGiven_thenShouldThrowError() throws Exception {
        restAccountMockMvc.perform(get("/v1/diff/hash1/right")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPositionIsInvalid_thenShouldThrowError() throws Exception {
        restAccountMockMvc.perform(get("/v1/diff/hash1/notvalid")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenHashIsValid_thenShouldBeSavedInDatabase() throws Exception {
        restAccountMockMvc.perform(get("/v1/diff/hash1/right")
                .contentType(MediaType.APPLICATION_JSON).content("{\"hash\": \"d2VhcmV3YWVz\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
