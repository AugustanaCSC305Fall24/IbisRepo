package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class SettingsController {
    @FXML
    private void switchToInterference() throws IOException {
        App.setRoot("");
    }

    @FXML
    private void switchToAccessibility() throws IOException {
        App.setRoot("");
    }

    @FXML
    private  void switchToProbTypes() throws IOException {
        App.setRoot("");
    }
}
