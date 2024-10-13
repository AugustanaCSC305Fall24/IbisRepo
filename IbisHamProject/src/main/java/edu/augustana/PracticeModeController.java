package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class PracticeModeController {

    //element for the quality slider
    @FXML
    private Slider qualitySlider;

    //element for the amount  slider
    @FXML
    private Slider amountSlider;

    //element for the speed slider
    @FXML
    private Slider speedSlider;

    //element for the visualizer checkbox
    @FXML
    private CheckBox visualizerCheckBox;

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
        App.setRoot("settings");
    }

    @FXML
    private void pracLaunch() throws IOException, IOException {
        App.setRoot("PracLaunched");
    }

    //Button action method to switch screens to the practice main screen fxml file by the controller file
    @FXML
    private void switchToPracPage() throws IOException {
        App.setRoot("practiceMode");
    }

    @FXML
    private void switchToInterference() throws IOException {
        App.setRoot("");
    }

    @FXML
    private void switchToAccessibility() throws IOException {
        App.setRoot("accessibilityMenu");
    }

    @FXML
    private  void switchToProbTypes() throws IOException {
        App.setRoot("");
    }

    //Button action method to switch screens to the launched practice fxml file by the controller file
    @FXML
    private void saveAndBack() throws IOException {
        //got the values from the sliders and checkbox
        double quality = qualitySlider.getValue();
        double amount = amountSlider.getValue();
        double speed = speedSlider.getValue();
        boolean isVisualizerEnabled = visualizerCheckBox.isSelected();

        //print values to the command line for testing
        System.out.println("Quality Slider Value: " + quality);
        System.out.println("Amount Slider Value: " + amount);
        System.out.println("Speed Slider Value: " + speed);
        System.out.println("Visualizer Enabled: " + isVisualizerEnabled);

        //switch to the launched practice screen
        App.setRoot("settings");
    }

}

