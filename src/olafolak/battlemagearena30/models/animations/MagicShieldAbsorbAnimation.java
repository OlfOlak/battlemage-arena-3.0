/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfMagicShieldAbsorbException;
import olafolak.battlemagearena30.models.game.Game;

/**
 * Extension of Animation class for distinguishing magic shield absorbtion animation.
 * @author OlfOlak
 */
public class MagicShieldAbsorbAnimation extends Animation{
    
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
    public MagicShieldAbsorbAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode) {
        super(fps, length, inputFrames, mode);
    }
    
    // METHODS.
    
    /**
     * Switches the animation frames in time and draws them.
     * @param x the x posision of the drawed frames.
     * @param y the y posision of the drawed frames.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws EndOfMagicShieldAbsorbException when the animation ends.
     */
    @Override
    public void run(double x, double y, Graphics graphics, Game observer) throws EndOfMagicShieldAbsorbException{
        
        double frameLength = length * fps / frames;

        if(ticks >= frameLength){
            
            if(state == frames - 1)
                throw new EndOfMagicShieldAbsorbException();         
            else
                state++;
                
            ticks = 0;
        }
        
        graphics.drawImage(framesList.get(state), (int)x, (int)y, observer);         
    }
    
    // SETTERS AND GETTERS.
}
