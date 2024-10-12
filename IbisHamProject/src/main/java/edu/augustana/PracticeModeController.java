package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PracticeModeController {

    //Button action method to switch screens to the morse code dictionary fxml file by the controller file
    @FXML
    private void switchToDictionary() throws IOException, IOException {
        App.setRoot("dictionary");
    }

    //Button action method to switch screens to the home screen fxml file by the controller file
    @FXML
    private void switchtoHomePage() throws IOException, IOException {
        App.setRoot("homePage");
    }

    //Button action method to switch screens to the accessibility menu/settings fxml file by the controller file
    @FXML
    private void switchtoPracSettings() throws IOException, IOException {
        App.setRoot("accessibilityMenu");
    }

    //Button action method to switch screens to the practice main screen fxml file by the controller file
    @FXML
    private void switchToPracPage() throws IOException {
        App.setRoot("practiceMode");
    }

    //Button action method to switch screens to the launched practice fxml file by the controller file
    @FXML
    private void switchToPracLaunch() throws IOException {
        App.setRoot("pracLaunched");
    }





}

