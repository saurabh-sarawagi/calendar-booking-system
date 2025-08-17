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

    @Transactional
    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        Owner owner = ownerService.validateAndGetOwner(appointmentRequestDTO.getOwnerId());
        validateAppointmentRequest(appointmentRequestDTO, owner);

        // save the appointment
        try {
            Appointment appointment = AppointmentMapper.mapToEntity(appointmentRequestDTO, owner);
            Appointment savedAppointment = appointmentRepository.saveAndFlush(appointment);
            return AppointmentMapper.mapToResponse(savedAppointment);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Time slot already booked. Please pick another.");
        }

    }

    public AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new Exception("Appointment not found with id: " + appointmentId));
        Owner owner = ownerService.validateAndGetOwner(existingAppointment.getOwner().getId());
        validateAppointmentRequest(appointmentRequestDTO, owner);
        AppointmentMapper.updateEntity(existingAppointment, appointmentRequestDTO);
        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
        return AppointmentMapper.mapToResponse(savedAppointment);
    }

    private void validateAppointmentRequest(AppointmentRequestDTO appointmentRequestDTO, Owner owner) throws Exception {
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

    public List<AppointmentResponseDTO> getUpcomingAppointments(Long ownerId, int size) throws Exception {
        ownerService.validateAndGetOwner(ownerId);
        Pageable pageable = PageRequest.of(0, size);
        List<Appointment> appointments = appointmentRepository.findUpcomingAppointments(ownerId, LocalDate.now(), LocalTime.now(), pageable);
        return appointments.stream().map(AppointmentMapper::mapToResponse).toList();
    }

    public void deleteAppointment(Long appointmentId) throws Exception {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new Exception("Appointment not found with id: " + appointmentId));
        appointmentRepository.delete(existingAppointment);
    }

}
