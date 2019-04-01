/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.hud;

import java.awt.Font;
import java.awt.Graphics;
import olafolak.battlemagearena30.models.exceptions.EndOfBreakException;
import olafolak.battlemagearena30.models.game.Game;

/**
 * Describes visual counter that counts down time before next wave.
 * @author OlfOlak
 */
public class NextWaveCounter {
    
    // FIELDS.
    /** How many seconds to count down.**/
    private int delay = 10;
    /** Seconds left to count.**/
    private int counter = 10;
    /** Ticks timer.**/
    private int timer = 0;
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param delay sets the counters duration.
     */
    public NextWaveCounter(int delay){
        this.delay = delay;
        this.counter = delay;
    }
    
    // METHODS.
    /**
     * Clocking method for incrementing the timer.
     */
    public void tick(){
        if(timer != 0)
            timer++;
    }
    
    
    /**
     * Counts the time and checks if it is up.
     * @throws EndOfBreakException when the countdown ends.
     */
    public void run() throws EndOfBreakException{
        
        if(timer == 0)
            timer++;
        
        if(timer == 60){
            counter--;
            
            if(counter == 0){
                timer = 0;
                throw new EndOfBreakException();
            }else
                timer = 1;    
        }
    }
    
    /**
     * Draws the seconds left on the screen.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     */
    public void draw(Graphics graphics, Game observer){
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
        graphics.drawString("Next wave starts in:", 400, 200);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 40));
        graphics.drawString(String.valueOf(counter), 450, 300);
    }
    
    // SETTERS AND GETTERS.
    
}
