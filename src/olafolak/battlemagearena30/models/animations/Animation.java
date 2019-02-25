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
    
    private int fps;
    private double length;
    private int frames;
    private int ticks = 0;
    private int state = 0;
    private int mode;
    private ArrayList<BufferedImage> framesList;
    
    
    public Animation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode){
        this.fps = fps;
        this.length = length;
        this.framesList = inputFrames;
        frames = framesList.size();
        this.mode = mode;
        
    }
    
    public void run(double x, double y, Graphics graphics, Game observer) throws EndSingleAnimationException{
        
        double frameLength = length * fps / frames;
        
        if(ticks >= frameLength){
            
            if(state == frames - 1){
                if(mode == 0)
                    state = 0;
                else{
                   throw new EndSingleAnimationException();         
                }
                
            }
            else
                state++;
                
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
