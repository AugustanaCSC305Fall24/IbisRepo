package edu.augustana;

import java.util.Random;

public class QuizBot {
    private String name;
    private final String[] answers = new String[]{"QRG", "QRI", "QRK", "QRL","QRM", "QRN", "QRO",
    "QRP","QRQ","QRR", "QRRR", "QRS", "QRT", "QRU", "QRV", "QRX", "QRZ"};
    private final String[] questions = new String[]{"Exact frequency", "Tone", "Intelligibility",
    "This frequency is busy.", "Man-made interference", "Natural interference", "Increase power",
    "Decrease power","Send more quickly","Temporarily unavailable/away", "Land distress", "Send more slowly",
    "Stop sending", "Have you anything for me?", "I am ready", "Will call you again", "You are being called by..."};

    public QuizBot(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String startMessage(){
        return "Hello define abbr";
    }

    public String generateQuestion(){
        Random rand = new Random();
        int randIndex = rand.nextInt(questions.length);
        return questions[randIndex];
    }

    public String generateResponse(String userMessage, String randQuestion){
        userMessage = userMessage.toLowerCase();
        return "";
    }
}

