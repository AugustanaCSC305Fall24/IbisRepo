package edu.augustana;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class ChatBotTest {

    ChatBot bot = new QuizBot("Alice","Nurse");

    @Test
    public void testInitBot(){
        assertEquals("Alice",bot.getName());
        assertEquals("Nurse",bot.getBotType());
    }

    @Test
    public void testResponseNoSpace(){
        assertEquals("Hello ur name?",bot.generateResponseMessage("adwdedededahelloerererer"));
        assertEquals("u tell me first", bot.generateResponseMessage("qththeravennevermore"));
        assertEquals("OP is " + bot.getName(), bot.generateResponseMessage("shamenameblamecamesamefame"));
        assertEquals("i am a " + bot.getBotType(), bot.generateResponseMessage("jobowner123843239"));
    }
    @Test
    public void testResponseSpaces(){
        assertEquals("Hello ur name?",bot.generateResponseMessage("hello friend"));
        assertEquals("u tell me first", bot.generateResponseMessage("qth pls"));
        assertEquals("OP is " + bot.getName(), bot.generateResponseMessage("what is your name"));
        assertEquals("i am a " + bot.getBotType(), bot.generateResponseMessage("do you have a job"));
    }
    @Test
    public void testResponseCaps(){
        assertEquals("Hello ur name?",bot.generateResponseMessage("HELLO MY FRIEND"));
        assertEquals("u tell me first", bot.generateResponseMessage("PLEASE QTH NOW"));
        assertEquals("OP is " + bot.getName(), bot.generateResponseMessage("TELL ME YOUR NAME"));
        assertEquals("i am a " + bot.getBotType(), bot.generateResponseMessage("WHAT IS YOUR JOB"));
    }
    @Test
    public void testDoesNotMatch(){
        assertNotSame("Hello ur name?",bot.generateResponseMessage("not telling you"));
        assertNotSame("u tell me first", bot.generateResponseMessage("nope"));
        assertNotSame("OP is " + bot.getName(), bot.generateResponseMessage("thanks"));
        assertNotSame("i am a " + bot.getBotType(), bot.generateResponseMessage("i dont care"));
    }

}
