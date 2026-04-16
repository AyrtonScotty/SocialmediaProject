package com.ayrton.socialmedia.model.video;

import com.ayrton.socialmedia.model.abstracts.VideoPublication;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TikTokVideo extends VideoPublication {

    private boolean hasEffects;

    public TikTokVideo() {}
    
    public TikTokVideo(UUID id, String author, String content, LocalDateTime createdAt, int duration, boolean hasEffects) {
        super(id, author, content, createdAt, duration);
        this.hasEffects = hasEffects;
    }

    public boolean isHasEffects() { return hasEffects; }
    public void setHasEffects(boolean hasEffects) { this.hasEffects = hasEffects; }

    @Override
    public boolean isValid() { return getValidationErrors().isEmpty(); }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        errors.addAll(super.getValidationErrors());

        if (getDuration() <= 0 || getDuration() > 120)
            errors.add("La durée doit être entre 1 et 120 secondes pour TikTok.");
        

        return errors;
    }

    @Override
    public String toInfo() {
        return super.toInfo() + "\nHas Effects: " + hasEffects;
    }
}