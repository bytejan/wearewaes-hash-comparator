package com.wearewaes.hashcomparator.repository;

import com.wearewaes.hashcomparator.domain.Hash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link Hash} entity.
 */
@Repository
public interface HashRepository extends JpaRepository<Hash, Long> {
    Hash findByHashVersionAndPosition(String hashVersion, String position);

    List<Hash> findByHashVersion(String hashVersion);
}
