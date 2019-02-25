/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.world;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import olafolak.battlemagearena30.controllers.GameController;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class Arena {
    
    private double width;
    private double height;
    private int tilesW = 5;
    private int tilesH = 3;
    private ArrayList<ArrayList<Image>> tilesList;
    
    public Arena(double w, double h){
        
        this.width = w;
        this.height = h;
        //double tilesW = width / 256;
        System.out.println("tileW: " + tilesW);
        //double tilesH = height / 256;
        System.out.println("tileH: " + tilesH);
        tilesList = new ArrayList<>();
        
        //for(int i = 0; i < tilesH; i++)
        //   tilesList.add(new ArrayList<>());
        try{
            generateArena();
        } catch(IOException e){
            System.out.println("Problem in arena generation.");
        }
        
    }
    
    private void generateArena() throws IOException{

        
        Image tmp;
        ArrayList<Image> tmpArray = new ArrayList<>();
        
        for (int i = 0; i < tilesH - 1; ++i){
            for(int j = 0; j < tilesW - 1; ++j){
                
                switch(i){
                    case 0:
                        switch(j){
                            case 0:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_1.png"));
                                tmpArray.add(tmp);
                                break;
                            default:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_2.png"));
                                tmpArray.add(tmp);
                                break;    
                        }
                        break;
                    default:
                        switch(j){
                            case 0:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_15.png"));
                                tmpArray.add(tmp);
                                break;
                            default:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_6.png"));
                                tmpArray.add(tmp);
                                break;    
                        }
                        break;       
                }  
            }
            
            if(i == 0){
                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_3.png"));
                tmpArray.add(tmp);
                tilesList.add(tmpArray);
                tmpArray = new ArrayList<>();
                System.out.println("Size of tilesList: " + tilesList.size());
            }
            else{
                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_16.png"));
                tmpArray.add(tmp);
                tilesList.add(tmpArray);
                tmpArray = new ArrayList<>();
                System.out.println("Size of tilesList: " + tilesList.size());
            }
        }
        // Filling up last row of tiles.
        
        for(int j = 0; j < tilesW - 1; j++){
            switch(j){
                case 0:
                    tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_9.png"));
                    tmpArray.add(tmp);
                    break;
                default:
                    tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_18.png"));
                    tmpArray.add(tmp);
                    break;    
            } 
        }
        tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_11.png"));
        tmpArray.add(tmp);
        tilesList.add(tmpArray);
        System.out.println("Size of tilesList: " + tilesList.size());
    }
    
    public void draw(Graphics graphics, Game observer){
        
        //System.out.println("Size of tilesList: " + tilesList.size());
        
        for(int i = 0; i < tilesH; i++){
            for(int j = 0; j < tilesW; j++){
                graphics.drawImage(tilesList.get(i).get(j), j * 256, i * 256, observer);
            }
        }
        
    }
    
    
    
}
