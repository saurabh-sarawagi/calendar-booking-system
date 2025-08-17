package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Repository for AvailabilityWeeklyRule entity.
 * Provides methods to fetch and delete weekly rules for an owner.
 */
public interface AvailabilityWeeklyRuleRepository extends JpaRepository<AvailabilityWeeklyRule, Long> {

    /**
     * Fetch all weekly rules for a given owner.
     */
    List<AvailabilityWeeklyRule> findByOwnerId(Long ownerId);

    /**
     * Delete all weekly rules for a given owner.
     */
    void deleteByOwnerId(Long ownerId);

    /**
     * Fetch a weekly rule for a specific owner and day.
     */
    AvailabilityWeeklyRule findByOwnerIdAndDayOfWeek(Long ownerId, DayOfWeek dayOfWeek);
}
