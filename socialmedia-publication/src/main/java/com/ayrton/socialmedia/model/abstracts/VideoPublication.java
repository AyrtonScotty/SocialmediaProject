package com.ayrton.socialmedia.model.abstracts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class VideoPublication extends Publication {

    private int duration;      // en secondes
    private String videoUrl;   // URL facultative ou obligatoire selon sous-classe

    // 🔥 Obligatoire pour Jackson
    public VideoPublication() {}

    // ✅ Constructeur de base (sans URL)
    public VideoPublication(UUID id, String author, String content, LocalDateTime createdAt, int duration) {
        super(id, author, content, createdAt);
        this.duration = duration;
    }

    // ✅ Constructeur complet (avec URL) pour LongVideoPublication
    public VideoPublication(UUID id, String author, String content, LocalDateTime createdAt, String videoUrl, int duration) {
        super(id, author, content, createdAt);
        this.videoUrl = videoUrl;
        this.duration = duration;
    }

    // GETTERS / SETTERS
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    // Validation
    @Override
    public boolean isValid() {
        if (duration <= 0) return false;
        return getValidationErrors().isEmpty();
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>(super.getValidationErrors());

        if (duration <= 0) {
            errors.add("La durée doit être supérieure à 0.");
        }
        if (videoUrl != null && videoUrl.isEmpty()) {
            errors.add("L'URL de la vidéo ne peut pas être vide si elle est définie.");
        }

        return errors;
    }

    @Override
    public String toInfo() {
        return super.toInfo() +
               "\nDuration: " + duration + " sec" +
               (videoUrl != null ? "\nVideo URL: " + videoUrl : "");
    }
}