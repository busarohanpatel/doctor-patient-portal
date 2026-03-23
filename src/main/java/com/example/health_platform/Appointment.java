package com.example.health_platform;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "appointments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"appointment_date", "appointment_time"})
})
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_name")
    @JsonProperty("patientName")
    private String patientName;

    @Column(name = "phone_number") 
    @JsonProperty("phoneNumber")
    private String phoneNumber; 

    private String symptoms;
    
    @Column(name = "appointment_date")
    @JsonProperty("appointmentDate")
    private String appointmentDate;

    @Column(name = "appointment_time")
    @JsonProperty("appointmentTime")
    private String appointmentTime; 
    
    @Column(name = "doctor_name")
    @JsonProperty("doctorName")
    private String doctorName; 

    // --- NEW FIELD FOR SPECIALTY ---
    @Column(name = "doctor_specialty")
    @JsonProperty("doctorSpecialty")
    private String doctorSpecialty; 

    private String status = "Pending Review";
    private String prescription = "Awaiting Doctor";
    private String report = "None";

    public Appointment() {}

    // Updated Constructor to include specialty
    public Appointment(String patientName, String phoneNumber, String symptoms, String appointmentDate, String appointmentTime, String doctorName, String doctorSpecialty) {
        this.patientName = patientName;
        this.phoneNumber = phoneNumber;
        this.symptoms = symptoms;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.doctorName = doctorName;
        this.doctorSpecialty = doctorSpecialty;
    }

    // Existing Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPhoneNumber() { return phoneNumber; } 
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getReport() { return report; }
    public void setReport(String report) { this.report = report; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    // --- NEW Getter and Setter for Doctor Specialty ---
    public String getDoctorSpecialty() { return doctorSpecialty; }
    public void setDoctorSpecialty(String doctorSpecialty) { this.doctorSpecialty = doctorSpecialty; }
}