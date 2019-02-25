/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.exceptions;

import olafolak.battlemagearena30.models.effects.Fireball;

/**
 *
 * @author OlafPC
 */
public class EndOfFireballException extends Exception{
    
    private Fireball fireball;
    
    public EndOfFireballException(Fireball fireball){
        this.fireball = fireball;
    }

    public Fireball getFireball() {
        return fireball;
    }

    public void setFireball(Fireball fireball) {
        this.fireball = fireball;
    }
    
    
    
}
