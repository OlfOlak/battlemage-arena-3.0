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
 *
 * @author OlafPC
 */
public class NextWaveCounter {
    
    private int delay = 10;
    private int counter = 10;
    private int timer = 0;
    
    
    
    
    public NextWaveCounter(int delay){
        this.delay = delay;
        this.counter = delay;
    }
    
    public void tick(){
        if(timer != 0)
            timer++;
    }
    
    
    
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
    
    public void draw(Graphics graphics, Game observer){
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
        graphics.drawString("Next wave starts in:", 400, 200);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 40));
        graphics.drawString(String.valueOf(counter), 450, 300);
    }
    
}
