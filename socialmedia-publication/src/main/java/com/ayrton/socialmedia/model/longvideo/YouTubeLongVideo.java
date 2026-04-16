package com.ayrton.socialmedia.model.longvideo;

import com.ayrton.socialmedia.model.abstracts.LongVideoPublication;
import java.time.LocalDateTime;
import java.util.UUID;

public class YouTubeLongVideo extends LongVideoPublication {

    private boolean hasAds; // spécifique YouTube
    private String videoUrl; // facultatif, on peut l'ajouter si souhaité

    public YouTubeLongVideo() {}
    
    // ✅ Constructeur correspondant à App.java
    public YouTubeLongVideo(UUID id, String author, String content, LocalDateTime createdAt,
                            int duration, int minLength, boolean hasAds) {
        super(id, author, content, createdAt, duration, minLength);
        this.hasAds = hasAds;
        this.videoUrl = "default.mp4"; // optionnel par défaut
    }

    // Getter / Setter
    public boolean isHasAds() { return hasAds; }
    public void setHasAds(boolean hasAds) { this.hasAds = hasAds; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    @Override
    public boolean isValid() {
        if (!validateVideoCommon()) return false;
        if (!validateLongVideo()) return false;
        if (videoUrl != null && videoUrl.isEmpty()) return false; // optionnel, si URL obligatoire
        return true;
    }

    @Override
    public String toInfo() {
        return super.toInfo() + "\nHas Ads: " + hasAds ;
    }
}