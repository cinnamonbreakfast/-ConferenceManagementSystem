package ui;

import com.sun.tools.javac.Main;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.naming.PartialResultException;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartWindow extends Application {

    @FXML public Button loginBtn;
    @FXML private AnchorPane anchpane;
    @FXML public TextField login_user_textbox;
    @FXML public ImageView loginHero;

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage stage) throws IOException {
//        Label label = new Label("Main window");
//        Scene scene = new Scene(label);
//        primaryStage.setScene(scene);
//        primaryStage.show();

        FXMLLoader root = new FXMLLoader(getClass().getResource("/StartWindow.fxml"));
        anchpane = root.load();
        anchpane.getStylesheets().add("ui/forms/basic_forms.css");

        Scene scene = new Scene(anchpane, 600, 485);

        stage.setTitle("CMS");
        stage.setScene(scene);
        stage.show();

        


    }


    private void displayExamples(){
        loginBtn.setOnAction(actionEvent -> {
            try {
                FXMLLoader root2 = new FXMLLoader(MainWindow.class.getResource("/MainWindow.fxml"));
                Parent parent = (Parent) root2.load();

                Stage new_stage = new Stage();
                new_stage.setTitle("CMS 2");
                new_stage.setScene(new Scene(parent));
                new_stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void loginUser(ActionEvent event) {
        try {
            FXMLLoader root2 = new FXMLLoader(MainWindow.class.getResource("/MainWindow.fxml"));
            Parent parent = (Parent) root2.load();

            Stage new_stage = new Stage();
            new_stage.setTitle("CMS 2");
            new_stage.setScene(new Scene(parent));
            new_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){
        TranslateTransition floatAnimation = new TranslateTransition(Duration.millis(3000), loginHero);
        floatAnimation.setFromY(loginHero.getTranslateY());
        floatAnimation.setToY(loginHero.getTranslateY()+20);
        floatAnimation.setCycleCount(Timeline.INDEFINITE);
        floatAnimation.setAutoReverse(true);
        floatAnimation.play();
        displayExamples();

    }
}
