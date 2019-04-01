/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.utilities;

import java.io.File; 
import java.io.IOException;  
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 



/**
 * Enables loading audio file and play it.
 * @author OlfOlak
 */
public class AudioPlayer {
    
    // FIELDS.
    
    // to store current position 
    /** Indicates current frame of the clip.**/
    Long currentFrame;
    /** Keeps the clip file.**/
    Clip clip; 
      
    // current status of clip 
    /** Current status of the clip.**/
    String status; 
    
    /** Indicates if the clip is in infinite loop.**/
    private boolean loop;
    
    /** Streams sound files.**/
    AudioInputStream audioInputStream; 
    /** File path to the sound file for the clip.**/
    static String filePath; 
  
    // CONSTRUCTORS.
    /**
     * Constructor to initialize streams and clip
     * @param source file path of the sound file.
     * @param loop sets loopable.
     * @throws UnsupportedAudioFileException -
     * @throws IOException -
     * @throws LineUnavailableException -
     */
    public AudioPlayer(String source, boolean loop) 
        throws UnsupportedAudioFileException, 
        IOException, LineUnavailableException  
    { 
        // create AudioInputStream object 
        audioInputStream =  
                AudioSystem.getAudioInputStream(new File(source).getAbsoluteFile()); 
          
        // create clip reference 
        clip = AudioSystem.getClip(); 
          
        // open audioInputStream to the clip 
        clip.open(audioInputStream); 
        
        this.loop = loop;
        
        if(loop)
            clip.loop(Clip.LOOP_CONTINUOUSLY); 
    } 
    
    // METHODS.
    /**
     * Starts playing the clip.
     */
    public void play()  
    { 
        
        
        //start the clip 
        clip.start(); 
          
        status = "play"; 
    } 
     
    /**
     * Method to pause the audio 
     */
    public void pause()  
    { 
        /*if (status.equals("paused"))  
        { 
            System.out.println("audio is already paused"); 
            return; 
        } */
        this.currentFrame =  
        this.clip.getMicrosecondPosition(); 
        clip.stop(); 
        status = "paused"; 
    } 
      
    /**
     * Method to resume the audio. 
     * @throws UnsupportedAudioFileException -
     * @throws IOException -
     * @throws LineUnavailableException -
     */
    public void resumeAudio() throws UnsupportedAudioFileException, 
                                IOException, LineUnavailableException  
    { 
        if (status.equals("play"))  
        { 
            System.out.println("Audio is already "+ 
            "being played"); 
            return; 
        } 
        clip.close(); 
        resetAudioStream(); 
        clip.setMicrosecondPosition(currentFrame); 
        this.play(); 
    } 
      
    
    /**
     * Method to restart the audio.
     * @throws IOException -
     * @throws LineUnavailableException -
     * @throws UnsupportedAudioFileException -
     */
    public void restart() throws IOException, LineUnavailableException, 
                                            UnsupportedAudioFileException  
    { 
        clip.stop(); 
        clip.close(); 
        resetAudioStream(); 
        currentFrame = 0L; 
        clip.setMicrosecondPosition(0); 
        this.play(); 
    } 
       
    /**
     * Method to stop the audio.
     * @throws UnsupportedAudioFileException -
     * @throws IOException -
     * @throws LineUnavailableException -
     */
    public void stop() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException  
    { 
        currentFrame = 0L; 
        clip.stop(); 
        clip.close(); 
    } 
        
    /**
     * Method to jump over a specific part.
     * @param c frame of the clip to jump to.
     * @throws UnsupportedAudioFileException -
     * @throws IOException -
     * @throws LineUnavailableException -
     */
    public void jump(long c) throws UnsupportedAudioFileException, IOException, 
                                                        LineUnavailableException  
    { 
        if (c > 0 && c < clip.getMicrosecondLength())  
        { 
            clip.stop(); 
            clip.close(); 
            resetAudioStream(); 
            currentFrame = c; 
            clip.setMicrosecondPosition(c); 
            this.play(); 
        } 
    } 
       
    /**
     * Method to reset audio stream.
     * @throws UnsupportedAudioFileException -
     * @throws IOException -
     * @throws LineUnavailableException -
     */
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
                                            LineUnavailableException  
    { 
        audioInputStream = AudioSystem.getAudioInputStream( 
        new File(filePath).getAbsoluteFile()); 
        clip.open(audioInputStream); 
        
    } 

    // SETTERS AND GETTERS.
    
    public Clip getClip() {
        return clip;
    }
    
}
