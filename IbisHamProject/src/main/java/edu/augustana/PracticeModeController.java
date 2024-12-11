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

    //bots
    QuizBot quizBot = new QuizBot("Mr. Prof", "QuizBot");
    private int questionCount = 0;
    private int currentLevel = 1;
    private final int numQuestions = 3;
    private int currentSpeed = 20;

    //queues for audio buttons
    private final List<String> playQueue = new ArrayList<>();
    private final List<String> playQueueAll = new ArrayList<>();

    private ChatBot currentBot;
    private boolean hidePlainText = false;
    private boolean isLvlSelection = true;

    @FXML private Label FrequencyLabel;
    @FXML private Slider FrequencySlider;
    @FXML private Slider FilterSlider;
    @FXML private Slider speedSlider;
    @FXML private TextArea MessageBox;
    @FXML private TextArea MainMessageBox;
    @FXML private CheckBox disButton;
    @FXML
    private ComboBox<String> ScenBotChoice; // The ComboBox from the FXML


    //initializes fxml sliders and page
    @FXML
    public void initialize() {
        //to use the static noise for filtering
        disButton.setSelected(false);
        HamRadio user = new HamRadio();

        // frequency slider
        if (FrequencySlider != null) {
            FrequencySlider.setValue(0.0);
            FrequencyLabel.setText(String.format("Current Frequency: %.3f MHz", FrequencySlider.getValue()));

            //to get current slider values
            FrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                FrequencyLabel.setText(String.format("Current Frequency: %.3f MHz", newValue.doubleValue()));
                updateFilterRange(newValue.doubleValue());
                user.setTuningFrequency(newValue.doubleValue());
                checkStatic(); //check if static noise is needed to be played
            });
        }

        // filter slider
        if (FilterSlider != null) {
            FilterSlider.setMax(0.026); // maximum filter range is 0.026 MHz (just a random choice)
            FilterSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                double currentFrequency = FrequencySlider.getValue();
                updateFilterRange(currentFrequency);
                checkStatic(); // check if static should be played
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


    //method to check if static morse code should be playing
    //static is off if either:
    //          the frequency slider is on the right frequency
    //          the users filter has the bots frequency in its range
    private void checkStatic() {
        double userFrequency = FrequencySlider.getValue();
        double quizBotFrequency = quizBot.botFrequency();
        double filterValue = FilterSlider.getValue();
        double minFrequency = Math.max(7.000, userFrequency - filterValue / 2);
        double maxFrequency = Math.min(7.067, userFrequency + filterValue / 2);

        //math for checking frequencies with a .001 margin of error
        boolean isFrequencyTheSame = Math.abs(userFrequency - quizBotFrequency) < 0.001;

        //math to check the filter and bot freq
        boolean isFrequencyInFilterRange = quizBotFrequency >= minFrequency && quizBotFrequency <= maxFrequency;

        //check if freq OR filter is matching to disable static
        boolean shouldDisableStatic = isFrequencyTheSame || isFrequencyInFilterRange;
        boolean staticOn = !shouldDisableStatic;

        //sets static audio on after checking filters and freq
        disButton.setSelected(staticOn);
        AudioController.setDistortion(staticOn);
    }


    private void updateMainMessage(String sender, String engText){
        String morseText = dictionaryController.translateToMorseCode(engText);
        String message;

        if (hidePlainText && sender.equals(quizBot.getName())) {
            message = sender + ": " + engText;
        } else {
            message = sender + ": " + morseText + " (" + engText + ")";
        }
        String existingText = MainMessageBox.getText();
        MainMessageBox.setText(existingText + (existingText.isEmpty() ? "" : "\n") + message);
        MainMessageBox.setScrollTop(Double.MAX_VALUE);

        playQueue.add(morseText);
        playQueueAll.add(morseText);
    }

    @FXML
    private void sendAction() {
        String userInput = MessageBox.getText().trim();
        if (userInput.isBlank()) return;
        updateMainMessage("User", userInput);

        processUserInput(userInput);
        MessageBox.clear();
    }

    private void processUserInput(String userInput){
        if(!isLvlSelection){
            processUserAnswer(userInput);
            return;
        }

        if(!(userInput.matches("[123]") || dictionaryController.translateToMorseCode(userInput).matches("[123]"))){
            if(userInput.equalsIgnoreCase("quit")){
                updateMainMessage(quizBot.getName(), "Good luck on CW");
            } else {
                updateMainMessage(quizBot.getName(), "Invalid input");
                updateMainMessage(quizBot.getName(), quizBot.askLevelSelection());
            }
        } else {
            processLevelSelection(userInput);
        }
    }

    private void processLevelSelection(String userInput) {
        if (userInput.matches("[.-]+")) {
            userInput = dictionaryController.morseToEnglish(userInput);
        }
        currentLevel = Integer.parseInt(userInput);
        String question = askNextQuestion();
        questionCount = 1;
        isLvlSelection = false;
        updateMainMessage(quizBot.getName(), "Level " + currentLevel + " selected.");
        updateMainMessage(quizBot.getName(), question);
    }

    private String askNextQuestion() {
        questionCount++;
        hidePlainText = true;
        switch (currentLevel) {
            case 1: return quizBot.generateLevel1Question();
            case 2: return quizBot.generateLevel2Question();
            case 3: return quizBot.generateLevel3Question();
            default: return "Invalid level.";
        }
    }

    private void processUserAnswer(String userInput) {
        hidePlainText = false;
        boolean isCorrect = quizBot.checkAnswer(userInput);
        String feedback = isCorrect ? "Correct" : "Wrong. correct answer is " +  dictionaryController.translateToMorseCode(quizBot.getCurrentAnswer());
        updateMainMessage(quizBot.getName(), feedback);

        if (questionCount < numQuestions) {
            String nextQuestion = askNextQuestion();
            updateMainMessage(quizBot.getName(), nextQuestion);
        } else {
            updateMainMessage(quizBot.getName(), "QUIZ COMPLETE. CHOOSE LVL OR TYPE QUIT.");
            isLvlSelection = true;
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

    //method to play morse code that user types into translating box
    //reverse of play all sort of, uses different queues
    @FXML
    private void play() {

        Thread audioThread = new Thread(() -> {
            //get most recent message to play
            if (!playQueue.isEmpty()) {
                String lastMessage = playQueue.get(playQueue.size() - 1);
                playQueue.clear();
                playQueue.add(lastMessage);
            }
            for (String morseMessage : playQueue) {

                //intellij had me throw the try catch like this
                try {
                    AudioController.playMorseMessage(morseMessage.trim(), FrequencySlider.getValue());
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Set the thread as a daemon so it doesn't block app closing
        audioThread.setDaemon(true);
        audioThread.start();
    }

//play all text method
    @FXML
    private void playAll() {
        Thread audioThreadAll = new Thread(() -> {
            try {
                //play all the messages in the queue
                for (String morseMessage : playQueueAll) {

                    AudioController.playMorseMessage(morseMessage.trim(), FrequencySlider.getValue());
                }

                //clear messages and add last one
                if (!playQueueAll.isEmpty()) {
                    String lastMessage = playQueueAll.get(playQueueAll.size() - 1);
                    playQueueAll.clear();
                    playQueueAll.add(lastMessage);
                }

            } catch (LineUnavailableException | InterruptedException e) {
                // exception for audio issues
                e.printStackTrace();
            }
        });

        //set thread as daemon so closing app/app functionality isnt frozen during audio playing
        audioThreadAll.setDaemon(true);
        audioThreadAll.start();
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
        StringBuilder morse = new StringBuilder();
        morse.append(MessageBox.getText());
        if (event.getText().equals(".")) {
            AudioController.playMorseMessage(".", FrequencySlider.getValue());
        }
        if(event.getCode() == KeyCode.LEFT){
            AudioController.playMorseMessage(".", FrequencySlider.getValue());
            morse.append(".");
        }
        if (event.getCode() == KeyCode.RIGHT) {
            AudioController.playMorseMessage("-", FrequencySlider.getValue());
            morse.append("-");
        }
        if(event.getText().equals("-")){
            AudioController.playMorseMessage("-", FrequencySlider.getValue());
        }

        MessageBox.setText(morse.toString());
        MessageBox.positionCaret(morse.length());
    }

    //distortion button settings and saving state
    @FXML
    public void disButton(){
        boolean isDistortion = disButton.isSelected();
        AudioController.setDistortion(isDistortion);
    }
}