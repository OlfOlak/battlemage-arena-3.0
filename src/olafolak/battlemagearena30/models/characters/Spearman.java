/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import olafolak.battlemagearena30.models.ai.PathFinder;
import olafolak.battlemagearena30.models.animations.AttackAnimation;
import olafolak.battlemagearena30.models.animations.BloodAnimation;
import olafolak.battlemagearena30.models.animations.DieAnimation;
import olafolak.battlemagearena30.models.animations.GetHurtAnimation;
import olafolak.battlemagearena30.models.animations.IdleAnimation;
import olafolak.battlemagearena30.models.animations.WalkAnimation;
import olafolak.battlemagearena30.models.exceptions.EnemyDiesException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfAttackException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfBloodException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfDieException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfFreezeException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfFrozenException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfGetHurtException;
import olafolak.battlemagearena30.models.game.Game;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;
import olafolak.battlemagearena30.models.game.Spawner;
import olafolak.battlemagearena30.models.sprites.BoundsBox;

/**
 *
 * @author OlafPC
 */
public class Spearman extends Enemy{
    
    
    private int characterWidth = (int)(WINDOW_WIDTH * 50 / 1280);
    private int characterHeight = (int)(WINDOW_HEIGHT * 50 / 768);
    private int frameWidth = (int)(1.4 * characterWidth);
    private int frameHeight = (int)(1.4 * characterHeight);
    private int freezeAnimationWidth = (int)(2.5 * characterWidth);
    private int freezeAnimationHeight = (int)(2.5 * characterHeight);
    
    public Spearman(int x, int y, int speed, int health, Player player) throws IOException {
        super(x, y, speed, health, player);
        
        progressValue = 10;
        
        idleAnimation = new IdleAnimation(60, 0.85,
                getAnimationFrames("src/res/sprites/spearman", "idle_left", 4, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/spearman", "idle", 4, frameWidth, frameHeight));
        walkAnimation = new WalkAnimation(60, 0.7, 
                getAnimationFrames("src/res/sprites/spearman", "walk_left", 4, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/spearman", "walk", 4, frameWidth, frameHeight));
        attackAnimation = new AttackAnimation(60, 0.7,
                getAnimationFrames("src/res/sprites/spearman", "attack_left", 4, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/spearman", "attack", 4, frameWidth, frameHeight));
        hurtAnimation = new GetHurtAnimation(60, 0.5,
                getAnimationFrames("src/res/sprites/spearman", "hurt_left", 4, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/spearman", "hurt", 4, frameWidth, frameHeight));
        dieAnimation = new DieAnimation(60, 0.7, 
                getAnimationFrames("src/res/sprites/spearman", "death_left", 5, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/spearman", "dead", 5, frameWidth, frameHeight));
        bloodAnimation = new BloodAnimation(60, 0.5, getAnimationFrames("src/res/effects/blood", "blood", 6, frameWidth, frameHeight), 1);
        
        boundsBox = new BoundsBox(originX, originY, characterWidth, characterHeight);
        leftRangeBox = new BoundsBox(x, originY, meleeRangeX, meleeRangeY);
        rightRangeBox = new BoundsBox(x + characterWidth, originY, meleeRangeX, meleeRangeY);
        healthBar = new BoundsBox(originX, y - (healthBarHeight / 2), health * healthBarWidth / maxHealth, healthBarHeight);
        baseline = new BoundsBox(baseX, baseY, characterWidth, 1);
        
    }
    
    public Spearman(int spawnPoint, int speed, int health, Player player) throws IOException{
        this(0, 0, speed, health, player);
        
        if(spawnPoint == 1){
            this.x = Spawner.spawnPointOneX - (characterWidth / 2);
            this.y = Spawner.spawnPointOneY - characterHeight;
        }else if(spawnPoint == 2){
            this.x = Spawner.spawnPointTwoX - (characterWidth / 2);
            this.y = Spawner.spawnPointTwoY - characterHeight;
        }
        this.pathFinder = new PathFinder(this, player, spawnPoint);        
    }
    /*
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
                freezeAnimation.run(freezeAnimationWidth, freezeAnimationHeight, graphics, observer);
            }
            else if(isFrozen){
                idleAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(originX - frameWidth / 2, originY - frameHeight / 2, graphics, observer);
                frozenAnimation.run(freezeAnimationWidth, freezeAnimationHeight, graphics, observer);
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
        
    }*/
    
}
