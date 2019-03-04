/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static olafolak.battlemagearena30.models.characters.Character.characterHeight;
import static olafolak.battlemagearena30.models.characters.Character.characterWidth;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfBloodException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfFreezeException;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class FreezeAnimation extends Animation{
    
    public FreezeAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode) {
        super(fps, length, inputFrames, mode);
    }
    
    public void run(double x, double y, Graphics graphics, Game observer) throws EndOfFreezeException{
        
        double frameLength = length * fps / frames;

        if(ticks >= frameLength){
            
            if(state == frames - 1)
                throw new EndOfFreezeException();         
            else
                state++;
                
            ticks = 0;
        }
        
        graphics.drawImage(framesList.get(state),
                (int)(x - (framesList.get(0).getWidth() / 2) + (characterWidth / 2)),
                (int)(y - (framesList.get(0).getHeight() / 2) + (characterHeight / 2)),
                observer);         
    }
    
}
