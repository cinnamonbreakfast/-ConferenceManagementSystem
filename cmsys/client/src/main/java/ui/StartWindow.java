package ui;

import dto.UserDTO;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.UserProvider;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class StartWindow {

    @FXML public Button loginBtn;
    @FXML private AnchorPane anchpane;
    @FXML public TextField login_user_textbox;
    @FXML public PasswordField login_password_textbox;
    @FXML public ImageView loginHero;

    private AnnotationConfigApplicationContext applicationContext;

    @Autowired
    private UserProvider userProvider;

    AnchorPane getView() {
        return anchpane;
    }

    void loadNext(UserDTO userDTO) {
        if(userDTO.getToken() != null)
        {
            try {
                FXMLLoader root2 = new FXMLLoader(MainWindow.class.getResource("/MainWindow.fxml"));
                root2.setControllerFactory(applicationContext::getBean);
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
            System.out.println("Bad login");
        }
    }

    @FXML
    void loginUser(ActionEvent event) {
//        CompletableFuture<UserDTO> promise = userProvider.login(login_user_textbox.getText(), login_password_textbox.getText());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                UserDTO result = userProvider.login(login_user_textbox.getText(), login_password_textbox.getText());

                if(result != null)
                {
                    loadNext(result);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.getDialogPane().getStylesheets().add("ui/forms/basic_forms.css");;
                    alert.setTitle("Login");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password.");

                    alert.showAndWait();
                }
            }
        });


//        try {
//            FXMLLoader root2 = new FXMLLoader(MainWindow.class.getResource("/MainWindow.fxml"));
//            Parent parent = (Parent) root2.load();
//
//            Stage new_stage = new Stage();
//            new_stage.setTitle("CMS 2");
//            new_stage.setScene(new Scene(parent));
//            new_stage.show();
//
//            Stage stage = (Stage) loginBtn.getScene().getWindow();
//            stage.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
