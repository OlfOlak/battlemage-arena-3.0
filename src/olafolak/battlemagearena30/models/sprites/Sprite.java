/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import olafolak.battlemagearena30.controllers.GameController;
import olafolak.battlemagearena30.models.animations.Animation;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class Sprite {
    
    private double posX;
    private double posY;
    private int width;
    private int height;
    private Image texture;

    public Sprite(double x, double y, Image tex){
        this.posX = x;
        this.posY = y;
        this.texture = tex;
        width = texture.getWidth(null);
        height = texture.getHeight(null);
        
        
        
    }
    
    public void scale(int w, int h){
        
        
        
        
        
    }
    
    
    public void draw(Game observer){
        
    }
    
    
    
}
