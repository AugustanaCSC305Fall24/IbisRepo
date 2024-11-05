package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

public class PracticeModeController {
    private final DictionaryController dictionaryController = new DictionaryController();

    @FXML private Slider FrequencySlider;
    @FXML private Label FrequencyLabel;
    @FXML private Slider qualitySlider;
    @FXML private Slider amountSlider;
    @FXML private Slider speedSlider;
    @FXML private CheckBox visualizerCheckBox;
    @FXML private TextArea MessageBox;
    @FXML private TextArea TranslateBox;
    @FXML private TextArea MainMessageBox;
    @FXML private Button playButton;

    @FXML public void initialize() {
        // Frequency initialization
        if (FrequencySlider != null) {
            FrequencySlider.setValue(0.0);
            FrequencyLabel.setText(String.format("Current Frequency: %.3f MHz", FrequencySlider.getValue()));
            FrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                FrequencyLabel.setText(String.format("Current Frequency: %.3f MHz", newValue.doubleValue()));
            });
        }
    }

    // Method to translate plain text to Morse code using DictionaryController
    private String translateToMorseCode(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();

        // Convert text to upper case and iterate through each character
        for (char c : text.toUpperCase().toCharArray()) {
            String morseSymbol = dictionaryController.getMorseCode(c);
            if (!morseSymbol.isEmpty()) {
                morseCodeBuilder.append(morseSymbol).append(" ");
            } else {
                morseCodeBuilder.append("? ");
            }
        }
        return morseCodeBuilder.toString().trim();
    }

    // Button action methods
    @FXML
    private void sendAction() {
        String msgText = MessageBox.getText();

        // Check if MessageBox is not empty
        if (!msgText.isBlank()) {
            // Translate MessageBox content to Morse code
            String morseText = translateToMorseCode(msgText);
            TranslateBox.setText(morseText);

            // Translate Morse code back to English for confirmation
            String englishTranslation = dictionaryController.morseToEnglish(morseText);
            MessageBox.setText(englishTranslation);

            // Append the translated message to MainMessageBox
            String existingText = MainMessageBox.getText();
            String fullMessage = "User: " + morseText + " (" + msgText + ")";
            MainMessageBox.setText(existingText + (existingText.isEmpty() ? "" : "\n") + fullMessage);

            // Clear MessageBox after sending
            MessageBox.clear();
        } else {
            // If MessageBox is empty, check if TranslateBox contains Morse code
            String morseCode = TranslateBox.getText().trim();
            if (!morseCode.isEmpty()) {
                // Translate Morse code to English and update MessageBox
                String englishTranslation = dictionaryController.morseToEnglish(morseCode);
                MessageBox.setText(englishTranslation);

                // Append the Morse code message to MainMessageBox
                String existingText = MainMessageBox.getText();
                String fullMessage = "User: " + morseCode + " (" + englishTranslation + ")";
                MainMessageBox.setText(existingText + (existingText.isEmpty() ? "" : "\n") + fullMessage);

                // Clear TranslateBox after sending
                TranslateBox.clear();
            }
        }
    }


    @FXML
    private void play() throws LineUnavailableException, InterruptedException {
        AudioController.playSound(TranslateBox.getText().trim());
    }

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
