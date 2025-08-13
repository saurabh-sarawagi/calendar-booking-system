package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // boolean existsByOwnerIdAndDateAndStartTimeAndEndTime(Long ownerId, LocalDate date, LocalTime startTime, LocalTime endTime);

    List<Appointment> findByOwnerIdAndDate(Long ownerId, LocalDate date);

    @Query("SELECT a FROM Appointment a WHERE a.owner.id = :ownerId AND (a.date > :today OR (a.date = :today AND a.startTime >= :currentTime)) ORDER BY a.date, a.startTime")
    List<Appointment> findUpcomingAppointments(@Param("ownerId") Long ownerId, @Param("today") LocalDate today, @Param("currentTime") java.time.LocalTime currentTime);
}
