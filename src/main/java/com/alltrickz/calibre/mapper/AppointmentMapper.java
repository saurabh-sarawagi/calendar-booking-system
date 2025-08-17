package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.Owner;

import java.time.LocalTime;

public class AppointmentMapper {

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

    public static void updateEntity(Appointment appointment, AppointmentRequestDTO appointmentRequestDTO) {
        // Update the appointment fields - owner won't be updated
        appointment.setDate(appointmentRequestDTO.getDate());
        appointment.setStartTime(LocalTime.parse(appointmentRequestDTO.getStartTime()));
        appointment.setEndTime(LocalTime.parse(appointmentRequestDTO.getEndTime()));
        appointment.setInviteeName(appointmentRequestDTO.getInviteeName());
        appointment.setInviteeEmail(appointmentRequestDTO.getInviteeEmail());
    }


}
