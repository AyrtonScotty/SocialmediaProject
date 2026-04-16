package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.video.InstagramVideo;
import com.ayrton.socialmedia.model.abstracts.Publication;

import java.time.LocalDateTime;
import java.util.UUID;

public class InstagramVideoDialog extends PublicationDialog {

    @Override
    public Publication create(Publication existing, String author, String content, boolean extra, int duration) {

        UUID id = existing != null ? existing.getId() : UUID.randomUUID();

        return new InstagramVideo(id, author, content, LocalDateTime.now(), duration, extra);
    }
}