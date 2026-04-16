package com.ayrton.socialmedia.model.video;

import com.ayrton.socialmedia.model.abstracts.VideoPublication;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstagramVideo extends VideoPublication {

    private boolean isReel;

    public InstagramVideo() {}
    
    public InstagramVideo(UUID id, String author, String content, LocalDateTime createdAt, int duration, boolean isReel) {
        super(id, author, content, createdAt, duration);
        this.isReel = isReel;
    }

    public boolean isReel() { return isReel; }
    public void setReel(boolean reel) { isReel = reel; }

    @Override
    public boolean isValid() { return getValidationErrors().isEmpty(); }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        errors.addAll(super.getValidationErrors());

        if (getDuration() <= 0 || getDuration() > 120)
            errors.add("La durée doit être entre 1 et 120 secondes pour Instagram Reel.");
        

        return errors;
    }

    @Override
    public String toInfo() {
        return super.toInfo() + "\nIs Reel: " + isReel;
    }
}