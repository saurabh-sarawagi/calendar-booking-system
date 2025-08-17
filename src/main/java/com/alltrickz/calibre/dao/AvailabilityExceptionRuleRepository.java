package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.AvailabilityExceptionRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for AvailabilityExceptionRule entity.
 * Provides methods to fetch exception rules for a given owner.
 */
public interface AvailabilityExceptionRuleRepository extends JpaRepository<AvailabilityExceptionRule, Long> {

    /**
     * Fetch all exception rules for an owner.
     */
    List<AvailabilityExceptionRule> findByOwnerId(Long ownerId);

    /**
     * Fetch a single exception rule for an owner by date.
     */
    AvailabilityExceptionRule findByOwnerIdAndDate(Long ownerId, LocalDate date);
}
