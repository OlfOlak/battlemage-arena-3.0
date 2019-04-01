/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.sprites;

import java.awt.Graphics;
import java.awt.Rectangle;
import olafolak.battlemagearena30.models.game.Game;

/**
 * Extension of the rectangle class by adding the center point.
 * @author OlfOlak
 */
public class BoundsBox extends Rectangle{
    
    // FIELDS.
    /** The x position of the center of the rectangle.**/
    public int originX;
    /** The y position of the center of the rectangle.**/
    public int originY;

    // CONSTRUCTORS.
    /**
     * Basic constuctor to initialize by center point.
     * @param originX sets the x position of center point.
     * @param originY sets the y position of center point.
     * @param w sets the width of the box.
     * @param h sets the height of the box.
     */
    public BoundsBox(int originX, int originY, int w, int h){
        super(originX - (w / 2), originY - (h / 2), w, h);
        this.originX = originX;
        this.originY = originY;
    }

    // METHODS.
    /**
     * Draws the bounds box by top left corner point.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     */
    public void draw(Graphics graphics, Game observer){
        graphics.drawRect(x, y, width, height);
        
    }
    
    /**
     * Sets the position and dimensions of the bounds box by its center point.
     * @param originX sets the x position of center point.
     * @param originY sets the y position of center point.
     * @param width sets the width of the box.
     * @param height sets the height of the box.
     */
    public void setBoundsByOrigin(int originX, int originY, int width, int height){
        this.originX = originX;
        this.originY = originY;
        this.width = width;
        this.height = height;
        this.x = originX - (width / 2);
        this.y = originY - (height / 2);
    }

    // SETTERS AND GETTERS.
    
}
