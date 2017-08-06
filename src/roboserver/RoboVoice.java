/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roboserver;
import javax.sound.sampled.*;
import java.io.*;
import java.util.Random;




/**
 *
 * @author Eloy
 */
public class RoboVoice {
    
    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
{
  SourceDataLine res = null;
  DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
  res = (SourceDataLine) AudioSystem.getLine(info);
  res.open(audioFormat);
  return res;
} 
    private void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException,                                                                                                LineUnavailableException
{
  byte[] data = new byte[4096];
  SourceDataLine line = getLine(targetFormat); 
  if (line != null)
  {
    // Start
    line.start();
    int nBytesRead = 0, nBytesWritten = 0;
    while (nBytesRead != -1)
    {
        nBytesRead = din.read(data, 0, data.length);
        if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
    }
    // Stop
    line.drain();
    line.stop();
    line.close();
    din.close();
  } 
}
    public void playStandUp()
    {
       int maximum=16;
       int minimum=0;
       int randomNum = minimum + (int)(Math.random() * maximum); 
       String fname=randomNum + "";
       if(fname.length()<2)fname="0" + fname;
        fname="rnd_start/z" + fname + ".wav";
        playSound(fname,true);
      
    }
            
      public void playVoiceUp()
    {
       int maximum=6;
       int minimum=1;
       int randomNum = minimum + (int)(Math.random() * maximum); 
       String fname=randomNum + "";
       if(fname.length()<2)fname="0" + fname;
        fname="when_run/w" + fname + ".wav";
        playSound(fname,true);
    }

    public void playSound(String file,boolean wait) {
    try {
        
      
        
        
         InputStream iStream=getClass().getResourceAsStream("/" + file);
       
        //add buffer for mark/reset support
        if(iStream==null)      System.out.println("InputStream null");
        
        InputStream bufferedIn = new BufferedInputStream(iStream);
        
        if(bufferedIn==null)      System.out.println("bufferedIn null");

        AudioInputStream in = AudioSystem.getAudioInputStream(bufferedIn);
         
    AudioInputStream din = null;
    AudioFormat baseFormat = in.getFormat();
    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                                                                                  baseFormat.getSampleRate(),
                                                                                  16,
                                                                                  baseFormat.getChannels(),
                                                                                  baseFormat.getChannels() * 2,
                                                                                  baseFormat.getSampleRate(),
                                                                                  false);
    din = AudioSystem.getAudioInputStream(decodedFormat, in);
  
  
    
    Clip clip= AudioSystem.getClip();
    clip.open(din);
    clip.start();
    clip.addLineListener(event -> {
    if(LineEvent.Type.STOP.equals(event.getType())) {
        clip.close();
    }
    });
     

    } 
    catch(java.lang.IllegalArgumentException ex)
    {
         System.err.println("File:" +file+  "\r\n" + ex.getMessage());
    }
    catch(Exception ex) {
        System.out.println("Error with playing sound.");
        ex.printStackTrace();
    }
}
}
