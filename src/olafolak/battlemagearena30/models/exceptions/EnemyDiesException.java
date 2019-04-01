/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.exceptions;

import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.exceptions.CharacterDiesException;



/**
 * Exception class for distinguishing the death of enemy typed object.
 * @author OlfOlak
 */
public class EnemyDiesException extends CharacterDiesException {
    
    // FIELDS.
    /** Stores the reference to enemy object.**/
    private Enemy enemy;
    
    // CONSTRUCTORS.
    /**
     * Basic constructor for carrying the reference of specific enemy objects.
     * @param enemy reference to specific enemy object.
     */
    public EnemyDiesException(Enemy enemy){
        this.enemy = enemy;
    }

    // SETTERS AND GETTERS.
    
    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

}
