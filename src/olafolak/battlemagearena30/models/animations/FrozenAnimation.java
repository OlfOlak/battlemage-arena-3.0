/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfFrozenException;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.characters.Character;

/**
 * Extension of Animation class for distinguishing freeze animation.
 * @author OlfOlak
 */
public class FrozenAnimation extends Animation{
    
    // FIELDS.
    // -inherited.
    /** Reference to character object that is to be freezing.**/
    private Character target;
    
    // CONSTRUCTORS.
    
    /**
     * Basic constructor.
     * @param fps informs in what fps the animation is run.
     * @param length how long does one cycle of animation lasts.
     * @param inputFrames list of images that animation consists of.
     * @param target Reference to object that is to be freezing.
     */
    public FrozenAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, Character target) {
        super(fps, length, inputFrames, 1);
        
        this.target = target;
    }
    
    // METHODS.
    
    /**
     * Switches the animation frames in time and draws them.
     * @param x the x posision of the drawed frames.
     * @param y the y posision of the drawed frames.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws EndOfFrozenException when the animation ends.
     */
    @Override
    public void run(double x, double y, Graphics graphics, Game observer) throws EndOfFrozenException{
        
        double frameLength = length * fps / frames;

        if(ticks >= frameLength){
            
            if(state == frames - 1)
                throw new EndOfFrozenException();         
            else
                state++;
                
            ticks = 0;
        }
        
        graphics.drawImage(framesList.get(state),
                (int)(x - (framesList.get(0).getWidth() / 2) + (target.getCharacterWidth() / 2)),
                (int)(y - (framesList.get(0).getHeight() / 2) + (target.getCharacterHeight() / 2)),
                observer);          
    }
    
    
    // SETTERS AND GETTERS.
    
    public void setLength(int length){
        this.length = length;
    }
    
}
