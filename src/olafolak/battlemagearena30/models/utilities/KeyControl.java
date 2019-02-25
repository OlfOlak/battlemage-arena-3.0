/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import olafolak.battlemagearena30.controllers.GameController;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class KeyControl extends KeyAdapter{

    private Game game;
    
    public KeyControl(Game game){
        this.game = game;
    }
    
    
    public void keyPressed(KeyEvent e){
        game.keyPressed(e);
    }
    
    public void keyReleased(KeyEvent e){
        game.keyReleased(e);
    }
    
}
