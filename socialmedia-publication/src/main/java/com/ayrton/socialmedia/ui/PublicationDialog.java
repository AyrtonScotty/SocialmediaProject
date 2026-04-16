package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.abstracts.Publication;

public abstract class PublicationDialog {

    public abstract Publication create(
            Publication existing,
            String author,
            String content,
            boolean extra,
            int duration
    );
}