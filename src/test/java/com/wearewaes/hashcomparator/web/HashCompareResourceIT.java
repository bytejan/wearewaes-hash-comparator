package com.wearewaes.hashcomparator.web;

import com.wearewaes.hashcomparator.HashComparatorApplication;
import com.wearewaes.hashcomparator.domain.Hash;
import com.wearewaes.hashcomparator.repository.HashRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = HashComparatorApplication.class)
public class HashCompareResourceIT {

    @Autowired
    private MockMvc restAccountMockMvc;

    @Autowired
    private HashRepository hashRepository;

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
        // Given.
        String hashVersion = "12343";
        String position = "right";
        restAccountMockMvc.perform(get("/v1/diff/" + hashVersion + "/" + position)
                .contentType(MediaType.APPLICATION_JSON).content("{\"hash\": \"d2VhcmV3YWVz\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // When.
        Hash hash = hashRepository.findByHashVersionAndPosition(hashVersion, position);

        // Then.
        assertEquals(hash.getHash(), "d2VhcmV3YWVz");
        assertEquals(hash.getHashVersion(), hashVersion);
        assertEquals(hash.getPosition(), position);
    }
}
