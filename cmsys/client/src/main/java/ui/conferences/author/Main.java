package ui.conferences.author;

import dto.PaperDTO;
import dto.PermissionDTO;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.ConferenceProvider;
import provider.UserProvider;
import ui.MainWindow;

import java.io.IOException;
import java.util.List;

public class Main {
    @FXML public TabPane tabPane;
    @FXML public AnchorPane mainAuthor;
    @FXML public VBox paperList;


    PermissionDTO permission;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private ConferenceProvider conferenceProvider;

    @Autowired
    private AnnotationConfigApplicationContext context;

    @Autowired
    MainWindow mainWindow;

    public void setPermission(PermissionDTO perm) {
        permission = perm;
    }

    public void tabClick(Event event) {
        String tab = tabPane.getSelectionModel().getSelectedItem().getText();
        Integer tabID = tabPane.getSelectionModel().getSelectedIndex();

        if(tabID == 0)
        {
            mainWindow.mainContent.getChildren().clear();
            mainWindow.mainContent.getChildren().add(mainAuthor);
        }
    }

    public void cancelUpload() {
        mainAuthor.setVisible(true);
        mainAuthor.setManaged(true);
    }

    public void uploadFile(ActionEvent actionEvent) {
        FXMLLoader authorMain = new FXMLLoader(SelectFile.class.getResource("/FXML/conferences/author/SelectFile.fxml"));
        authorMain.setControllerFactory(context::getBean);

        try {
            AnchorPane test = authorMain.load();

            AnchorPane.setBottomAnchor(test, 0D);
            AnchorPane.setRightAnchor(test, 0D);
            AnchorPane.setTopAnchor(test, 0D);
            AnchorPane.setLeftAnchor(test, 0D);

            mainWindow.mainContent.getChildren().add(test);

            mainAuthor.setVisible(false);
            mainAuthor.setManaged(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMyPapers() {
        paperList.getChildren().clear();

        List<PaperDTO> papers = conferenceProvider.getMyPapersFromConference().getPapers();

        System.out.println(papers);

        papers.forEach(e -> {
            FXMLLoader authorMain = new FXMLLoader(Paper.class.getResource("/FXML/Paper.fxml"));

            try {
                AnchorPane test = authorMain.load();

                Paper paper = (Paper) authorMain.getController();
                paper.setPaper(e);

                paperList.getChildren().add(test);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    @FXML
    void initialize() {
        loadMyPapers();
    }
}
