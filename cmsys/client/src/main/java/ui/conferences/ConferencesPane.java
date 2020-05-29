package ui.conferences;

import dto.ConferenceDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import ui.MainWindow;

public class ConferencesPane {

    @FXML public Text title;
    @FXML public Label author;
    @FXML public Label description;
    @FXML public Label phase;

    private ConferenceDTO dto;

    private MainWindow mainWindow;

    public void setParent(MainWindow parent)
    {
        mainWindow = parent;
    }

    public void setDTO(ConferenceDTO conferenceDTO)
    {
        title.setText(conferenceDTO.getTitle());
        author.setText(conferenceDTO.getChair().getFirstName()+" "+conferenceDTO.getChair().getLastName());
        description.setText(conferenceDTO.getDescription());
        phase.setText("Phase "+ conferenceDTO.getPhase());

        dto = conferenceDTO;
    }

    public void openEvent(MouseEvent mouseEvent) {
        mainWindow.openEvent(dto);
    }
}
