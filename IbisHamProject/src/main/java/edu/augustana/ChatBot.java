package edu.augustana;

import java.util.Random;

public class ChatBot {
    private String name;
    private String botType;
    private static final Random randomGen = new Random();

    public ChatBot(String name, String botType){
        this.name = name;
        this.botType = botType;
    }

    public String getName(){
        return name;
    }

    public String getBotType(){
        return botType;
    }

    public String generateResponseMessage(String userMessage){
        userMessage = userMessage.toLowerCase();
        if(userMessage.contains("hello")){
            return "Hello ur name?";
        } else if(userMessage.contains("Where")){
            return "u tell me first";
        } else if(randomGen.nextInt(10) < 3){
            return "AGN";
        } else if(userMessage.contains("name")){
            return getName();
        }else if(userMessage.contains("job")){
            return "i am "+ getBotType();
        }else {
            return "?";
        }
    }

    public String generateNewMessage(){
        if(randomGen.nextInt(10) < 3){
            return "QTH";
        } else if(randomGen.nextInt(10) > 7){
            return "WX UR JOB?";
        }else {
            return "SK";
        }
    }
}

