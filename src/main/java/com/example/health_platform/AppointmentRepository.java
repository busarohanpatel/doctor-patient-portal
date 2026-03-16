package com.example.health_platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository 
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Checks if a specific date and time slot is already taken.
     * Spring Data JPA will automatically handle the mapping to 
     * 'appointment_date' and 'appointment_time' columns.
     */
    boolean existsByAppointmentDateAndAppointmentTime(String appointmentDate, String appointmentTime);
    
    /**
     * Fetches all appointments for a specific date. 
     * Useful for checking availability on the frontend.
     */
    List<Appointment> findByAppointmentDate(String appointmentDate);

    /**
     * Fetches appointments for a specific patient name (Case Sensitive).
     */
    List<Appointment> findByPatientName(String patientName);

    /**
     * Added: Search for patients by name (Case Insensitive / Partial Match).
     * This is very helpful for the Doctor Dashboard search bar.
     */
    List<Appointment> findByPatientNameContainingIgnoreCase(String patientName);
}