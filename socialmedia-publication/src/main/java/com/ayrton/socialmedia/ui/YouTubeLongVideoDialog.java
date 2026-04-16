package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.longvideo.YouTubeLongVideo;
import com.ayrton.socialmedia.model.abstracts.Publication;

import java.time.LocalDateTime;
import java.util.UUID;

public class YouTubeLongVideoDialog extends PublicationDialog {

    @Override
    public Publication create(Publication existing, String author, String content, boolean extra, int duration) {

        UUID id = existing != null ? existing.getId() : UUID.randomUUID();

        return new YouTubeLongVideo(id, author, content, LocalDateTime.now(), duration, 120, extra);
    }
}