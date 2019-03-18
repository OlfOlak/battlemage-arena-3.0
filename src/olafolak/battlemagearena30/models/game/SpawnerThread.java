/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.game;

/**
 *
 * @author OlafPC
 */
public class SpawnerThread extends Thread implements Runnable{
    
    @Override
    public void run(){
        while(true)
            System.out.println("New THREAD");
    }
    
}
