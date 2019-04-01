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
import olafolak.battlemagearena30.models.characters.Player;
import olafolak.battlemagearena30.models.exceptions.EndOfIceBreathException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.sprites.BoundsBox;
import olafolak.battlemagearena30.models.utilities.AudioPlayer;

/**
 * Describes the ice breath spell that freezes enemys.
 * @author OlfOlak
 */
public class IceBreath extends Effect{
    
    // FIELDS.
    //Technical fields.
    /** Stores animation of ice breath.**/
    private Animation breathAnimation;
    /** The area that all enemy objects within freeze.**/
    private BoundsBox freezeArea;
    
    /** Plays the sound of freezing characters.**/
    private AudioPlayer freezeSound;
    
    /** Indicates if any enemys became frozen.**/
    private boolean frozeEnemys = false;
    
    // Bounds.
    /** The width of the ice breath animation.**/
    private int breathWidth = (int)(1.5 * Player.characterWidth);
    /** The height of the ice breath animation.**/
    private int breathHeight = (int)(0.6 * Player.characterHeight);
    /** The width of the effect's range box.**/
    private int freezeAreaWidth = (int)(1.0 * Player.characterWidth);
    /** The height of the effect's range box.**/
    private int freezeAreaHeight = (int)(0.8 * Player.characterHeight);
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param x the x position of the breath animation.
     * @param y the y position of the breath animation.
     * @param range sets the range that enemys within would be affected.
     * @param rightDirection sets the direction of ice breath.
     * @param enemysList reference to list of all enemy objects in game.
     * @throws IOException if problem with reading animation files occurs.
     */
    public IceBreath(int x, int y, int range, boolean rightDirection, ArrayList<Enemy> enemysList) throws IOException{
        
        super(x, y, range, rightDirection, enemysList);

        if(isHeadedRight == true){
            breathAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/iceBreath", "iceBreath_right", 9, breathWidth, breathHeight), 1);
            freezeArea = new BoundsBox(x + (int)(0.95 * Player.characterWidth), y + (int)(0.1 * Player.characterHeight), freezeAreaWidth, freezeAreaHeight);
        }
        else{
            breathAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/iceBreath", "iceBreath_left", 9, breathWidth, breathHeight), 1);
            freezeArea = new BoundsBox(x - (int)(0.95 * Player.characterWidth) + (int)(1.5 * Player.characterWidth) , y + (int)(0.1 * Player.characterHeight), freezeAreaWidth, freezeAreaHeight);
        }
        
        try{
            freezeSound = new AudioPlayer("src/res/sounds/soundEffects/spells/freeze2.wav", false);
        }catch(Exception e){
            
        }
        
    }
    
    // METHODS.
    
    /**
     * Drawing method that switches animations and runs them.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws EndOfIceBreathException when the ice breath ends.
     */
    public void draw(Graphics graphics, Game observer) throws EndOfIceBreathException{
        try{
            breathAnimation.run(x, y, graphics, observer);
            //graphics.drawRect(x, y - 30, 150, 90);
            graphics.drawRect(freezeArea.x, freezeArea.y, freezeArea.width, freezeArea.height);
            if(!frozeEnemys){
                freezeEnemys();
                frozeEnemys = true;
                freezeSound.play();
            }
        }
        catch(EndSingleAnimationException e){
            System.out.println("It must be freezing!");
            throw new EndOfIceBreathException();
            
        }
    }
    
    /**
     * Clocking method for updating effect's data.
     */
    public void tick(){     
        breathAnimation.incrementTicks();        
    }
    
    /**
     * Runs through enemys list and freezes those in range.
     */
    public void freezeEnemys(){
        
        for(Enemy e : enemysList){
            if(freezeArea.contains(e.getOriginX(), e.getOriginY()))
                e.freeze();
        }  
    }
    
    // SETTERS AND GETTERS.

}
