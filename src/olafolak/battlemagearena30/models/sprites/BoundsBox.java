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
public class BoundsBox {
    
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Dimension leftUpCorner;
    private Dimension rightUpCorner;
    private Dimension leftDownCorner;
    private Dimension rightDownCorner;
    
    
    public BoundsBox(int x, int y, int w, int h){
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = w;
        leftUpCorner = new Dimension(x - width / 2, y - height / 2);
        rightUpCorner = new Dimension(x + width / 2, y - height / 2);
        leftDownCorner = new Dimension(x - width / 2, y + height / 2);
        rightDownCorner = new Dimension(x + width + 2, y + height / 2);
    }
    
    public boolean contains(double x, double y){
        
        if(x <= rightUpCorner.width 
                && x >= leftUpCorner.width
                && y >= leftUpCorner.height
                && y <= leftDownCorner.height)
            return true;
        else
            return false;
    }
    
    public void draw(Graphics graphics, Game observer){
        graphics.drawRect(leftUpCorner.width, leftUpCorner.height, width, height);
        
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Dimension getLeftUpCorner() {
        return leftUpCorner;
    }

    public void setLeftUpCorner(Dimension leftUpCorner) {
        this.leftUpCorner = leftUpCorner;
    }

    public Dimension getRightUpCorner() {
        return rightUpCorner;
    }

    public void setRightUpCorner(Dimension rightUpCorner) {
        this.rightUpCorner = rightUpCorner;
    }

    public Dimension getLeftDownCorner() {
        return leftDownCorner;
    }

    public void setLeftDownCorner(Dimension leftDownCorner) {
        this.leftDownCorner = leftDownCorner;
    }

    public Dimension getRightDownCorner() {
        return rightDownCorner;
    }

    public void setRightDownCorner(Dimension rightDownCorner) {
        this.rightDownCorner = rightDownCorner;
    }

    
    
    
    
}
