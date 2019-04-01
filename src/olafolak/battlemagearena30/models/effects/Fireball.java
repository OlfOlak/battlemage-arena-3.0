/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.effects;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.animations.Animation;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.characters.Player;
import olafolak.battlemagearena30.models.exceptions.EndOfFireballException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.sprites.BoundsBox;
import olafolak.battlemagearena30.models.utilities.AudioPlayer;

/**
 * Describes thrown fireball effect, it's flying projectile and final explosion.
 * @author OlfOlak
 */
public class Fireball extends Effect{
    
    // FIELDS.
    //Technical fields.
    /** Stores animation fun during the movement of the projectile.**/
    private Animation flyAnimation;
    /** Stores animation of explosion of the projectile.**/
    private Animation explosionAnimation;
    /** Stores animation of smoke after explosion**/
    private Animation smokeAnimation;
    /** Indicates if the projectile is flying.**/
    private boolean flys = true;
    /** Indicates if the projectile is exploding.**/
    private boolean explodes = false;
    /** Indicates if the explosionb dealt damage.**/
    private boolean dealtDamage = false;
    /** Framing of the explosion animation.**/
    private BoundsBox explosionArea;
    /** Framing of the progectile animation.**/
    private BoundsBox projectileArea;
    /** Plays the sound of flying projectile.**/
    private AudioPlayer fireballSound;
    /** Plays the sound of explosion.**/
    private AudioPlayer explosionSound;
    
    // Bounds.
    /** The width of projectile animation.**/
    private int projectileWidth = (int)(0.6 * Player.characterWidth);
    /** The height of projectile animation.**/
    private int projectileHeight = (int)(0.36 * Player.characterHeight);
    /** The width of explosion animation.**/
    private int explosionWidth = (int)(3.0 * Player.characterWidth);
    /** The height of explosion animation.**/
    private int explosionHeight = (int)(3.0 * Player.characterHeight);
    /** The width of range box of the explosion.**/
    private int explosionAreaWidth = (int)(explosionWidth / 3);
    /** The height of range box of the explosion.**/
    private int explosionAreaHeight = (int)(explosionHeight / 3);
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param originX sets the x position of center of the projectile's animation.
     * @param originY sets the y position of center of the projectile's animation.
     * @param range sets the maximum distance of projectile's movement.
     * @param rightDirection sets the direction of flight of the projectile.
     * @param enemysList reference to list of all enemy objects in game.
     * @throws IOException if problem with reading animation files occurs.
     */
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
        
        try{
            fireballSound = new AudioPlayer("src/res/sounds/soundEffects/spells/foom_0.wav", false);
            explosionSound = new AudioPlayer("src/res/sounds/soundEffects/explosions/explodemini.wav", false);
        }catch(Exception e){
            
        }
        fireballSound.play();
    }
    
    // METHODS.
    
    /**
     * Drawing method that switches animations and runs them.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws EndOfFireballException when projectiles explodes.
     */
    public void draw(Graphics graphics, Game observer) throws EndOfFireballException{
        
        try{
            if(flys == true){
                flyAnimation.run(x, y, graphics, observer);
                graphics.drawRect(projectileArea.x, projectileArea.y, projectileArea.width, projectileArea.height);
            }
            else{ 
                explosionSound.play();
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
    
    /**
     * Clocking method for updating effect's data.
     */
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
    
    /**
     * Updates bounds being relative to the effect's posision (x,y).
     */
    private void updateBounds(){
        if(flys == true){
            originX = x + (projectileWidth / 2);
            originY = y + (projectileHeight / 2);
        }
        projectileArea.setBoundsByOrigin(originX, originY, projectileWidth, projectileHeight * 2);
        
    }
    
    /**
     * Runs through enemys list and deals damage to those in the explosion area.
     */
    private void dealDamage(){
        
        
        for(Enemy e : enemysList){
            if(explosionArea.contains(e.getOriginX(), e.getOriginY()))
                e.takeDamage(50);
        }
           
        
    }
    
    /**
     * Checks if the flying projectile encounters any of the enemys on its way.
     * @return true if enemy encountered, otherwise false.
     */
    private boolean projectileMeetsEnemy(){
        
        for(Enemy e : enemysList){
            if(projectileArea.contains(e.getOriginX(), e.getOriginY()))
                return true;
        }
        return false;    
    }
    
    // SETTERS AND GETTERS.
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
