package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByOwnerIdAndDateAndStartTimeAndEndTime(Long ownerId, LocalDate date, LocalTime startTime, LocalTime endTime);
    List<Appointment> findByOwnerIdAndDate(Long ownerId, LocalDate date);
}
