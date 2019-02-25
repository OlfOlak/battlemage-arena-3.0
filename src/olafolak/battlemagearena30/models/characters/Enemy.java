/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import olafolak.battlemagearena30.models.animations.Animation;
import static olafolak.battlemagearena30.models.characters.Player.scale;
import olafolak.battlemagearena30.models.effects.Fireball;
import olafolak.battlemagearena30.models.exceptions.EnemyDiesException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.sprites.BoundsBox;

/**
 *
 * @author OlafPC
 */
public class Enemy extends Character implements CharacterInterface{

    // Technical fields.
    private Animation hurtRightAnimation;
    private Animation hurtLeftAnimation;
    private Animation bloodAnimation;
    private Animation dieRightAnimation;
    private Animation dieLeftAnimation;
    private boolean playerInRange = false;
    private boolean canAttack = true;
    private PathFinder pathFinder;
    private int attackTimer = 0;
    private Player player;
    
    
    
    
    
    public Enemy(int x, int y, int speed, int health, Player player) throws IOException {
        super(x, y, speed, health);
        
        pathFinder = new PathFinder(this, player);
        this.player = player;
        isHeadedRight = false;
        
        idleRightAnimation = new Animation(60, 0.85, getAnimationFrames("src/res/sprites/w_warrior", "idle_right", 5, 100, 100), 0);
        idleLeftAnimation = new Animation(60, 0.85, getAnimationFrames("src/res/sprites/w_warrior", "idle_left", 5, 100, 100), 0);
        walkRightAnimation = new Animation(60, 0.7, getAnimationFrames("src/res/sprites/w_warrior", "walk_right", 5, 100, 100), 0);
        walkLeftAnimation = new Animation(60, 0.7, getAnimationFrames("src/res/sprites/w_warrior", "walk_left", 5, 100, 100), 0);
        attackRightAnimation = new Animation (60, 0.7, getAnimationFrames("src/res/sprites/w_warrior", "attack_right", 5, 100, 100), 1);
        attackLeftAnimation = new Animation (60, 0.7, getAnimationFrames("src/res/sprites/w_warrior", "attack_left", 5, 100, 100), 1); 
        hurtRightAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/sprites/w_warrior", "hurt_right", 5, 100, 100), 1);
        hurtLeftAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/sprites/w_warrior", "hurt_left", 5, 100, 100), 1); 
        bloodAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/blood", "blood", 6, 100, 100), 1);
        dieRightAnimation = new Animation(60, 0.7, getAnimationFrames("src/res/sprites/w_warrior", "die_right", 5, 100, 100), 1);
        dieLeftAnimation = new Animation(60, 0.7, getAnimationFrames("src/res/sprites/w_warrior", "die_left", 5, 100, 100), 1);
        
    }

    @Override
    public void draw(Graphics graphics, Game observer) throws EnemyDiesException{
        
        try{
            
            if(isIdle){
                if(isHeadedRight)
                    idleRightAnimation.run(x, y, graphics, observer);
                else
                    idleLeftAnimation.run(x, y, graphics, observer);    
            }
            if(isMoving){
                if(isHeadedRight)
                    walkRightAnimation.run(x, y, graphics, observer);
                else
                    walkLeftAnimation.run(x, y, graphics, observer);
            }
            if(isAttacking){
                if(isHeadedRight)
                    attackRightAnimation.run(x, y, graphics, observer);
                else
                    attackLeftAnimation.run(x, y, graphics, observer);
            }
            if(takesDamage){
                if(isHeadedRight){
                    hurtRightAnimation.run(x, y, graphics, observer);
                }
                else{
                    hurtLeftAnimation.run(x, y, graphics, observer);
                }
                bloodAnimation.run(x, y, graphics, observer);
            }
            if(isDying){
                if(isHeadedRight)
                    dieRightAnimation.run(x, y, graphics, observer);
                else
                    dieLeftAnimation.run(x, y, graphics, observer);
            }
     
        }catch(EndSingleAnimationException e){

            if(isDying){
                dieLeftAnimation.setState(0);
                dieRightAnimation.setState(0);
                dieLeftAnimation.setTicks(0);
                dieRightAnimation.setTicks(0);
                throw new EnemyDiesException(this);
            }
            if(takesDamage){
                takesDamage = false;
                hurtRightAnimation.setState(0);
                hurtLeftAnimation.setState(0);
                bloodAnimation.setState(0);
                hurtRightAnimation.setTicks(0);
                hurtLeftAnimation.setTicks(0);
                bloodAnimation.setTicks(0);
            }
            if(isAttacking){
                player.takeDamage(10);
                isAttacking = false;
                attackRightAnimation.setState(0);
                attackLeftAnimation.setState(0);
                attackRightAnimation.setTicks(0);
                attackLeftAnimation.setTicks(0);
            }
                
        }
        graphics.drawRect(boundsBox.x, boundsBox.y, boundsBox.width, boundsBox.height);
        graphics.drawRect(leftRangeBox.x, leftRangeBox.y, leftRangeBox.width, leftRangeBox.height);
        graphics.drawRect(rightRangeBox.x, rightRangeBox.y, rightRangeBox.width, rightRangeBox.height);
        graphics.setColor(Color.red);
        graphics.fillRect(x, y - 10, health * 100 / maxHealth, 10);
        graphics.setColor(Color.black);
        
    }
    
    public void tick(Player player){
        
        moveToPlayer(player);
        updateMovement();
        updateBounds(); 
        updateAnimations();
        checkPlayerInRange(player);
        
        if(attackTimer != 0)
            attackTimer++;
        
        if(attackTimer == 180)
            attackTimer = 0;
        
        

    }
    
    protected void updateMovement(){
        
        if(!isDying){
            if(!takesDamage){
                if(!isAttacking){

                    if(!movesLeft && !movesRight && !movesUp && !movesDown){
                        animState = 0;
                        isIdle = true;
                        isMoving = false;
                    }
                    else{
                        isIdle = false;
                        isMoving = true;
                        if(movesLeft && canGoLeft){
                            x -= speed;
                            canGoRight = true;
                            isHeadedRight = false;
                            animState = 1;
                        }
                        if(movesRight && canGoRight){
                            x += speed;
                            canGoLeft = true;
                            isHeadedRight = true;
                            animState = 1;
                        }
                        if(movesUp && canGoUp){
                            y -= speed;
                            canGoDown = true;
                            animState = 1;
                        }
                        if(movesDown && canGoDown){
                            y += speed;
                            canGoUp = true;
                            animState = 1;
                        }
                    }
                }
                else{
                    animState = 2;
                    isIdle = false;
                    isMoving = false;
                }
            }
            else{
                animState = 3;
                isAttacking = false;
                isIdle = false;
                isMoving = false;
            }
        }
        else{
            isAttacking = false;
            takesDamage = false;
            isIdle = false;
            isMoving = false;

            animState = 4;
        }
    }
    
    private void updateAnimations(){
        
        idleRightAnimation.incrementTicks();
        idleLeftAnimation.incrementTicks();
        walkRightAnimation.incrementTicks();
        walkLeftAnimation.incrementTicks();
        bloodAnimation.incrementTicks();
        hurtRightAnimation.incrementTicks();
        hurtLeftAnimation.incrementTicks();
        dieRightAnimation.incrementTicks();
        dieLeftAnimation.incrementTicks();
        attackRightAnimation.incrementTicks();
        attackLeftAnimation.incrementTicks();
        
    }
    
    public void takeDamage(int damage) {
        
        health -= damage;
        
        if(health <= 0){
            isDying = true;
            takesDamage = false;
        }
        else
            takesDamage = true;
        
    }

    public void checkPlayerInRange(Player player){
        
        if(isHeadedRight && rightRangeBox.contains(player.getOriginX(), player.getOriginY())){
            playerInRange = true;
        }
        else if(!isHeadedRight && leftRangeBox.contains(player.getOriginX(), player.getOriginY())){
            playerInRange = true;
        }
        else
            playerInRange = false;
    }
    
    public void moveToPlayer(Player player){
        
        if(!playerInRange)
            pathFinder.run(this, player);
        else{
            movesLeft = false;
            movesRight = false;
            movesUp = false;
            movesDown = false;
            if(attackTimer == 0)
                attack(player);    
        }    
    }
    
    private void attack(Player player){  
        isAttacking = true;
        attackTimer = 1;   
        
    }
    
    // Setters and getters.
    public Animation getIdleRightAnimation() {
        return idleRightAnimation;
    }

    public void setIdleRightAnimation(Animation idleRightAnimation) {
        this.idleRightAnimation = idleRightAnimation;
    }

    public Animation getIdleLeftAnimation() {
        return idleLeftAnimation;
    }

    public void setIdleLeftAnimation(Animation idleLeftAnimation) {
        this.idleLeftAnimation = idleLeftAnimation;
    }

    public Animation getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public void setWalkRightAnimation(Animation walkRightAnimation) {
        this.walkRightAnimation = walkRightAnimation;
    }

    public Animation getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public void setWalkLeftAnimation(Animation walkLeftAnimation) {
        this.walkLeftAnimation = walkLeftAnimation;
    }

    public Animation getAttackRightAnimation() {
        return attackRightAnimation;
    }

    public void setAttackRightAnimation(Animation attackRightAnimation) {
        this.attackRightAnimation = attackRightAnimation;
    }

    public Animation getAttackLeftAnimation() {
        return attackLeftAnimation;
    }

    public void setAttackLeftAnimation(Animation attackLeftAnimation) {
        this.attackLeftAnimation = attackLeftAnimation;
    }

    public Animation getHurtRightAnimation() {
        return hurtRightAnimation;
    }

    public void setHurtRightAnimation(Animation hurtRightAnimation) {
        this.hurtRightAnimation = hurtRightAnimation;
    }

    public Animation getHurtLeftAnimation() {
        return hurtLeftAnimation;
    }

    public void setHurtLeftAnimation(Animation hurtLeftAnimation) {
        this.hurtLeftAnimation = hurtLeftAnimation;
    }

    public boolean isTakesDamage() {
        return takesDamage;
    }

    public void setTakesDamage(boolean takesDamage) {
        this.takesDamage = takesDamage;
    }
    
    
    
    
    
    
}
