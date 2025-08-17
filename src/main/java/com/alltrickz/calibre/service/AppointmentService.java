package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AppointmentRepository;
import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.Owner;
import com.alltrickz.calibre.mapper.AppointmentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final OwnerService ownerService;
    private final TimeSlotService timeSlotService;

    /**
     * Books a new appointment for the given request.
     * Ensures time slot availability and handles concurrency conflicts.
     *
     * @param appointmentRequestDTO appointment details
     * @return AppointmentResponseDTO with saved appointment info
     * @throws Exception if owner not found or slot unavailable
     */
    @Transactional
    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        // validate and fetch owner details
        Owner owner = ownerService.validateAndGetOwner(appointmentRequestDTO.getOwnerId());

        // validating appointment request with checks on available slot
        validateAppointmentRequest(appointmentRequestDTO, owner);

        // save the appointment
        try {
            Appointment appointment = AppointmentMapper.mapToEntity(appointmentRequestDTO, owner);
            Appointment savedAppointment = appointmentRepository.saveAndFlush(appointment);
            return AppointmentMapper.mapToResponse(savedAppointment);
        } catch (DataIntegrityViolationException e) {
            // handles race conditions where another appointment was booked simultaneously for same slot
            throw new Exception("Time slot already booked. Please pick another.");
        }
    }

    /**
     * Updates an existing appointment.
     * Owner is not updated.
     *
     * @param appointmentId ID of the appointment
     * @param appointmentRequestDTO updated appointment details
     * @return AppointmentResponseDTO with updated info
     * @throws Exception if appointment or owner not found, or slot unavailable
     */
    public AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        // Fetching existing appointment from DB
        Appointment existingAppointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new Exception("Appointment not found with id: " + appointmentId));

        // validate and fetch owner details
        Owner owner = ownerService.validateAndGetOwner(existingAppointment.getOwner().getId());

        // validating appointment request with checks on available slot
        validateAppointmentRequest(appointmentRequestDTO, owner);

        // update the entity and persist
        AppointmentMapper.updateEntity(existingAppointment, appointmentRequestDTO);
        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
        return AppointmentMapper.mapToResponse(savedAppointment);
    }

    /**
     * Validates appointment request:
     *  - Slot duration must be exactly 1 hour
     *  - Slot must be available based on Owner Rules and existing appointments
     *
     * @param appointmentRequestDTO appointment request
     * @param owner appointment owner
     * @throws Exception if slot is invalid or unavailable
     */
    private void validateAppointmentRequest(AppointmentRequestDTO appointmentRequestDTO, Owner owner) throws Exception {
        LocalTime start = LocalTime.parse(appointmentRequestDTO.getStartTime());
        LocalTime end = LocalTime.parse(appointmentRequestDTO.getEndTime());

        // checking that the requested slot is 1 hr
        if (!end.equals(start.plusHours(1))) {
            throw new Exception("End Time should always be 1 hour more than Start Time");
        }

        // checking slot availability
        List<TimeSlotResponseDTO> availableTimeSlots = timeSlotService.getAvailableTimeSlots(owner.getId(), appointmentRequestDTO.getDate());

        Optional<TimeSlotResponseDTO> matchingSlot = availableTimeSlots.stream().filter(slot -> slot.getStart().equals(start) && slot.getEnd().equals(end)).findFirst();

        if (matchingSlot.isEmpty()) {
            throw new Exception("This Timeslot is not available for booking appointment.");
        }
    }

    /**
     * Fetch upcoming appointments for an owner, limited by the requested size.
     *
     * @param ownerId owner ID
     * @param size max number of appointments to return
     * @return list of AppointmentResponseDTO
     * @throws Exception if owner not found
     */
    public List<AppointmentResponseDTO> getUpcomingAppointments(Long ownerId, int size) throws Exception {
        // validating the owner
        ownerService.validateAndGetOwner(ownerId);

        // creating pageable for agnostic use of limit function
        Pageable pageable = PageRequest.of(0, size);

        // fetch upcoming appointments from the DB
        List<Appointment> appointments = appointmentRepository.findUpcomingAppointments(ownerId, LocalDate.now(), LocalTime.now(), pageable);
        return appointments.stream().map(AppointmentMapper::mapToResponse).toList();
    }

    /**
     * Deletes an existing appointment.
     *
     * @param appointmentId appointment ID
     * @throws Exception if appointment not found
     */
    public void deleteAppointment(Long appointmentId) throws Exception {
        // fetch existing appointment
        Appointment existingAppointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new Exception("Appointment not found with id: " + appointmentId));
        appointmentRepository.delete(existingAppointment);
    }

}
