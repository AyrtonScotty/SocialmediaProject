package com.ayrton.socialmedia.ui;

import javafx.scene.control.*;

public class FormManager {

    public static void hideFields(Label authorLabel, Label contentLabel,
                                  TextField authorField, TextField contentField,
                                  CheckBox extraCheck,
                                  TextField durationField, Label durationLabel) {

        authorLabel.setVisible(false);
        contentLabel.setVisible(false);
        authorField.setVisible(false);
        contentField.setVisible(false);
        extraCheck.setVisible(false);
        durationField.setVisible(false);
        durationLabel.setVisible(false);
    }

    public static void showBasicFields(Label authorLabel, Label contentLabel,
                                       TextField authorField, TextField contentField,
                                       CheckBox extraCheck) {

        authorLabel.setVisible(true);
        contentLabel.setVisible(true);
        authorField.setVisible(true);
        contentField.setVisible(true);
        extraCheck.setVisible(true);
    }

    public static void updateForm(String type, Label extraLabel,
                                  TextField durationField, Label durationLabel) {

        if (type == null) return;

        boolean isVideo = type.contains("Video");
        durationLabel.setVisible(isVideo);
        durationField.setVisible(isVideo);

        switch (type) {
            case "FacebookText": extraLabel.setText("Has Hashtags ?"); break;
            case "ThreadsText": extraLabel.setText("Is Reply ?"); break;
            case "FacebookImage": extraLabel.setText("Has Tag People ?"); break;
            case "InstagramImage": extraLabel.setText("Has Filter ?"); break;
            case "TikTokImage": extraLabel.setText("Has Music ?"); break;
            case "ThreadsImage": extraLabel.setText("Is Profile Image ?"); break;
            case "FacebookVideo": extraLabel.setText("Has Caption ?"); break;
            case "InstagramVideo": extraLabel.setText("Is Reel ?"); break;
            case "TikTokVideo": extraLabel.setText("Has Effects ?"); break;
            case "ThreadsVideo": extraLabel.setText("Has Thread Link ?"); break;
            case "YouTubeLongVideo": extraLabel.setText("Has Ads ?"); break;
            default: extraLabel.setText(""); break;
        }
    }
}