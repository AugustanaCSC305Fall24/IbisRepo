package edu.augustana;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Random;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.sound.sampled.LineUnavailableException;

public class PracticeModeController {

    private final DictionaryController dictionaryController = new DictionaryController();
    ChatBot chatBot = new QuizBot("Mr. Prof", "QuizBot");
    private int currentSpeed = 20;

    @FXML private Label FrequencyLabel;
    @FXML private Slider FrequencySlider;
    @FXML private Slider FilterSlider;
    @FXML private Slider speedSlider;
    @FXML private TextArea MessageBox;
    @FXML private TextArea TranslateBox;
    @FXML private TextArea MainMessageBox;
    @FXML private CheckBox disButton;

    //initializes fxml sliders and page
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
        if (speedSlider != null) {
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
        if (minFrequency == maxFrequency) { //this gets rid of "range: 0.055 - 0.055" when filter is 0
            FrequencyLabel.setText(String.format("Current Frequency Range: %.3f MHz", minFrequency));
        } else {
            FrequencyLabel.setText(String.format("Current Frequency Range: %.3f - %.3f MHz", minFrequency, maxFrequency));
        }
    }

    //method that handles bot messages getting sent to the text area
    @FXML
    private void sendAction() {
        //message from user
        //translates message
        String msgText = MessageBox.getText().trim(); // Trim whitespace
        if (!msgText.isBlank()) {
            String morseText = dictionaryController.translateToMorseCode(msgText);
            TranslateBox.setText(morseText);

            String englishTranslation = dictionaryController.morseToEnglish(morseText);
            MessageBox.setText(englishTranslation);


            String existingText = MainMessageBox.getText();
            String fullMessage = "User: " + morseText + " (" + msgText + ")";
            MainMessageBox.setText(existingText + (existingText.isEmpty() ? "" : "\n") + fullMessage);

            MessageBox.clear();
            botResponse(englishTranslation, existingText);
        }
        else {
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

    //clears the boxes
    @FXML
    private void clear() {
        TranslateBox.setText("");
    }

    //response by bots and threading for them
    public void botResponse(String userMessage, String existingText) {
        // make a thread for bot/s
        Thread botThread = new Thread(() -> {
            try {
                //delay for bot response
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //make response
            String chatResponse = chatBot.generateResponseMessage(userMessage);
            String chatResponseMorse = dictionaryController.translateToMorseCode(chatResponse);
            String botMessage = chatBot.getName() + ": " + chatResponseMorse + " (" + chatResponse + ")";


            // updates the ui to handle the thread
            javafx.application.Platform.runLater(() -> {
                String updatedText = MainMessageBox.getText();
                MainMessageBox.setText(updatedText + (updatedText.isEmpty() ? "" : "\n") + botMessage);

            });
        });

        botThread.setDaemon(true); // stop the threads when app
        botThread.start();
    }


    //method to play morse code that user types into translating box
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

    @FXML
    private void switchToDictionary() throws IOException {
        App.setRoot("dictionary");
    }

    @FXML
    private void switchtoHomePage() throws IOException {
        App.setRoot("homePage");
    }

    //method to handle the paddles and audio for pressing < or > on keyboard
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

    //distortion button settings and saving state
    @FXML
    public void disButton(){
        boolean isDistortion = disButton.isSelected();
        AudioController.setDistortion(isDistortion);
    }


    @FXML private void goBack() throws IOException {
        App.setRoot("settings");
    }
}
