package com.ayrton.socialmedia.model.image;

import com.ayrton.socialmedia.model.abstracts.ImagePublication;

import java.time.LocalDateTime;
import java.util.UUID;

public class InstagramImage extends ImagePublication {

    private String filter;

    public InstagramImage() {}

    public InstagramImage(UUID id, String author, String content,
                          LocalDateTime createdAt, String imageUrl, String filter) {

        super(id, author, content, createdAt, imageUrl);
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public boolean isValid() {
        if (filter == null || filter.isEmpty()) {
            return false;
        }
        return super.isValid();
    }
}