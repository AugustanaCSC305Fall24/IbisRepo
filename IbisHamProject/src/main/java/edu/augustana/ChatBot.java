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
        if(userMessage.equals("Hello")){
            return "Hello ur name?";
        } else if(userMessage.contains("Where")){
            return "u tell me first";
        } else if(randomGen.nextInt(10) < 3){
            return "AGN";
        } else if(userMessage.contains("name")){
            return "K9ABC";
        }else {
            return "?";
        }
    }

    public String generateNewMessage(){
        if(randomGen.nextInt(10) < 3){
            return "QTH";
        } else {
            return "SK";
        }
    }
}

