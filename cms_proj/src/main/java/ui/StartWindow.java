package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.*;

import java.io.IOException;

public class StartWindow extends Application {

    @FXML public AnchorPane anchpane;
    @FXML private VBox mainLayout;

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
        AnchorPane anchorPane = root.load();

        Scene scene = new Scene(anchorPane, 700, 500);

        mainLayout.maxHeight(100);

        stage.setTitle("CMS");
        stage.setScene(scene);
        stage.show();
    }
}
