package edu.augustana;

import java.sql.SQLOutput;
import java.util.Random;

class QuizBot extends ChatBot {
    private static final Random randomGen = new Random();
    private final DictionaryController dictionaryController = new DictionaryController();

    private String name;
    private final String[] qCodeAnswers = new String[]{"QRG", "QRI", "QRK", "QRL", "QRM", "QRN", "QRO",
            "QRP", "QRQ", "QRR", "QRRR", "QRS", "QRT", "QRU", "QRV", "QRX", "QRZ"};
    private final String[] qCodeQuestions = new String[]{"Exact frequency", "Tone", "Intelligibility",
            "This frequency is busy.", "Man-made interference", "Natural interference", "Increase power",
            "Decrease power", "Send more quickly", "Temporarily unavailable/away", "Land distress", "Send more slowly",
            "Stop sending", "Have you anything for me?", "I am ready", "Will call you again", "You are being called by..."};
    private String currentQuestion;
    private String currentAnswer;

    public QuizBot(String name, String botType) {
        super(name, botType);
    }

    public String startMessage() {
        return "I QUIZ ON CW";
    }

    public String askLevelSelection() {
        return "LVL 1 2 3 ";
    }

    public String generateLevel1Question() {
        char randomLetter = (char) ('A' + randomGen.nextInt(26));
        currentAnswer = String.valueOf(randomLetter);

        currentQuestion = "Morse for letter " + currentAnswer;
        return currentQuestion;
    }
    ///ADR Address
    //AGN Again
    //AS Wait
    //C Correct/Yes
    //CFM Confirm
    //CS Callsign
    //EMRG Emergency
    //FER For
    //FM From
    //FREQ Frequency
    //N No
    //OK ok
    //RPT Repeat
    //SN Soon
    //TEMP Temperature
    ///

    public String generateLevel2Question() {
        String[] abbrQuestions = {"ADR", "AGN", "AS", "C", "CFM", "CS", "EMRG", "FER", "FM", "FREQ", "N", "OK", "RPT", "SN", "TEMP"};
        String[] abbrAnswers = {"Address", "Again", "Wait", "Correct/Yes", "Confirm", "Callsign", "Emergency", "For", "From",
                "Frequency", "No", "Ok", "Repeat", "Soon", "Temperature"};
        int index = randomGen.nextInt(abbrQuestions.length);
        currentQuestion = abbrQuestions[index];
        currentAnswer = abbrAnswers[index];
        return "What is " + currentQuestion;
    }

    public String generateLevel3Question() {
        int index = randomGen.nextInt(qCodeQuestions.length);
        currentQuestion = qCodeQuestions[index];
        currentAnswer = qCodeAnswers[index];
        return "What is the Qcode for " + currentQuestion;
    }

    public boolean checkAnswer(String userResponse) {
        String currentMorseAnswer = retainMorseCharacters(dictionaryController.translateToMorseCode(currentAnswer));
        //for debugging
        System.out.println("current answer: " + currentAnswer + "\n currentAns Morse " +currentMorseAnswer);

        return userResponse.equalsIgnoreCase(currentAnswer) ||
                userResponse.equalsIgnoreCase(currentMorseAnswer);
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public String retainMorseCharacters(String input) {
        return input.replaceAll("[^.-]", ""); // Replace everything except '-' or '.' with an empty string
    }
}



