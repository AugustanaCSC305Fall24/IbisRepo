package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class PracticeModeController {

    @FXML
    private void switchToTDictionary() throws IOException {
        App.setRoot("dictionary");
    }
}