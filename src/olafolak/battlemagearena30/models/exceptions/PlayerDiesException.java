/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.exceptions;

import olafolak.battlemagearena30.models.characters.Player;
import olafolak.battlemagearena30.models.exceptions.CharacterDiesException;

/**
 * Exception class for distinguishing the death of the player.
 * @author OlfOlak
 */
public class PlayerDiesException extends CharacterDiesException{
    
    // FIELDS.
    /** Stores reference to player's object.**/
    private Player player;
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param player reference to the player's object.
     */
    public PlayerDiesException(Player player){
        this.player = player;
    }
    
    // SETTERS AND GETTERS.
    
}
