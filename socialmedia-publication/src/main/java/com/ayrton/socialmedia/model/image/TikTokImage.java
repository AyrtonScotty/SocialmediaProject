package com.ayrton.socialmedia.model.image;

import com.ayrton.socialmedia.model.abstracts.ImagePublication;

import java.time.LocalDateTime;
import java.util.UUID;

public class TikTokImage extends ImagePublication {

    private boolean hasMusic;

    public TikTokImage() {}

    public TikTokImage(UUID id, String author, String content,
                       LocalDateTime createdAt,
                       String imageUrl, boolean hasMusic) {

        super(id, author, content, createdAt, imageUrl);
        this.hasMusic = hasMusic;
    }

    public boolean isHasMusic() {
        return hasMusic;
    }

    public void setHasMusic(boolean hasMusic) {
        this.hasMusic = hasMusic;
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    public String toInfo() {
        return super.toInfo() + "\nHas Music: " + hasMusic;
    }
}