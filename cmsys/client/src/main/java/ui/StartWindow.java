package ui;

import dto.UserDTO;
import dto.UserRegisterDTO;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.UserProvider;

import java.io.IOException;

public class StartWindow {

    @FXML public Button loginBtn;
    @FXML public Tab signUpTab;
    @FXML public Tab signInTab;
    @FXML public Button signUpBtn;
    @FXML public TextField signup_Email;
    @FXML public TextField signup_FirstName;
    @FXML public TextField signup_LastName;
    @FXML public TextField signup_Username;
    @FXML public PasswordField signup_Password;
    @FXML private AnchorPane anchpane;
    @FXML public TextField login_user_textbox;
    @FXML public PasswordField login_password_textbox;
    @FXML public ImageView loginHero;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private AnnotationConfigApplicationContext context;

    AnchorPane getView() {
        return anchpane;
    }

    void loadNext(UserDTO userDTO) {
        if(userDTO != null) {
            if (userDTO.getToken() != null) {
                try {
                    FXMLLoader root2 = new FXMLLoader(MainWindow.class.getResource("/MainWindow.fxml"));
                    root2.setControllerFactory(context::getBean);
                    Parent parent = (Parent) root2.load();

                    Stage new_stage = new Stage();
                    new_stage.setTitle("CMS 2");
                    new_stage.setScene(new Scene(parent));
                    new_stage.show();

                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    System.out.println("error");
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");
                ;
                alert.setTitle("Sign In");
                alert.setHeaderText(null);
                alert.setContentText("Cannot login at this time. Please, try again later (code LN74).");

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");;
            alert.setTitle("Sign Up");
            alert.setHeaderText(null);
            alert.setContentText("Cannot match the data. Check your credentials again?");

            alert.showAndWait();
        }
    }

    @FXML
    void loginUser(ActionEvent event) {
        if(login_user_textbox.getText().trim().isEmpty() || login_user_textbox.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");;
            alert.setTitle("Sign Up");
            alert.setHeaderText(null);
            alert.setContentText("Please, fill up the form.");

            alert.showAndWait();

            return;
        }

        Task loginTask = new Task<UserDTO>() {
            @Override
            public UserDTO call() {
                UserDTO result = userProvider.login(login_user_textbox.getText(), login_password_textbox.getText());

                return result;
            }
        };
        loginTask.setOnSucceeded(response -> loadNext((UserDTO) loginTask.getValue()));
        new Thread(loginTask).start();
    }

    public void registerResponse(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");;
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void signUp(ActionEvent actionEvent) {
        if(signup_Email.getText().trim().isEmpty() ||
        signup_FirstName.getText().trim().isEmpty() ||
        signup_LastName.getText().trim().isEmpty() ||
        signup_Username.getText().trim().isEmpty() ||
        signup_Password.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");;
            alert.setTitle("Sign Up");
            alert.setHeaderText(null);
            alert.setContentText("Please, fill up the form.");

            alert.showAndWait();

            return;
        }


        Task task = new Task<String>() {
            @Override
            public String call() {
                UserRegisterDTO dto = new UserRegisterDTO();
                dto.setEmail(signup_Email.getText());
                dto.setFirstName(signup_FirstName.getText());
                dto.setLastName(signup_LastName.getText());
                dto.setUsername(signup_Username.getText());
                dto.setPassword(signup_Password.getText());

                String result = userProvider.register(dto);

                return result;
            }
        };
        task.setOnSucceeded(response -> registerResponse(task.getMessage()));
        new Thread(task).start();
    }

    @FXML
    void signUpPage(Event event) {
        if(signUpTab.isSelected())
        {
            anchpane.prefHeight(620);
        } else {
            anchpane.prefHeight(485);
        }
    }

    @FXML
    void signInPage(Event event) {
        anchpane.setPrefHeight(485);
    }

    @FXML
    void initialize(){
        TranslateTransition floatAnimation = new TranslateTransition(Duration.millis(3000), loginHero);
        floatAnimation.setFromY(loginHero.getTranslateY());
        floatAnimation.setToY(loginHero.getTranslateY()+20);
        floatAnimation.setCycleCount(Timeline.INDEFINITE);
        floatAnimation.setAutoReverse(true);
        floatAnimation.play();
    }
}
