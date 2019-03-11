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
import olafolak.battlemagearena30.models.animations.AttackAnimation;
import olafolak.battlemagearena30.models.animations.BloodAnimation;
import olafolak.battlemagearena30.models.animations.DieAnimation;
import olafolak.battlemagearena30.models.animations.FreezeAnimation;
import olafolak.battlemagearena30.models.animations.FrozenAnimation;
import olafolak.battlemagearena30.models.animations.GetHurtAnimation;
import olafolak.battlemagearena30.models.animations.IdleAnimation;
import olafolak.battlemagearena30.models.animations.WalkAnimation;
import static olafolak.battlemagearena30.models.characters.Player.scale;
import olafolak.battlemagearena30.models.effects.Fireball;
import olafolak.battlemagearena30.models.exceptions.EnemyDiesException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.exceptions.PlayerDiesException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfAttackException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfBloodException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfDieException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfFreezeException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfFrozenException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfGetHurtException;
import olafolak.battlemagearena30.models.game.Game;
import static olafolak.battlemagearena30.models.game.Game.HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WIDTH;
import olafolak.battlemagearena30.models.sprites.BoundsBox;
import olafolak.battlemagearena30.models.utilities.AudioPlayer;

/**
 *
 * @author OlafPC
 */
public class Enemy extends Character implements CharacterInterface{

    // Technical fields.
    protected GetHurtAnimation hurtAnimation;
    protected DieAnimation dieAnimation;
    protected BloodAnimation bloodAnimation;
    protected FreezeAnimation freezeAnimation;
    protected FrozenAnimation frozenAnimation;
    private boolean playerInRange = false;
    private boolean canAttack = true;
    private boolean isFreezing = false;
    private boolean isFrozen = false;
    private PathFinder pathFinder;
    private int attackTimer = 0;
    private Player player;
    
    // Sounds.
    private AudioPlayer walkSound;
    private AudioPlayer meleeAttackSound;
    private AudioPlayer hurtSound;
    
    // Bounds.
    public static int characterWidth = Character.characterWidth;//(int)(WIDTH * (100.0 / WIDTH));
    public static int characterHeight = Character.characterHeight;//(int)(HEIGHT * (100.0 / HEIGHT));
    private int freezeAnimationWidth = (int)(2.5 * characterWidth);
    private int freezeAnimationHeight = (int)(2.5 * characterHeight);
    
    
    
    public Enemy(int x, int y, int speed, int health, Player player) throws IOException {
        super(x, y, speed, health);
        
        pathFinder = new PathFinder(this, player);
        this.player = player;
        isHeadedRight = false;
        idleAnimation = new IdleAnimation(60, 0.85,
                getAnimationFrames("src/res/sprites/w_warrior", "idle_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/w_warrior", "idle_right", 5, characterWidth, characterHeight));
        walkAnimation = new WalkAnimation(60, 0.7, 
                getAnimationFrames("src/res/sprites/w_warrior", "walk_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/w_warrior", "walk_right", 5, characterWidth, characterHeight));
        attackAnimation = new AttackAnimation(60, 0.7,
                getAnimationFrames("src/res/sprites/w_warrior", "attack_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/w_warrior", "attack_right", 5, characterWidth, characterHeight));
        hurtAnimation = new GetHurtAnimation(60, 0.5,
                getAnimationFrames("src/res/sprites/w_warrior", "hurt_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/w_warrior", "hurt_right", 5, characterWidth, characterHeight));
        dieAnimation = new DieAnimation(60, 0.7, 
                getAnimationFrames("src/res/sprites/w_warrior", "die_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/w_warrior", "die_right", 5, characterWidth, characterHeight));
        bloodAnimation = new BloodAnimation(60, 0.5, getAnimationFrames("src/res/effects/blood", "blood", 6, characterWidth, characterHeight), 1);
        
        freezeAnimation = new FreezeAnimation(60, 2, getAnimationFrames("src/res/effects/freeze", "freeze", 6, freezeAnimationWidth, freezeAnimationHeight), 1);
        frozenAnimation = new FrozenAnimation(60, 5, getAnimationFrames("src/res/effects/freeze", "frozen", 1, freezeAnimationWidth, freezeAnimationHeight), 1);
        
        try{
            meleeAttackSound = new AudioPlayer("src/res/sounds/soundEffects/combat/swordSwish.wav", false);
            hurtSound = new AudioPlayer("src/res/sounds/soundEffects/combat/meleeHit.wav", false);
            //walkSound = new AudioPlayer("src/res/sounds/soundEffects/ambient/step.wav", true);
    
        }catch(Exception e){
            
        }
    }

    @Override
    public void draw(Graphics graphics, Game observer) throws EnemyDiesException{
         
        try{
            if(isIdle){
                idleAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(x, y, graphics, observer);    
            }
            if(isMoving){
                walkAnimation.updateDirection(isHeadedRight);
                walkAnimation.run(x, y, graphics, observer);
            }
            if(isAttacking){
                attackAnimation.updateDirection(isHeadedRight);
                attackAnimation.run(x, y, graphics, observer);
            }
            if(takesDamage){
                hurtAnimation.updateDirection(isHeadedRight);
                hurtAnimation.run(x, y, graphics, observer);
                bloodAnimation.run(x, y, graphics, observer);
            }
            if(isDying){
                dieAnimation.updateDirection(isHeadedRight);
                dieAnimation.run(x, y, graphics, observer);
            }
            if(isFreezing){
                idleAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(x, y, graphics, observer);
                idleAnimation.freeze(2);
                freezeAnimation.run(x, y, graphics, observer);
            }
            else if(isFrozen){
                idleAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(x, y, graphics, observer);
                frozenAnimation.run(x, y, graphics, observer);
                //System.out.println("Is frozen!");
            }
        }catch(EndOfDieException e){
            dieAnimation.reset();
            throw new EnemyDiesException(this);
        }catch(EndOfGetHurtException e){
            takesDamage = false;
            hurtAnimation.reset();
            bloodAnimation.reset();
        }catch(EndOfAttackException e){
            isAttacking = false;
            attackAnimation.reset();
            
            /*try{
                meleeAttackSound = new AudioPlayer("src/res/sounds/soundEffects/combat/swordSwish.wav", false);
            }catch(Exception ex){

            }*/
        }catch(EndOfBloodException e){
            bloodAnimation.reset();
        }catch(EndOfFreezeException e){
            isFrozen = true;
            isFreezing = false; 
            freezeAnimation.reset();
        }catch(EndOfFrozenException e){
            isFrozen = false;
            isLocked = false;
            frozenAnimation.reset();
            System.out.println("End of frozen!");
        }
        graphics.drawRect(boundsBox.x, boundsBox.y, boundsBox.width, boundsBox.height);
        graphics.drawRect(leftRangeBox.x, leftRangeBox.y, leftRangeBox.width, leftRangeBox.height);
        graphics.drawRect(rightRangeBox.x, rightRangeBox.y, rightRangeBox.width, rightRangeBox.height);
        graphics.setColor(Color.red);
        graphics.fillRect(healthBar.x, healthBar.y, health * healthBarWidth / maxHealth, healthBarHeight);
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
            if(!isLocked){
                if(!takesDamage){
                    if(!isAttacking){

                        if(!movesLeft && !movesRight && !movesUp && !movesDown){
                            animState = 0;
                            isIdle = true;
                            isMoving = false;
                            /*try{
                                walkSound.stop();
                                walkSound = new AudioPlayer("src/res/sounds/soundEffects/ambient/step.wav", true);
                            }catch(Exception e){
                                
                            }*/
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
                            //walkSound.play();
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
        
        idleAnimation.incrementTicks();
        walkAnimation.incrementTicks();
        bloodAnimation.incrementTicks();
        hurtAnimation.incrementTicks();
        dieAnimation.incrementTicks();
        attackAnimation.incrementTicks();
        
        if(isFreezing)
            freezeAnimation.incrementTicks();
        else
            freezeAnimation.reset();
        
        if(isFrozen)
            frozenAnimation.incrementTicks();
        else
            frozenAnimation.reset();
        
    }
    
    public void takeDamage(int damage) {
        
        health -= damage;
        
        if(health <= 0){
            isDying = true;
            takesDamage = false;
        }
        else
            takesDamage = true;
        
        hurtSound.getClip().loop(1);
    }
    
    public void freeze(){
        stopMovement();
        isLocked = true;
        isFreezing = true;
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
        meleeAttackSound.getClip().loop(1);
        player.takeDamage(1);
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


    public Animation getFreezeAnimation() {
        return freezeAnimation;
    }


    public boolean isPlayerInRange() {
        return playerInRange;
    }

    public void setPlayerInRange(boolean playerInRange) {
        this.playerInRange = playerInRange;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean isIsFreezing() {
        return isFreezing;
    }

    public void setIsFreezing(boolean isFreezing) {
        this.isFreezing = isFreezing;
    }

    public boolean isIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    public int getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(int attackTimer) {
        this.attackTimer = attackTimer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static int getCharacterWidth() {
        return characterWidth;
    }

    public static void setCharacterWidth(int characterWidth) {
        Enemy.characterWidth = characterWidth;
    }

    public static int getCharacterHeight() {
        return characterHeight;
    }

    public static void setCharacterHeight(int characterHeight) {
        Enemy.characterHeight = characterHeight;
    }
    
    
    
    
    
    
    
    
}
