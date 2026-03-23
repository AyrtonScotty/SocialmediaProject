package com.ayrton.socialmedia.model.text;

import com.ayrton.socialmedia.model.abstracts.TextPublication;

import java.time.LocalDateTime;
import java.util.UUID;

public class ThreadsText extends TextPublication {

    private boolean isReply;

    public ThreadsText() {}

    public ThreadsText(UUID id, String author, String content,
                       LocalDateTime createdAt, boolean isReply) {
        super(id, author, content, createdAt, false); // ThreadsText n’a pas de "partage", on met false
        this.isReply = isReply;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    @Override
    public boolean isValid() {
        // Pour ThreadsText, aucune règle supplémentaire pour l’instant
        return super.isValid();
    }

    @Override
    public String toString() {
        return super.toString() + "\nIs Reply: " + isReply;
    }
}