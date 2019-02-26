/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.sprites;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class BoundsBox extends Rectangle{
    
    public int originX;
    public int originY;

    public BoundsBox(int originX, int originY, int w, int h){
        super(originX - (w / 2), originY - (h / 2), w, h);
        this.originX = originX;
        this.originY = originY;
    }

    public void draw(Graphics graphics, Game observer){
        graphics.drawRect(x, y, width, height);
        
    }
    
    public void setBoundsByOrigin(int originX, int originY, int width, int height){
        this.originX = originX;
        this.originY = originY;
        this.width = width;
        this.height = height;
        this.x = originX - (width / 2);
        this.y = originY - (height / 2);
    }

}
