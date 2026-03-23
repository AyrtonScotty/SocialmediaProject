package com.ayrton.socialmedia.model.text;

import com.ayrton.socialmedia.model.abstracts.TextPublication;

import java.time.LocalDateTime;
import java.util.UUID;

public class FacebookText extends TextPublication {

    private int likes;
    private boolean hasHashtags; // ✅ ajouté pour PublicationEditorDialog

    public FacebookText() {}

    public FacebookText(UUID id, String author, String content,
                        LocalDateTime createdAt, boolean hasHashtags, int likes) {
        super(id, author, content, createdAt, false); // 'partage' remplacé par false ou vrai selon ton usage
        this.hasHashtags = hasHashtags;
        this.likes = likes;
    }

    // ─── GETTERS / SETTERS ─────────────────
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean getHasHashtags() {
        return hasHashtags;
    }

    public void setHasHashtags(boolean hasHashtags) {
        this.hasHashtags = hasHashtags;
    }

    // ─── VALIDATION ───────────────────────
    @Override
    public boolean isValid() {
        if (likes < 0) return false;
        return super.isValid();
    }

    // ─── INFOS POUR AFFICHAGE ─────────────
    @Override
    public String toInfo() {
        return super.toInfo() + "\nHashtags : " + hasHashtags ;
    }
}