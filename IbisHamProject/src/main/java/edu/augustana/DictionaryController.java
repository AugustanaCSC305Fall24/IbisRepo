package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DictionaryController {
    private final Random random = new Random();
    private char currentRandomLetter;
    @FXML private Button AbvPlayBtn;
    @FXML private TextArea AbvTestBox;
    @FXML private Button PracFromDict;
    @FXML private Button buttonA;
    @FXML private Button buttonB;
    @FXML private Button buttonC;
    @FXML private Button buttonD;
    @FXML private Button buttonE;
    @FXML private Button buttonEight;
    @FXML private Button buttonF;
    @FXML private Button buttonFive;
    @FXML private Button buttonFour;
    @FXML
    private Button buttonG;

    @FXML
    private Button buttonH;

    @FXML
    private Button buttonI;

    @FXML
    private Button buttonJ;

    @FXML
    private Button buttonK;

    @FXML
    private Button buttonL;

    @FXML
    private Button buttonM;

    @FXML
    private Button buttonN;

    @FXML
    private Button buttonNine;

    @FXML
    private Button buttonO;

    @FXML
    private Button buttonOne;

    @FXML
    private Button buttonP;

    @FXML
    private Button buttonQ;

    @FXML
    private Button buttonR;

    @FXML
    private Button buttonS;

    @FXML
    private Button buttonSeven;

    @FXML
    private Button buttonSix;

    @FXML
    private Button buttonT;

    @FXML
    private Button buttonThree;

    @FXML
    private Button buttonTwo;

    @FXML
    private Button buttonU;

    @FXML
    private Button buttonV;

    @FXML
    private Button buttonW;

    @FXML
    private Button buttonX;

    @FXML
    private Button buttonY;

    @FXML
    private Button buttonZ;

    @FXML
    private Button buttonZero;

    @FXML
    private Button check;


    private final Map<Character, String> morseCodeMap = new HashMap<>();
    private final Map<String, Character> morseToEng = new HashMap<>();

    public DictionaryController() {
        initDicts();

    }

    //initialize the dictionaries for English to Morse code
    private void initDicts() {
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
        morseCodeMap.put('?', "..--..");
        morseCodeMap.put(' ', "/"); // Use "/" to represent a space

        for (Map.Entry<Character, String> entry : morseCodeMap.entrySet()) {
            morseToEng.put(entry.getValue(), entry.getKey());
        }
    }

    //access method for converting English to Morse
    public String getMorseCode(char c) {
        return morseCodeMap.getOrDefault(c, "");
    }

    //method to convert Morse code to English - used in practiceModeController
    public String morseToEnglish(String morseCode) {
        if (morseCode == null || morseCode.isBlank()) {
            return "";
        }

        StringBuilder englishText = new StringBuilder();
        String[] words = morseCode.split(" / "); //split words by " / "

        for (String word : words) {
            String[] characters = word.split(" "); //split characters by space
            for (String morseChar : characters) {
                englishText.append(morseToEng.getOrDefault(morseChar, '?'));
            }
            englishText.append(' '); //add space between words
        }
        return englishText.toString().trim(); //trim trailing spaces
    }

    //translate from english to morse code - used in practiceModeController
    public String translateToMorseCode(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            String morseSymbol = getMorseCode(c);
            morseCodeBuilder.append(",");
            if (!morseSymbol.isEmpty()) {
                morseCodeBuilder.append(morseSymbol).append(" ");
            } else {
                morseCodeBuilder.append("~ ");
            }
        }
        return morseCodeBuilder.toString().trim();
    }

    @FXML
    private void checkButtonLetterTest() {
        String userInput = AbvTestBox.getText().trim();
        String decodedInput = morseToEnglish(userInput);
        if (decodedInput.equalsIgnoreCase(String.valueOf(currentRandomLetter))) {
            AbvTestBox.setText("Correct! The random letter/number was: " + currentRandomLetter);
        } else {
            AbvTestBox.setText("Incorrect. The random letter/number was: " + currentRandomLetter +", you entered: " + decodedInput);
        }

    }

    @FXML
    private void playRandomLetter() throws LineUnavailableException, InterruptedException {
        // Get a random character from the Morse code map
        Object[] letters = morseCodeMap.keySet().toArray();
        currentRandomLetter = (char) letters[random.nextInt(letters.length)];

        // Play the Morse code for the random letter
        String morseCode = morseCodeMap.get(currentRandomLetter);
        if (morseCode != null) {
            AudioController.playMorseMessage(morseCode, 7000);
        }

        AbvTestBox.setText("");    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.setRoot("homePage");
    }

    @FXML
    private void switchToPracPage() throws IOException {
        App.setRoot("practiceMode");
    }

    @FXML
    private void fetchButtonID(ActionEvent event) throws LineUnavailableException, InterruptedException {
        String buttonString = event.toString().trim();
        String morseString = buttonString.substring(buttonString.indexOf(" \"") + 1);
        morseString = morseString.substring(0, morseString.indexOf("\"']"));
        AudioController.playMorseMessage(morseString, 7000);
    }
}
