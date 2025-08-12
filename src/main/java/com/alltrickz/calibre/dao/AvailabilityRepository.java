package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.OwnerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<OwnerAvailability, Integer> {
}
