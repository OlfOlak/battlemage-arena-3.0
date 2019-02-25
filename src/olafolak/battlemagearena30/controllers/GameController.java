package olafolak.battlemagearena30.controllers;

import java.awt.Dimension;
import javax.swing.JFrame;
import olafolak.battlemagearena30.models.game.Game;



/**
 *
 * @author OlafPC
 */
public class GameController {

    private static Game game;
    
    
    public static void main(String[] args){
        
        game = new Game();
        
        game.setPreferredSize(new Dimension((int)(game.WIDTH * game.SCALE), (int)(game.HEIGHT * game.SCALE)));
        game.setMaximumSize(new Dimension((int)(game.WIDTH * game.SCALE), (int)(game.HEIGHT * game.SCALE)));
        game.setMinimumSize(new Dimension((int)(game.WIDTH * game.SCALE), (int)(game.HEIGHT * game.SCALE)));
        
        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        game.start();
    }
    
    
}