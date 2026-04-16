 package com.ayrton.socialmedia.repository;

import com.ayrton.socialmedia.model.abstracts.Publication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PublicationFileRepository {

    private static PublicationFileRepository instance;

    private final File folder;
    private final ObjectMapper mapper;

    private PublicationFileRepository(String folderPath) {
        this.folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();

        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.registerSubtypes(
                new NamedType(com.ayrton.socialmedia.model.text.FacebookText.class, "FacebookText"),
                new NamedType(com.ayrton.socialmedia.model.text.ThreadsText.class, "ThreadsText"),
                new NamedType(com.ayrton.socialmedia.model.image.FacebookImage.class, "FacebookImage"),
                new NamedType(com.ayrton.socialmedia.model.image.InstagramImage.class, "InstagramImage"),
                new NamedType(com.ayrton.socialmedia.model.image.TikTokImage.class, "TikTokImage"),
                new NamedType(com.ayrton.socialmedia.model.image.ThreadsImage.class, "ThreadsImage"),
                new NamedType(com.ayrton.socialmedia.model.video.FacebookVideo.class, "FacebookVideo"),
                new NamedType(com.ayrton.socialmedia.model.video.InstagramVideo.class, "InstagramVideo"),
                new NamedType(com.ayrton.socialmedia.model.video.TikTokVideo.class, "TikTokVideo"),
                new NamedType(com.ayrton.socialmedia.model.video.ThreadsVideo.class, "ThreadsVideo"),
                new NamedType(com.ayrton.socialmedia.model.longvideo.YouTubeLongVideo.class, "YouTubeLongVideo")
        );
    }

    // ✅ Singleton
    public static PublicationFileRepository getInstance() {
        if (instance == null) {
            instance = new PublicationFileRepository("publication");
        }
        return instance;
    }

    // ─── SAVE (Exception gérée à l’intérieur) ─────────
    public void save(Publication pub) {
        try {
            File file = new File(folder, pub.getId() + ".json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, pub);
        } catch (IOException e) {
            System.err.println("⚠️ Impossible de sauvegarder " + pub.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ─── LOAD 1 PUBLICATION ───────────
    public Publication load(UUID id) {
        try {
            File file = new File(folder, id + ".json");
            if (!file.exists()) return null;
            return mapper.readValue(file, Publication.class);
        } catch (IOException e) {
            System.err.println("⚠️ Erreur load: " + e.getMessage());
            return null;
        }
    }

    // ─── FIND ALL ─────────────────────
    public List<Publication> findAll() {
        List<Publication> publications = new ArrayList<>();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File f : files) {
                try {
                    Publication pub = mapper.readValue(f, Publication.class);
                    publications.add(pub);
                } catch (IOException e) {
                    System.err.println("⚠️ Impossible de charger " + f.getName());
                }
            }
        }
        return publications;
    }

    // ─── DELETE PAR UUID ──────────────
    public boolean delete(UUID id) {
        File file = new File(folder, id + ".json");
        return file.exists() && file.delete();
    }

    // 🔁 Compatibilité ancien code
    public boolean delete(Publication pub) {
        if (pub == null) return false;
        return delete(pub.getId());
    }

    public List<Publication> loadAll() {
        return findAll();
    }
}