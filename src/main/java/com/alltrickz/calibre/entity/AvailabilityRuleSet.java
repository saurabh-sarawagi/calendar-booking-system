package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class AvailabilityRuleSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Owner owner;

    @OneToMany(mappedBy = "availabilityRuleSet", fetch = FetchType.LAZY)
    private List<AvailabilityWeeklyRule> availabilityWeeklyRules;

    @OneToMany(mappedBy = "availabilityRuleSet", fetch = FetchType.LAZY)
    private List<AvailabilityExceptionRule> availabilityExceptionRules;

}
