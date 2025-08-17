package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.Owner;

import java.time.LocalTime;

/**
 * Mapper class for converting between Appointment entity and DTOs.
 * All methods are static and stateless.
 */
public class AppointmentMapper {

    /**
     * Converts AppointmentRequestDTO to Appointment entity.
     *
     * @param appointmentRequestDTO the request DTO
     * @param owner                 the owner entity
     * @return mapped Appointment entity
     */
    public static Appointment mapToEntity(AppointmentRequestDTO appointmentRequestDTO, Owner owner) {
        Appointment appointment = new Appointment();
        appointment.setOwner(owner);
        appointment.setInviteeName(appointmentRequestDTO.getInviteeName());
        appointment.setInviteeEmail(appointmentRequestDTO.getInviteeEmail());
        appointment.setDate(appointmentRequestDTO.getDate());
        appointment.setStartTime(LocalTime.parse(appointmentRequestDTO.getStartTime()));
        appointment.setEndTime(LocalTime.parse(appointmentRequestDTO.getEndTime()));
        return appointment;
    }

    /**
     * Converts Appointment entity to AppointmentResponseDTO.
     *
     * @param appointment the Appointment entity
     * @return mapped response DTO
     */
    public static AppointmentResponseDTO mapToResponse(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getOwner().getId(),
                appointment.getInviteeName(),
                appointment.getInviteeEmail(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );
    }

    /**
     * Updates fields of an existing Appointment entity from a DTO.
     * The owner is not updated.
     *
     * @param appointment           the Appointment entity to update
     * @param appointmentRequestDTO the DTO with new values
     */
    public static void updateEntity(Appointment appointment, AppointmentRequestDTO appointmentRequestDTO) {
        // Update the appointment fields - owner won't be updated
        appointment.setDate(appointmentRequestDTO.getDate());
        appointment.setStartTime(LocalTime.parse(appointmentRequestDTO.getStartTime()));
        appointment.setEndTime(LocalTime.parse(appointmentRequestDTO.getEndTime()));
        appointment.setInviteeName(appointmentRequestDTO.getInviteeName());
        appointment.setInviteeEmail(appointmentRequestDTO.getInviteeEmail());
    }
}
