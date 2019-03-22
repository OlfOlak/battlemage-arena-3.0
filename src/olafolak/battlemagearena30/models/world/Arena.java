/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import olafolak.battlemagearena30.models.game.Game;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;
import olafolak.battlemagearena30.models.sprites.BoundsBox;

/**
 *
 * @author OlafPC
 */
public class Arena {
    
    private double width;
    private double height;
    private int tilesW = 20;
    private int tilesH = 12;
    private ArrayList<ArrayList<BufferedImage>> tilesList;
    private BufferedImage background;
    public static BoundsBox movementArea;
    
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
            background = ImageIO.read(new File("src/res/world/backgrounds/arenaBackground_3.png"));
            background = scale(background, 1340, 300);
            generateArena();
        } catch(IOException e){
            System.out.println("Problem in arena generation.");
        }
        
        movementArea = new BoundsBox((int)(WINDOW_WIDTH / 2), (int)(WINDOW_HEIGHT / 2 + 0.09 * WINDOW_HEIGHT), (int)(0.97 * WINDOW_WIDTH), (int)(0.8 * WINDOW_HEIGHT));
        
    }
    
    private static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    
    private void generateArena() throws IOException{

        
        BufferedImage tmp;
        ArrayList<BufferedImage> tmpArray = new ArrayList<>();
        
        for (int i = 0; i < 11; ++i){
            for(int j = 0; j < 21; ++j){
                
                switch(i){
                    case 0:
                        switch(j){
                            case 0:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_1.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 19:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_3.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 20:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_17_right.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            default:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_2.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;    
                        }
                        break;
                    case 3:
                        switch(j){
                            case 0:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_15.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 19:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_7.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 20:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_8.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            default:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_6.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                        }
                        break;
                    case 9:
                        switch(j){
                            case 0:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_9.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 5:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_10.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 19:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_11.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 20:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_17_right.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            default:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_18.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                        }
                        break;
                    case 10:
                        switch(j){
                            case 5:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_13.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 20:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_17_right.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            default:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_17.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                        }
                        break;
                    default:
                        switch(j){
                            case 0:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_15.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 19:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_16.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            case 20:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_17_right.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;
                            default:
                                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_6.png"));
                                tmp = scale(tmp, 64, 64);
                                tmpArray.add(tmp);
                                break;    
                        }
                        break;       
                }  
            }
            tilesList.add(tmpArray);
            tmpArray = new ArrayList<>();
            
            /*if(i == 0){
                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_3.png"));
                tmp = scale(tmp, 64, 64);
                tmpArray.add(tmp);
                tilesList.add(tmpArray);
                tmpArray = new ArrayList<>();
                System.out.println("Size of tilesList: " + tilesList.size());
            }
            else if(i == 5){
                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_8.png"));
                tmp = scale(tmp, 64, 64);
                tmpArray.add(tmp);
                tilesList.add(tmpArray);
                tmpArray = new ArrayList<>();
            }
            else{
                tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_16.png"));
                tmp = scale(tmp, 64, 64);
                tmpArray.add(tmp);
                tilesList.add(tmpArray);
                tmpArray = new ArrayList<>();
                System.out.println("Size of tilesList: " + tilesList.size());
            }*/
            
        }
        
        // Filling up last row of tiles.
        
        /*for(int j = 0; j < tilesW - 2; j++){
            switch(j){
                case 0:
                    tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_17.png"));
                    tmp = scale(tmp, 64, 64);
                    tmpArray.add(tmp);
                    break;
                case 6:
                    tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_13.png"));
                    tmp = scale(tmp, 64, 64);
                    tmpArray.add(tmp);
                default:
                    tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_17.png"));
                    tmp = scale(tmp, 64, 64);
                    tmpArray.add(tmp);
                    break;    
            } 
        
            
        }
        tmp = ImageIO.read(new File("src/res/world/arenaTiles/land_12_bottomright.png"));
        tmp = scale(tmp, 64, 64);
        tmpArray.add(tmp);
        tilesList.add(tmpArray);
        System.out.println("Size of tilesList: " + tilesList.size());*/
    }
    
    public void draw(Graphics graphics, Game observer){
        
        //System.out.println("Size of tilesList: " + tilesList.size());
        graphics.drawImage(background, 0, 0, observer);
        
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 21; j++){
                if(!(i == 0 && j == 20))
                graphics.drawImage(tilesList.get(i).get(j), j * 64, i * 64 + 128, observer);
            }
        }
        
        graphics.drawRect(movementArea.x, movementArea.y, movementArea.width, movementArea.height);
        //graphics.drawLine(1250, 350, 1330, 350);
        //graphics.drawLine(352, 700, 352, 800);
    }
    
    
    
}
