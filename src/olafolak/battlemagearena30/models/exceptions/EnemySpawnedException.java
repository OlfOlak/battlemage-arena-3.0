/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.exceptions;

import olafolak.battlemagearena30.models.characters.Enemy;

/**
 * Exception class for distinguishing the spawn of enemy.
 * @author OlfOlak
 */
public class EnemySpawnedException extends Exception{
    
    // FIELDS.
    /** Stores reference to specific enemy object.**/
    private Enemy enemy;
    
    // CONSTRUCTORS.
    /**
     * Basic constructor for carrying the reference of specific enemy objects.
     * @param enemy reference to specific enemy object.
     */
    public EnemySpawnedException(Enemy enemy){
        this.enemy = enemy;
    }
    
    // SETTERS AND GETTERS.
    
    public Enemy getEnemy(){
        return enemy;
    }
    
}
