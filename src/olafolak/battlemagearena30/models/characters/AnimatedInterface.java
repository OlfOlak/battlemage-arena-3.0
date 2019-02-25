/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.animations.Animation;

/**
 *
 * @author OlafPC
 */
public interface AnimatedInterface {
    
    ArrayList<Image> getIdleRightAnimationFrames() throws IOException;
    ArrayList<Image> getIdleLeftAnimationFrames() throws IOException;
    ArrayList<Image> getWalkRightAnimationFrames() throws IOException;
    ArrayList<Image> getWalkLeftAnimationFrames() throws IOException;
    ArrayList<Image> getAttackRightAnimationFrames() throws IOException;
    ArrayList<Image> getAttackLeftAnimationFrames() throws IOException;
    
    
}
