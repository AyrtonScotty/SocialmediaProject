package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.abstracts.Publication;
import com.ayrton.socialmedia.model.text.*;
import com.ayrton.socialmedia.model.image.*;
import com.ayrton.socialmedia.model.video.*;
import com.ayrton.socialmedia.model.longvideo.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class PublicationEditorDialog {

    public static Publication createPublication(
            String type,
            Publication existing,
            String author,
            String content,
            boolean extra,
            int duration
    ) {

        UUID id = existing != null ? existing.getId() : UUID.randomUUID();

        switch (type) {
            case "FacebookText":
                return new FacebookText(id, author, content, LocalDateTime.now(), extra, 100);

            case "ThreadsText":
                return new ThreadsText(id, author, content, LocalDateTime.now(), extra);

            case "FacebookImage":
                return new FacebookImage(id, author, content, LocalDateTime.now(), "default.png", extra);

            case "InstagramImage":
                return new InstagramImage(id, author, content, LocalDateTime.now(), "default.png", extra ? "filter" : "none");

            case "TikTokImage":
                return new TikTokImage(id, author, content, LocalDateTime.now(), "default.png", extra);

            case "ThreadsImage":
                return new ThreadsImage(id, author, content, LocalDateTime.now(), "default.png", extra);

            case "FacebookVideo":
                return new FacebookVideo(id, author, content, LocalDateTime.now(), duration, extra);

            case "InstagramVideo":
                return new InstagramVideo(id, author, content, LocalDateTime.now(), duration, extra);

            case "TikTokVideo":
                return new TikTokVideo(id, author, content, LocalDateTime.now(), duration, extra);

            case "ThreadsVideo":
                return new ThreadsVideo(id, author, content, LocalDateTime.now(), duration, extra);

            case "YouTubeLongVideo":
                return new YouTubeLongVideo(id, author, content, LocalDateTime.now(), duration, 120, extra);

            default:
                return null;
        }
    }
}