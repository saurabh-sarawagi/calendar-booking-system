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
        validateAppointmentRequest(appointmentRequestDTO, owner);

        // save the appointment
        Appointment appointment = AppointmentMapper.mapToEntity(appointmentRequestDTO, owner);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.mapToResponse(savedAppointment);

    }

    public AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new Exception("Appointment not found with id: " + appointmentId));
        Owner owner = ownerRepository.findById(existingAppointment.getOwner().getId()).orElseThrow(() -> new Exception("Owner Not Found"));
        validateAppointmentRequest(appointmentRequestDTO, owner);

        // Update the appointment fields - owner won't be updated
        existingAppointment.setDate(appointmentRequestDTO.getDate());
        existingAppointment.setStartTime(LocalTime.parse(appointmentRequestDTO.getStartTime()));
        existingAppointment.setEndTime(LocalTime.parse(appointmentRequestDTO.getEndTime()));
        existingAppointment.setInviteeName(appointmentRequestDTO.getInviteeName());
        existingAppointment.setInviteeEmail(appointmentRequestDTO.getInviteeEmail());
        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
        return AppointmentMapper.mapToResponse(savedAppointment);
    }

    private void validateAppointmentRequest(AppointmentRequestDTO appointmentRequestDTO, Owner owner) throws Exception {
        AvailabilityRule availabilityRule = availabilityRepository.findByOwner(owner);
        if (ObjectUtils.isEmpty(availabilityRule)) {
            throw new Exception("Owner has not defined his Availability.");
        }

        LocalTime start = LocalTime.parse(appointmentRequestDTO.getStartTime());
        LocalTime end = LocalTime.parse(appointmentRequestDTO.getEndTime());

        if (!end.equals(start.plusHours(1))) {
            throw new Exception("End Time should always be 1 hour more than Start Time");
        }

        // Check slot availability
        List<TimeSlotResponseDTO> availableTimeSlots = timeSlotService.getAvailableTimeSlots(owner.getId(), appointmentRequestDTO.getDate());

        Optional<TimeSlotResponseDTO> matchingSlot = availableTimeSlots.stream().filter(slot -> slot.getStart().equals(start) && slot.getEnd().equals(end)).findFirst();

        if (matchingSlot.isEmpty()) {
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

    public void deleteAppointment(Long appointmentId) throws Exception {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new Exception("Appointment not found with id: " + appointmentId));
        appointmentRepository.delete(existingAppointment);
    }

}
