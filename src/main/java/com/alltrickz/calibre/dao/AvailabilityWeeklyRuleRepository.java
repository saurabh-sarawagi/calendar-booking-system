package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface AvailabilityWeeklyRuleRepository extends JpaRepository<AvailabilityWeeklyRule, Long> {
    List<AvailabilityWeeklyRule> findByOwnerId(Long ownerId);

    void deleteByOwnerId(Long ownerId);

    AvailabilityWeeklyRule findByOwnerIdAndDayOfWeek(Long ownerId, DayOfWeek dayOfWeek);
}
