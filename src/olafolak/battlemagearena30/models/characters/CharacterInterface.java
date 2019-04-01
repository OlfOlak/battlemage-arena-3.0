/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

import java.awt.Graphics;
import olafolak.battlemagearena30.models.game.Game;

/**
 * Interface class for imposition of implementation the character's arbitrary methods.
 * @author OlfOlak
 */
public interface CharacterInterface {
    
    /**
     * Switches animations and runs them.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws Exception when the character dies.
     */
    public void draw(Graphics graphics, Game observer) throws Exception;
    
    /**
     * Clocking method for updating data.
     */
    public void tick();
    
    
}
