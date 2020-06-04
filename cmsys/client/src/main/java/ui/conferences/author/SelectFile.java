package ui.conferences.author;

import dto.PaperDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import provider.ConferenceProvider;
import provider.UserProvider;
import ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SelectFile {
    @FXML public Button selectFIle;
    @FXML public Button cancelUpload;
    @FXML public Label fileName;
    @FXML public VBox step1;
    @FXML public VBox step2;
    @FXML public Label paperTitle;
    @FXML public TextField dtoTITLE;
    @FXML public TextField dtoKEY;
    @FXML public TextField dtoTOP;
    @FXML public TextField submissionAuthors;
    @FXML public Button sndbtn;
    FileChooser fileChooser;

    private File selectedFile;

    @Autowired
    private Main main;

    @Autowired
    private ConferenceProvider conferenceProvider;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    MainWindow mainWindow;

    @FXML
    void initialize() {
        fileChooser = new FileChooser();

        step2.setVisible(false);
        step2.setManaged(false);
    }

    public void CancelUploadAction(ActionEvent actionEvent) {
        main.cancelUpload();
    }

    public void filePickFNC(ActionEvent actionEvent) {

        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Documents","*.txt", "*.doc", "*.docx", "*.pdf"));

        selectedFile = fileChooser.showOpenDialog(selectFIle.getScene().getWindow());
        if (selectedFile != null) {
            fileName.setText(selectedFile.getPath());

            paperTitle.setText(selectedFile.getName());

            step1.setVisible(false);
            step1.setManaged(false);

            step2.setVisible(true);
            step2.setManaged(true);


            sndbtn.setOnMouseClicked(e -> {
                PaperDTO paperus = PaperDTO.builder()
                        .title(dtoTITLE.getText())
                        .keywords(dtoKEY.getText())
                        .topics(dtoTOP.getText())
                        .conferenceDTO(conferenceProvider.getLoggedConference())
                        .user(userProvider.getUser())
                        .build();

                ResponseEntity<String> response = userProvider.postFile(paperus);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");;
                alert.setTitle("New paper");
                alert.setHeaderText(null);
                alert.setContentText("A new paper has been added.");

                alert.showAndWait();

                mainWindow.openAsAuthor(conferenceProvider.getLoggPermission());
            });
        }
    }

    public void pingFIle(MouseEvent mouseEvent) {
        if(selectedFile != null) {
            try {
                Desktop.getDesktop().open(new File(selectedFile.getPath()));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");;
                alert.setTitle("File upload");
                alert.setHeaderText(null);
                alert.setContentText("File not found. Maybe it was deleted in meantime.");

                alert.showAndWait();
            }
        }

    }

    public void sendFileByeBye(ActionEvent actionEvent) {
    }
}
