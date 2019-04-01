/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfDieException;
import olafolak.battlemagearena30.models.game.Game;

/**
 * Extension of Animation class for distinguishing death animation.
 * @author OlfOlak
 */
public class DieAnimation extends DirectionalAnimation{

    // FIELDS.
    // -inherited.
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param fps informs in what fps the animation is run.
     * @param length how long does one cycle of animation lasts.
     * @param leftFrames list of images for character headed left that animation consists of.
     * @param rightFrames list of images for character headed right that animation consists of.
     */
    public DieAnimation(int fps, double length, ArrayList<BufferedImage> leftFrames, ArrayList<BufferedImage> rightFrames) {
        super(fps, length, leftFrames, rightFrames);
    }

    // METHODS.
    /**
     * Switches the animation frames in time and draws them.
     * @param x the x posision of the drawed frames.
     * @param y the y posision of the drawed frames.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws EndOfDieException when the animation ends.
     */
    @Override
    public void run(double x, double y, Graphics graphics, Game observer) throws EndOfDieException{
        
        double frameLength = length * fps / frames;
        
        if(ticks >= frameLength){
            
            if(state == frames - 1)
                throw new EndOfDieException();         
            else
                state++;
                
            ticks = 0;
        }
        if(rightDirection)
            graphics.drawImage(rightDirectionFrames.get(state), (int)x, (int)y, observer);    
        else
            graphics.drawImage(leftDirectionFrames.get(state), (int)x, (int)y, observer); 
            
    }
    
    // SETTERS AND GETTERS.
    
}
