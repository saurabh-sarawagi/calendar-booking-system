package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
