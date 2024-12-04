package edu.augustana;

import javax.sound.sampled.*;


public class AudioController{

    private static int SAMPLE_RATE = 1024*16;
    private static double DEFAULT_SIDE_TONE_FREQUENCY = 600;

    private static double currentRadioFrequency =70000;

    private static int currentSpeed;
    private static SourceDataLine sourceDataLine;

    public static void playMorseMessage(String preSplitMorseMessage, double sendersFrequency) throws LineUnavailableException, InterruptedException {
        String[] morseMessage = preSplitMorseMessage.replaceAll("\\s+","").trim().split("");


        AudioFormat audioFormat = new AudioFormat((float) SAMPLE_RATE, 8, 1, true, false);

        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        int dotDuration = 40;
        int dashDuration = (3*dotDuration);
        int slashDuration = currentSpeed *dotDuration;

        // TODO: change this frequency up/down if the sender's frequency doesn't match
        // our radio's receiving (tuned) frequency
        double diff = sendersFrequency - currentRadioFrequency;
        double adjustedFrequency = DEFAULT_SIDE_TONE_FREQUENCY; // maybe + diff / some constant to convert to a reasonable range?
        double adjustedVolume = 1.0; // maybe reduce if diff is bigger

        for(String pattern : morseMessage){
            for(char c: pattern.toCharArray()) {
                if (c == '.') {
                    playBeep(sourceDataLine,dotDuration, adjustedFrequency, adjustedVolume) ;
                    playSilence(sourceDataLine,dotDuration);
//                    Thread.sleep(dotDuration/2);
                } else if (c == '-'){
                    playBeep(sourceDataLine,dashDuration, adjustedFrequency, adjustedVolume);
                    playSilence(sourceDataLine,dotDuration);
//                    Thread.sleep(dotDuration/2);
                }else if (c == '/'){
                    // TODO: playSilence special method
                    playSilence(sourceDataLine, slashDuration);
//                    Thread.sleep(slashDuration);
                }
                else if (c==','){
                    playSilence(sourceDataLine, slashDuration/2);
//                    Thread.sleep(slashDuration/2);
                }
            }
            playSilence(sourceDataLine, dotDuration);
//            Thread.sleep(dotDuration);
        }
        sourceDataLine.drain();
//        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
    }

    private static void playSilence(SourceDataLine sourceDataLine, int duration) {
        playBeep(sourceDataLine, duration, 100, 0);

    }

    /**
     *
     * @param line
     * @param durationInMillis
     * @param frequency - in Hz (sound frequency)
     * @param volume - between 0.0 (silent) and 1.0 (full volume)
     */
    private static void playBeep(SourceDataLine line, int durationInMillis, double frequency, double volume){
        byte[] data = new byte[((int) (SAMPLE_RATE*durationInMillis/1000))];

        for(int i=0;i< data.length;i++){
            double angle = (double) i /(SAMPLE_RATE/frequency)*2*Math.PI;

            data[i] = (byte) (Math.sin(angle)*127*volume);
        }
        line.write(data,0, data.length);
    }

    public static void setSpeed(int speed){
        currentSpeed=speed;
    }
    public static int getSpeed(){
        return currentSpeed;
    }
    public static void setRadioFreq(double freq){
        currentRadioFrequency = (freq * 1000);
        System.out.println(currentRadioFrequency);
    }
}
