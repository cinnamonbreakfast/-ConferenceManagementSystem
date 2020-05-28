package ui;

import dto.ConferencesDTO;
import dto.UserRegisterDTO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.ConferenceProvider;
import provider.UserProvider;
import ui.conferences.Hello;
import ui.conferences.NewConference;

import java.awt.*;
import java.io.IOException;

public class MainWindow {

    @FXML public AnchorPane anchorPane;
    @FXML public Button conferencesTab;
    @FXML public Button settingsTab;
    @FXML public Label tabLocation;
    @FXML public Label tabTitle;
    @FXML public SplitPane splitPane;
    @FXML public Rectangle avatar;
    @FXML public ImageView crown;
    @FXML public AnchorPane mainContent;
    private int activeTab = 2;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private ConferenceProvider conferenceProvider;

    @Autowired
    private AnnotationConfigApplicationContext context;

    @FXML
    void conferencesClick(ActionEvent event) {
        conferencesTab.getStyleClass().remove("active");
        settingsTab.getStyleClass().remove("active");

        conferencesTab.getStyleClass().add("active");
        activeTab = 1;

        tabLocation.setText("Home > Conferences");
        tabTitle.setText("Conferences");

        Task conferencesList = new Task<ConferencesDTO>() {
            @Override
            public ConferencesDTO call() {
                return conferenceProvider.getMyConferences();
            }
        };
        conferencesList.setOnSucceeded(response -> setConferences((ConferencesDTO)conferencesList.getValue()));
        new Thread(conferencesList).start();
    }

    @FXML
    void membersClick(ActionEvent event)
    {
        conferencesTab.getStyleClass().remove("active");
        settingsTab.getStyleClass().remove("active");

        activeTab = 0;

        tabLocation.setText("Home > Members");
        tabTitle.setText("Members");
    }

    @FXML
    void settingsClick(ActionEvent event)
    {
        conferencesTab.getStyleClass().remove("active");
        settingsTab.getStyleClass().remove("active");

        settingsTab.getStyleClass().add("active");
        activeTab = 2;

        tabLocation.setText("Home > Settings");
        tabTitle.setText("Settings");
    }

    void setConferences(ConferencesDTO dtos)
    {
        if(dtos.getSize() == 0)
        {
            FXMLLoader root3 = new FXMLLoader(Hello.class.getResource("/FXML/conferences/Hello.fxml"));
            root3.setControllerFactory(context::getBean);
            try {
                AnchorPane test = root3.load();

                AnchorPane.setBottomAnchor(test, 0D);
                AnchorPane.setRightAnchor(test, 0D);
                AnchorPane.setTopAnchor(test, 0D);
                AnchorPane.setLeftAnchor(test, 0D);

                mainContent.getChildren().clear();
                mainContent.getChildren().add(test);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void initialize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        anchorPane.setPrefWidth(width);
        anchorPane.setPrefHeight(height - 60);
        anchorPane.getStylesheets().add("ui/forms/basic_forms.css");
        anchorPane.getStylesheets().add("ui/main/main_window.css");

        ImagePattern pattern = new ImagePattern(new Image("images/sign/andrew.jpg"));

        avatar.setFill(pattern);

        TranslateTransition floatAnimation = new TranslateTransition(Duration.millis(3000), crown);
        floatAnimation.setFromX(crown.getTranslateX());
        floatAnimation.setToX(crown.getTranslateX()+5);
        floatAnimation.setFromY(crown.getTranslateY());
        floatAnimation.setToY(crown.getTranslateY()-5);
        floatAnimation.setCycleCount(Timeline.INDEFINITE);
        floatAnimation.setAutoReverse(true);
        floatAnimation.play();

        conferenceProvider.setToken(userProvider.getToken());

        conferencesClick(null);
    }
}
