package com.ayrton.socialmedia.model.abstracts;

import java.time.LocalDateTime;
import java.util.UUID;

public class ImagePublication extends Publication {

    private String imageUrl;

    public ImagePublication() {}

    // ✅ CONSTRUCTEUR COMPLET
    public ImagePublication(UUID id, String author, String content,
                            LocalDateTime createdAt, String imageUrl) {

        super(id, author, content, createdAt);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean isValid() {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }
        return getValidationErrors().isEmpty();
    }
}