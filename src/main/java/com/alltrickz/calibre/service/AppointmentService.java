package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AppointmentRepository;
import com.alltrickz.calibre.dao.AvailabilityRepository;
import com.alltrickz.calibre.dao.OwnerRepository;
import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import com.alltrickz.calibre.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilityRepository availabilityRepository;
    private final OwnerRepository ownerRepository;
    private final TimeSlotService timeSlotService;

    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        Owner owner = ownerRepository.findById(appointmentRequestDTO.getOwnerId()).orElseThrow(() -> new Exception("Owner Not Found"));

        AvailabilityRule availabilityRule = availabilityRepository.findByOwner(owner);

        if (ObjectUtils.isEmpty(availabilityRule)) {
            throw new Exception("Owner has not defined his Availability.");
        }

        LocalTime start = LocalTime.parse(appointmentRequestDTO.getStartTime());
        LocalTime end = LocalTime.parse(appointmentRequestDTO.getEndTime());

        if (!end.equals(start.plusHours(1))) {
            throw new Exception("End Time should always be 1 hour more than Start Time");
        }

        // checking availability using Search available time slot api
        List<TimeSlotResponseDTO> availableTimeSlots = timeSlotService.getAvailableTimeSlots(appointmentRequestDTO.getOwnerId(), appointmentRequestDTO.getDate());

        Optional<TimeSlotResponseDTO> matchingSlot = availableTimeSlots.stream().filter(slot -> slot.getStart().equals(start) && slot.getEnd().equals(end)).findFirst();

        if (matchingSlot.isPresent()) {
            Appointment appointment = AppointmentMapper.mapToEntity(appointmentRequestDTO, owner);
            Appointment savedAppointment = appointmentRepository.save(appointment);
            return AppointmentMapper.mapToResponse(savedAppointment);
        } else {
            throw new Exception("This Timeslot is not available for booking appointment.");
        }
    }

    public List<AppointmentResponseDTO> getUpcomingAppointments(@RequestParam Long ownerId) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));

        AvailabilityRule availabilityRule = availabilityRepository.findByOwner(owner);

        if (ObjectUtils.isEmpty(availabilityRule)) {
            throw new Exception("Owner is not available for appointments.");
        }

        List<Appointment> appointments = appointmentRepository.findUpcomingAppointments(ownerId, LocalDate.now(), LocalTime.now());
        return appointments.stream().map(AppointmentMapper::mapToResponse).toList();
    }
}
