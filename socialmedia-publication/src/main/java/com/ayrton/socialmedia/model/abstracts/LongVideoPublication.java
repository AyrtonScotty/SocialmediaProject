package com.ayrton.socialmedia.model.abstracts;

import java.time.LocalDateTime;
import java.util.UUID;
import com.ayrton.socialmedia.model.abstracts.VideoPublication;

/**
 * Classe abstraite pour toutes les vidéos longues.
 * Contient les attributs et comportements communs.
 */
public abstract class LongVideoPublication extends VideoPublication {

    // Longueur minimale en secondes
    private int minLength;
    
    public LongVideoPublication() {}

    // Constructeur adapté à UUID et LocalDateTime
    public LongVideoPublication(UUID id, String author, String content,
                                LocalDateTime createdAt, int duration, int minLength) {
        super(id, author, content, createdAt, duration);
        this.minLength = minLength;
    }

    // Getter/Setter
    public int getMinLength() { return minLength; }
    public void setMinLength(int minLength) { this.minLength = minLength; }

    // Méthode pour validation longueur minimale
    protected boolean validateLongVideo() {
        return getDuration() >= minLength;
    }

    // Validation commune
    protected boolean validateVideoCommon() {
        return getContent() != null && !getContent().isEmpty() && getDuration() > 0;
    }

}