package com.alltrickz.calibre.dao;

import com.alltrickz.calibre.entity.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for Appointment entity.
 * Provides methods to fetch appointments based on owner and date.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Fetch all appointments for a given owner on a specific date.
     *
     * @param ownerId the owner's ID
     * @param date    the appointment date
     * @return list of appointments
     */
    List<Appointment> findByOwnerIdAndDate(Long ownerId, LocalDate date);

    /**
     * Fetch upcoming appointments for a given owner.
     * Only appointments after today or later today with start time >= currentTime are returned.
     * Supports pagination using Pageable.
     *
     * @param ownerId     the owner's ID
     * @param today       current date
     * @param currentTime current time
     * @param pageable    pagination
     * @return list of upcoming appointments
     */
    @Query("SELECT a FROM Appointment a WHERE a.owner.id = :ownerId AND (a.date > :today OR (a.date = :today AND a.startTime >= :currentTime)) ORDER BY a.date, a.startTime")
    List<Appointment> findUpcomingAppointments(@Param("ownerId") Long ownerId, @Param("today") LocalDate today, @Param("currentTime") java.time.LocalTime currentTime, Pageable pageable);
}
