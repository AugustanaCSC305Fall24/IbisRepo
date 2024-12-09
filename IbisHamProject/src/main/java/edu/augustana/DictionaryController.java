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
    @FXML private Button check;


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

    //translate from english text box input to morse code and put in text boxes - used in practiceModeController
    public String translateToMorseCode(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder(); //string for use with audio queues adding , and ~
        String finalTranslation = ""; // cleaned up version to display for users

        for (char c : text.toUpperCase().toCharArray()) {
            String morseSymbol = getMorseCode(c);
            morseCodeBuilder.append(",");
            if (!morseSymbol.isEmpty()) {
                morseCodeBuilder.append(morseSymbol).append(" ");
            } else {
                morseCodeBuilder.append("~ ");
            }
        }

        //gets rid of ~ and , in text displayed, idk what they do but i dont want to delete them
        finalTranslation = morseCodeBuilder.toString().replace("~", "").replace(",", "").trim();

        return finalTranslation;
    }


    //checks the user input when check is clicked agianst the random button played
    //tells the user the correct letter/number in the text box then clears the next time play is clicked
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

    //exits to the practice mode launched radio page
    @FXML private void pracLaunch() throws IOException {
        App.setRoot("PracLaunched");
    }

    //play button that plays and remembers a random letter/number
    @FXML
    private void playRandomLetter() throws LineUnavailableException, InterruptedException {
        Object[] letters = morseCodeMap.keySet().toArray();
        currentRandomLetter = (char) letters[random.nextInt(letters.length)];

        String morseCode = morseCodeMap.get(currentRandomLetter);
        if (morseCode != null) {
            AudioController.playMorseMessage(morseCode, 7000);
        }

        AbvTestBox.setText("");
    }

    //switches back to the home page
    @FXML
    private void switchToHomePage() throws IOException {
        App.setRoot("homePage");
    }

    //gets the button id
    @FXML
    private void fetchButtonID(ActionEvent event) throws LineUnavailableException, InterruptedException {
        String buttonString = event.toString().trim();
        String morseString = buttonString.substring(buttonString.indexOf(" \"") + 1);
        morseString = morseString.substring(0, morseString.indexOf("\"']"));
        AudioController.playMorseMessage(morseString, 7000);
    }
}
