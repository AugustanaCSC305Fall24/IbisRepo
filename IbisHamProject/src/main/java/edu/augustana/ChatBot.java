package edu.augustana;

import java.util.Random;

public abstract class ChatBot {
    private final String name;
    private final String botType;

    private static final Random randomGen = new Random();

    public ChatBot(String name, String botType) {
        this.name = name;
        this.botType = botType;
    }

    //the message the bots say at start
    public abstract String startMessage();

    //getter method for the bot name
    public String getName() {
        return name;
    }

    //getter method for bot type
    public String getBotType() {
        return botType;
    }

}

