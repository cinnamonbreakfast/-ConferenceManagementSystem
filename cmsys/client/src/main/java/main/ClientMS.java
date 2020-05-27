package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ui.StartWindow;

@ComponentScan({"ui", "provider"})
public class ClientMS extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("setup");

        FXMLLoader root = new FXMLLoader(StartWindow.class.getResource("/StartWindow.fxml"));
        root.setControllerFactory(context::getBean);

        AnchorPane acp = root.load();
        acp.getStylesheets().add("ui/forms/basic_forms.css");

        Scene scene = new Scene(acp, 600, 485);

        stage.setTitle("CMS");
        stage.setScene(scene);

        stage.show();
    }
}
