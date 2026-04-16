package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.image.InstagramImage;
import com.ayrton.socialmedia.model.abstracts.Publication;

import java.time.LocalDateTime;
import java.util.UUID;

public class InstagramImageDialog extends PublicationDialog {

    @Override
    public Publication create(Publication existing, String author, String content, boolean extra, int duration) {

        UUID id = existing != null ? existing.getId() : UUID.randomUUID();

        return new InstagramImage(id, author, content, LocalDateTime.now(), "default.png", extra ? "filter" : "none");
    }
}