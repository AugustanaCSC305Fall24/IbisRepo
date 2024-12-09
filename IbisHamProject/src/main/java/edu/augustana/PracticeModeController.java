package edu.augustana;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.sound.sampled.LineUnavailableException;

public class PracticeModeController {

    private final DictionaryController dictionaryController = new DictionaryController();
    QuizBot quizBot = new QuizBot("Mr. Prof", "QuizBot");
    private int currentSpeed = 20;
    private int questionCount = 0;
    private int currentLevel = 1;
    private int numQuestions = 3;

    private final List<String> playQueue = new ArrayList<>();

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

        updateMainMessage(quizBot.getName(), quizBot.startMessage());
        updateMainMessage(quizBot.getName(), quizBot.askLevelSelection());
    }

    private void updateMainMessage(String sender, String engText){
        String morseText = dictionaryController.translateToMorseCode(engText);
        TranslateBox.setText(morseText);
        String message = sender + ": " + morseText +" (" + engText + ")";
        String existingText = MainMessageBox.getText();
        MainMessageBox.setText(existingText + (existingText.isEmpty() ? "" : "\n") + message);

        playQueue.add(morseText);
    }

    @FXML
    private void sendAction() {
        String userInput = MessageBox.getText().trim();
        if (userInput.isBlank()) return;
        updateMainMessage("User", userInput);

        if ((questionCount == 0 || questionCount == numQuestions) && userInput.matches("[123]")) {
            currentLevel = Integer.parseInt(userInput);
            String question = askNextQuestion(); // Get the next question
            updateMainMessage(quizBot.getName(), question); // Display the question
        } else {
            processUserAnswer(userInput);
        }
        MessageBox.clear();
    }

    private String askNextQuestion() {
        questionCount++;
        switch (currentLevel) {
            case 1: return quizBot.generateLevel1Question();
            case 2: return quizBot.generateLevel2Question();
            case 3: return quizBot.generateLevel3Question();
            default: return "Invalid level.";
        }
    }

    private void processUserAnswer(String userInput) {
        boolean isCorrect = quizBot.checkAnswer(userInput);
        String feedback = isCorrect ? "Correct!" : "Wrong! The correct answer is " + quizBot.getCurrentAnswer();
        updateMainMessage(quizBot.getName(), feedback);

        if (questionCount < numQuestions) {
            String nextQuestion = askNextQuestion();
            updateMainMessage(quizBot.getName(), nextQuestion);
        } else {
            updateMainMessage(quizBot.getName(), "QUIZ COMPLETE. CHOOSE LVL OR TYPE QUIT.");
            questionCount = 0;
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

    //clears the boxes
    @FXML
    private void clear() {
        TranslateBox.setText("");
    }

    //method to play morse code that user types into translating box
    @FXML
    private void play() {
        System.out.println("on play "+ playQueue.toString());
        System.out.println("Size " + playQueue.size());
        //thread creation for audio
        Thread audioThread = new Thread(() -> {
            try {
                // Play all messages in the queue
                for (String morseMessage : playQueue) {
                    AudioController.playMorseMessage(morseMessage.trim(), FrequencySlider.getValue());
                }

                // Clear all but the most recent message
                if (!playQueue.isEmpty()) {
                    String lastMessage = playQueue.get(playQueue.size() - 1);
                    playQueue.clear();
                    playQueue.add(lastMessage);
                }
                System.out.println("emptied but last message");

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
