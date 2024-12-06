package edu.augustana;

import java.util.Random;

public abstract class ChatBot {
    private final String name;
    private final String botType;

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
}

