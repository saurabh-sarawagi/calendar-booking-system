package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<AvailabilityRule, Long> {

    Optional<AvailabilityRule> findByOwner(Owner owner);

}
