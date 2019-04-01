/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.game.Game;

/**
 * Enables controlling and drawing sets of uploaded images in set time.
 * @author OlfOlak
 */
public class Animation {
    
    // FIELDS.
    
    protected int fps;
    protected double length;
    protected int frames;
    protected int ticks = 0;
    protected int state = 0;
    protected int mode;
    protected ArrayList<BufferedImage> framesList;
    
    protected int freezeTimer = 0;
    protected int freezeTimerDuration;
    protected boolean isFrozen;
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param fps informs in what fps the animation is run.
     * @param length how long does one cycle of animation lasts.
     * @param inputFrames list of images that animation consists of.
     * @param mode 0 for infinite loop of animation, other values indicate numbers of cicles.
     */
    public Animation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode){
        this.fps = fps;
        this.length = length;
        this.framesList = inputFrames;
        if(framesList != null)
            frames = framesList.size();
        this.mode = mode;
        
    }
    
    // METHODS.
    
    /**
     * Switches the animation frames in time and draws them.
     * @param x the x posision of the drawed frames.
     * @param y the y posision of the drawed frames.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws EndSingleAnimationException when the single cicle of the animation ends.
     */
    public void run(double x, double y, Graphics graphics, Game observer) throws EndSingleAnimationException{
        
        double frameLength = length * fps / frames;
        
        if(freezeTimer != 0)
            freezeTimer++;
        
        if(freezeTimer == freezeTimerDuration){
            freezeTimer = 0;
            isFrozen = false;
        }
        
        if(ticks >= frameLength){
            
            if(state == frames - 1){
                if(mode == 0)
                    state = 0;
                else{
                   throw new EndSingleAnimationException();         
                }
                
            }
            else{
                if(!isFrozen)
                    state++;
            }    
            ticks = 0;
        }

        graphics.drawImage(framesList.get(state), (int)x, (int)y, observer);
            
    }
    
    /**
     * Resets animation's to the initial state.
     */
    public void reset(){
        ticks = 0;
        state = 0;
    }
    
    /**
     * Increments the clocking.
     */
    public void incrementTicks(){
        ticks++;
    }
    
    /**
     * Freezes animation for specified time.
     * @param duration how long the animation is to be freezed, in seconds.
     */
    public void freeze(int duration){
        freezeTimerDuration = 60 * duration;
        isFrozen = true;
    }
    
    // SETTERS AND GETTERS.
    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}
