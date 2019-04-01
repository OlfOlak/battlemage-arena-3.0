/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static olafolak.battlemagearena30.models.characters.Player.characterHeight;
import static olafolak.battlemagearena30.models.characters.Player.characterWidth;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfCastIceBreathException;
import olafolak.battlemagearena30.models.game.Game;

/**
 * Extension of Animation class for distinguishing icebreath spell cast animation.
 * @author OlfOlak
 */
public class CastIceBreathAnimation extends DirectionalAnimation{
    
    // FIELDS.
    // -inherited.
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param fps informs in what fps the animation is run.
     * @param length how long does one cycle of animation lasts.
     * @param inputFrames list of images that animation consists of.
     * @param mode 0 for infinite loop of animation, other values indicate numbers of cicles.
     */
    public CastIceBreathAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode) {
        super(fps, length, inputFrames, mode);
    }
    
    // METHODS.
    /**
     * Switches the animation frames in time and draws them.
     * @param x the x posision of the drawed frames.
     * @param y the y posision of the drawed frames.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws EndOfCastIceBreathException when the animation ends.
     */
    @Override
    public void run(double x, double y, Graphics graphics, Game observer) throws EndOfCastIceBreathException{
        
        double frameLength = length * fps / frames;

        if(ticks >= frameLength){
            
            if(state == frames - 1)
                throw new EndOfCastIceBreathException();         
            else
                state++;
                
            ticks = 0;
        }
        if(rightDirection){
            graphics.drawImage(framesList.get(state), (int)(x + (0.2 * characterWidth) - (0.5 * 1.5 * characterWidth)), (int)(y + (0.7 * characterHeight) - (0.5 * 1.5 * characterHeight)), observer);
        }
        else{
            graphics.drawImage(framesList.get(state), (int)(x + (0.8 * characterWidth) - (0.5 * 1.5 * characterWidth)), (int)(y + (0.7 * characterHeight) - (0.5 * 1.5 * characterHeight)), observer);  
        }          
    }
    
    // SETTERS AND GETTERS.
    
}
