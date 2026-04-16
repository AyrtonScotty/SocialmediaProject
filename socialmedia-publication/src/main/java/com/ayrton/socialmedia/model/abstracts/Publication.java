package com.ayrton.socialmedia.model.abstracts;

import com.ayrton.socialmedia.model.text.*;
import com.ayrton.socialmedia.model.image.*;
import com.ayrton.socialmedia.model.video.*;
import com.ayrton.socialmedia.model.longvideo.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)

@JsonSubTypes({

        // TEXT
        @JsonSubTypes.Type(value = FacebookText.class, name = "FacebookText"),
        @JsonSubTypes.Type(value = ThreadsText.class, name = "ThreadsText"),

        // IMAGE
        @JsonSubTypes.Type(value = FacebookImage.class, name = "FacebookImage"),
        @JsonSubTypes.Type(value = InstagramImage.class, name = "InstagramImage"),
        @JsonSubTypes.Type(value = TikTokImage.class, name = "TikTokImage"),
        @JsonSubTypes.Type(value = ThreadsImage.class, name = "ThreadsImage"),

        // VIDEO
        @JsonSubTypes.Type(value = FacebookVideo.class, name = "FacebookVideo"),
        @JsonSubTypes.Type(value = InstagramVideo.class, name = "InstagramVideo"),
        @JsonSubTypes.Type(value = TikTokVideo.class, name = "TikTokVideo"),
        @JsonSubTypes.Type(value = ThreadsVideo.class, name = "ThreadsVideo"),

        // LONG VIDEO
        @JsonSubTypes.Type(value = YouTubeLongVideo.class, name = "YouTubeLongVideo")
})

public abstract class Publication {

    protected UUID id;
    protected String author;
    protected String content;
    protected LocalDateTime createdAt;

    // 🔥 OBLIGATOIRE POUR JACKSON
    public Publication() {}

    public Publication(UUID id, String author, String content, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    // GETTERS / SETTERS
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // VALIDATION
    public abstract boolean isValid();

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (id == null) errors.add("L'ID est manquant.");
        if (author == null || author.isEmpty()) errors.add("L'auteur est vide.");
        if (content == null || content.isEmpty()) errors.add("Le contenu est vide.");
        if (createdAt == null) errors.add("La date de création est manquante.");

        return errors;
    }

    // AFFICHAGE
    public String toInfo() {
        return "ID: " + id +
                "\nAuthor: " + author +
                "\nContent: " + content +
                "\nCreatedAt: " + createdAt;
    }
}