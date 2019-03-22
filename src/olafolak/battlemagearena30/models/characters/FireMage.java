/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

import java.io.IOException;
import olafolak.battlemagearena30.models.ai.PathFinder;
import olafolak.battlemagearena30.models.animations.AttackAnimation;
import olafolak.battlemagearena30.models.animations.BloodAnimation;
import olafolak.battlemagearena30.models.animations.DieAnimation;
import olafolak.battlemagearena30.models.animations.GetHurtAnimation;
import olafolak.battlemagearena30.models.animations.IdleAnimation;
import olafolak.battlemagearena30.models.animations.WalkAnimation;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;
import olafolak.battlemagearena30.models.game.Spawner;
import olafolak.battlemagearena30.models.sprites.BoundsBox;

/**
 *
 * @author OlafPC
 */
public class FireMage extends Enemy{
    
    private int characterWidth = (int)(WINDOW_WIDTH * 50 / 1280);
    private int characterHeight = (int)(WINDOW_HEIGHT * 50 / 768);
    private int frameWidth = (int)(1.3 * characterWidth);
    private int frameHeight = (int)(1.3 * characterHeight);
    private int freezeAnimationWidth = (int)(2.5 * characterWidth);
    private int freezeAnimationHeight = (int)(2.5 * characterHeight);
    
    
    public FireMage(int x, int y, int speed, int health, Player player) throws IOException {
        super(x, y, speed, health, player);
        
        progressValue = 30;
        
        idleAnimation = new IdleAnimation(60, 0.85,
                getAnimationFrames("src/res/sprites/wizard_fire", "idle_left", 4, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/wizard_fire", "idle", 4, frameWidth, frameHeight));
        walkAnimation = new WalkAnimation(60, 0.7, 
                getAnimationFrames("src/res/sprites/wizard_fire", "walk_left", 4, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/wizard_fire", "walk", 4, frameWidth, frameHeight));
        attackAnimation = new AttackAnimation(60, 0.7,
                getAnimationFrames("src/res/sprites/wizard_fire", "attack_left", 3, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/wizard_fire", "attack", 3, frameWidth, frameHeight));
        hurtAnimation = new GetHurtAnimation(60, 0.5,
                getAnimationFrames("src/res/sprites/wizard_fire", "hurt_left", 3, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/wizard_fire", "hurt", 3, frameWidth, frameHeight));
        dieAnimation = new DieAnimation(60, 0.7, 
                getAnimationFrames("src/res/sprites/wizard_fire", "dead_left", 5, frameWidth, frameHeight),
                getAnimationFrames("src/res/sprites/wizard_fire", "dead", 5, frameWidth, frameHeight));
        bloodAnimation = new BloodAnimation(60, 0.5, getAnimationFrames("src/res/effects/blood", "blood", 6, frameWidth, frameHeight), 1);
        
        boundsBox = new BoundsBox(originX, originY, characterWidth, characterHeight);
        leftRangeBox = new BoundsBox(x, originY, meleeRangeX, meleeRangeY);
        rightRangeBox = new BoundsBox(x + characterWidth, originY, meleeRangeX, meleeRangeY);
        healthBar = new BoundsBox(originX, y - (healthBarHeight / 2), health * healthBarWidth / maxHealth, healthBarHeight);
        baseline = new BoundsBox(baseX, baseY, characterWidth, 1);
    }
    
    public FireMage(int spawnPoint, int speed, int health, Player player) throws IOException{
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
    
    
    
}
