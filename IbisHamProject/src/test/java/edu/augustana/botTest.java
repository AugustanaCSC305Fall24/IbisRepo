package edu.augustana;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class botTest {

    @Test
    void testChatBot(){
        assertNotNull(ChatBot.getName());
        assertNotNull(ChatBot.getBotType());
    }

    @Test
    void testQuizBot(){
        assertNotNull(QuizBot.getName());
        assertNotNull(QuizBot.getAnswers());
        assertNotNull(QuizBot.getQuestions());
    }

}
