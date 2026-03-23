package com.example.health_platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository 
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Checks if a specific date and time slot is already taken.
     * Maps to 'appointment_date' and 'appointment_time' in MySQL.
     */
    boolean existsByAppointmentDateAndAppointmentTime(String appointmentDate, String appointmentTime);
    
    /**
     * Fetches all appointments for a specific date.
     */
    List<Appointment> findByAppointmentDate(String appointmentDate);

    /**
     * Fetches appointments for a specific patient name (Exact Match).
     */
    List<Appointment> findByPatientName(String patientName);

    /**
     * Search for patients by name (Case Insensitive / Partial Match).
     */
    List<Appointment> findByPatientNameContainingIgnoreCase(String patientName);

    /**
     * Fetches all appointments for a specific doctor.
     */
    List<Appointment> findByDoctorName(String doctorName);

    /**
     * NEW: Fetches all appointments by medical specialty.
     * Useful for seeing all "Heart Specialist" or "Eye Specialist" patients.
     */
    List<Appointment> findByDoctorSpecialty(String doctorSpecialty);

    /**
     * Filter by Doctor AND Date.
     */
    List<Appointment> findByDoctorNameAndAppointmentDate(String doctorName, String appointmentDate);

    /**
     * NEW: Find by Specialty AND Date.
     * Shows all patients for a specific department on a specific day.
     */
    List<Appointment> findByDoctorSpecialtyAndAppointmentDate(String doctorSpecialty, String appointmentDate);
}