/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.ai;

import java.util.Random;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.characters.Player;
import olafolak.battlemagearena30.models.world.Arena;

/**
 *
 * @author OlafPC
 */
public class PathFinder {
    
    private Enemy searcher;
    private Player player;
    private Random rand = new Random();
    private boolean wayOfFlanking;
    private boolean playerInYTunnel = false;
    private int spawnPoint = 0;
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
    
    
    
    public PathFinder(Enemy searcher, Player player, int spawnPoint){
        this.searcher = searcher;
        this.player = player;
        this.spawnPoint = spawnPoint;
        
        update();     
    }
    
    public void run(Enemy searcher, Player player){
        
        update(searcher, player);
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
                if(player.getOriginX() >= (searcher.getLeftRangeBox().x + searcher.getLeftRangeBox().width) &&
                        player.getOriginX() <= searcher.getRightRangeBox().x){
                    state = 7;
                }else{
                    // Player in the leftRangeBox Y tunnel.
                    if(player.getOriginX() > searcher.getLeftRangeBox().x
                            && player.getOriginX() < (searcher.getLeftRangeBox().x + searcher.getLeftRangeBox().width)){

                        if(player.getOriginY() < searcher.getLeftRangeBox().y)
                            state = 8;
                        else
                            state = 9; 
                    // Player in the rightRangeBox Y tunnel;
                    }else if(player.getOriginX() > searcher.getRightRangeBox().x &&
                            player.getOriginX() < (searcher.getRightRangeBox().x + searcher.getRightRangeBox().width)){

                        if(player.getOriginY() < searcher.getLeftRangeBox().y)
                            state = 10;
                        else
                            state = 11; 
                    }else{
                        // Player on the right.
                        if(player.getOriginX() - searcher.getOriginX() > 0){
                            // Player in X tunnel on the right.
                            if(player.getOriginY() < (searcher.getRightRangeBox().y + searcher.getRightRangeBox().height) 
                                    && player.getOriginY() > searcher.getRightRangeBox().y){
                                state = 2;
                            }  
                            // Player not in X tunnel.
                            else{
                                // Player in top right.
                                if(player.getOriginY() < searcher.getRightRangeBox().y){
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
                            if(player.getOriginY() < (searcher.getRightRangeBox().y + searcher.getRightRangeBox().height) 
                                    && player.getOriginY() > searcher.getRightRangeBox().y){
                                state = 1;
                            }  
                            // Player not in X tunnel.
                            else{
                                // Player in top left.
                                if(player.getOriginY() < searcher.getRightRangeBox().y){
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
    
    private void update(Enemy searcher, Player player){
        this.searcher = searcher;
        this.player = player;
    }
    
    
}
