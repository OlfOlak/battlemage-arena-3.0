/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.effects;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.animations.Animation;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.exceptions.EndOfIceBreathException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class IceBreath extends Effect{
    
    //Technical fields.
    private Animation breathAnimation;
    
    public IceBreath(int x, int y, int range, boolean rightDirection, ArrayList<Enemy> enemysList) throws IOException{
        
        super(x, y, range, rightDirection, enemysList);
        
        
        if(isHeadedRight == true)
            breathAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/iceBreath", "iceBreath_right", 9, 150, 60), 1);
        else
            breathAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/iceBreath", "iceBreath_left", 9, 150, 60), 1);
        
    }
    
    public void draw(Graphics graphics, Game observer) throws EndOfIceBreathException{
        try{
            breathAnimation.run(x, y, graphics, observer);
        }
        catch(EndSingleAnimationException e){
            System.out.println("It must be freezing!");
            throw new EndOfIceBreathException();
            
        }
    }
    
    public void tick(){     
        breathAnimation.incrementTicks();
            
    }
    
    
    
    
}
