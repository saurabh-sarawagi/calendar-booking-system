package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Owner entity.
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
