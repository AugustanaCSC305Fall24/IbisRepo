package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class HomePageController {

    //button action method to switch to practice mode: one of 3 planned possible choices currently
    @FXML
    private void switchToPracPage() throws IOException {
        App.setRoot("PracLaunched");
    }

    @FXML
    private void switchToScenarioPage() throws IOException {
        App.setRoot("ScenarioPage");
    }

    @FXML
    private void switchToMultiPage() throws IOException {
        App.setRoot("MultiLaunched");
    }

}
