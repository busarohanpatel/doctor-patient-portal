package com.example.health_platform;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class AiController {

    @PostMapping("/analyze")
    public Map<String, String> analyzeSymptoms(@RequestBody Map<String, String> request) {
        String symptoms = request.get("symptoms").toLowerCase();
        String advice;

        // Simple AI Logic (You can replace this with an API call later)
        if (symptoms.contains("chest pain") || symptoms.contains("breathless")) {
            advice = "URGENT: These symptoms may relate to Heart issues. Please consult a Heart Specialist immediately.";
        } else if (symptoms.contains("eye") || symptoms.contains("blurry")) {
            advice = "SUGGESTION: Possible vision issue. We recommend booking an appointment with our Eye Specialist.";
        } else if (symptoms.contains("fever") || symptoms.contains("cough")) {
            advice = "ADVICE: Common flu symptoms detected. Consult a General Physician and stay hydrated.";
        } else {
            advice = "AI Analysis: Symptoms recorded. A doctor will review this during your appointment.";
        }

        return Map.of("advice", advice);
    }
}