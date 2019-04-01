/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.characters.FireMage;
import olafolak.battlemagearena30.models.characters.Spearman;
import olafolak.battlemagearena30.models.exceptions.WaveEndedException;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;

/**
 * Class for generating rounds enemys and distributing them into battle.
 * @author OlfOlak
 */
public class Spawner{
    
    // FIELDS.
    
    /** Reference to game's list of enemys. **/
    private ArrayList<Enemy> enemysList;
    /** List of file generated enemys with including the division into waves. **/
    private ArrayList<ArrayList<Enemy>> wavesList;
    /** List of enemys to be spawned during current wave. **/
    private ArrayList<Enemy> waveQueue;
    /** Iterator of wave enemys queue. **/
    private int waveQueueIterator = 0;
    
    /** Ticking timer for delaying enemy spawning. **/
    private int spawnTimer = 1;
    /** Delay with which next enemy spawns. **/
    private int spawnDelay = 5;
    
    /** The number of the current wave. **/
    private int wave = 1;
    /** The number of the current round**/
    private int round = 1;
    
    /** Sum of all enemys's progress values in current wave. **/
    private int waveOverallProgress = 0;
    /** Limit of spawned enemy's progress value. **/
    private int waveProgressLimit;
    /** Sum of progress values of the currently spawned enemys. **/
    private int currentProgressToGain = 0;
    /** Sum of progress values of the conquered enemys.**/
    private int currentProgressGained = 0;
    
    /** Value for generating random values. **/
    private Random rand = new Random();
    /** Enum of all types of enemys for switching. **/
    private enum EnemyType {SPEARMAN, FIREMAGE}; 
    
    /** The X position of spawn point number one. **/
    public static int spawnPointOneX = (int)(352 * WINDOW_WIDTH / 1280);
    /** The Y position of spawn point number one. **/
    public static int spawnPointOneY = (int)(WINDOW_HEIGHT + 0.4 * WINDOW_HEIGHT);
    /** The X position of spawn point number two. **/
    public static int spawnPointTwoX = (int)(WINDOW_WIDTH + 0.2 * WINDOW_WIDTH);
    /** The Y position of spawn point number two. **/
    public static int spawnPointTwoY = (int)(350 * WINDOW_HEIGHT / 768);
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param round indicates which rounds enemys should be generated.
     * @param enemysList reference to games list of spawned enemys.
     */
    public Spawner(int round, ArrayList<Enemy> enemysList){
        
        this.enemysList = enemysList;
        waveQueue = new ArrayList<>();
        wavesList = new ArrayList<>();
        
        fillWaveQueue();
        loadWaveQueue();
    }
    
    // METHODS.
    
    /**
     * Clocking method for updating spawner's data.
     */
    public void tick(){
        if(spawnTimer != 0)
            spawnTimer++; 
        //System.out.println("progressToGain: " + currentProgressToGain);
    }
    
    /**
     * Run method for calculating the possibility spawning enemies and passing them to the list of spawned enemys.
     * @throws WaveEndedException when the wave ends.
     */
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
    
    /**
     * Method that fills current round's enemys list by generating enemys objects based on the wave programming file (GameProgress.txt).
     */
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
                
                wavesList.add(new ArrayList<>());
                
                for(int k = 1; k < tmp.length; k++){
                    tmp2 = tmp[k].split("-");

                    for(int l = 0; l < Integer.valueOf(tmp2[0]); l++){
                        wavesList.get(i - 1).add(spawnByStairs(tmp2[1]));
                    }
                }
            }
            i++;    
        }
    }
    
    /**
     * Method for loading the current wave's enemys queue.
     */
    private void loadWaveQueue(){
        waveQueue = wavesList.get(wave - 1);
        
        for(Enemy e : waveQueue)
            waveOverallProgress += e.getProgressValue();
    }
    
    /**
     * Method converting enemy's type string input into enum type.
     * @param input string representation of enemy type.
     * @return null if input not recognized, otherwise enum representation of enemy type.
     */
    private EnemyType characterStringToEnum(String input){
        
        if(input.equals("Spearman"))
            return EnemyType.SPEARMAN;
        else if(input.equals("Firemage"))
            return EnemyType.FIREMAGE;
        else
            return null;
    }
    
    /**
     * Method for the spawner for next wave by loading new enemys queue list and reseting progress indicators.
     */
    public void nextWave(){
        waveQueue.clear();
        waveQueueIterator = 0;
        wave++;
        waveOverallProgress = 0;
        currentProgressToGain = 0;
        currentProgressGained = 0;
        spawnTimer = 1;
        loadWaveQueue();    
    }
    
    /**
     * Generates chosen enemy in one of the two stairs spawn points.
     * @param input string representation of enemy to spawn.
     * @return initiated enemy object.
     */
    private Enemy spawnByStairs(String input){
        
        try {
            int tmpRand = rand.nextInt(2) + 1;
            
            switch(tmpRand){
                case 1:
                    switch(characterStringToEnum(input)){
                        case SPEARMAN:
                            System.out.println("SPEARMAN SPAWNED");
                            return new Spearman(1, 1, 70);
                            //break;
                        case FIREMAGE:
                            System.out.println("FIREMAGE SPAWNED");
                            return new FireMage(1, 1, 70);
                            //break;    
                    }
                    break;
                case 2:
                    switch(characterStringToEnum(input)){
                        case SPEARMAN:
                            System.out.println("SPEARMAN SPAWNED");
                            return new Spearman(2, 1, 70);
                            //break;
                        case FIREMAGE:
                            System.out.println("FIREMAGE SPAWNED");
                            return new FireMage(2, 1, 70);
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
    
    /**
     * Adds up progress value of defeated enemy.
     * @param progress defeated enemy's progress value.
     */
    public void addProgress(int progress){
        currentProgressGained += progress;
        currentProgressToGain -= progress;
    }

    // SETTERS AND GETTERS.
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
