/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.exceptions;

import olafolak.battlemagearena30.models.effects.Fireball;

/**
 * Exception class for distinguishing the end of fireball spell.
 * @author OlfOlak
 */
public class EndOfFireballException extends Exception{
    
    // FIELDS.
    /** Stores specific fireball object.**/
    private Fireball fireball;
    
    // CONSTRUCTORS.
    /**
     * Basic constructor for carrying the reference of specific fireball objects.
     * @param fireball reference to specific fireball object.
     */
    public EndOfFireballException(Fireball fireball){
        this.fireball = fireball;
    }

    // SETTERS AND GETTERS.
    
    public Fireball getFireball() {
        return fireball;
    }

    public void setFireball(Fireball fireball) {
        this.fireball = fireball;
    }
    
    
    
}
