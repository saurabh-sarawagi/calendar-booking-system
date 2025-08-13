package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AppointmentRepository;
import com.alltrickz.calibre.dao.AvailabilityRepository;
import com.alltrickz.calibre.dao.OwnerRepository;
import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import com.alltrickz.calibre.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilityRepository availabilityRepository;
    private final OwnerRepository ownerRepository;

    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        Owner owner = ownerRepository.findById(appointmentRequestDTO.getOwnerId()).orElseThrow(() -> new Exception("Owner Not Found"));

        AvailabilityRule availabilityRule = availabilityRepository.findByOwner(owner);

        if (ObjectUtils.isEmpty(availabilityRule)) {
            throw new Exception("Owner has not defined any Availability Rule.");
        }

        LocalTime start = LocalTime.parse(appointmentRequestDTO.getStartTime());
        LocalTime end = LocalTime.parse(appointmentRequestDTO.getEndTime());

        if (!end.equals(start.plusHours(1))) {
            throw new Exception("Only 1 hour slot is allowed for booking");
        }

        if (start.isBefore(availabilityRule.getStartTime()) || end.isAfter(availabilityRule.getEndTime())) {
            throw new Exception("Slot not within availability");
        }

        boolean appointmentExists = appointmentRepository.existsByOwnerIdAndDateAndStartTimeAndEndTime(appointmentRequestDTO.getOwnerId(), appointmentRequestDTO.getDate(), start, end);
        if (appointmentExists) {
            throw new Exception("This Timeslot is not available for booking appointment.");
        }

        Appointment appointment = AppointmentMapper.mapToEntity(appointmentRequestDTO, owner);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.mapToResponse(savedAppointment);
    }
}
