package com.ayrton.socialmedia.model.video;

import com.ayrton.socialmedia.model.abstracts.VideoPublication;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ThreadsVideo extends VideoPublication {

    private boolean hasThreadLink;

    public ThreadsVideo() {}
    
    public ThreadsVideo(UUID id, String author, String content, LocalDateTime createdAt, int duration, boolean hasThreadLink) {
        super(id, author, content, createdAt, duration);
        this.hasThreadLink = hasThreadLink;
    }

    public boolean isHasThreadLink() { return hasThreadLink; }
    public void setHasThreadLink(boolean hasThreadLink) { this.hasThreadLink = hasThreadLink; }

    @Override
    public boolean isValid() { return getValidationErrors().isEmpty(); }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        errors.addAll(super.getValidationErrors());

        if (getDuration() <= 0 || getDuration() > 300)
            errors.add("La durée doit être entre 1 et 300 secondes pour Threads.");
        

        return errors;
    }

    @Override
    public String toInfo() {
        return super.toInfo() + "\nHas Thread Link: " + hasThreadLink;
    }
}