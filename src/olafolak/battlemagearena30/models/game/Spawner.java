/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.game;

import java.awt.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.characters.FireMage;
import olafolak.battlemagearena30.models.characters.Spearman;
import olafolak.battlemagearena30.models.exceptions.EnemySpawnedException;
import olafolak.battlemagearena30.models.exceptions.WaveEndedException;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;

/**
 *
 * @author OlafPC
 */
public class Spawner{
    
    private ArrayList<Enemy> enemysList;
    private ArrayList<Enemy> waveQueue;
    private int waveQueueIterator = 0;
    
    private int spawnTimer = 1;
    private int spawnDelay = 5;
    
    private int waveOverallProgress = 0;
    private int waveProgressLimit;
    private int currentProgressToGain = 0;
    private int currentProgressGained = 0;
    
    private Random rand = new Random();
    private enum EnemyType {SPEARMAN, FIREMAGE}; 
    
    public static int spawnPointOneX = (int)(352 * WINDOW_WIDTH / 1280);
    public static int spawnPointOneY = (int)(WINDOW_HEIGHT + 0.4 * WINDOW_HEIGHT);
    public static int spawnPointTwoX = (int)(WINDOW_WIDTH + 0.2 * WINDOW_WIDTH);
    public static int spawnPointTwoY = (int)(350 * WINDOW_HEIGHT / 768);
    
    
    public Spawner(int delay, int progressLimit, ArrayList<Enemy> enemysList){
        this.spawnDelay = delay;
        this.waveProgressLimit = progressLimit;
        this.enemysList = enemysList;
        fillWaveQueue();
        
        for(Enemy e : waveQueue)
            waveOverallProgress += e.getProgressValue();
        
    }
    
    public void tick(){
        if(spawnTimer != 0)
            spawnTimer++;
        System.out.println("progressToGain: " + currentProgressToGain);
        
    }
    
    public void run() throws WaveEndedException{
        
        if(waveQueueIterator < waveQueue.size()){
            if(spawnTimer == (60 * spawnDelay)){
                if(waveQueue.get(waveQueueIterator).getProgressValue() <= (waveProgressLimit - currentProgressToGain)){
                    spawnTimer = 0;
                    enemysList.add(waveQueue.get(waveQueueIterator));
                    currentProgressToGain += waveQueue.get(waveQueueIterator).getProgressValue();
                    waveQueueIterator++;
                }
                spawnTimer = 1;
            }
        }else{
            if(currentProgressGained == waveOverallProgress)
                throw new WaveEndedException();
        }    
    }
    
    private void fillWaveQueue() throws IOException{
        
        String[] progressData = (String[]) Files.lines(Paths.get("src/res/others/GameProgress.txt"))
                .filter(x -> x.contains('@' + String.valueOf(1)))
                .toArray();
        
        String[] tmp;
        
        int i = 0;        
        for(String s : progressData){
            
            tmp = progressData[i].split(",");
            
            switch(i){
                case 1:
                    
                    break;
            }
            
        }    
                
                
                
        
        
        
        /*waveQueue = new ArrayList<>();
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));
        waveQueue.add(spawnByStairs(EnemyType.FIREMAGE));
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));*/
        
    }
    
    private void nextWave(){
        
    }
    
    private Enemy spawnByStairs(EnemyType type){
        
        try {
            int tmpRand = rand.nextInt(2) + 1;
            
            switch(tmpRand){
                case 1:
                    switch(type){
                        case SPEARMAN:
                            System.out.println("SPEARMAN SPAWNED");
                            return new Spearman(1, 1, 70, Game.player);
                            //break;
                        case FIREMAGE:
                            System.out.println("FIREMAGE SPAWNED");
                            return new FireMage(1, 1, 70, Game.player);
                            //break;
                    }
                    break;
                case 2:
                    switch(type){
                        case SPEARMAN:
                            System.out.println("SPEARMAN SPAWNED");
                            return new Spearman(2, 1, 70, Game.player);
                            //break;
                        case FIREMAGE:
                            System.out.println("FIREMAGE SPAWNED");
                            return new FireMage(2, 1, 70, Game.player);
                            //break;
                    }
                    break;
                default:
                    System.out.println("Zhakowano nas kurła");
                    break;
            } 
            
        } catch (IOException ex) {
            Logger.getLogger(Spawner.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Spawner IOException");
        }
        
        return null;
    }
    
    public void addProgress(int progress){
        currentProgressGained += progress;
        currentProgressToGain -= progress;
    }

    public int getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    public ArrayList<Enemy> getEnemysList() {
        return enemysList;
    }

    public void setEnemysList(ArrayList<Enemy> enemysList) {
        this.enemysList = enemysList;
    }

    public ArrayList<Enemy> getWaveQueue() {
        return waveQueue;
    }

    public void setWaveQueue(ArrayList<Enemy> waveQueue) {
        this.waveQueue = waveQueue;
    }

    public int getWaveQueueIterator() {
        return waveQueueIterator;
    }

    public void setWaveQueueIterator(int waveQueueIterator) {
        this.waveQueueIterator = waveQueueIterator;
    }

    public int getSpawnDelay() {
        return spawnDelay;
    }

    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    public int getWaveOverallProgress() {
        return waveOverallProgress;
    }

    public void setWaveOverallProgress(int waveOverallProgress) {
        this.waveOverallProgress = waveOverallProgress;
    }

    public int getWaveProgressLimit() {
        return waveProgressLimit;
    }

    public void setWaveProgressLimit(int waveProgressLimit) {
        this.waveProgressLimit = waveProgressLimit;
    }

    public int getCurrentProgressToGain() {
        return currentProgressToGain;
    }

    public void setCurrentProgressToGain(int currentProgressToGain) {
        this.currentProgressToGain = currentProgressToGain;
    }

    public int getCurrentProgressGained() {
        return currentProgressGained;
    }

    public void setCurrentProgressGained(int currentProgressGained) {
        this.currentProgressGained = currentProgressGained;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public static int getSpawnPointOneX() {
        return spawnPointOneX;
    }

    public static void setSpawnPointOneX(int spawnPointOneX) {
        Spawner.spawnPointOneX = spawnPointOneX;
    }

    public static int getSpawnPointOneY() {
        return spawnPointOneY;
    }

    public static void setSpawnPointOneY(int spawnPointOneY) {
        Spawner.spawnPointOneY = spawnPointOneY;
    }

    public static int getSpawnPointTwoX() {
        return spawnPointTwoX;
    }

    public static void setSpawnPointTwoX(int spawnPointTwoX) {
        Spawner.spawnPointTwoX = spawnPointTwoX;
    }

    public static int getSpawnPointTwoY() {
        return spawnPointTwoY;
    }

    public static void setSpawnPointTwoY(int spawnPointTwoY) {
        Spawner.spawnPointTwoY = spawnPointTwoY;
    }
    

    
    
    
    
}
