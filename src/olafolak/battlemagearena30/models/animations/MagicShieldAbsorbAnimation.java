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
 *
 * @author OlafPC
 */
public class MagicShieldAbsorbAnimation extends Animation{
    
    public MagicShieldAbsorbAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode) {
        super(fps, length, inputFrames, mode);
    }
    
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
    
}
