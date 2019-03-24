/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.game;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
    private ArrayList<ArrayList<Enemy>> wavesList;
    private ArrayList<Enemy> waveQueue;
    private int waveQueueIterator = 0;
    
    private int spawnTimer = 1;
    private int spawnDelay = 5;
    
    private int wave = 1;
    private int round = 2;
    
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
    
    
    public Spawner(int round, ArrayList<Enemy> enemysList){
        
        this.enemysList = enemysList;
        waveQueue = new ArrayList<>();
        
        fillWaveQueue();
        
        for(Enemy e : waveQueue)
            waveOverallProgress += e.getProgressValue();
    }
    
    public void tick(){
        if(spawnTimer != 0)
            spawnTimer++;
        //System.out.println("progressToGain: " + currentProgressToGain);
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
    
    private void fillWaveQueue(){
        
        ArrayList<String> progressData = new ArrayList<>();
        
        try{
            String line;
            
            FileReader fileReader = new FileReader("src/res/others/GameProgress.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
                if(line.contains("@" + Integer.toString(round)))
                    progressData.add(line);
            }  
            
        }catch(FileNotFoundException e){
            System.out.println("GameProgress file not found");
        }catch(IOException e){
            e.printStackTrace();
        }
        
        int i = 0;
        String[] tmp;
        String[] tmp2;

        
        for(String s : progressData){
            
            tmp = progressData.get(i).split(",");
            
            if(i == 0){
                spawnDelay = Integer.valueOf(tmp[1]);
                waveProgressLimit = Integer.valueOf(tmp[2]);
            }else{
                
                if(i == wave){
                    for(int k = 1; k < tmp.length; k++){
                        tmp2 = tmp[k].split("-");

                        for(int l = 0; l < Integer.valueOf(tmp2[0]); l++){
                            waveQueue.add(spawnByStairs(tmp2[1]));
                        }
                    }
                }
            }
            i++;    
        }  

        /*waveQueue = new ArrayList<>();
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));
        waveQueue.add(spawnByStairs(EnemyType.FIREMAGE));
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));
        waveQueue.add(spawnByStairs(EnemyType.SPEARMAN));*/
        
    }
    
    private EnemyType characterStringToEnum(String input){
        
        if(input.equals("Spearman"))
            return EnemyType.SPEARMAN;
        else if(input.equals("Firemage"))
            return EnemyType.FIREMAGE;
        else
            return null;
    }
    
    public void nextWave(){
        wave++;
        fillWaveQueue();
    }
    
    private Enemy spawnByStairs(String input){
        
        try {
            int tmpRand = rand.nextInt(2) + 1;
            
            switch(tmpRand){
                case 1:
                    switch(characterStringToEnum(input)){
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
                    switch(characterStringToEnum(input)){
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
