/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.ai;

import java.util.Random;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.world.Arena;

/**
 * Controls its enemy type subject to move to the player.
 * @author OlfOlak
 */
public class PathFinder {
    
    // FIELDS.
    /** Subject to control.**/
    private Enemy searcher;
    /** Generates random values.**/
    private Random rand = new Random();
    /** Which way the searcher will be flanking the player.**/
    private boolean wayOfFlanking;
    /** Is true if the player's origin is in the area limited by extensions of inner range box side borders.**/
    private boolean playerInYTunnel = false;
    /** Points which spawned point was the searcher spawned at.**/
    private int spawnPoint = 0;
    /** Points the position of the player relative to the searcher.**/
    private int state = 0;
    // 0 - start state.
    // 1 - player is on the same X tunnel on the left.
    // 2 - player is on the same X tunnel on the right.
    // 3 - player is on the top left.
    // 4 - player is on the top right.
    // 5 - player is on the down left.
    // 6 - player is on the down right.
    // 7 - player is on the same Y tunnel.
    // 8 - player is on leftRangeBox Y tunnel up.
    // 9 - player is on leftRangeBox Y tunnel down.
    // 10 - player is on rightRangeBox Y tunnel up.
    // 11 - player is on rightRangeBox Y tunnel down.
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param searcher reference to enemy type object that is to be controled.
     * @param spawnPoint sets the spawn point which the searcher was spawned at.
     */
    public PathFinder(Enemy searcher, int spawnPoint){
        this.searcher = searcher;
        this.spawnPoint = spawnPoint;
        
        update();     
    }
    
    // METHODS.
    
    /**
     * Controls the subject's movement in order to close by the player.
     */
    public void run(){
        
        //update(searcher);
        update();
        //searcher.stopMotion();
        
        switch(state){
            
            case 1:
                searcher.setMovesLeft(true);
                searcher.setMovesRight(false);
                searcher.setMovesUp(false);
                searcher.setMovesDown(false);
                break;
            case 2:
                searcher.setMovesLeft(false);
                searcher.setMovesRight(true);
                searcher.setMovesUp(false);
                searcher.setMovesDown(false);
                break;
            case 3:
                searcher.setMovesLeft(true);
                searcher.setMovesRight(false);
                searcher.setMovesUp(true);
                searcher.setMovesDown(false);
                break;
            case 4:
                searcher.setMovesLeft(false);
                searcher.setMovesRight(true);
                searcher.setMovesUp(true);
                searcher.setMovesDown(false);
                break;
            case 5:
                searcher.setMovesLeft(true);
                searcher.setMovesRight(false);
                searcher.setMovesUp(false);
                searcher.setMovesDown(true);
                break;    
            case 6:
                searcher.setMovesLeft(false);
                searcher.setMovesRight(true);
                searcher.setMovesUp(false);
                searcher.setMovesDown(true);
                break;
            case 7:
                if(!playerInYTunnel){
                    wayOfFlanking = rand.nextBoolean();
                    playerInYTunnel = true;
                }else{
                    if(wayOfFlanking){
                        searcher.setMovesLeft(false);
                        searcher.setMovesRight(true);
                        searcher.setMovesUp(false);
                        searcher.setMovesDown(false);
                    }else{
                        searcher.setMovesLeft(true);
                        searcher.setMovesRight(false);
                        searcher.setMovesUp(false);
                        searcher.setMovesDown(false);
                    }
                }
                break;
            case 8:
                searcher.setIsHeadedRight(false);
                searcher.setMovesLeft(false);
                searcher.setMovesRight(false);
                searcher.setMovesUp(true);
                searcher.setMovesDown(false);
                break;
            case 9:
                searcher.setIsHeadedRight(false);
                searcher.setMovesLeft(false);
                searcher.setMovesRight(false);
                searcher.setMovesUp(false);
                searcher.setMovesDown(true);
                break;
            case 10:
                searcher.setIsHeadedRight(true);
                searcher.setMovesLeft(false);
                searcher.setMovesRight(false);
                searcher.setMovesUp(true);
                searcher.setMovesDown(false);
                break;
            case 11:
                searcher.setIsHeadedRight(true);
                searcher.setMovesLeft(false);
                searcher.setMovesRight(false);
                searcher.setMovesUp(false);
                searcher.setMovesDown(true);
                break;
            default:
                break;
        }
            
    }
    
    /**
     * Sets proper state based on subject's and player's positions.
     */
    private void update(){
        try{
            // Enemy spawning.
            if(!Arena.movementArea.contains(searcher.getBaseX(), searcher.getBaseY())){
                if(spawnPoint == 1)
                    state = 8;
                else if(spawnPoint == 2)
                    state = 1;
            }else{
                // Player in the Y tunnel.
                if(Game.player.getOriginX() >= (searcher.getLeftRangeBox().x + searcher.getLeftRangeBox().width) &&
                        Game.player.getOriginX() <= searcher.getRightRangeBox().x){
                    state = 7;
                }else{
                    // Player in the leftRangeBox Y tunnel.
                    if(Game.player.getOriginX() > searcher.getLeftRangeBox().x
                            && Game.player.getOriginX() < (searcher.getLeftRangeBox().x + searcher.getLeftRangeBox().width)){

                        if(Game.player.getOriginY() < searcher.getLeftRangeBox().y)
                            state = 8;
                        else
                            state = 9; 
                    // Player in the rightRangeBox Y tunnel;
                    }else if(Game.player.getOriginX() > searcher.getRightRangeBox().x &&
                            Game.player.getOriginX() < (searcher.getRightRangeBox().x + searcher.getRightRangeBox().width)){

                        if(Game.player.getOriginY() < searcher.getLeftRangeBox().y)
                            state = 10;
                        else
                            state = 11; 
                    }else{
                        // Player on the right.
                        if(Game.player.getOriginX() - searcher.getOriginX() > 0){
                            // Player in X tunnel on the right.
                            if(Game.player.getOriginY() < (searcher.getRightRangeBox().y + searcher.getRightRangeBox().height) 
                                    && Game.player.getOriginY() > searcher.getRightRangeBox().y){
                                state = 2;
                            }  
                            // Player not in X tunnel.
                            else{
                                // Player in top right.
                                if(Game.player.getOriginY() < searcher.getRightRangeBox().y){
                                    state = 4;
                                }
                                else{
                                    state = 6;
                                }
                            }  
                        }
                        // Player on the left.
                        else{
                            // Player in X tunnel on the left.
                            if(Game.player.getOriginY() < (searcher.getRightRangeBox().y + searcher.getRightRangeBox().height) 
                                    && Game.player.getOriginY() > searcher.getRightRangeBox().y){
                                state = 1;
                            }  
                            // Player not in X tunnel.
                            else{
                                // Player in top left.
                                if(Game.player.getOriginY() < searcher.getRightRangeBox().y){
                                    state = 3;
                                }
                                else{
                                    state = 5;
                                }
                            }
                        }    
                    }
                    playerInYTunnel = false;

                }
            }
            
            
            
        }catch(NoSuchMethodError e){
            
        }
    } 
    
    
    
    // GETTERS AND SETTERS.
}
