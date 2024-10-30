package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class DictionaryController {

    private final Map<Character, String> morseCodeMap = new HashMap<>();
    private final Map<String, Character> morseToEng = new HashMap<>();

    public DictionaryController() {
        initDicts();
    }

    //dict from english to morse
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
        morseCodeMap.put(' ', "/"); // space

        for (Map.Entry<Character, String> entry : morseCodeMap.entrySet()) {
            morseToEng.put(entry.getValue(), entry.getKey());
        }
    }

    //dict access method for eng to morse
    public String getMorseCode(char c) {
        return morseCodeMap.getOrDefault(c, ""); // Return Morse code or empty string if not found
    }

    //fxml
    @FXML private void switchToHomePage() throws IOException {
        App.setRoot("homePage");
    }

    @FXML private void switchToPracPage() throws IOException {
        App.setRoot("practiceMode");
    }
    @FXML private void fetchButtonID(ActionEvent event) {
        System.out.println(event.toString());
    }

}
