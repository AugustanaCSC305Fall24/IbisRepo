package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class HomePageController {

    @FXML
    private void switchToPracticeMode() throws IOException {
        App.setRoot("practiceMode");
    }
}
