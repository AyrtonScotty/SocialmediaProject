package com.ayrton.socialmedia.controller;

import com.ayrton.socialmedia.model.abstracts.Publication;
import com.ayrton.socialmedia.repository.PublicationFileRepository;
import com.ayrton.socialmedia.ui.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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

        TableManager.setup(
                tableView, idCol, authorCol, contentCol, typeCol, dateCol, validCol
        );

        setupRowDoubleClick();
        setupTypeBox();

        FormManager.hideFields(
                authorLabel, contentLabel,
                authorField, contentField,
                extraCheck, durationField, durationLabel
        );

        load();
    }

    // ─────────────────────────────
    // DOUBLE CLICK DETAILS
    // ─────────────────────────────
    private void setupRowDoubleClick() {

        tableView.setRowFactory(tv -> {
            TableRow<Publication> row = new TableRow<>();

            row.setOnMouseClicked(event -> {

                if (event.getClickCount() == 2 && !row.isEmpty()) {

                    Publication p = row.getItem();

                    StringBuilder msg = new StringBuilder();

                    msg.append("=== Détails de la Publication ===\n\n");
                    msg.append("ID : ").append(p.getId()).append("\n");
                    msg.append("Author : ").append(blank(p.getAuthor())).append("\n");
                    msg.append("Content : ").append(blank(p.getContent())).append("\n");
                    msg.append("CreatedAt : ").append(p.getCreatedAt()).append("\n");
                    msg.append("Type : ").append(p.getClass().getSimpleName()).append("\n\n");

                    // VIDEO
                    if (p instanceof com.ayrton.socialmedia.model.abstracts.VideoPublication vp) {

                        msg.append("Duration : ")
                                .append(vp.getDuration())
                                .append(" sec\n");

                        if (p instanceof com.ayrton.socialmedia.model.longvideo.YouTubeLongVideo yt) {

                            msg.append("Intervalle : (")
                                    .append(yt.getMinLength())
                                    .append(" - ∞ sec)\n");

                            msg.append("Has Ads : ")
                                    .append(yt.isHasAds())
                                    .append("\n");

                        } else {
                            msg.append("Intervalle : (1 - 120 sec)\n");
                        }
                    }

                    // TEXT
                    if (p instanceof com.ayrton.socialmedia.model.text.FacebookText fb) {
                        msg.append("Hashtags : ").append(fb.getHasHashtags()).append("\n");
                    }

                    // VALIDATION
                    if (!p.isValid()) {

                        msg.append("\n⚠ PUBLICATION INVALIDE\n\n");

                        for (String error : p.getValidationErrors()) {

                            if (error.toLowerCase().contains("auteur")) {
                                msg.append("⚠ Auteur manquant (obligatoire)\n");
                            }

                            else if (error.toLowerCase().contains("durée")
                                    || error.toLowerCase().contains("duration")) {
                                msg.append("⚠ Durée hors intervalle autorisé\n");
                            }

                            else {
                                msg.append("• ").append(error).append("\n");
                            }
                        }
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Détails Publication");
                    alert.setHeaderText(p.getClass().getSimpleName());
                    alert.setContentText(msg.toString());
                    alert.showAndWait();
                }
            });

            return row;
        });
    }

    // ─────────────────────────────
    private String blank(String v) {
        return (v == null || v.isBlank()) ? "" : v;
    }

    // ─────────────────────────────
    private void setupTypeBox() {

        typeBox.getItems().addAll(
                "FacebookText", "ThreadsText",
                "FacebookImage", "InstagramImage", "TikTokImage", "ThreadsImage",
                "FacebookVideo", "InstagramVideo", "TikTokVideo", "ThreadsVideo",
                "YouTubeLongVideo"
        );

        typeBox.setOnAction(e -> {

            FormManager.showBasicFields(
                    authorLabel, contentLabel,
                    authorField, contentField,
                    extraCheck
            );

            FormManager.updateForm(
                    typeBox.getValue(),
                    extraLabel,
                    durationField,
                    durationLabel
            );
        });
    }

    // ─────────────────────────────
    private void load() {
        tableView.getItems().setAll(repo.findAll());
    }

    // ─────────────────────────────
    @FXML
    private void handleAdd() {

        publicationEnCours = null;

        authorField.clear();
        contentField.clear();
        durationField.clear();
        extraCheck.setSelected(false);
        typeBox.setValue(null);

        FormManager.hideFields(
                authorLabel, contentLabel,
                authorField, contentField,
                extraCheck, durationField, durationLabel
        );

        formHeader.setText("Nouvelle Publication");
        form.setVisible(true);
    }

    // ─────────────────────────────
    @FXML
    private void handleEdit() {

        publicationEnCours = tableView.getSelectionModel().getSelectedItem();

        if (publicationEnCours != null) {

            PublicationFormHelper.fillForm(
                    publicationEnCours,
                    authorField,
                    contentField,
                    durationField,
                    extraCheck
            );

            typeBox.setValue(publicationEnCours.getClass().getSimpleName());

            FormManager.showBasicFields(
                    authorLabel, contentLabel,
                    authorField, contentField,
                    extraCheck
            );

            FormManager.updateForm(
                    typeBox.getValue(),
                    extraLabel,
                    durationField,
                    durationLabel
            );

            formHeader.setText("Modification d'une Publication");
            form.setVisible(true);
        }
    }

    // ─────────────────────────────
    @FXML
    private void handleClone() {

        Publication selected = tableView.getSelectionModel().getSelectedItem();

        if (selected != null) {

            publicationEnCours = null;

            PublicationFormHelper.fillForm(
                    selected,
                    authorField,
                    contentField,
                    durationField,
                    extraCheck
            );

            typeBox.setValue(selected.getClass().getSimpleName());

            FormManager.showBasicFields(
                    authorLabel, contentLabel,
                    authorField, contentField,
                    extraCheck
            );

            formHeader.setText("Clonage de Publication");
            form.setVisible(true);
        }
    }

    // ─────────────────────────────
    @FXML
    private void handleSave() {

        String type = typeBox.getValue();
        String author = authorField.getText();
        String content = contentField.getText();
        boolean extra = extraCheck.isSelected();

        int duration = 0;

        if (durationField.isVisible()) {
            try {
                duration = Integer.parseInt(durationField.getText());
            } catch (Exception e) {
                showError("Durée invalide");
                return;
            }
        }

        PublicationDialog dialog = PublicationDialogFactory.create(type);

        Publication pub = null;

        if (dialog != null) {
            pub = dialog.create(
                    publicationEnCours,
                    author,
                    content,
                    extra,
                    duration
            );
        }

        if (pub != null) {

            repo.save(pub);
            load();
            form.setVisible(false);

            showValidationFeedback(pub);
        }
    }

    // ─────────────────────────────
    private void showValidationFeedback(Publication pub) {

        if (pub.isValid()) return;

        StringBuilder sb = new StringBuilder();
        sb.append("⚠ Publication invalide\n\n");

        for (String error : pub.getValidationErrors()) {
            sb.append("• ").append(error).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation");
        alert.setHeaderText("Problème détecté");
        alert.setContentText(sb.toString());
        alert.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML private void handleDelete() {
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