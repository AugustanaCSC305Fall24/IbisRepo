package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.io.File;
import java.io.IOException;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PracticeModeController {
    private final Map<Character, String> morseCodeMap = new HashMap<>();

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

    @FXML public void initialize() { // adds barchart with random 'busy' frequencies to listen too
        // Initialize slider to a default value of 0.0 to avoid null pointer exception
        if (FrequencySlider != null) {
            String[] frequencies = {"88", "90", "92", "94", "96", "98", "100", "102", "104", "106", "108"};
            Random random = new Random();

            FrequencySlider.setValue(0.0);
            FrequencyLabel.setText(String.format("Current Frequency: %.1f MHz", FrequencySlider.getValue()));
            FrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                FrequencyLabel.setText(String.format("Current Frequency: %.1f MHz", newValue.doubleValue()));
            });

            xAxis.setCategories(javafx.collections.FXCollections.observableArrayList(frequencies));
            // random "busy" values for each frequency, with values from 1 to 10
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (String frequency : frequencies) {
                series.getData().add(new XYChart.Data<>(frequency, 1 + random.nextInt(10)));
            }
            frequencyBarChart.getData().add(series);

            initializeMorseCodeMap();

            // Add listener to MessageBox for real-time Morse code translation
            MessageBox.textProperty().addListener((observable, oldValue, newValue) -> {
                String morseCode = translateToMorseCode(newValue);
                TranslateBox.setText(morseCode);  // Update TranslateBox with Morse code
            });

            
        }
    }

    private void initializeMorseCodeMap() {
        morseCodeMap.put('A', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('Z', "--..");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");
        morseCodeMap.put('0', "-----");
        morseCodeMap.put(' ', "/"); // Slash to represent space between words
    }

    // Method to translate plain text to Morse code
    private String translateToMorseCode(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();

        // Convert text to upper case and iterate through each character
        for (char c : text.toUpperCase().toCharArray()) {
            if (morseCodeMap.containsKey(c)) {
                morseCodeBuilder.append(morseCodeMap.get(c)).append(" "); // Separate each Morse symbol with space
            } else {
                morseCodeBuilder.append("? "); // Use '?' for unknown characters
            }
        }

        return morseCodeBuilder.toString().trim();
    }

    //button action methods
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

    @FXML private void switchToAccessibility() throws IOException {
        App.setRoot("accessibilityMenu");
    }
    
    @FXML private void switchToInterference() throws IOException {
        App.setRoot("interferenceMenu");
    }

    @FXML private void switchToProbTypes() throws IOException {
        App.setRoot("problemTypesMenu");
    }

    // Save the settings and go back
    @FXML private void saveAndBack() throws IOException {
        double quality = qualitySlider.getValue();
        double amount = amountSlider.getValue();
        double speed = speedSlider.getValue();
        boolean isVisualizerEnabled = visualizerCheckBox.isSelected();
        App.setRoot("settings");
        //test code below
        System.out.println("Quality Slider Value: " + quality);
        System.out.println("Amount Slider Value: " + amount);
        System.out.println("Speed Slider Value: " + speed);
        System.out.println("Visualizer Enabled: " + isVisualizerEnabled);
    }

    @FXML private void goBack() throws IOException {
        App.setRoot("settings");
    }

    @FXML private void onPlayAudioDit(){
        String musicFile = "C:\\git\\IbisRepo\\IbisHamProject\\src\\main\\resources\\dit.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    @FXML private void onPlayAudioDash(){
        String musicFile = "C:\\git\\IbisRepo\\IbisHamProject\\src\\main\\resources\\dash.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
