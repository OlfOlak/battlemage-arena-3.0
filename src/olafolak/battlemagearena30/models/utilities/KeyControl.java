/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import olafolak.battlemagearena30.models.game.Game;

/**
 * Enables implementing key listening control.
 * @author OlfOlak
 */
public class KeyControl extends KeyAdapter{

    // FIELDS.
    /** References the game in which the key control takes part.**/
    private Game game;
    
    // CONSTRUCTORS.
    /**
     * Basic constructor to pass game reference.
     * @param game game reference for key control.
     */
    public KeyControl(Game game){
        this.game = game;
    }
    
    // METHODS.
    /**
     * Method that check if any key is pressed.
     * @param e indicates which key has been pressed.
     */
    public void keyPressed(KeyEvent e){
        game.keyPressed(e);
    }
    
    /**
     * Method that check if any of the pressed keys is being released.
     * @param e indicates which key has been released.
     */
    public void keyReleased(KeyEvent e){
        game.keyReleased(e);
    }
    
}
