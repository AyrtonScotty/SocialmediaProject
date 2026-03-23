package com.ayrton.socialmedia.model.abstracts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TextPublication extends Publication {

    protected boolean partage;
    protected int maxLength; // ✅ Attribut pour limiter la longueur du texte

    
    
    public TextPublication() {
        this.maxLength = 500; // valeur par défaut
    }

    
    public TextPublication(UUID id, String author, String content, LocalDateTime createdAt, boolean partage) {
        super(id, author, content, createdAt);
        this.partage = partage;
        this.maxLength = 500;
    }

    public boolean isPartage() { return partage; }
    public void setPartage(boolean partage) { this.partage = partage; }

    public int getMaxLength() { return maxLength; } // ✅ Getter nécessaire pour ThreadsText
    public void setMaxLength(int maxLength) { this.maxLength = maxLength; }

    @Override
    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>(super.getValidationErrors());
        if (getContent() != null && getContent().length() > maxLength) {
            errors.add("Le texte dépasse le nombre maximal de caractères (" + maxLength + ").");
        }
        return errors;
    }
}