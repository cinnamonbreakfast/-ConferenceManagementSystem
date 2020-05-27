package ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import provider.UserProvider;

import java.awt.*;

public class MainWindow {

    @FXML public AnchorPane anchorPane;
    @FXML public Button membersTab;
    @FXML public Button conferencesTab;
    @FXML public Button settingsTab;
    @FXML public Label tabLocation;
    @FXML public Label tabTitle;
    @FXML public SplitPane splitPane;
    @FXML public Rectangle avatar;
    @FXML public ImageView crown;
    private int activeTab = 2;

    @Autowired
    private UserProvider userProvider;

    @FXML
    void conferencesClick(ActionEvent event) {
        membersTab.getStyleClass().remove("active");
        conferencesTab.getStyleClass().remove("active");
        settingsTab.getStyleClass().remove("active");

        conferencesTab.getStyleClass().add("active");
        activeTab = 1;

        tabLocation.setText("Home > Conferences");
        tabTitle.setText("Conferences");
    }

    @FXML
    void membersClick(ActionEvent event)
    {
        membersTab.getStyleClass().remove("active");
        conferencesTab.getStyleClass().remove("active");
        settingsTab.getStyleClass().remove("active");

        membersTab.getStyleClass().add("active");
        activeTab = 0;

        tabLocation.setText("Home > Members");
        tabTitle.setText("Members");
    }

    @FXML
    void settingsClick(ActionEvent event)
    {
        membersTab.getStyleClass().remove("active");
        conferencesTab.getStyleClass().remove("active");
        settingsTab.getStyleClass().remove("active");

        settingsTab.getStyleClass().add("active");
        activeTab = 2;

        tabLocation.setText("Home > Settings");
        tabTitle.setText("Settings");
    }


    @FXML
    void createConference(ActionEvent event)
    {
//        splitPane.setDividerPosition(0, 0.07);

        BooleanProperty collapsed = new SimpleBooleanProperty();
        collapsed.bind(splitPane.getDividers().get(0).positionProperty().isEqualTo(0, 0.06));

        double target = collapsed.get() ? 0.2 : 0.06 ;
        KeyValue keyValue = new KeyValue(splitPane.getDividers().get(0).positionProperty(), target);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), keyValue));
        timeline.play();
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
    }
}
