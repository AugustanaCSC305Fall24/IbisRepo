package edu.augustana;

import javafx.fxml.FXML;

import java.io.IOException;

public class TertiaryController {

        @FXML
        private void switchToPrimary() throws IOException {
            App.setRoot("primary");
        }

}
