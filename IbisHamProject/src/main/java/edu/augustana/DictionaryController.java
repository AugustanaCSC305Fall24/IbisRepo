package edu.augustana;

import javafx.fxml.FXML;

import java.io.IOException;

public class DictionaryController {

        @FXML
        private void switchToPrimary() throws IOException {
            App.setRoot("homePage");
        }

}
