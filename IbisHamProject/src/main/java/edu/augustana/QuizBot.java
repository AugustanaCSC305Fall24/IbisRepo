package edu.augustana;

import java.util.Random;

class QuizBot extends ChatBot {

    private static final Random randomGen = new Random();
    private final DictionaryController dictionaryController = new DictionaryController();
    private static final double quizBotFrequency = 7.034;
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
        return "CW QUIZ BOT(FRQ: " + botFrequency() + ")";
    }

    public String askLevelSelection() {
        return "LVL 1 2 3 ";
    }

    @Override
    public double botFrequency() {
        return quizBotFrequency;
    }

    public String generateLevel1Question() {
        char randomLetter = (char) ('A' + randomGen.nextInt(25));
        currentAnswer = String.valueOf(randomLetter);

        currentQuestion =dictionaryController.translateToMorseCode("Morse for letter ") +  "(Morse for letter) " + currentAnswer;
        return currentQuestion;
    }

    public String generateLevel2Question() {
        String[] abbrQuestions = {"ADR", "AGN", "AS", "C", "CFM", "CS", "EMRG", "FER", "FM", "FREQ", "N", "OK", "RPT", "SN", "TEMP"};
        String[] abbrAnswers = {"Address", "Again", "Wait", "Correct/Yes", "Confirm", "Callsign", "Emergency", "For", "From",
                "Frequency", "No", "Ok", "Repeat", "Soon", "Temperature"};
        int index = randomGen.nextInt(abbrQuestions.length);

        currentQuestion = dictionaryController.translateToMorseCode("What is " + abbrQuestions[index]) + (" (What is " + abbrQuestions[index]+")");
        currentAnswer = abbrAnswers[index];
        return currentQuestion;
    }

    public String generateLevel3Question() {
        int index = randomGen.nextInt(qCodeQuestions.length);

        currentAnswer = qCodeAnswers[index];
        currentQuestion = dictionaryController.translateToMorseCode("Enter Qcode for " + qCodeQuestions[index])
                + " (Enter Qcode for " + (qCodeQuestions[index]) + ")";
        return currentQuestion;
    }


    public boolean checkAnswer(String userResponse) {
        String currentMorseAnswer = retainMorseCharacters(dictionaryController.translateToMorseCode(currentAnswer));
        return userResponse.equalsIgnoreCase(currentMorseAnswer);
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public String retainMorseCharacters(String input) {
        return input.replaceAll("[^.-]", ""); // Replace everything except '-' or '.' with an empty string
    }
}
