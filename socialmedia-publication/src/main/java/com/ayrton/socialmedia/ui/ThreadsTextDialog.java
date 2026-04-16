package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.text.ThreadsText;
import com.ayrton.socialmedia.model.abstracts.Publication;

import java.time.LocalDateTime;
import java.util.UUID;

public class ThreadsTextDialog extends PublicationDialog {

    @Override
    public Publication create(Publication existing, String author, String content, boolean extra, int duration) {

        UUID id = existing != null ? existing.getId() : UUID.randomUUID();

        return new ThreadsText(id, author, content, LocalDateTime.now(), extra);
    }
}