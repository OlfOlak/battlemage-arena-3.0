/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.effects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.animations.Animation;
import olafolak.battlemagearena30.models.characters.Enemy;
import static olafolak.battlemagearena30.models.characters.Character.*;
import olafolak.battlemagearena30.models.exceptions.EndOfFireballException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.sprites.BoundsBox;

/**
 *
 * @author OlafPC
 */
public class Fireball extends Effect{
    
    //Technical fields.
    private Animation flyAnimation;
    private Animation explosionAnimation;
    private Animation smokeAnimation;
    private boolean flys = true;
    private boolean explodes = false;
    private boolean dealtDamage = false;
    private BoundsBox explosionArea;
    private BoundsBox projectileArea;
    
    // Bounds.
    private int projectileWidth = (int)(0.6 * characterWidth);
    private int projectileHeight = (int)(0.36 * characterHeight);
    private int explosionWidth = (int)(3.0 * characterWidth);
    private int explosionHeight = (int)(3.0 * characterHeight);
    private int explosionAreaWidth = (int)(explosionWidth / 3);
    private int explosionAreaHeight = (int)(explosionHeight / 3);
    
    
    public Fireball(int originX, int originY, int range, boolean rightDirection, ArrayList<Enemy> enemysList) throws IOException{
        
        super(originX, originY, range, rightDirection, enemysList);
        this.originX = originX;
        this.originY = originY;
        this.x = originX - (projectileWidth / 2);
        this.y = originY - (projectileHeight / 2);
        projectileArea = new BoundsBox(originX, originY, projectileWidth, projectileHeight);
        
        if(isHeadedRight == true)
            flyAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/fireball", "projectile_right", 9, projectileWidth, projectileHeight), 0);
        else
            flyAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/fireball", "projectile_left", 9, projectileWidth, projectileHeight), 0);
        
        explosionAnimation = new Animation(60, 1, getAnimationFrames("src/res/effects/fireball", "explosion", 13, explosionWidth, explosionHeight), 1);
        //smokeAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/fireball", "smoke", 13, 300, 300), 1);    
    }
    
    public void draw(Graphics graphics, Game observer) throws EndOfFireballException{
        
        try{
            if(flys == true){
                flyAnimation.run(x, y, graphics, observer);
                graphics.drawRect(x, y, projectileWidth, projectileHeight);
            }
            else{ 
                explosionArea = new BoundsBox(originX, originY, explosionAreaWidth, explosionAreaHeight);
                explosionAnimation.run(originX - (explosionWidth / 2), originY - (explosionHeight / 2), graphics, observer);
                if(!dealtDamage){
                    dealDamage();
                    dealtDamage = true;
                }

                graphics.drawRect(explosionArea.x, explosionArea.y, explosionArea.width, explosionArea.height);
                System.out.println("x: " + x + " y: " + y);
                //smokeAnimation.run(x, y, graphics, observer);
            }
        }
        catch(EndSingleAnimationException e){
            System.out.println("KAAABOOOOOOM!!!!!!!");
            explosionAnimation.reset();
            throw new EndOfFireballException(this);    
        }
    }
    
    public void tick(){
        
        updateBounds();
        
        if(range == 0 || projectileMeetsEnemy() == true){
            flys = false;
            explodes = true;
        }
            
        if(flys == true){
            System.out.println("Flys");
            if(isHeadedRight == true)
                x += 5;
            else
                x -= 5;
            range -= 5;
        }
        else if(explodes == true)
            System.out.println("Explodes");
        
        flyAnimation.incrementTicks();
        if(explodes == true){
            explosionAnimation.incrementTicks();
            //smokeAnimation.setTicks(smokeAnimation.getTicks() + 1);
        }
    }
    
    private void updateBounds(){
        if(flys == true){
            originX = x + (projectileWidth / 2);
            originY = y + (projectileHeight / 2);
        }
        projectileArea.setBounds(x, y, projectileWidth, projectileHeight);
        
    }
    
    private void dealDamage(){
        
        
        for(Enemy e : enemysList){
            if(explosionArea.contains(e.getOriginX(), e.getOriginY()))
                e.takeDamage(50);
        }
           
        
    }
    
    private boolean projectileMeetsEnemy(){
        
        for(Enemy e : enemysList){
            if(projectileArea.contains(e.getOriginX(), e.getOriginY()))
                return true;
        }
        return false;    
    }
    
    // Setters and getters.
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Animation getFlyAnimation() {
        return flyAnimation;
    }

    public void setFlyAnimation(Animation flyAnimation) {
        this.flyAnimation = flyAnimation;
    }

    public Animation getExplosionAnimation() {
        return explosionAnimation;
    }

    public void setExplosionAnimation(Animation explosionAnimation) {
        this.explosionAnimation = explosionAnimation;
    }

    public boolean isFlys() {
        return flys;
    }

    public void setFlys(boolean flys) {
        this.flys = flys;
    }

    public boolean isExplodes() {
        return explodes;
    }

    public void setExplodes(boolean explodes) {
        this.explodes = explodes;
    }

    public boolean isIsHeadedRight() {
        return isHeadedRight;
    }

    public void setIsHeadedRight(boolean isHeadedRight) {
        this.isHeadedRight = isHeadedRight;
    }
    
    
    
}
