package olafolak.battlemagearena30.controllers;

import java.awt.Dimension;
import javax.swing.JFrame;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.menu.MainMenu;



/**
 * @author OlfOlak
 * @version 0.4.x
 * Main class for controlling main game components (main menu, game, loading game etc.)
 */
public class GameController {

    /**
     * Game type field for setting up a game.
     */
    private static Game game;
    private static MainMenu mainMenu;
    
    
    public static void main(String[] args){
        
        mainMenu = new MainMenu();
        mainMenu.run(args);
        
        /*game = new Game();
        
        game.setPreferredSize(new Dimension((int)(game.WINDOW_WIDTH), (int)(game.WINDOW_HEIGHT)));
        game.setMaximumSize(new Dimension((int)(game.WINDOW_WIDTH), (int)(game.WINDOW_HEIGHT)));
        game.setMinimumSize(new Dimension((int)(game.WINDOW_WIDTH), (int)(game.WINDOW_HEIGHT)));
        
        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        game.start();*/
    }
    
    
}