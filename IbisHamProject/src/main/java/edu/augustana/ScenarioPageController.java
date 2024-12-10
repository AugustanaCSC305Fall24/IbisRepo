package edu.augustana;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ScenarioPageController {

    @FXML
    private Button ConfirmButton;
    @FXML
    private ComboBox<?> ScenarioBox;

    @FXML
    private void switchToHomePage() throws IOException {
        App.setRoot("homePage");
    }
    @FXML
    private void switchToLaunchedPage() throws IOException{
        App.setRoot("ScenarioLaunched");
    }

}