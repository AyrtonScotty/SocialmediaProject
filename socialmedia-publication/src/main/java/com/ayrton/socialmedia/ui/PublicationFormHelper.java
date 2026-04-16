package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.abstracts.Publication;
import com.ayrton.socialmedia.model.abstracts.VideoPublication;

import javafx.scene.control.*;

public class PublicationFormHelper {

    public static void fillForm(Publication pub,
                                TextField authorField,
                                TextField contentField,
                                TextField durationField,
                                CheckBox extraCheck) {

        authorField.setText(pub.getAuthor());
        contentField.setText(pub.getContent());

        if (pub instanceof VideoPublication video) {
            durationField.setText(String.valueOf(video.getDuration()));
        }

        // ⚠️ IMPORTANT : on garde ton comportement actuel (pas de changement)
    }
}