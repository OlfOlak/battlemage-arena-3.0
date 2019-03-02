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
 *
 * @author OlafPC
 */
public class Animation {
    
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
    
    
    public Animation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode){
        this.fps = fps;
        this.length = length;
        this.framesList = inputFrames;
        if(framesList != null)
            frames = framesList.size();
        this.mode = mode;
        
    }
    
    // Methods.
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
    
    public void reset(){
        ticks = 0;
        state = 0;
    }
    
    public void incrementTicks(){
        ticks++;
    }
    
    public void freeze(int duration){
        freezeTimerDuration = 60 * duration;
        isFrozen = true;
    }
    
    // Setters and getters.
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

    /*public ArrayList<Image> getFramesList() {
        return framesList;
    }

    public void setFramesList(ArrayList<Image> framesList) {
        this.framesList = framesList;
    }*/


    
    
}
