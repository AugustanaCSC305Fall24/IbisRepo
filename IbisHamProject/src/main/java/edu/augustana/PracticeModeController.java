package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.io.IOException;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.util.Random;

public class PracticeModeController {

    @FXML
    private Slider FrequencySlider;  // Ensure the name matches fx:id in FXML

    @FXML
    private Label FrequencyLabel;

    @FXML
    private Slider qualitySlider;

    @FXML
    private Slider amountSlider;

    @FXML
    private Slider speedSlider;

    @FXML
    private CheckBox visualizerCheckBox;

    @FXML
    private BarChart<String, Number> frequencyBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    public void initialize() {
        // Initialize slider to a default value of 0.0 to avoid null pointer exception
        if (FrequencySlider != null) {
            FrequencySlider.setValue(0.0);  // Ensure the slider starts at 0.0

            // Set an initial label value based on the slider’s current value
            FrequencyLabel.setText(String.format("Current Frequency: %.1f MHz", FrequencySlider.getValue()));

            // Add listener to update label when slider is moved
            FrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                FrequencyLabel.setText(String.format("Current Frequency: %.1f MHz", newValue.doubleValue()));



            });
            String[] frequencies = {"88", "90", "92", "94", "96", "98", "100", "102", "104", "106", "108"};
            xAxis.setCategories(javafx.collections.FXCollections.observableArrayList(frequencies));

            // Simulate random "busy" values for each frequency, with values from 1 to 10
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            Random random = new Random();
            for (String frequency : frequencies) {
                series.getData().add(new XYChart.Data<>(frequency, 1 + random.nextInt(10)));  // Values between 1 and 10
            }

            // Add data to the bar chart
            frequencyBarChart.getData().add(series);
        }
    }

    // Button action methods
    @FXML
    private void switchToDictionary() throws IOException {
        App.setRoot("dictionary");
    }

    @FXML
    private void switchtoHomePage() throws IOException {
        App.setRoot("homePage");
    }

    @FXML
    private void switchtoPracSettings() throws IOException {
        App.setRoot("settings");
    }

    @FXML
    private void pracLaunch() throws IOException {
        App.setRoot("PracLaunched");
    }

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
    private void switchToProbTypes() throws IOException {
        App.setRoot("");
    }

    // Save the settings and go back
    @FXML
    private void saveAndBack() throws IOException {
        double quality = qualitySlider.getValue();
        double amount = amountSlider.getValue();
        double speed = speedSlider.getValue();
        boolean isVisualizerEnabled = visualizerCheckBox.isSelected();

        System.out.println("Quality Slider Value: " + quality);
        System.out.println("Amount Slider Value: " + amount);
        System.out.println("Speed Slider Value: " + speed);
        System.out.println("Visualizer Enabled: " + isVisualizerEnabled);

        // Switch to the settings screen
        App.setRoot("settings");
    }
}
