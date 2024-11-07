package edu.augustana;

import java.util.Random;

public class ChatBot {
    private String name;
    private String botType;
    //private String[] greetings = new ["hi"];
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
        } else if(userMessage.contains("qth")){
            return "u tell me first";
        } else if(userMessage.contains("name")){
            return "OP is " + getName();
        } else if(userMessage.contains("job")){
            return "i am a " + getBotType();
        } else if(randomGen.nextInt(10) < 3){
            return "AGN";
        } else {
            return "?";
        }
    }

    public String generateNewMessage(){
        if(randomGen.nextInt(10) < 3){
            return "QTH?"; //asking for location
        } else if(randomGen.nextInt(10) > 7){
            return "WX UR JOB?";
        }else {
            return "SK"; //signing off
        }
    }
}

