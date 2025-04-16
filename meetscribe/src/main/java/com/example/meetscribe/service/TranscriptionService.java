package com.example.meetscribe.service;

// public class TranscriptionService {
    
// }


// package com.manimaran.transcription.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TranscriptionService {
    private Process ffmpegProcess;
    private final String OUTPUT_FILE = "record.wav";
    // Update DEVICE_NAME if different on your system
    private final String DEVICE_NAME = "Stereo Mix (Realtek(R) Audio)";

    // Start recording using FFmpeg
    public String startRecording() throws IOException {
        if (ffmpegProcess != null && ffmpegProcess.isAlive()) {
            return "Recording already in progress!";
        }

        ProcessBuilder builder = new ProcessBuilder("ffmpeg", "-f", "dshow", "-i", "audio=" + DEVICE_NAME, OUTPUT_FILE);
        ffmpegProcess = builder.start();
        return "Recording started";
    }

    // Stop recording (terminate FFmpeg)
    public String stopRecording() {
        if (ffmpegProcess != null && ffmpegProcess.isAlive()) {
            // Try a graceful shutdown first
            ffmpegProcess.destroy();
            try {
                // Wait for the process to exit gracefully
                if (!ffmpegProcess.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    // If still alive after timeout, forcefully terminate
                    ffmpegProcess.destroyForcibly();
                    ffmpegProcess.waitFor();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Error stopping recording: interrupted";
            }
            return "Recording stopped";
        }
        return "No active recording";

        // if (ffmpegProcess != null) {
        //     ffmpegProcess.destroy();
        //     return "Recording stopped.";
        // }
        // return "No recording in progress.";
    }

    // Invoke the Python Whisper transcription script and log the transcription
    public String transcribeAudio() {
        File file = new File(OUTPUT_FILE);
        if (!file.exists()) return "Recording file not found";

        try {
            // Call the Python script and pass the output file
            ProcessBuilder pb = new ProcessBuilder("python", "whisper_transcribe.py", OUTPUT_FILE);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder transcription = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                transcription.append(line).append("\n");
            }
            
            // Write the transcription with a timestamp to a text file
            String timestamp = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Transcribed_data.txt", true))) {
                writer.write(timestamp + " " + transcription.toString());
                writer.newLine();
                writer.newLine();
            }
            
            return transcription.toString().trim();
        } catch (IOException e) {
            return "Error during transcription: " + e.getMessage();
        }
    }
}
