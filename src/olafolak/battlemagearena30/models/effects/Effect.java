/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.effects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import olafolak.battlemagearena30.models.characters.Enemy;
import static olafolak.battlemagearena30.models.characters.Player.scale;

/**
 *
 * @author OlafPC
 */
public abstract class Effect {
    
    //Technical fields.
    protected int x;
    protected int y;
    protected int originX;
    protected int originY;
    protected int range;
    protected boolean isHeadedRight;
    
    protected ArrayList<Enemy> enemysList;
    protected Rectangle damageArea;
    
    // Bounds.
    
    
    // Constructors.
    public Effect(int x, int y, int range, boolean rightDirection, ArrayList<Enemy> enemysList) throws IOException{
        this.x = x;
        this.y = y;
        this.range = range;
        isHeadedRight = rightDirection;
        this.enemysList = enemysList;
    }
    
    
    // Methods.
    protected ArrayList<BufferedImage> getAnimationFrames(String source, String filename, int fileCount, int width, int height) throws IOException{
        
        ArrayList<BufferedImage> tmp = new ArrayList<>();
        //int width = 60;
        //int height = 36;
        
        BufferedImage tmpImage = ImageIO.read(new File(source + '/' + filename + "_1.png"));
        tmpImage = scale(tmpImage, width, height);
        tmp.add(tmpImage);
        
        for(int i = 1; i < fileCount; i++){

            tmpImage = ImageIO.read(new File(source + '/' + filename + '_' + String.valueOf(i) + ".png")); 
            tmpImage = scale(tmpImage, width, height);
            tmp.add(tmpImage);
        }

        return tmp;
    }
    
}
