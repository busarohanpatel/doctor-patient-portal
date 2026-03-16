package com.example.health_platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@RestController
@RequestMapping("/api") 
// Adding @CrossOrigin here as a second layer of defense for port 3001
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class HealthController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * UPDATED GLOBAL CORS FIX: 
     * Now explicitly allows both 3000 and 3001 to stop the red console errors.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000", "http://localhost:3001")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    // This endpoint works for both DoctorDashboard.js and PatientStatus.js
    // URL: http://localhost:8080/api/doctor/appointments
    @GetMapping("/doctor/appointments")
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    // URL: http://localhost:8080/api/book
    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody Appointment appointment) {
        boolean isTaken = appointmentRepository.existsByAppointmentDateAndAppointmentTime(
            appointment.getAppointmentDate(), 
            appointment.getAppointmentTime()
        );

        if (isTaken) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("This time slot is already booked.");
        }

        Appointment saved = appointmentRepository.save(appointment);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/update/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment updateData) {
        Appointment a = appointmentRepository.findById(id).orElseThrow();
        a.setPrescription(updateData.getPrescription());
        a.setReport(updateData.getReport());
        a.setStatus("Completed & Notified");
        return appointmentRepository.save(a);
    }
}