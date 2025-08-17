package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityWeeklyRuleRepository extends JpaRepository<AvailabilityWeeklyRule, Long> {
    List<AvailabilityWeeklyRule> findByOwnerId(Long ownerId);
}
