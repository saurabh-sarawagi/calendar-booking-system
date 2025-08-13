package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<AvailabilityRule, Long> {

    AvailabilityRule findByOwner(Owner owner);

}
