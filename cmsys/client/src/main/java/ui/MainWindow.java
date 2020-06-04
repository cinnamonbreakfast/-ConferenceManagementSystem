package ui;

import dto.ConferenceDTO;
import dto.ConferencesDTO;
import dto.PermissionDTO;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.ConferenceProvider;
import provider.UserProvider;
import ui.conferences.Hello;
import ui.conferences.MyConferences;
import ui.conferences.author.Main;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Label userName;
    public Button chairAssignTab;
    public Button chairMembers;
    public VBox chairButtons;
    public Button reviewerMembers;
    public Button reviewerTaskTab;
    public VBox reviewerButtons;
    public Button authorMembers;
    public Button authorTabMain;
    public VBox authorButtons;
    public VBox menuButtons;
    private int activeTab = 2;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private ConferenceProvider conferenceProvider;

    @Autowired
    private AnnotationConfigApplicationContext context;

    public void setLocation(String title, String location) {
        tabTitle.setText(title);
        tabLocation.setText(location);
    }

    @FXML
    public void conferencesClick(ActionEvent event) {
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

    public void loadHello()
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

    public void loadConferences(ConferencesDTO dtos)
    {
        FXMLLoader root4 = new FXMLLoader(MyConferences.class.getResource("/FXML/conferences/MyConferences.fxml"));
        root4.setControllerFactory(context::getBean);

        try {
            AnchorPane test = root4.load();

            MyConferences myConferences = (MyConferences) root4.getController();
            myConferences.setSetupDTO(dtos);

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

    void setConferences(ConferencesDTO dtos)
    {
        if(dtos.getSize() == 0)
        {
            loadHello();
        } else {
            loadConferences(dtos);
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

        ImagePattern pattern = new ImagePattern(new Image(userProvider.getURL()+"/usr/"+userProvider.getUser().getUsername()+".jpg"));
        userName.setText(userProvider.getUser().getLastName());

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

        menuButtons.setVisible(true);
        menuButtons.setManaged(true);

        authorButtons.setVisible(false);
        authorButtons.setManaged(false);

        reviewerButtons.setVisible(false);
        reviewerButtons.setManaged(false);

        chairButtons.setVisible(false);
        chairButtons.setManaged(false);
    }

    @FXML
    public void signOut(ActionEvent actionEvent) {
        userProvider.logout();
        Stage stage = (Stage) crown.getScene().getWindow();
        stage.close();
    }

    public void openAsAuthor(PermissionDTO permission){
        FXMLLoader authorMain = new FXMLLoader(Main.class.getResource("/FXML/conferences/author/Main.fxml"));
        authorMain.setControllerFactory(context::getBean);

        try {
            AnchorPane test = authorMain.load();

            Main authorMainController = (Main) authorMain.getController();
            authorMainController.setPermission(permission);

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

    public void openEvent(ConferenceDTO dto) {
        setLocation(dto.getTitle(),dto.getTitle() + " > Main");

        conferenceProvider.setLoggedConference(dto);

        PermissionDTO permission = userProvider.getPermissions(dto.getId());

        conferenceProvider.setLoggPermission(permission);

        List<String> choices = new ArrayList<>();

        if(permission.getAuthor() != null) choices.add("Author");
        if(permission.getReviewer() != null) choices.add("Reviewer");
        if(permission.getCoChair() != null) choices.add("Co-Chair");
        if(permission.getChair() != null) choices.add("Chair");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Enter with role");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter with role:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> {
            if(s.equals("Author"))
            {
                openAsAuthor(permission);
                conferenceProvider.setPermissionOn(s);

                menuButtons.setVisible(false);
                menuButtons.setManaged(false);

                authorButtons.setVisible(true);
                authorButtons.setManaged(true);

                reviewerButtons.setVisible(false);
                reviewerButtons.setManaged(false);

                chairButtons.setVisible(false);
                chairButtons.setManaged(false);
            }
        });
    }
}
