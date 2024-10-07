package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PracticeModeController {


    @FXML
    private void switchToDictionary() throws IOException, IOException {
        App.setRoot("dictionary");
    }

    @FXML
    private void switchtoHomePage() throws IOException, IOException {
        App.setRoot("homePage");
    }

    @FXML
    private void switchtoPracSettings() throws IOException, IOException {
        App.setRoot("pracSetPage");
    }

    @FXML
    private void switchToPracPage() throws IOException {
        App.setRoot("practiceMode");
    }

    @FXML
    private void switchToPracLaunch() throws IOException {
        App.setRoot("pracLaunched");
    }





}

