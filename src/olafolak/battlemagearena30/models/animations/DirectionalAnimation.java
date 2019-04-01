/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.animations;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Extension of animation class for handling two directional animations.
 * @author OlfOlak
 */
public class DirectionalAnimation extends Animation{
    
    // FIELDS.
    
    // -inherited.
    /** List of frames for depicting if direction flag is set to left.**/
    protected ArrayList<BufferedImage> leftDirectionFrames;
    /** List of frames for depicting if direction flag is set to right.**/
    protected ArrayList<BufferedImage> rightDirectionFrames;
    /** Indicates direction the animation is to be headed.**/
    protected boolean rightDirection = true;
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param fps informs in what fps the animation is run.
     * @param length how long does one cycle of animation lasts.
     * @param inputFrames list of images that animation consists of.
     * @param mode 0 for infinite loop of animation, other values indicate numbers of cicles.
     */
    public DirectionalAnimation(int fps, double length, ArrayList<BufferedImage> inputFrames, int mode) {
        super(fps, length, inputFrames, mode);
    }
    
    /**
     * Basic constructor.
     * @param fps informs in what fps the animation is run.
     * @param length how long does one cycle of animation lasts.
     * @param leftFrames list of images for character headed left that animation consists of.
     * @param rightFrames list of images for character headed right that animation consists of.
     */
    public DirectionalAnimation(int fps, double length, ArrayList<BufferedImage> leftFrames, ArrayList<BufferedImage> rightFrames){
        super(fps, length, null, 0);
        frames = leftFrames.size();
        this.leftDirectionFrames = leftFrames;
        this.rightDirectionFrames = rightFrames;   
    }
    
    // METHODS.
    
    /**
     * Changes animation's direction flag.
     * @param rightDirection indicates direction the animation is to be headed.
     */
    public void updateDirection(boolean rightDirection){
        this.rightDirection = rightDirection;
    }
    
    // SETTERS AND GETTERS.
    
}
