package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AvailabilityRepository;
import com.alltrickz.calibre.entity.OwnerAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public String setAvailability(LocalTime startTime, LocalTime endTime) {
        //todo input to be validated

        OwnerAvailability ownerAvailability = new OwnerAvailability();
        ownerAvailability.setStartTime(startTime);
        ownerAvailability.setEndTime(endTime);
        availabilityRepository.save(ownerAvailability);

        return "SUCCESS";
    }

}
