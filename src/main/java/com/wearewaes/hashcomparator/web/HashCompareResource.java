package com.wearewaes.hashcomparator.web;
import com.wearewaes.hashcomparator.domain.Position;
import com.wearewaes.hashcomparator.exceptions.HashPositionAlreadyExistException;
import com.wearewaes.hashcomparator.service.HashService;
import com.wearewaes.hashcomparator.service.dto.CompareResultDTO;
import com.wearewaes.hashcomparator.web.dto.HashDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/diff")
public class HashCompareResource {

    private final HashService hashService;

    public HashCompareResource(HashService hashService) {
        this.hashService = hashService;
    }

    @GetMapping("/{hashId}/{position}")
    public ResponseEntity saveHash(@PathVariable String hashId, @PathVariable Position position,
                                   @Valid @RequestBody HashDTO hash) throws HashPositionAlreadyExistException {

        hashService.saveHash(hashId, hash.getHash(), position.toString());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/{hashId}")
    public ResponseEntity<CompareResultDTO> getHashDiffResult(@PathVariable String hashId) throws HashPositionAlreadyExistException {
        CompareResultDTO result = hashService.getHashDiffResult(hashId);
        return ResponseEntity.ok(result);
    }
}
