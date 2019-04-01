/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

import olafolak.battlemagearena30.models.ai.PathFinder;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import olafolak.battlemagearena30.models.animations.Animation;
import olafolak.battlemagearena30.models.animations.BloodAnimation;
import olafolak.battlemagearena30.models.animations.DieAnimation;
import olafolak.battlemagearena30.models.animations.FreezeAnimation;
import olafolak.battlemagearena30.models.animations.FrozenAnimation;
import olafolak.battlemagearena30.models.animations.GetHurtAnimation;
import olafolak.battlemagearena30.models.exceptions.EnemyDiesException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfAttackException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfBloodException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfDieException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfFreezeException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfFrozenException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfGetHurtException;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.game.Spawner;
import olafolak.battlemagearena30.models.utilities.AudioPlayer;

/**
 * Enemy class for extending the character class by functionality characteristic for enemy.
 * @author OlfOlak
 */
public abstract class Enemy extends Character implements CharacterInterface{

    // FIELDS.
    
    // Technical fields.
    /** Stores animation of taking damage action.**/
    protected GetHurtAnimation hurtAnimation;
    /** Stores animation of death action.**/
    protected DieAnimation dieAnimation;
    /** Stores blood effet animation.**/
    protected BloodAnimation bloodAnimation;
    /** Stores animation of freezing.**/
    protected FreezeAnimation freezeAnimation;
    /** Stores animation of being frozen.**/
    protected FrozenAnimation frozenAnimation;
    /** Indicates if player is in enemy's range box.**/
    protected boolean playerInRange = false;
    /** Indicates possibility of attacking the player.**/
    protected boolean canAttack = true;
    /** Indicates if enemy is freezing.**/
    protected boolean isFreezing = false;
    /** Indicates if enemy is frozen.**/
    protected boolean isFrozen = false;
    /** Amount of progress points that will be added by killing the enemy.**/
    protected int progressValue;
    /** Controls the movement of the enemy to get the player in range.**/
    protected PathFinder pathFinder;
    /** Counts the delay of the next attack possibility.**/
    protected int attackTimer = 0;
    /** Reference to player object.**/
    protected Player player = Game.player; 
    
    // Sounds.
    /** Plays walking sound.**/
    private AudioPlayer walkSound;
    /** Plays melee attack sound.**/
    private AudioPlayer meleeAttackSound;
    /** Plays taking damage sound.**/
    private AudioPlayer hurtSound;
    
    // Bounds.
    /** The width of the visual representation.**/
    private int characterWidth = 50;
    /** The height of the visual representation.**/
    private int characterHeight = 50;
    /** The width of the basic animations' frames.**/
    private int frameWidth = (int)(1.4 * characterWidth);
    /** The height of the basic animations' frames.**/
    private int frameHeight = (int)(1.4 * characterHeight);
    /** The width of the freeze animation frames.**/
    private int freezeAnimationWidth = (int)(2.5 * characterWidth);
    /** The height of the freeze animation frames.**/
    private int freezeAnimationHeight = (int)(2.5 * characterHeight);
    
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param x sets the x position of the visual representation.
     * @param y sets the y position of the visual representation.
     * @param speed sets the speed of the character.
     * @param health sets amount of maximum health of the character.
     * @throws IOException if there is problem with reading animation files.
     */
    public Enemy(int x, int y, int speed, int health) throws IOException {
        super(x, y, speed, health);
        
        this.pathFinder = new PathFinder(this, 0);
        this.progressValue = 10;
        isHeadedRight = false;
        
        freezeAnimation = new FreezeAnimation(60, 2, getAnimationFrames("src/res/effects/freeze", "freeze", 6, freezeAnimationWidth, freezeAnimationHeight), this);
        frozenAnimation = new FrozenAnimation(60, 5, getAnimationFrames("src/res/effects/freeze", "frozen", 1, freezeAnimationWidth, freezeAnimationHeight), this);
        
        try{
            meleeAttackSound = new AudioPlayer("src/res/sounds/soundEffects/combat/swordSwish.wav", false);
            hurtSound = new AudioPlayer("src/res/sounds/soundEffects/combat/meleeHit.wav", false);
            //walkSound = new AudioPlayer("src/res/sounds/soundEffects/ambient/step.wav", true);
    
        }catch(Exception e){
            
        }
    }
    
    /**
     * Spawn point based constructor.
     * @param spawnPoint spawn point in which the enemy is to be spawned.
     * @param speed sets the speed of the character.
     * @param health sets amount of maximum health of the character.
     * @throws IOException if there is problem with reading animation files.
     */
    public Enemy(int spawnPoint, int speed, int health) throws IOException{
        this(0, 0, speed, health);
        
        if(spawnPoint == 1){
            this.x = Spawner.spawnPointOneX - (characterWidth / 2);
            this.y = Spawner.spawnPointOneY - characterHeight;
        }else if(spawnPoint == 2){
            this.x = Spawner.spawnPointTwoX - (characterWidth / 2);
            this.y = Spawner.spawnPointTwoY - characterHeight;
        }
        this.pathFinder = new PathFinder(this, spawnPoint);        
    }

    // METHODS.
    
    /**
     * Overriden drawing method that switches animations and runs them.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws EnemyDiesException when the enemy dies.
     */
    @Override
    public void draw(Graphics graphics, Game observer) throws EnemyDiesException{
         
        try{
            if(isIdle){
                idleAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);    
            }
            if(isMoving){
                walkAnimation.updateDirection(isHeadedRight);
                walkAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);
            }
            if(isAttacking){
                attackAnimation.updateDirection(isHeadedRight);
                attackAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);
            }
            if(takesDamage){
                hurtAnimation.updateDirection(isHeadedRight);
                hurtAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);
                bloodAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);
            }
            if(isDying){
                dieAnimation.updateDirection(isHeadedRight);
                dieAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);
            }
            if(isFreezing){
                idleAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);
                idleAnimation.freeze(2);
                freezeAnimation.run(x, y, graphics, observer);
            }
            else if(isFrozen){
                idleAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);
                frozenAnimation.run(x, y, graphics, observer);
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
    
    /**
     * Clocking method for updating enemys data.
     */
    public void tick(){
        
        moveToPlayer();
        updateMovement();
        updateBounds(); 
        updateAnimations();
        checkPlayerInRange();
        
        if(attackTimer != 0)
            attackTimer++;
        
        if(attackTimer == 180)
            attackTimer = 0;
        
        

    }
    
    /**
     * Controls movement depending on action variables and pdates movement and action flags.
     */
    protected void updateMovement(){
        
        if(!isDying){
            if(!isLocked){
                if(!takesDamage){
                    if(!isAttacking){

                        if(!movesLeft && !movesRight && !movesUp && !movesDown){
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
                            }
                            if(movesRight && canGoRight){
                                x += speed;
                                canGoLeft = true;
                                isHeadedRight = true;
                            }
                            if(movesUp && canGoUp){
                                y -= speed;
                                canGoDown = true;
                            }
                            if(movesDown && canGoDown){
                                y += speed;
                                canGoUp = true;
                            }
                            //walkSound.play();
                        }
                    }
                    else{
                        isIdle = false;
                        isMoving = false;
                    }
                }
                else{
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
        }
    }
    
    /**
     * Clocks animations.
     */
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
    
    /**
     * Decreases health by received damage and checks if enemy is not to be dead.
     * @param damage the amount of health to be decresead.
     */
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
    
    /**
     * Stops enemy's movement, locks him and proceeds to run the freezing animation.
     */
    public void freeze(){
        stopMovement();
        isLocked = true;
        isFreezing = true;
    }
    
    /**
     * Check if player's origin is present in the enemy's range box.
     */
    public void checkPlayerInRange(){
        
        if(isHeadedRight && rightRangeBox.contains(Game.player.getOriginX(), Game.player.getOriginY())){
            playerInRange = true;
        }
        else if(!isHeadedRight && leftRangeBox.contains(Game.player.getOriginX(), Game.player.getOriginY())){
            playerInRange = true;
        }
        else
            playerInRange = false;
    }
    
    /**
     * Sets enemy's control to follow the player.
     */
    public void moveToPlayer(){
        
        if(!playerInRange)
            pathFinder.run();
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
        Game.player.takeDamage(10);
    }
    
    // SETTERS AND GETTERS.
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

    public int getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(int progressValue) {
        this.progressValue = progressValue;
    }
    

}
