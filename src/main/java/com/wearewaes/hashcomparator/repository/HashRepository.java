package com.wearewaes.hashcomparator.repository;

import com.wearewaes.hashcomparator.domain.Hash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Hash} entity.
 */
@Repository
public interface HashRepository extends JpaRepository<Hash, Long> {
    Hash findByHashVersionAndPosition(String hashVersion, String position);
}
