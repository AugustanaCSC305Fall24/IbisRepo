package edu.augustana;

import javafx.fxml.FXML;

import java.io.IOException;

public class DictionaryController {

        @FXML
        private void switchToHomePage() throws IOException {
            App.setRoot("homePage");
        }

        @FXML
        private void switchToPracPage() throws IOException {
            App.setRoot("practiceMode");
        }

}
