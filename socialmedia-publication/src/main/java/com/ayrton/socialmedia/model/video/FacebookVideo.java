package com.ayrton.socialmedia.model.video;

import com.ayrton.socialmedia.model.abstracts.VideoPublication;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FacebookVideo extends VideoPublication {

    private boolean hasCaption;

    public FacebookVideo() {}
    // ✅ Constructeur complet
    public FacebookVideo(UUID id, String author, String content, LocalDateTime createdAt, int duration, boolean hasCaption) {
        super(id, author, content, createdAt, duration);
        this.hasCaption = hasCaption;
    }

    public boolean isHasCaption() { return hasCaption; }
    public void setHasCaption(boolean hasCaption) { this.hasCaption = hasCaption; }

    @Override
    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        errors.addAll(super.getValidationErrors());

        if (getDuration() <= 0 || getDuration() > 120)
            errors.add("La durée doit être entre 1 et 120 secondes.");
        

        return errors;
    }

    @Override
    public String toInfo() {
        return super.toInfo() + "\nHas Caption: " + hasCaption;
    }
}