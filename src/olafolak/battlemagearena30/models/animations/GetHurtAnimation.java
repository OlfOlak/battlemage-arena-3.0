/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfGetHurtException;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class GetHurtAnimation extends DirectionalAnimation{

    public GetHurtAnimation(int fps, double length, ArrayList<BufferedImage> leftFrames, ArrayList<BufferedImage> rightFrames) {
        super(fps, length, leftFrames, rightFrames);
    }
    
    @Override
    public void run(double x, double y, Graphics graphics, Game observer) throws EndOfGetHurtException{
        
        double frameLength = length * fps / frames;
        
        if(ticks >= frameLength){
            
            if(state == frames - 1)
                throw new EndOfGetHurtException();         
            else
                state++;
                
            ticks = 0;
        }
        if(rightDirection)
            graphics.drawImage(rightDirectionFrames.get(state), (int)x, (int)y, observer);    
        else
            graphics.drawImage(leftDirectionFrames.get(state), (int)x, (int)y, observer);     
    }
    
}
