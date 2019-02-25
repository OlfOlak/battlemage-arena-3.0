/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

/**
 *
 * @author OlafPC
 */
public class PathFinder {
    
    private Enemy searcher;
    private Player player;
    private int state = 0;
    // 0 - start state.
    // 1 - player is on the same X tunnel on the left.
    // 2 - player is on the same X tunnel on the right.
    // 3 - player is on the top left.
    // 4 - player is on the top right.
    // 5 - player is on the down left.
    // 6 - player is on the down right.
    
    public PathFinder(Enemy searcher, Player player){
        this.searcher = searcher;
        this.player = player;
        
        update(); 
        
        
        
    }
    
    public void run(Enemy searcher, Player player){
        
        update(searcher, player);
        update();
        //searcher.stopMotion();
        
        switch(state){
            
            case 1:
                //searcher.setInMotionX('-');
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
            default:
                break;
        }
        
        
        
    }
    
    private void update(){
        
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
    
    private void update(Enemy searcher, Player player){
        this.searcher = searcher;
        this.player = player;
    }
    
    
}
