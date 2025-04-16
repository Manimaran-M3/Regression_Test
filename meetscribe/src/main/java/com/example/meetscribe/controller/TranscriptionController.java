package com.example.meetscribe.controller;

import com.example.meetscribe.service.TranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class TranscriptionController {

    @Autowired
    private TranscriptionService transcriptionService;

    // Endpoint to start the recording
    @GetMapping("/start")
    public String startRecording() {
        try {
            return transcriptionService.startRecording();
        } catch (Exception e) {
            return "Failed to start recording: " + e.getMessage();
        }
    }

    // Endpoint to stop the recording
    @GetMapping("/stop")
    public String stopRecording() {
        return transcriptionService.stopRecording();
    }

    // Endpoint to transcribe the recorded audio
    @GetMapping("/transcribe")
    public String transcribe() {
        return transcriptionService.transcribeAudio();
    }
}
