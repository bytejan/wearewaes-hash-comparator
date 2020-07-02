package com.wearewaes.hashcomparator.web;

import com.wearewaes.hashcomparator.HashComparatorApplication;
import com.wearewaes.hashcomparator.domain.Hash;
import com.wearewaes.hashcomparator.repository.HashRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @BeforeEach
    public void init() {
        hashRepository.deleteAll();
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

    @Test
    public void whenHashIsNotBase64_thenShouldThrowError() throws Exception {
        // Given.
        String hashVersion = "12343";
        String position = "right";
        restAccountMockMvc.perform(get("/v1/diff/" + hashVersion + "/" + position)
                .contentType(MediaType.APPLICATION_JSON).content("{\"hash\": \"weare#$ weas\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("hash is not a valid base 64"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenHashIsAlreadySaved_thenShouldReturnError() throws Exception {
        // Given.
        String hashVersion = "12343";
        String position = "right";

        Hash hash = new Hash();
        hash.setHashVersion(hashVersion);
        hash.setPosition(position);
        hash.setHash("myhash");
        hashRepository.save(hash);

        // When.
        restAccountMockMvc.perform(get("/v1/diff/" + hashVersion + "/" + position)
                .contentType(MediaType.APPLICATION_JSON).content("{\"hash\": \"d2VhcmV3YWVz\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("The right hash with id 12343 already exist"))
                .andExpect(status().isBadRequest());

        // Then.
        assertEquals(hashRepository.findAll().size(), 1);
    }

    @Test
    public void whenTryToGetHashResultWithFromAnUnknownId_thenShouldReturnError() throws Exception {
        // Given.
        String hashVersion = "12343";

        // When.
        restAccountMockMvc.perform(get("/v1/diff/" + hashVersion)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Not all hashes for id 12343 are set"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenHashesAreSame_thenShouldReturnEqualResponse() throws Exception {
        // Given.
        String hashVersion = "12343";

        Hash hash = new Hash();
        hash.setHashVersion(hashVersion);
        hash.setPosition("LEFT");
        hash.setHash("d2VhcmV3YWVz");
        hashRepository.save(hash);

        Hash hash2 = new Hash();
        hash2.setHashVersion(hashVersion);
        hash2.setPosition("LEFT");
        hash2.setHash("d2VhcmV3YWVz");
        hashRepository.save(hash2);

        // When / Then.
        restAccountMockMvc.perform(get("/v1/diff/" + hashVersion)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.equal").value(true))
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void whenHashesAreNotTheSame_thenShouldReturnDiffErrorResponse() throws Exception {
        // Given.
        String hashVersion = "12343";

        Hash hash = new Hash();
        hash.setHashVersion(hashVersion);
        hash.setPosition("LEFT");
        hash.setHash("a2VhcmV3YWVz");
        hashRepository.save(hash);

        Hash hash2 = new Hash();
        hash2.setHashVersion(hashVersion);
        hash2.setPosition("LEFT");
        hash2.setHash("d2VhcmV3YWVzdd");
        hashRepository.save(hash2);

        // When / Then.
        restAccountMockMvc.perform(get("/v1/diff/" + hashVersion)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.equal").value(false))
                .andExpect(jsonPath("$.errors[0].position").value(1))
                .andExpect(jsonPath("$.errors[0].offset").value(1))
                .andExpect(jsonPath("$.errors[1].position").value(13))
                .andExpect(jsonPath("$.errors[1].offset").value(2))
                .andExpect(status().isOk());
    }
}
