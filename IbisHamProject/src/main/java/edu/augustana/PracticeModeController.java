package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.util.Random;

public class PracticeModeController {

    @FXML private Slider FrequencySlider;
    @FXML private Label FrequencyLabel;
    @FXML private Slider qualitySlider;
    @FXML private Slider amountSlider;
    @FXML private Slider speedSlider;
    @FXML private CheckBox visualizerCheckBox;
    @FXML private BarChart<String, Number> frequencyBarChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private TextArea MessageBox;
    @FXML private TextArea TranslateBox;
    @FXML private Button backButton;

    private DictionaryController dictControl;

    @FXML public void initialize() {
        dictControl = new DictionaryController();  //create instance of DictionaryController for Morse code
        dictControl.engToMorseMap();      //initialize the Morse code map

        //make 0.0 so error doesnt occur
            if (FrequencySlider != null) {
            String[] frequencies = {"88", "90", "92", "94", "96", "98", "100", "102", "104", "106", "108"};
            Random random = new Random();

            FrequencySlider.setValue(0.0);
            FrequencyLabel.setText(String.format("Current Frequency: %.1f MHz", FrequencySlider.getValue()));
            FrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                FrequencyLabel.setText(String.format("Current Frequency: %.1f MHz", newValue.doubleValue()));
            });

            xAxis.setCategories(javafx.collections.FXCollections.observableArrayList(frequencies));
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (String frequency : frequencies) {
                series.getData().add(new XYChart.Data<>(frequency, 1 + random.nextInt(10)));
            }
            frequencyBarChart.getData().add(series);

            // code to add the translation to text box live
            MessageBox.textProperty().addListener((observable, oldValue, newValue) -> {
                String morseCode = translateToMorseCode(newValue);
                TranslateBox.setText(morseCode);
            });
        }
    }

    //translation code from english to morse
    private String translateToMorseCode(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();

        //convert to all caps for uniformality
        for (char c : text.toUpperCase().toCharArray()) {
            String morseCode = dictControl.getMorseCode(c);
            if (!morseCode.isEmpty()) {
                morseCodeBuilder.append(morseCode).append(" ");
            } else {
                // ? for unknown characters
                morseCodeBuilder.append("? ");
            }
        }

        return morseCodeBuilder.toString().trim();
    }

    // Button action methods
    @FXML private void switchToDictionary() throws IOException {
        App.setRoot("dictionary");
    }

    @FXML private void switchtoHomePage() throws IOException {
        App.setRoot("homePage");
    }

    @FXML private void switchtoPracSettings() throws IOException {
        App.setRoot("settings");
    }

    @FXML private void pracLaunch() throws IOException {
        App.setRoot("PracLaunched");
    }

    @FXML private void switchToPracPage() throws IOException {
        App.setRoot("practiceMode");
    }

    //settings fxml buttons
    @FXML private void switchToAccessibility() throws IOException {
        App.setRoot("accessibilityMenu");
    }

    @FXML private void switchToInterference() throws IOException {
        App.setRoot("interferenceMenu");
    }

    @FXML private void switchToProbTypes() throws IOException {
        App.setRoot("problemTypesMenu");
    }

    @FXML private void saveAndBack() throws IOException {
        double quality = qualitySlider.getValue();
        double amount = amountSlider.getValue();
        double speed = speedSlider.getValue();
        boolean isVisualizerEnabled = visualizerCheckBox.isSelected();
        App.setRoot("settings");

        // Test code
        System.out.println("Quality Slider Value: " + quality);
        System.out.println("Amount Slider Value: " + amount);
        System.out.println("Speed Slider Value: " + speed);
        System.out.println("Visualizer Enabled: " + isVisualizerEnabled);
    }

    @FXML private void goBack() throws IOException {
        App.setRoot("settings");
    }
}
