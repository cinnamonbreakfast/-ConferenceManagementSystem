package ui.conferences;

import dto.ConferencesDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.ConferenceProvider;
import provider.UserProvider;
import ui.MainWindow;

import java.io.IOException;

public class MyConferences {
    @FXML public VBox myConferences;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private ConferenceProvider conferenceProvider;

    @Autowired
    private AnnotationConfigApplicationContext context;

    @Autowired
    private MainWindow mainWindow;

    @Autowired
    private Hello hello;

    private ConferencesDTO setupDTO;

    public void setSetupDTO(ConferencesDTO conferencesDTO) {
        setupDTO = conferencesDTO;
        myConferences.getChildren().clear();

        setupDTO.getConferences().forEach(e -> {
            FXMLLoader conference = new FXMLLoader(ConferencesPane.class.getResource("/FXML/conferences/ConferencesPane.fxml"));

            try {
                AnchorPane cfp = conference.load();

                ConferencesPane pane = ((ConferencesPane) conference.getController());

                pane.setDTO(e);
                pane.setParent(mainWindow);

                myConferences.getChildren().add(cfp);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    @FXML
    void initialize()
    {

    }

    public void newConference(ActionEvent actionEvent) {
        mainWindow.loadHello();
        hello.setStep(0);
        hello.forceStep(0);
    }
}
