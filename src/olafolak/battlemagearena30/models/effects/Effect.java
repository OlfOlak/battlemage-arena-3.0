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
 * Describes basic properties for use by magic spells, etc.
 * @author OlfOlak
 */
public abstract class Effect {
    
    // FIELDS.
    //Technical fields.
    /** The x position of the visual representation.**/
    protected int x;
    /** The y position of the visual representation.**/
    protected int y;
    /** The x position of center of the visual representation.**/
    protected int originX;
    /** The y position of center of the visual representation.**/
    protected int originY;
    /** The maximum distance the effect would move.**/
    protected int range;
    /** Indicates the direction of movement.**/
    protected boolean isHeadedRight;
    
    /** List of all enemy objects in game.**/
    protected ArrayList<Enemy> enemysList;
    /** The area within the character objects would be affected.**/
    protected Rectangle damageArea;
    
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param x sets the x position of visual representation.
     * @param y sets the y position of visual representation.
     * @param range sets the maximum distance of movement.
     * @param rightDirection sets the flag of direction.
     * @param enemysList reference to all enemys list in game.
     */
    public Effect(int x, int y, int range, boolean rightDirection, ArrayList<Enemy> enemysList){
        this.x = x;
        this.y = y;
        this.range = range;
        isHeadedRight = rightDirection;
        this.enemysList = enemysList;
    }
    
    
    // METHODS.
    /**
     * Loads images from given source to be animations frames.
     * @param source file source of the images to be loaded.
     * @param filename general name of every's image file.
     * @param fileCount number of file to be loaded, counted from 1.
     * @param width width of the loaded image.
     * @param height height of the loaded image/
     * @return list of images.
     * @throws IOException when there is problem with reading any image file.
     */
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
