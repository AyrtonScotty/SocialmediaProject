package com.ayrton.socialmedia.ui;

import com.ayrton.socialmedia.model.abstracts.Publication;
import javafx.scene.control.*;

public class TableManager {

    public static void setup(TableView<Publication> tableView,
                             TableColumn<Publication, String> idCol,
                             TableColumn<Publication, String> authorCol,
                             TableColumn<Publication, String> contentCol,
                             TableColumn<Publication, String> typeCol,
                             TableColumn<Publication, String> dateCol,
                             TableColumn<Publication, String> validCol) {

        idCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getId().toString()));
        authorCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getAuthor()));
        contentCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getContent()));
        typeCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getClass().getSimpleName()));
        dateCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
                d.getValue().getCreatedAt() != null ? d.getValue().getCreatedAt().toString() : ""
        ));
        validCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
                d.getValue().isValid() ? "Valide" : "Invalide"
        ));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}