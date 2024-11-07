package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictionaryController {

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
            if (!morseSymbol.isEmpty()) {
                morseCodeBuilder.append(morseSymbol).append(" ");
            } else {
                morseCodeBuilder.append("~ ");
            }
        }
        return morseCodeBuilder.toString().trim();
    }


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
        AudioController.playSound(morseString);
    }
}
