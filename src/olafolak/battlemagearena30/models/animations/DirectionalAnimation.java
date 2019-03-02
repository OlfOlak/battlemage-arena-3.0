/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author OlafPC
 */
public class DirectionalAnimation extends Animation{
    
    // Fields.
    protected ArrayList<BufferedImage> leftDirectionFrames;
    protected ArrayList<BufferedImage> rightDirectionFrames;
    protected boolean rightDirection = true;
    
    
    public DirectionalAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode) {
        super(fps, length, inputFrames, mode);
    }
    
    public DirectionalAnimation(int fps, double length, ArrayList<BufferedImage> leftFrames, ArrayList<BufferedImage> rightFrames){
        super(fps, length, null, 0);
        frames = leftFrames.size();
        this.leftDirectionFrames = leftFrames;
        this.rightDirectionFrames = rightFrames;   
    }
    
    public void updateDirection(boolean rightDirection){
        this.rightDirection = rightDirection;
    }
    
}
