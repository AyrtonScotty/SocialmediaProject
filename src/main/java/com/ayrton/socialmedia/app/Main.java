package com.ayrton.socialmedia.app;

import com.ayrton.socialmedia.model.abstracts.Publication;
import com.ayrton.socialmedia.model.text.*;
import com.ayrton.socialmedia.model.image.*;
import com.ayrton.socialmedia.model.video.*;
import com.ayrton.socialmedia.model.longvideo.*;
import com.ayrton.socialmedia.repository.PublicationFileRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

    	PublicationFileRepository repo = PublicationFileRepository.getInstance();

        // ─── Création des publications ───────────────────────────────
        List<Publication> publications = new ArrayList<>();

        // ─── TEXTES ───────────────────────────────
        publications.add(new FacebookText(
                UUID.randomUUID(),
                "Alice",
                "Facebook text post #hello",
                LocalDateTime.now(),
                true,
                5
        ));

        publications.add(new ThreadsText(
                UUID.randomUUID(),
                "Bob",
                "Threads text post",
                LocalDateTime.now(),
                false
        ));

        // ─── IMAGES ──────────────────────────────
        publications.add(new FacebookImage(
                UUID.randomUUID(),
                "Alice",
                "FB Image",
                LocalDateTime.now(),
                "http://fb.jpg",
                true
        ));

        publications.add(new InstagramImage(
                UUID.randomUUID(),
                "Bob",
                "Insta Image",
                LocalDateTime.now(),
                "http://insta.png",
                "Clarendon"
        ));

        publications.add(new TikTokImage(
                UUID.randomUUID(),
                "Chris",
                "TikTok Image",
                LocalDateTime.now(),
                "http://tt.jpg",
                true
        ));

        publications.add(new ThreadsImage(
                UUID.randomUUID(),
                "Dana",
                "Threads Image",
                LocalDateTime.now(),
                "http://th.png",
                true
        ));

        // ─── VIDÉOS ──────────────────────────────
        publications.add(new FacebookVideo(
                UUID.randomUUID(),
                "Alice",
                "FB Video",
                LocalDateTime.now(),
                120,
                true
        ));

        publications.add(new InstagramVideo(
                UUID.randomUUID(),
                "Bob",
                "Insta Reel",
                LocalDateTime.now(),
                60,
                true
        ));

        publications.add(new TikTokVideo(
                UUID.randomUUID(),
                "Dana",
                "TikTok Video",
                LocalDateTime.now(),
                30,
                true
        ));

        publications.add(new ThreadsVideo(
                UUID.randomUUID(),
                "Eve",
                "Threads Video",
                LocalDateTime.now(),
                45,
                true
        ));

        // ─── LONG VIDEOS ─────────────────────────
        publications.add(new YouTubeLongVideo(
                UUID.randomUUID(),
                "ProYT",
                "YouTube long video",
                LocalDateTime.now(),              
                1800,  // duration en secondes
                180,
                true   // monetized
        ));

        // ─── Sauvegarde de toutes les publications ───────────────────────────────
        for (Publication pub : publications) {
            try {
                repo.save(pub);  // 🔹 chaque publication devient un fichier JSON dans le dossier 'publication'
            } catch (Exception e) {
                System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
            }
        }

        // ─── Chargement et affichage de toutes les publications du dossier ───────────────────────────────
        System.out.println("===== PUBLICATIONS CHARGÉES DU REPOSITORY =====\n");
        List<Publication> loaded = repo.loadAll();  // 🔹 loadAll() gère les erreurs de lecture
        for (Publication pub : loaded) {
            System.out.println(pub.toInfo());
            System.out.println("--------------------------------\n");
        }
    }
}