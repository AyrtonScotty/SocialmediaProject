package com.ayrton.socialmedia.model.image;

import com.ayrton.socialmedia.model.abstracts.ImagePublication;

import java.time.LocalDateTime;
import java.util.UUID;

public class FacebookImage extends ImagePublication {

    private boolean hasTagPeople;

    public FacebookImage() {}

    public FacebookImage(UUID id, String author, String content,
                         LocalDateTime createdAt,
                         String imageUrl, boolean hasTagPeople) {

        super(id, author, content, createdAt, imageUrl);
        this.hasTagPeople = hasTagPeople;
    }

    public boolean isHasTagPeople() {
        return hasTagPeople;
    }

    public void setHasTagPeople(boolean hasTagPeople) {
        this.hasTagPeople = hasTagPeople;
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    public String toInfo() {
        return super.toInfo() + "\nHas Tag People: " + hasTagPeople;
    }
}