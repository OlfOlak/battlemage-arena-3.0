/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

import java.awt.Graphics;
import olafolak.battlemagearena30.models.exceptions.CharacterDiesException;
import olafolak.battlemagearena30.models.exceptions.EnemyDiesException;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public interface CharacterInterface {
    
    //public void draw(Graphics graphics, Game observer) throws CharacterDiesException;
    
    public void tick();
    
    
}
