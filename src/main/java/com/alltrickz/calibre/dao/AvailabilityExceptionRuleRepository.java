package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.AvailabilityExceptionRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityExceptionRuleRepository extends JpaRepository<AvailabilityExceptionRule, Long> {
    List<AvailabilityExceptionRule> findByOwnerId(Long ownerId);

    Optional<AvailabilityExceptionRule> findByOwnerIdAndDate(Long ownerId, LocalDate date);
}
