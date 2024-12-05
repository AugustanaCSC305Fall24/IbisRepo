package edu.augustana;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Random;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javax.sound.sampled.LineUnavailableException;

public class PracticeModeController {
    private static final Random randomGen = new Random();
    private final DictionaryController dictionaryController = new DictionaryController();
    QuizBot chatBot = new QuizBot("Mr. Prof", "QuizBot");
    private int currentSpeed = 20;

    @FXML private Label FrequencyLabel;
    @FXML private Slider FrequencySlider;
    @FXML private Slider FilterSlider;
    @FXML private Slider speedSlider;
    @FXML private TextArea MessageBox;
    @FXML private TextArea TranslateBox;
    @FXML private TextArea MainMessageBox;
    @FXML private CheckBox SolarFlaresButton;
    @FXML private CheckBox StormyWeatherButton;
    @FXML private CheckBox MachineInterferButton;

    @FXML public void initialize() {

        HamRadio user = new HamRadio();

        // frequency slider
        if (FrequencySlider != null) {
            FrequencySlider.setValue(0.0);
            FrequencyLabel.setText(String.format("Current Frequency: %.3f MHz", FrequencySlider.getValue()));
            FrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                FrequencyLabel.setText(String.format("Current Frequency: %.3f MHz", newValue.doubleValue()));
                updateFilterRange(newValue.doubleValue());
                user.setTuningFrequency(newValue.doubleValue());
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

        // speed slider
        if (speedSlider != null){
            speedSlider.setValue(20.0);
            speedSlider.setMin(5);
            speedSlider.setMax(40);
            speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                currentSpeed = (int) speedSlider.getValue();
                AudioController.setSpeed(currentSpeed);
                System.out.println("The current speed is " + currentSpeed);
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

    //button action methods
    @FXML
    private void sendAction() {
        String msgText = MessageBox.getText();
        if (!msgText.isBlank()) {
            String morseText = dictionaryController.translateToMorseCode(msgText);
            TranslateBox.setText(morseText);

            String englishTranslation = dictionaryController.morseToEnglish(morseText);
            MessageBox.setText(englishTranslation);

            String existingText = MainMessageBox.getText();
            String fullMessage = "User: " + morseText + " (" + msgText + ")";
            MainMessageBox.setText(existingText + (existingText.isEmpty() ? "" : "\n") + fullMessage);

            MessageBox.clear();
            int BOT_SPEED_DELAY = randomGen.nextInt(5) + 4;
            PauseTransition pause = new PauseTransition(Duration.seconds(BOT_SPEED_DELAY));
            //pause.setOnFinished( e -> botResponse(englishTranslation, existingText));
            pause.play();

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
    private void clear(){
        TranslateBox.setText("");
    }

    public void botResponse(String englishTranslation, String existingText) {
        // make a thread for bot/s
        Thread botThread = new Thread(() -> {
            try {
                //delay for bot response
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //make response
            String chatResponse = chatBot.generateResponseMessage(englishTranslation);
            String chatResponseMorse = dictionaryController.translateToMorseCode(chatResponse);
            String botMessage = chatBot.getName() + ": " + chatResponseMorse + " (" + chatResponse + ")";


            // updates the ui to handle the thread
            javafx.application.Platform.runLater(() -> {
                String updatedText = MainMessageBox.getText();
                MainMessageBox.setText(updatedText + (updatedText.isEmpty() ? "" : "\n") + botMessage);
                TranslateBox.setText(chatResponseMorse);
            });
        });
        botThread.setDaemon(true); // stop the threads when app
        botThread.start();
    }


    @FXML
    private void play() {
        //thread creation for audio
        Thread audioThread = new Thread(() -> {
            try {
                // code from Zane that gets audio from the audio controller
                AudioController.playMorseMessage(TranslateBox.getText().trim(), FrequencySlider.getValue());
            } catch (LineUnavailableException | InterruptedException e) {
                // exception for audio issues
                e.printStackTrace();
            }
        });
        //set thread as daemon so closing app/app functionality isnt frozen during audio playing
        audioThread.setDaemon(true);
        audioThread.start();
    }

    @FXML private void switchToDictionary() throws IOException { App.setRoot("dictionary"); }
    @FXML private void switchtoHomePage() throws IOException { App.setRoot("homePage"); }
    @FXML private void switchtoPracSettings() throws IOException { App.setRoot("settings"); }
    @FXML private void switchToPracPage() throws IOException { App.setRoot("practiceMode"); }

    //save settings and launch practice mode
    @FXML private void pracLaunch() throws IOException {
        boolean SolarFlares = SolarFlaresButton.isSelected();
        boolean StormyWeather = StormyWeatherButton.isSelected();
        boolean MachineInterference = MachineInterferButton.isSelected();
        //test code
        System.out.println("Solar Flares: " + SolarFlares);
        System.out.println("Stormy Weather: " + StormyWeather);
        System.out.println("Machine Interference: " + MachineInterference);
        App.setRoot("PracLaunched");
    }

    @FXML
    private void handleKeyPress(KeyEvent event) throws LineUnavailableException, InterruptedException {
        StringBuilder old = new StringBuilder();
        old.append(TranslateBox.getText());
        if (event.getCode() == KeyCode.LEFT || event.getText().equals(".")) {
            AudioController.playMorseMessage(".", FrequencySlider.getValue());
            old.append(".");

        }
        if (event.getCode() == KeyCode.RIGHT || event.getText().equals("-")) {
            AudioController.playMorseMessage("-", FrequencySlider.getValue());
            old.append("-");
        }
        TranslateBox.setText(old.toString());
    }

    @FXML private void goBack() throws IOException { App.setRoot("settings"); }
}
