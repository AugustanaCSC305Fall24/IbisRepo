package edu.augustana;

import javax.sound.sampled.*;


public class AudioController{

    private static int currentSpeed;

    public static void playSound(String preSplitMorseMessage) throws LineUnavailableException, InterruptedException {
        String[] morseMessage = preSplitMorseMessage.replaceAll("\\s+","").trim().split("");
        AudioFormat audioFormat = new AudioFormat(44100,16,1,true,false);

        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        int dotDuration = 200;
        int dashDuration = (int)(2.5*dotDuration);
        int slashDuration = currentSpeed*dotDuration/10;

        for(String pattern : morseMessage){
            for(char c: pattern.toCharArray()) {
                if (c == '.') {
                    playBeep(sourceDataLine,dotDuration);
                    Thread.sleep(dotDuration/2);
                } else if (c == '-'){
                    playBeep(sourceDataLine,dashDuration);
                    Thread.sleep(dotDuration/2);
                }else if (c == '/'){
                    Thread.sleep(slashDuration);
                }
                else if (c==','){
                    Thread.sleep(slashDuration/2);
                }
            }
            Thread.sleep(dotDuration);
        }
        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
    }

    private static void playBeep(SourceDataLine line, int duration){
        byte[] data = new byte[duration*44100/1000];

        for(int i=0;i< data.length;i++){
            double angle = (double) i /((double) 44100 /440)*2*Math.PI;

            data[i] = (byte) (Math.sin(angle)*127);
        }
        line.write(data,0, data.length);
    }

    public static void setSpeed(int speed){
        currentSpeed=speed;
    }
    public static int getSpeed(){
        return currentSpeed;
    }
}
