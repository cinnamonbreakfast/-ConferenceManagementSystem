package ui.conferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.tools.Duplicatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.ConferenceProvider;
import provider.UserProvider;
import org.controlsfx.control.textfield.TextFields;

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

    private int step = 0;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private ConferenceProvider conferenceProvider;

    @Autowired
    private AnnotationConfigApplicationContext context;

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
            System.out.println("create");
        }
    }


    @FXML
    void initialize()
    {
        setStep(0);
        step = 0;
        conferenceProvider.setToken(userProvider.getToken());

        String[] suggestions = {"@raduceaca", "@bunuradu", "@radcoaiele"};

        TextFields.bindAutoCompletion(createInviteUsername, suggestions);
    }
}
