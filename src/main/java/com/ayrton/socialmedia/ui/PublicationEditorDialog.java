package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.abstracts.Publication;
import com.ayrton.socialmedia.model.text.*;
import com.ayrton.socialmedia.model.image.*;
import com.ayrton.socialmedia.model.video.*;
import com.ayrton.socialmedia.model.longvideo.*;

import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Arrays;

public class PublicationEditorDialog {

    // ─── CRÉER UNE NOUVELLE PUBLICATION ─────────────
    public static Publication showAndCreatePublication() {
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Texte", "Texte", "Image", "Vidéo", "LongVideo");
        typeDialog.setTitle("Ajouter Publication");
        typeDialog.setHeaderText("Sélectionnez le type de publication");
        typeDialog.setContentText("Type :");

        return typeDialog.showAndWait().map(PublicationEditorDialog::createPublicationOfType).orElse(null);
    }

    private static Publication createPublicationOfType(String type) {
        return switch (type) {
            case "Texte" -> handleTextPublication(null);
            case "Image" -> handleImagePublication(null);
            case "Vidéo" -> handleVideoPublication(null);
            case "LongVideo" -> handleLongVideoPublication(null);
            default -> null;
        };
    }

    // ─── MODIFIER UNE PUBLICATION EXISTANTE ─────────
    public static Publication showAndEditPublication(Publication pub) {
        if (pub == null) return null;

        if (pub instanceof FacebookText || pub instanceof ThreadsText) {
            return handleTextPublication(pub);
        } else if (pub instanceof FacebookImage || pub instanceof InstagramImage ||
                   pub instanceof TikTokImage || pub instanceof ThreadsImage) {
            return handleImagePublication(pub);
        } else if (pub instanceof FacebookVideo || pub instanceof InstagramVideo ||
                   pub instanceof TikTokVideo || pub instanceof ThreadsVideo) {
            return handleVideoPublication(pub);
        } else if (pub instanceof YouTubeLongVideo) {
            return handleLongVideoPublication(pub);
        } else return null;
    }

    // ─── MÉTHODES PRIVÉES POUR CHAQUE TYPE ─────────
    private static Publication handleTextPublication(Publication existing) {
        String[] choices = {"FacebookText", "ThreadsText"};
        ChoiceDialog<String> classDialog = new ChoiceDialog<>(choices[0], Arrays.asList(choices));
        classDialog.setTitle("Classe de texte");
        classDialog.setHeaderText("Sélectionnez la classe");
        String cls = classDialog.showAndWait().orElse(null);
        if (cls == null) return null;

        TextInputDialog authorDialog = new TextInputDialog(existing != null ? existing.getAuthor() : "");
        authorDialog.setHeaderText("Auteur");
        String author = authorDialog.showAndWait().orElse("");

        TextInputDialog contentDialog = new TextInputDialog(existing != null ? existing.getContent() : "");
        contentDialog.setHeaderText("Contenu");
        String content = contentDialog.showAndWait().orElse("");

        if ("FacebookText".equals(cls)) {
            boolean hasHashtags = existing instanceof FacebookText fb && fb.getHasHashtags();
            TextInputDialog hashtagDialog = new TextInputDialog(Boolean.toString(hasHashtags));
            hashtagDialog.setHeaderText("Hashtags présents ?");
            hasHashtags = Boolean.parseBoolean(hashtagDialog.showAndWait().orElse("true"));

            return new FacebookText(existing != null ? existing.getId() : UUID.randomUUID(),
                    author, content, LocalDateTime.now(), hasHashtags, 500);
        } else {
            boolean isReply = existing instanceof ThreadsText th && th.isReply();
            TextInputDialog replyDialog = new TextInputDialog(Boolean.toString(isReply));
            replyDialog.setHeaderText("Est-ce une réponse ?");
            isReply = Boolean.parseBoolean(replyDialog.showAndWait().orElse("false"));

            return new ThreadsText(existing != null ? existing.getId() : UUID.randomUUID(),
                    author, content, LocalDateTime.now(), isReply);
        }
    }

    private static Publication handleImagePublication(Publication existing) {
        String[] choices = {"FacebookImage", "InstagramImage", "TikTokImage", "ThreadsImage"};
        ChoiceDialog<String> classDialog = new ChoiceDialog<>(choices[0], Arrays.asList(choices));
        classDialog.setTitle("Classe Image");
        classDialog.setHeaderText("Sélectionnez la classe");
        String cls = classDialog.showAndWait().orElse(null);
        if (cls == null) return null;

        TextInputDialog authorDialog = new TextInputDialog(existing != null ? existing.getAuthor() : "");
        authorDialog.setHeaderText("Auteur");
        String author = authorDialog.showAndWait().orElse("");

        TextInputDialog urlDialog = new TextInputDialog("");
        urlDialog.setHeaderText("URL de l'image");
        String url = urlDialog.showAndWait().orElse("");

        return switch (cls) {
            case "FacebookImage" -> new FacebookImage(existing != null ? existing.getId() : UUID.randomUUID(), author, "", LocalDateTime.now(), url, true);
            case "InstagramImage" -> new InstagramImage(existing != null ? existing.getId() : UUID.randomUUID(), author, "", LocalDateTime.now(), url, "None");
            case "TikTokImage" -> new TikTokImage(existing != null ? existing.getId() : UUID.randomUUID(), author, "", LocalDateTime.now(), url, true);
            case "ThreadsImage" -> new ThreadsImage(existing != null ? existing.getId() : UUID.randomUUID(), author, "", LocalDateTime.now(), url, true);
            default -> null;
        };
    }

    private static Publication handleVideoPublication(Publication existing) {
        String[] choices = {"FacebookVideo", "InstagramVideo", "TikTokVideo", "ThreadsVideo"};
        ChoiceDialog<String> classDialog = new ChoiceDialog<>(choices[0], Arrays.asList(choices));
        classDialog.setTitle("Classe Vidéo");
        classDialog.setHeaderText("Sélectionnez la classe");
        String cls = classDialog.showAndWait().orElse(null);
        if (cls == null) return null;

        TextInputDialog authorDialog = new TextInputDialog(existing != null ? existing.getAuthor() : "");
        authorDialog.setHeaderText("Auteur");
        String author = authorDialog.showAndWait().orElse("");

        int duration = 10;
        try {
            TextInputDialog durationDialog = new TextInputDialog("10");
            durationDialog.setHeaderText("Durée (s)");
            duration = Integer.parseInt(durationDialog.showAndWait().orElse("10"));
        } catch (NumberFormatException e) {
            duration = 10;
        }

        return switch (cls) {
            case "FacebookVideo" -> new FacebookVideo(existing != null ? existing.getId() : UUID.randomUUID(), author, "", LocalDateTime.now(), duration, true);
            case "InstagramVideo" -> new InstagramVideo(existing != null ? existing.getId() : UUID.randomUUID(), author, "", LocalDateTime.now(), duration, true);
            case "TikTokVideo" -> new TikTokVideo(existing != null ? existing.getId() : UUID.randomUUID(), author, "", LocalDateTime.now(), duration, true);
            case "ThreadsVideo" -> new ThreadsVideo(existing != null ? existing.getId() : UUID.randomUUID(), author, "", LocalDateTime.now(), duration, true);
            default -> null;
        };
    }

    private static Publication handleLongVideoPublication(Publication existing) {
        TextInputDialog authorDialog = new TextInputDialog(existing != null ? existing.getAuthor() : "");
        authorDialog.setHeaderText("Auteur");
        String author = authorDialog.showAndWait().orElse("");

        int duration = 60;
        int minLength = 10;
        boolean hasAds = true;

        try {
            TextInputDialog durationDialog = new TextInputDialog("60");
            durationDialog.setHeaderText("Durée (s)");
            duration = Integer.parseInt(durationDialog.showAndWait().orElse("60"));

            TextInputDialog minLengthDialog = new TextInputDialog("10");
            minLengthDialog.setHeaderText("Durée minimale (s)");
            minLength = Integer.parseInt(minLengthDialog.showAndWait().orElse("10"));

            TextInputDialog adsDialog = new TextInputDialog("true");
            adsDialog.setHeaderText("Contient des pubs ?");
            hasAds = Boolean.parseBoolean(adsDialog.showAndWait().orElse("true"));
        } catch (Exception e) {
            duration = 60;
            minLength = 10;
            hasAds = true;
        }

        return new YouTubeLongVideo(existing != null ? existing.getId() : UUID.randomUUID(),
                author, "", java.time.LocalDateTime.now(), duration, minLength, hasAds);
    }
}