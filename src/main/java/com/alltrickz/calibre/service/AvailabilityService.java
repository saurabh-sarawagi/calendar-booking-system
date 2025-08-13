package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AvailabilityRepository;
import com.alltrickz.calibre.dao.OwnerRepository;
import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final OwnerRepository ownerRepository;

    public AvailabilityRule setAvailability(Long ownerId, LocalTime startTime, LocalTime endTime) throws Exception {
        //todo input to be validated
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));

        AvailabilityRule availabilityRule = new AvailabilityRule();
        availabilityRule.setOwner(owner);
        availabilityRule.setStartTime(startTime);
        availabilityRule.setEndTime(endTime);
        return availabilityRepository.save(availabilityRule);

    }

}
