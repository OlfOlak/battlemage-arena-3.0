/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.characters.Spearman;
import olafolak.battlemagearena30.models.exceptions.EnemySpawnedException;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;

/**
 *
 * @author OlafPC
 */
public class Spawner{
    
    private ArrayList<Enemy> enemysList;
    private int spawnTimer = 1;
    private int spawnDelay = 5;
    private Random rand = new Random();
    private Spearman tmpSpearman = null;
    
    public Spawner(int delay, ArrayList<Enemy> enemysList){
        this.spawnDelay = delay;
        this.enemysList = enemysList;
    }
    
    public void tick(){
        
        if(spawnTimer != 0)
            spawnTimer++;
        
        if(spawnTimer == (spawnDelay * 60)){
            spawnTimer = 0;
            spawnByStairs(); 
            enemysList.add(tmpSpearman);
            spawnTimer = 1;    
        }
        
        
    }
    
    
    public void spawnByStairs(){
        
        try {
            int tmpRand = rand.nextInt(2) + 1;
            
            switch(tmpRand){
                case 1:
                    tmpSpearman = new Spearman((int)(300 * WINDOW_WIDTH / 1280) ,(int)(WINDOW_HEIGHT + 0.4 * WINDOW_HEIGHT), 1, 70, 1, Game.player);
                    break;
                case 2:
                    tmpSpearman = new Spearman((int)(WINDOW_WIDTH + (40 * WINDOW_WIDTH / 1280)),(int)(0.4 * WINDOW_HEIGHT), 1, 70, 2, Game.player);
                    break;
                default:
                    System.out.println("Zhakowano nas kurła");
                    break;
        }
            
        } catch (IOException ex) {
            Logger.getLogger(Spawner.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Spawner IOException");
        }
    }

    public int getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }
    
    
    
}
