package edu.augustana;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictionaryController {

    private final Map<Character, String> morseCodeMap = new HashMap<>();
    private Map<String, Character> reverseMorseCodeMap;

    //fxml
    @FXML private void switchToHomePage() throws IOException {
        App.setRoot("homePage");
    }

    @FXML private void switchToPracPage() throws IOException {
        App.setRoot("practiceMode");
    }

    //dict from english to morse
    public void engToMorseMap() {
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
    }

    public void morseToEngMap() {
        reverseMorseCodeMap.put(".-", 'A');
        reverseMorseCodeMap.put("-...", 'B');
        reverseMorseCodeMap.put("-.-.", 'C');
        reverseMorseCodeMap.put("-..", 'D');
        reverseMorseCodeMap.put(".", 'E');
        reverseMorseCodeMap.put("..-.", 'F');
        reverseMorseCodeMap.put("--.", 'G');
        reverseMorseCodeMap.put("....", 'H');
        reverseMorseCodeMap.put("..", 'I');
        reverseMorseCodeMap.put(".---", 'J');
        reverseMorseCodeMap.put("-.-", 'K');
        reverseMorseCodeMap.put(".-..", 'L');
        reverseMorseCodeMap.put("--", 'M');
        reverseMorseCodeMap.put("-.", 'N');
        reverseMorseCodeMap.put("---", 'O');
        reverseMorseCodeMap.put(".--.", 'P');
        reverseMorseCodeMap.put("--.-", 'Q');
        reverseMorseCodeMap.put(".-.", 'R');
        reverseMorseCodeMap.put("...", 'S');
        reverseMorseCodeMap.put("-", 'T');
        reverseMorseCodeMap.put("..-", 'U');
        reverseMorseCodeMap.put("...-", 'V');
        reverseMorseCodeMap.put(".--", 'W');
        reverseMorseCodeMap.put("-..-", 'X');
        reverseMorseCodeMap.put("-.--", 'Y');
        reverseMorseCodeMap.put("--..", 'Z');
        reverseMorseCodeMap.put(".----", '1');
        reverseMorseCodeMap.put("..---", '2');
        reverseMorseCodeMap.put("...--", '3');
        reverseMorseCodeMap.put("....-", '4');
        reverseMorseCodeMap.put(".....", '5');
        reverseMorseCodeMap.put("-....", '6');
        reverseMorseCodeMap.put("--...", '7');
        reverseMorseCodeMap.put("---..", '8');
        reverseMorseCodeMap.put("----.", '9');
        reverseMorseCodeMap.put("-----", '0');
        reverseMorseCodeMap.put("/", ' '); // space
    }



    //dict access method for eng to morse
    public String getMorseCode(char c) {
        return morseCodeMap.getOrDefault(c, ""); // Return Morse code or empty string if not found
    }

    //dict access method for morse to english
    public String getEnglish(String s) {
        return String.valueOf(reverseMorseCodeMap); // Return Morse code or empty string if not found
    }

}
