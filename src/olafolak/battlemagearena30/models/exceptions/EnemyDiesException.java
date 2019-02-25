/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.exceptions;

import olafolak.battlemagearena30.models.characters.Enemy;



/**
 *
 * @author OlafPC
 */
public class EnemyDiesException extends CharacterDiesException {
    
    private Enemy enemy;
    
    public EnemyDiesException(Enemy enemy){
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    
    
    
    
}
