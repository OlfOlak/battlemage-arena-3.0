/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.exceptions;

import olafolak.battlemagearena30.models.characters.Player;

/**
 *
 * @author OlafPC
 */
public class PlayerDiesException extends CharacterDiesException{
    
    private Player player;
    
    public PlayerDiesException(Player player){
        this.player = player;
    }
    
}
