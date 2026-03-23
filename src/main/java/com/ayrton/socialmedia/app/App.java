package com.ayrton.socialmedia.app;

import com.ayrton.socialmedia.model.abstracts.Publication;
import com.ayrton.socialmedia.model.text.*;
import com.ayrton.socialmedia.model.image.*;
import com.ayrton.socialmedia.model.video.*;
import com.ayrton.socialmedia.model.longvideo.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class App {

    public static void main(String[] args) {

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
        // ✅ Correction appliquée : correspond au constructeur actuel de YouTubeLongVideo
        publications.add(new YouTubeLongVideo(
                UUID.randomUUID(),
                "ProYT",
                "YouTube long video",
                LocalDateTime.now(),
                1800,  // duration en secondes
                180,
                true   // hasAds / monetized
        ));

        // ─── AFFICHAGE ───────────────────────────
        System.out.println("===== VALIDATION DES PUBLICATIONS =====\n");

        for (Publication pub : publications) {
            System.out.println(pub.toInfo());
            System.out.println("--------------------------------");

            if (pub.isValid()) {
                System.out.println("✅ Statut : VALIDE");
                System.out.println("Raison : Toutes les règles de validation sont respectées.");
            } else {
                System.out.println("❌ Statut : INVALIDE");
                System.out.println("Raisons :");
                for (String err : pub.getValidationErrors()) {
                    System.out.println(" - " + err);
                }
            }

            System.out.println("--------------------------------\n");
        }
    }
}