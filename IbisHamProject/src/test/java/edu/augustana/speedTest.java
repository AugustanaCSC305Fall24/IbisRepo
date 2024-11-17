package edu.augustana;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class speedTest {
    @Test
    public void testSpeedSlider(){
        AudioController.setSpeed(5);
        assertEquals(5,AudioController.getSpeed());
    }
}
