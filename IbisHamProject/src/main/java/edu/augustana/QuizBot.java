package edu.augustana;

import java.util.Arrays;
import java.util.Random;

public class QuizBot extends ChatBot {
    private String name;
    private final String[] answers = new String[]{"QRG", "QRI", "QRK", "QRL","QRM", "QRN", "QRO",
    "QRP","QRQ","QRR", "QRRR", "QRS", "QRT", "QRU", "QRV", "QRX", "QRZ"};
    private final String[] questions = new String[]{"Exact frequency", "Tone", "Intelligibility",
    "This frequency is busy.", "Man-made interference", "Natural interference", "Increase power",
    "Decrease power","Send more quickly","Temporarily unavailable/away", "Land distress", "Send more slowly",
    "Stop sending", "Have you anything for me?", "I am ready", "Will call you again", "You are being called by..."};


    public QuizBot(String name, String botType){
        super(name, botType);
    }

    public String[] getAnswers() {return answers;}

    public String[] getQuestions() {return questions;}

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
        if (userMessage.contains(answers[Arrays.asList(questions).indexOf(randQuestion)])){
            return "Great";
        } else {
            return "Wrong";
        }
    }
}

