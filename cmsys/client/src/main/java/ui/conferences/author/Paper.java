package ui.conferences.author;

import dto.PaperDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Paper {
    @FXML public Label name;
    @FXML public Label date;

    PaperDTO paper;

    void setPaper(PaperDTO paper) {
        this.paper = paper;

        name.setText(paper.getTitle());
        date.setText("Keywords: "+paper.getKeywords());
    }
}
