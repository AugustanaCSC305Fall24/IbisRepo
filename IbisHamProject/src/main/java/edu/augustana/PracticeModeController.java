package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import javax.sound.sampled.LineUnavailableException;

public class PracticeModeController {
    private final DictionaryController dictionaryController = new DictionaryController();

    @FXML private Slider FrequencySlider;
    @FXML private Label FrequencyLabel;
    @FXML private Slider FilterSlider;
    @FXML private Slider qualitySlider;
    @FXML private Slider amountSlider;
    @FXML private Slider speedSlider;
    @FXML private CheckBox visualizerCheckBox;
    @FXML private TextArea MessageBox;
    @FXML private TextArea TranslateBox;
    @FXML private TextArea MainMessageBox;
    @FXML private Button playButton;

    @FXML public void initialize() {



        //frequency should be at 

        // frequency slider

        if (FrequencySlider != null) {
            FrequencySlider.setValue(0.0);
            FrequencyLabel.setText(String.format("Current Frequency: %.3f MHz", FrequencySlider.getValue()));
            FrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                FrequencyLabel.setText(String.format("Current Frequency: %.3f MHz", newValue.doubleValue()));
                updateFilterRange(newValue.doubleValue());
            });
        }

        // filter slider
        if (FilterSlider != null) {
            FilterSlider.setMax(0.026); // maximum filter range is 0.026 MHz (just a random choice)
            FilterSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                double currentFrequency = FrequencySlider.getValue();
                updateFilterRange(currentFrequency);
            });
        }
    }

    //change the displayed range based on current frequency and filter width
    private void updateFilterRange(double currentFrequency) {
        double filterValue = FilterSlider.getValue();
        double minFrequency = Math.max(7.000, currentFrequency - filterValue / 2);
        double maxFrequency = Math.min(7.067, currentFrequency + filterValue / 2);
        if (minFrequency == maxFrequency){ //this gets rid of "range: 0.055 - 0.055" when filter is 0
            FrequencyLabel.setText(String.format("Current Frequency Range: %.3f MHz", minFrequency));
        } else {
            FrequencyLabel.setText(String.format("Current Frequency Range: %.3f - %.3f MHz", minFrequency, maxFrequency));
        }
    }

    // text to Morse code using DictionaryController
    private String translateToMorseCode(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();
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

    //button action methods
    @FXML
    private void sendAction() {
        String msgText = MessageBox.getText();
        if (!msgText.isBlank()) {
            String morseText = translateToMorseCode(msgText);
            TranslateBox.setText(morseText);

            String englishTranslation = dictionaryController.morseToEnglish(morseText);
            MessageBox.setText(englishTranslation);

            String existingText = MainMessageBox.getText();
            String fullMessage = "User: " + morseText + " (" + msgText + ")";
            MainMessageBox.setText(existingText + (existingText.isEmpty() ? "" : "\n") + fullMessage);

            MessageBox.clear();
        } else {
            String morseCode = TranslateBox.getText().trim();
            if (!morseCode.isEmpty()) {
                String englishTranslation = dictionaryController.morseToEnglish(morseCode);
                MessageBox.setText(englishTranslation);

                String existingText = MainMessageBox.getText();
                String fullMessage = "User: " + morseCode + " (" + englishTranslation + ")";
                MainMessageBox.setText(existingText + (existingText.isEmpty() ? "" : "\n") + fullMessage);

                TranslateBox.clear();
            }
        }
    }

    @FXML
    private void play() throws LineUnavailableException, InterruptedException {
        AudioController.playSound(TranslateBox.getText().trim());
    }

    @FXML private void switchToDictionary() throws IOException { App.setRoot("dictionary"); }
    @FXML private void switchtoHomePage() throws IOException { App.setRoot("homePage"); }
    @FXML private void switchtoPracSettings() throws IOException { App.setRoot("settings"); }
    @FXML private void pracLaunch() throws IOException { App.setRoot("PracLaunched"); }
    @FXML private void switchToPracPage() throws IOException { App.setRoot("practiceMode"); }
    @FXML private void switchToAccessibility() throws IOException { App.setRoot("accessibilityMenu"); }
    @FXML private void switchToInterference() throws IOException { App.setRoot("interferenceMenu"); }
    @FXML private void switchToProbTypes() throws IOException { App.setRoot("problemTypesMenu"); }

    //save the settings and go back
    @FXML private void saveAndBack() throws IOException {
        double quality = qualitySlider.getValue();
        double amount = amountSlider.getValue();
        double speed = speedSlider.getValue();
        boolean isVisualizerEnabled = visualizerCheckBox.isSelected();
        App.setRoot("settings");
        System.out.println("Quality Slider Value: " + quality);
        System.out.println("Amount Slider Value: " + amount);
        System.out.println("Speed Slider Value: " + speed);
        System.out.println("Visualizer Enabled: " + isVisualizerEnabled);
    }

    @FXML
    private void handleKeyPress(KeyEvent event) throws LineUnavailableException, InterruptedException {
        if(event.getCode()==KeyCode.LEFT){
            AudioController.playSound(".");
        }
        if(event.getCode()==KeyCode.RIGHT){
            AudioController.playSound("-");
        }
    }



    @FXML private void goBack() throws IOException { App.setRoot("settings"); }
}
