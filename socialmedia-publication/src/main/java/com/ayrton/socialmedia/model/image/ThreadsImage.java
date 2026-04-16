package com.ayrton.socialmedia.model.image;

import com.ayrton.socialmedia.model.abstracts.ImagePublication;

import java.time.LocalDateTime;
import java.util.UUID;

public class ThreadsImage extends ImagePublication {

    private boolean isProfileImage;

    public ThreadsImage() {}

    public ThreadsImage(UUID id, String author, String content,
                        LocalDateTime createdAt,
                        String imageUrl, boolean isProfileImage) {

        super(id, author, content, createdAt, imageUrl);
        this.isProfileImage = isProfileImage;
    }

    public boolean isProfileImage() {
        return isProfileImage;
    }

    public void setProfileImage(boolean profileImage) {
        isProfileImage = profileImage;
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    public String toInfo() {
        return super.toInfo() + "\nIs Profile Image: " + isProfileImage;
    }
}