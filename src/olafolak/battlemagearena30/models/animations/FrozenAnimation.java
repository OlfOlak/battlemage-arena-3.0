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
 *
 * @author OlafPC
 */
public class FrozenAnimation extends Animation{
    
    private Character target;
    
    public FrozenAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, Character target) {
        super(fps, length, inputFrames, 1);
        
        this.target = target;
    }
    
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
    
    public void setLength(int length){
        this.length = length;
    }
    
}
