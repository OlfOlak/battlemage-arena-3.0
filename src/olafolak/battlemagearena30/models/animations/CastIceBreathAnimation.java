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
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfMagicShieldAbsorbException;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class CastIceBreathAnimation extends DirectionalAnimation{
    
    public CastIceBreathAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode) {
        super(fps, length, inputFrames, mode);
    }
    
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
    
}
