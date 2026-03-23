package com.example.health_platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class HealthController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * Get appointments for a specific doctor.
     * URL: http://localhost:8080/api/doctor/appointments?doctor=Dr. Rohan
     */
    @GetMapping("/doctor/appointments")
    public List<Appointment> getAppointments(@RequestParam String doctor) {
        return appointmentRepository.findByDoctorName(doctor);
    }

    /**
     * NEW: Get appointments filtered by specialty.
     * URL: http://localhost:8080/api/specialty?type=Eye Specialist
     */
    @GetMapping("/specialty")
    public List<Appointment> getBySpecialty(@RequestParam String type) {
        return appointmentRepository.findByDoctorSpecialty(type);
    }

    /**
     * Book an appointment. 
     * Now includes doctorName and doctorSpecialty in the saved data.
     */
    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody Appointment appointment) {
        // Check for double booking
        boolean isTaken = appointmentRepository.existsByAppointmentDateAndAppointmentTime(
            appointment.getAppointmentDate(), 
            appointment.getAppointmentTime()
        );

        if (isTaken) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("This time slot is already booked.");
        }

        // Save appointment (doctorSpecialty is automatically saved if present in JSON)
        Appointment saved = appointmentRepository.save(appointment);
        return ResponseEntity.ok(saved);
    }

    /**
     * Update treatment details (Prescription and Report).
     */
    @PostMapping("/update/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment updateData) {
        Appointment a = appointmentRepository.findById(id).orElseThrow();
        a.setPrescription(updateData.getPrescription());
        a.setReport(updateData.getReport());
        a.setStatus("Completed & Notified");
        return appointmentRepository.save(a);
    }
}