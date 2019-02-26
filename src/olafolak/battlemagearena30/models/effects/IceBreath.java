/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.effects;

import java.awt.Graphics;
import java.awt.Rectangle;
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
    private Rectangle freezeArea;
    
    private boolean frozeEnemys = false;
    
    public IceBreath(int x, int y, int range, boolean rightDirection, ArrayList<Enemy> enemysList) throws IOException{
        
        super(x, y, range, rightDirection, enemysList);
        freezeArea = new Rectangle(x, y - 30, 150, 90);
        
        
        if(isHeadedRight == true)
            breathAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/iceBreath", "iceBreath_right", 9, 150, 60), 1);
        else
            breathAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/iceBreath", "iceBreath_left", 9, 150, 60), 1);
        
    }
    
    public void draw(Graphics graphics, Game observer) throws EndOfIceBreathException{
        try{
            breathAnimation.run(x, y, graphics, observer);
            graphics.drawRect(x, y - 30, 150, 90);
            if(!frozeEnemys){
                freezeEnemys();
                frozeEnemys = true;
            }
        }
        catch(EndSingleAnimationException e){
            System.out.println("It must be freezing!");
            throw new EndOfIceBreathException();
            
        }
    }
    
    public void tick(){     
        breathAnimation.incrementTicks();        
    }
    
    public void freezeEnemys(){
        
        for(Enemy e : enemysList){
            if(freezeArea.contains(e.getOriginX(), e.getOriginY()))
                e.freeze();
        }  
    }
    
    
    
    
}
