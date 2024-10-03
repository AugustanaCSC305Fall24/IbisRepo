package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class PracticeModeController {

    @FXML
    private void switchToTertiary() throws IOException {
        App.setRoot("tertiary");
    }
}