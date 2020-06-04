package ui.conferences;

import dto.ConferenceDTO;
import dto.UserDTO;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.tools.Duplicatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import provider.ConferenceProvider;
import provider.UserProvider;
import org.controlsfx.control.textfield.TextFields;
import ui.MainWindow;
import ui.user.UserCard;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hello {

    @FXML public AnchorPane anchorPane;
    @FXML public VBox hello;
    @FXML public VBox steps;
    @FXML public Label step1Label;
    @FXML public Text step2Label;
    @FXML public Text step3Label;
    @FXML public TextField titleBox;
    @FXML public TextArea descrBox;
    @FXML public Button backBtn;
    @FXML public Button continueBtn;
    @FXML public Button btnCreate;
    @FXML public AnchorPane step1;
    @FXML public AnchorPane step2;
    @FXML public AnchorPane step3;
    @FXML public TextField createInviteUsername;
    @FXML public AnchorPane invitedUserInstance;
    @FXML public SplitMenuButton inviteRank;
    @FXML public Button inviteAdd;
    @FXML public HBox invitedUserList;
    @FXML public DatePicker abstractDeadline;
    @FXML public DatePicker fullpapersDeadline;
    @FXML public DatePicker biddingDeadline;

    private int step = 0;

    private ConferenceDTO conferenceDTO;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private ConferenceProvider conferenceProvider;

    @Autowired
    private AnnotationConfigApplicationContext context;

    @Autowired
    private MainWindow mainWindow;

    @FXML
    void createConference(ActionEvent event) {
//        splitPane.setDividerPosition(0, 0.07);
//
//        BooleanProperty collapsed = new SimpleBooleanProperty();
//        collapsed.bind(splitPane.getDividers().get(0).positionProperty().isEqualTo(0, 0.06));
//
//        double target = collapsed.get() ? 0.2 : 0.06 ;
//        KeyValue keyValue = new KeyValue(splitPane.getDividers().get(0).positionProperty(), target);
//        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), keyValue));
//        timeline.play();
        setStep(++step);
    }

    void conflict(String error)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");;
        alert.setTitle("New conference");
        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

    void forceStep(int step)
    {
        this.step = step;
    }

    void setStep(int step)
    {
        if(step == 0)
        {
            steps.setVisible(false);
            hello.setVisible(true);

            step1.setVisible(true);
            step1.setManaged(true);

            step2.setVisible(false);
            step2.setManaged(false);

            step3.setVisible(false);
            step3.setManaged(false);

            step1Label.setText("Step 1. Basic Information");
            step2Label.setText("Step 2");
            step3Label.setText("Step 3");

            continueBtn.setText("Continue");
        } else if(step == 1)
        {
            steps.setVisible(true);
            hello.setVisible(false);

            step1.setVisible(true);
            step1.setManaged(true);

            step2.setVisible(false);
            step2.setManaged(false);

            step3.setVisible(false);
            step3.setManaged(false);

            step1Label.setText("Step 1. Basic Information");
            step2Label.setText("Step 2");
            step3Label.setText("Step 3");

            continueBtn.setText("Continue");
        } else if(step == 2) {
            if(titleBox.getText().equals(""))
            {
                this.step--;
                this.conflict("Title field cannot be empty.");
                return;
            }

            steps.setVisible(true);
            hello.setVisible(false);

            step1.setVisible(false);
            step1.setManaged(false);

            step2.setVisible(true);
            step2.setManaged(true);

            step3.setVisible(false);
            step3.setManaged(false);

            step1Label.setText("Step 1");
            step2Label.setText("Step 2. Deadlines");
            step3Label.setText("Step 3");

            continueBtn.setText("Continue");
        } else if(step == 3) {
            if(abstractDeadline.getValue() == null)
            {
                this.step--;
                this.conflict("Abstract deadline cannot be left empty.");
                return;
            }

            if(fullpapersDeadline.getValue() == null)
            {
                this.step--;
                this.conflict("Full papers deadline cannot be left empty.");
                return;
            }

            if(biddingDeadline.getValue() == null)
            {
                this.step--;
                this.conflict("Bidding phase deadline cannot be left empty.");
                return;
            }

            steps.setVisible(true);
            hello.setVisible(false);

            step1.setVisible(false);
            step1.setManaged(false);

            step2.setVisible(false);
            step2.setManaged(false);

            step3.setVisible(true);
            step3.setManaged(true);

            step1Label.setText("Step 1");
            step2Label.setText("Step 2");
            step3Label.setText("Step 3. Finish touch");

            continueBtn.setText("Finish");
        }
    }

    @FXML
    void back(ActionEvent event) {
        if(step > 0)
        {
            setStep(--step);
        }
    }

    @FXML
    void nextStep(ActionEvent event) {
        if(step < 3)
        {
            setStep(++step);
        } else {
            conferenceDTO.setTitle(titleBox.getText());
            conferenceDTO.setDescription(descrBox.getText());

            conferenceDTO.setAbstractDeadline(LocalDateTime.of(abstractDeadline.getValue().getYear(), abstractDeadline.getValue().getMonth(), abstractDeadline.getValue().getDayOfMonth(), 23, 59));
            conferenceDTO.setFullDeadline(LocalDateTime.of(fullpapersDeadline.getValue().getYear(), fullpapersDeadline.getValue().getMonth(), fullpapersDeadline.getValue().getDayOfMonth(), 23, 59));
            conferenceDTO.setBiddingDeadline(LocalDateTime.of(biddingDeadline.getValue().getYear(), biddingDeadline.getValue().getMonth(), biddingDeadline.getValue().getDayOfMonth(), 23, 59));

            conferenceDTO.setPhase(0);

            ResponseEntity<String> response = conferenceProvider.makeConference(conferenceDTO);

            if(response.getStatusCode().equals(HttpStatus.OK))
            {
                conflict("A new conference has been created.");

                mainWindow.conferencesClick(null);

                // TODO: transition to conference list
            } else {
                conflict("There is a problem with your conference. "+response.getBody());
            }
        }
    }


    @FXML
    void initialize()
    {
        setStep(0);
        step = 0;
        conferenceProvider.setToken(userProvider.getToken());

        String[] suggestions = {"@raduceaca", "@bunuradu"}; // TODO: getlist

        TextFields.bindAutoCompletion(createInviteUsername, suggestions);

        MenuItem choice1 = inviteRank.getItems().get(0);
        MenuItem choice2 = inviteRank.getItems().get(1);
        MenuItem choice3 = inviteRank.getItems().get(2);

        choice1.setOnAction((e)-> {
            inviteRank.setText(choice1.getText());
        });
        choice2.setOnAction((e)-> {
            inviteRank.setText(choice2.getText());
        });
        choice3.setOnAction((e)-> {
            inviteRank.setText(choice3.getText());
        });

        conferenceDTO = new ConferenceDTO();
        conferenceDTO.setAuthors(new ArrayList<>());
        conferenceDTO.setReviewers(new ArrayList<>());
        conferenceDTO.setCochairs(new ArrayList<>());
    }

    public void selectRank(ActionEvent actionEvent) {
        System.out.println(inviteRank.getText());
    }

    public void inviteUser(ActionEvent actionEvent) {
        UserDTO target = userProvider.getByUsername(createInviteUsername.getText());

        if(target == null)
        {
            conflict("No user found by that username.");
            return;
        }

        FXMLLoader root3 = new FXMLLoader(UserCard.class.getResource("/FXML/user/UserCard.fxml"));
        try {
            AnchorPane test = root3.load();

            invitedUserList.getChildren().add(test);

            UserCard userCard = ((UserCard) root3.getController());
            userCard.setUserProvider(userProvider);
            userCard.setUser(createInviteUsername.getText(), inviteRank.getText());
            userCard.setUser(target);

            if(inviteRank.getText().equals("Author"))
            {
                List<String> authors = conferenceDTO.getAuthors();
                authors.add(createInviteUsername.getText());
                conferenceDTO.setAuthors(authors);
            } else if(inviteRank.getText().equals("Co-Chair"))
            {
                List<String> cochairs = conferenceDTO.getCochairs();
                cochairs.add(createInviteUsername.getText());
                conferenceDTO.setCochairs(cochairs);
            } else if(inviteRank.getText().equals("Reviewer"))
            {
                List<String> reviewers = conferenceDTO.getReviewers();
                reviewers.add(createInviteUsername.getText());
                conferenceDTO.setReviewers(reviewers);
            } else {
                System.out.println("Some error?");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSuggestions(List<String> suggestions)
    {
        TextFields.bindAutoCompletion(createInviteUsername, suggestions);
    }

    public void deleteUser(String username, String rank)
    {
        if(rank.equals("Author"))
        {
            conferenceDTO.getAuthors().remove(username);
        }

        invitedUserList.getChildren().clear();

        conferenceDTO.getCochairs().forEach(e -> {
            FXMLLoader root3 = new FXMLLoader(UserCard.class.getResource("/FXML/user/UserCard.fxml"));
            root3.setControllerFactory(context::getBean);
            try {
                AnchorPane test = root3.load();

                invitedUserList.getChildren().add(test);

                UserCard userCard = ((UserCard) root3.getController());
                userCard.setUser(createInviteUsername.getText(), inviteRank.getText());
                userCard.setUser(userProvider.getByUsername(e));

            } catch (IOException er) {
                er.printStackTrace();
            }
        });
    }

    @FXML
    @SuppressWarnings("unchecked")
    public void getSuggestion(KeyEvent keyEvent) {
        Task loginTask = new Task<List<String>>() {
            @Override
            public List<String> call() {
                List<String> suggestions = userProvider.getMatchUsername(createInviteUsername.getText().replace("@", ""));

                System.out.println(suggestions);

                return suggestions;
            }
        };
        loginTask.setOnSucceeded(response -> loadSuggestions((List<String>) loginTask.getValue()));
        new Thread(loginTask).start();
    }
}
