package com.ayrton.socialmedia.controller;

import com.ayrton.socialmedia.model.abstracts.Publication;
import com.ayrton.socialmedia.model.abstracts.VideoPublication;
import com.ayrton.socialmedia.model.text.*;
import com.ayrton.socialmedia.model.image.*;
import com.ayrton.socialmedia.model.video.*;
import com.ayrton.socialmedia.model.longvideo.*;
import com.ayrton.socialmedia.repository.PublicationFileRepository;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.UUID;

public class MainController {

    @FXML private TableView<Publication> tableView;
    @FXML private TableColumn<Publication, String> idCol;
    @FXML private TableColumn<Publication, String> authorCol;
    @FXML private TableColumn<Publication, String> contentCol;
    @FXML private TableColumn<Publication, String> typeCol;
    @FXML private TableColumn<Publication, String> dateCol;
    @FXML private TableColumn<Publication, String> validCol;

    @FXML private VBox form;
    @FXML private Label formHeader;
    @FXML private ComboBox<String> typeBox;
    @FXML private Label authorLabel;
    @FXML private TextField authorField;
    @FXML private Label contentLabel;
    @FXML private TextField contentField;
    @FXML private CheckBox extraCheck;
    @FXML private Label extraLabel;
    @FXML private Label durationLabel;
    @FXML private TextField durationField;

    private final PublicationFileRepository repo = PublicationFileRepository.getInstance();
    private Publication publicationEnCours = null;

    @FXML
    public void initialize() {
        // Colonnes
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

        // TableView sans scroll horizontal
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Double clic sur ligne pour détails
        tableView.setRowFactory(tv -> {
            TableRow<Publication> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Publication p = row.getItem();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Détails");
                    alert.setHeaderText(p.getClass().getSimpleName() + " - " + p.getAuthor());
                    alert.setContentText(p.toInfo());
                    alert.showAndWait();
                }
            });
            return row;
        });

        // Types
        typeBox.getItems().addAll(
                "FacebookText", "ThreadsText",
                "FacebookImage", "InstagramImage", "TikTokImage", "ThreadsImage",
                "FacebookVideo", "InstagramVideo", "TikTokVideo", "ThreadsVideo",
                "YouTubeLongVideo"
        );

        // Masquer champs au départ
        authorLabel.setVisible(false);
        contentLabel.setVisible(false);
        authorField.setVisible(false);
        contentField.setVisible(false);
        extraCheck.setVisible(false);
        durationField.setVisible(false);
        durationLabel.setVisible(false);

        // Affichage formulaire selon type choisi
        typeBox.setOnAction(e -> {
            String type = typeBox.getValue();
            boolean typeSelected = type != null && !type.isEmpty();

            authorLabel.setVisible(typeSelected);
            contentLabel.setVisible(typeSelected);
            authorField.setVisible(typeSelected);
            contentField.setVisible(typeSelected);
            extraCheck.setVisible(typeSelected);

            updateForm();
        });

        load();
    }

    private void updateForm() {
        String type = typeBox.getValue();
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

    private void load() { tableView.getItems().setAll(repo.findAll()); }

    @FXML
    private void handleAdd() {
        publicationEnCours = null;
        authorField.clear();
        contentField.clear();
        durationField.clear();
        extraCheck.setSelected(false);
        typeBox.setValue(null);

        form.setVisible(true);
        authorLabel.setVisible(false);
        contentLabel.setVisible(false);
        authorField.setVisible(false);
        contentField.setVisible(false);
        extraCheck.setVisible(false);
        extraLabel.setText("");
        durationField.setVisible(false);
        durationLabel.setVisible(false);

        formHeader.setText("Nouvelle Publication");
    }

    @FXML
    private void handleEdit() {
        publicationEnCours = tableView.getSelectionModel().getSelectedItem();
        if (publicationEnCours != null) {
            authorField.setText(publicationEnCours.getAuthor());
            contentField.setText(publicationEnCours.getContent());
            typeBox.setValue(publicationEnCours.getClass().getSimpleName());
            formHeader.setText("Modification d'une Publication");

            authorLabel.setVisible(true);
            contentLabel.setVisible(true);
            authorField.setVisible(true);
            contentField.setVisible(true);
            extraCheck.setVisible(true);

            updateForm();

            if (publicationEnCours instanceof VideoPublication) {
                durationField.setText(String.valueOf(((VideoPublication) publicationEnCours).getDuration()));
            }

            if (publicationEnCours instanceof FacebookText) {
                extraCheck.setSelected(((FacebookText) publicationEnCours).getHasHashtags());
            }
            if (publicationEnCours instanceof ThreadsText) {
                extraCheck.setSelected(((ThreadsText) publicationEnCours).isReply());
            }
            if (publicationEnCours instanceof FacebookImage) {
                extraCheck.setSelected(((FacebookImage) publicationEnCours).isHasTagPeople());
            }

            form.setVisible(true);
        }
    }

    @FXML
    private void handleClone() {
        Publication selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            publicationEnCours = null;
            typeBox.setValue(selected.getClass().getSimpleName());
            authorField.setText(selected.getAuthor());
            contentField.setText(selected.getContent());

            if (selected instanceof VideoPublication) {
                durationField.setText(String.valueOf(((VideoPublication) selected).getDuration()));
            }

            extraCheck.setSelected(false); // ou copier selon type
            authorLabel.setVisible(true);
            contentLabel.setVisible(true);
            authorField.setVisible(true);
            contentField.setVisible(true);
            extraCheck.setVisible(true);
            formHeader.setText("Clonage de Publication");
            form.setVisible(true);
        }
    }

    @FXML
    private void handleSave() {
        try {
            String type = typeBox.getValue();
            String author = authorField.getText();
            String content = contentField.getText();
            boolean extra = extraCheck.isSelected();

            // Validation duration
            int duration = 0;
            if (durationField.isVisible()) {
                String durText = durationField.getText();
                if (durText.isEmpty()) durText = "0";
                try {
                    duration = Integer.parseInt(durText);
                    if (duration < 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setHeaderText("Durée invalide");
                    alert.setContentText("Veuillez entrer un nombre entier positif pour la durée.");
                    alert.showAndWait();
                    return;
                }
            }

            UUID id = publicationEnCours != null ? publicationEnCours.getId() : UUID.randomUUID();
            Publication pub = null;

            switch (type) {
                case "FacebookText":
                    pub = new FacebookText(id, author, content, LocalDateTime.now(), extra, 100);
                    break;
                case "ThreadsText":
                    pub = new ThreadsText(id, author, content, LocalDateTime.now(), extra);
                    break;
                case "FacebookImage":
                    pub = new FacebookImage(id, author, content, LocalDateTime.now(), "default.png", extra);
                    break;
                case "InstagramImage":
                    pub = new InstagramImage(id, author, content, LocalDateTime.now(), "default.png", extra ? "filter" : "none");
                    break;
                case "TikTokImage":
                    pub = new TikTokImage(id, author, content, LocalDateTime.now(), "default.png", extra);
                    break;
                case "ThreadsImage":
                    pub = new ThreadsImage(id, author, content, LocalDateTime.now(), "default.png", extra);
                    break;
                case "FacebookVideo":
                    pub = new FacebookVideo(id, author, content, LocalDateTime.now(), duration, extra);
                    break;
                case "InstagramVideo":
                    pub = new InstagramVideo(id, author, content, LocalDateTime.now(), duration, extra);
                    break;
                case "TikTokVideo":
                    pub = new TikTokVideo(id, author, content, LocalDateTime.now(), duration, extra);
                    break;
                case "ThreadsVideo":
                    pub = new ThreadsVideo(id, author, content, LocalDateTime.now(), duration, extra);
                    break;
                case "YouTubeLongVideo":
                    pub = new YouTubeLongVideo(id, author, content, LocalDateTime.now(), duration, 120, extra);
                    break;
            }

            if (pub != null) {
                repo.save(pub);
                load();
                form.setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Publication selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            repo.delete(selected.getId());
            load();
        }
    }

    @FXML private void handleRefresh() { load(); }
    @FXML private void handleCloseForm() { form.setVisible(false); }
    @FXML private void handleQuit() { System.exit(0); }
}