/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.sound.sampled.*;


/**
 *
 * @author OlafPC
 */
public class AudioPlayer {
    
    public AudioPlayer(String source){
        
        
        try{
            FileInputStream fileInputStream = new FileInputStream(source);
            Player player = new Player(fileInputStream);
        }catch(Exception e){
            
        }
        
    }
    
    
    
    
    /*private Clip clip;
    
    public AudioPlayer(String source){
        
        try{
            
            AudioInputStream ais =
                    AudioSystem.getAudioInputStream(
                        getClass().getResourceAsStream(
                                source
                        )
                    );
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
            );
            AudioInputStream dais =
                AudioSystem.getAudioInputStream(
                    decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }    
    }
    
    public void play(){
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }
    
    private void stop(){
        if(clip.isRunning()) clip.stop();
    }
    
    public void close(){
        stop();
        clip.close();
    }*/
    
}
