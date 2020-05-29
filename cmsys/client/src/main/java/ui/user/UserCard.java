package ui.user;

import dto.UserDTO;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.ConferenceProvider;
import provider.UserProvider;
import ui.conferences.Hello;

public class UserCard {
    @FXML public Rectangle avatar;
    @FXML public Text name;
    @FXML public Text rank;
    @FXML public ImageView crown;
    @FXML public AnchorPane card;

    private UserProvider userProvider;

    @Autowired
    private Hello hello;

    private UserDTO user;

    public void setUserProvider(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    public void setUser(String username, String rank)
    {
        this.name.setText(username);
        this.rank.setText(rank);
        ImagePattern pattern = new ImagePattern(new Image(userProvider.getURL()+"/usr/"+username+".jpg"));
        avatar.setFill(pattern);

        if(!rank.equals("Co-Chair"))
        {
            crown.setVisible(false);
        }
    }

    public UserDTO getUser()
    {
        return user;
    }

    public void setUser(UserDTO user)
    {
        this.user = user;
        this.name.setText(user.getLastName());
    }

    @FXML
    void initialize()
    {
        TranslateTransition floatAnimation = new TranslateTransition(Duration.millis(3000), crown);
        floatAnimation.setFromX(crown.getTranslateX());
        floatAnimation.setToX(crown.getTranslateX()+5);
        floatAnimation.setFromY(crown.getTranslateY());
        floatAnimation.setToY(crown.getTranslateY()-5);
        floatAnimation.setCycleCount(Timeline.INDEFINITE);
        floatAnimation.setAutoReverse(true);
        floatAnimation.play();
    }

    public void deleteUser(MouseEvent mouseEvent) {
        hello.deleteUser(user.getUsername(), rank.getText());
        System.out.println("User deleted");
    }
}
