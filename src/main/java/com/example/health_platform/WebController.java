package com.example.health_platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "Patient") String role, Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("role");
        if ("Doctor".equals(role)) {
            if (!"Doctor".equals(userRole)) {
                return "redirect:/login?role=Doctor";
            }
        }
        model.addAttribute("role", role);
        
        List<Appointment> appointments;
        if ("Doctor".equals(role)) {
            appointments = appointmentRepository.findAll();
        } else {
            appointments = appointmentRepository.findAll();
        }
        model.addAttribute("appointments", appointments);
        
        return "dashboard";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(defaultValue = "Patient") String role, Model model) {
        model.addAttribute("role", role);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam(defaultValue = "Patient") String role, HttpSession session) {
        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            session.setAttribute("role", userOpt.get().getRole());
            return "redirect:/?role=" + userOpt.get().getRole();
        } else {
            return "redirect:/login?role=" + role + "&error=true";
        }
    }

    @PostMapping("/book")
    public String bookAppointment(@RequestParam String name, @RequestParam String phone, @RequestParam String date, @RequestParam String time, @RequestParam String symptoms) {
        Appointment appointment = new Appointment();
        appointment.setPatientName(name);
        appointment.setPhoneNumber(phone);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setSymptoms(symptoms);
        appointment.setDoctorName("Dr. Default");
        appointment.setDoctorSpecialty("General");
        appointment.setStatus("Pending");
        
        appointmentRepository.save(appointment);
        return "redirect:/?role=Patient";
    }

    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable Long id, @RequestParam String rx, @RequestParam String rpt) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setPrescription(rx);
            appointment.setReport(rpt);
            appointment.setStatus("Completed & Notified");
            appointmentRepository.save(appointment);
        }
        return "redirect:/?role=Doctor";
    }
}