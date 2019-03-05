/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.animations.*;
import olafolak.battlemagearena30.models.effects.Fireball;
import olafolak.battlemagearena30.models.effects.IceBreath;
import olafolak.battlemagearena30.models.exceptions.EndOfFireballException;
import olafolak.battlemagearena30.models.exceptions.EndOfIceBreathException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.exceptions.PlayerDiesException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.*;
import olafolak.battlemagearena30.models.game.Game;
import static olafolak.battlemagearena30.models.game.Game.HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WIDTH;


/**
 *
 * @author OlafPC
 */
public class Player extends Character implements CharacterInterface{
    
    // Technical fields.
    private Animation magicShieldAnimation;
    private MagicShieldAbsorbAnimation magicShieldAbsorbAnimation;
    private CastFireballAnimation castFireballAnimation;
    private CastIceBreathAnimation castIceBreathAnimation;
    private IdleAnimation idleAnimation;
    private WalkAnimation walkAnimation;
    private AttackAnimation attackAnimation;
    private GetHurtAnimation hurtAnimation;
    private DieAnimation dieAnimation;
    private BloodAnimation bloodAnimation;
    
    private boolean magicShieldOn = false;
    private boolean shieldAbsorbsDamage = false;
    private boolean castsFireball = false;
    private boolean throwsFireball = false;
    private boolean castsIceBreath = false;
    private boolean firesIceBreath = false;
    
    private ArrayList<Fireball> fireballsList;
    private IceBreath iceBreath;
    
    private ArrayList<Enemy> enemysList;
    
    private int width = 100;
    private int height = 100;
    
    // Attribute fields.
    private int magicPower;
    
    // Bounds.
    public static int characterWidth = Character.characterWidth;//(int)(WIDTH * (100.0 / WIDTH));
    public static int characterHeight = Character.characterHeight;//(int)(HEIGHT * (100.0 / HEIGHT));
    private int magicShieldWidth = (int)(4.2 * characterWidth);
    private int magicShieldHeight = (int)(4.12 * characterHeight);
    private int magicShieldAbsorbWidth = (int)(4.26 * characterWidth);
    private int magicShieldAbsorbHeight = (int)(4.12 * characterHeight);
    private int castFireballWidth = (int)(1.5 * characterWidth);
    private int castFireballHeight = (int)(1.5 * characterHeight);
    private int castIceBreathWidth = (int)(1.5 * characterWidth);
    private int castIceBreathHeight = (int)(1.5 * characterHeight);
    private int iceBreathRightPositionX = (int)(0.05 * characterWidth);
    private int iceBreathLeftPositionX = (int)(0.95 * characterWidth);
    private int iceBreathPositionY = (int)(0.4 * characterHeight);
    
    // Constructors.
    public Player(int x, int y, int speed, int health, int magicPower) throws IOException{
        super(x, y, speed, health);
        this.magicPower = magicPower;

        fireballsList = new ArrayList<>();
        enemysList = new ArrayList<>();
        
        
        idleAnimation = new IdleAnimation(60, 0.85,
                getAnimationFrames("src/res/sprites/elfmage", "idle_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/elfmage", "idle_right", 5, characterWidth, characterHeight));
        walkAnimation = new WalkAnimation(60, 0.2, 
                getAnimationFrames("src/res/sprites/elfmage", "walk_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/elfmage", "walk_right", 5, characterWidth, characterHeight));
        
        attackAnimation = new AttackAnimation(60, 0.2, 
                getAnimationFrames("src/res/sprites/elfmage", "attack_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/elfmage", "attack_right", 5, characterWidth, characterHeight));
        hurtAnimation = new GetHurtAnimation(60, 0.5,
                getAnimationFrames("src/res/sprites/elfmage", "hurt_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/elfmage", "hurt_right", 5, characterWidth, characterHeight));
        bloodAnimation = new BloodAnimation(60, 0.5, getAnimationFrames("src/res/effects/blood", "blood", 6, characterWidth, characterHeight), 1);
        
        dieAnimation = new DieAnimation(60, 0.7,
                getAnimationFrames("src/res/sprites/elfmage", "die_left", 5, characterWidth, characterHeight),
                getAnimationFrames("src/res/sprites/elfmage", "die_right", 5, characterWidth, characterHeight));
        
        magicShieldAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/magicShield", "magicShield", 5, magicShieldWidth, magicShieldHeight), 0);
        magicShieldAbsorbAnimation = new MagicShieldAbsorbAnimation(60, 0.1, getAnimationFrames("src/res/effects/magicShield", "magicShield_absorb", 7, magicShieldAbsorbWidth, magicShieldAbsorbHeight), 1);
        castFireballAnimation = new CastFireballAnimation(60, 1, getAnimationFrames("src/res/effects/fireball", "cast", 11, castFireballWidth, castFireballHeight), 1);        
        castIceBreathAnimation = new CastIceBreathAnimation(60, 1, getAnimationFrames("src/res/effects/iceBreath", "iceBreath_cast", 13, castIceBreathWidth, castIceBreathHeight), 1);
        
    }
    
    // Methods.
    @Override
    public void draw(Graphics graphics, Game observer) throws PlayerDiesException{
        
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
            if(magicShieldOn)
                magicShieldAnimation.run(originX - (magicShieldWidth / 2), originY - (magicShieldHeight / 2), graphics, observer);
            
            if(shieldAbsorbsDamage)
                magicShieldAbsorbAnimation.run(originX - (magicShieldAbsorbWidth / 2), originY - (magicShieldAbsorbHeight / 2), graphics, observer);
            
            if(castsFireball){
                idleAnimation.updateDirection(isHeadedRight);
                castFireballAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(x, y, graphics, observer);
                castFireballAnimation.run(x, y, graphics, observer);
            }
            
            if(castsIceBreath){
                idleAnimation.updateDirection(isHeadedRight);
                castIceBreathAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(x, y, graphics, observer);
                castIceBreathAnimation.run(x, y, graphics, observer);
                
            }
                    
            if(throwsFireball){
                for(Fireball f : fireballsList)
                    f.draw(graphics, observer);
            }
            
            if(firesIceBreath){
                idleAnimation.updateDirection(isHeadedRight);
                idleAnimation.run(x, y, graphics, observer);
                iceBreath.draw(graphics, observer);
            }
            
        }catch(EndOfDieException e){
            dieAnimation.reset();
            throw new PlayerDiesException(this);
        }catch(EndOfGetHurtException e){
            takesDamage = false;
            hurtAnimation.reset();
            bloodAnimation.reset();
        }catch(EndOfAttackException e){
            dealDamage();
            isAttacking = false;
            attackAnimation.reset();
        }catch(EndOfMagicShieldAbsorbException e){
            shieldAbsorbsDamage = false;
            magicShieldAbsorbAnimation.reset();
        }catch(EndOfBloodException e){
            bloodAnimation.reset();
        }catch(EndOfCastFireballException e){
            isLocked = false;
            castsFireball = false;
            throwsFireball = true;
            generateFireball();
        }catch(EndOfCastIceBreathException e){
            castsIceBreath = false;
            firesIceBreath = true;                
        }catch(EndOfFireballException e){
            fireballsList.remove(e.getFireball());
            if(fireballsList.isEmpty())
                throwsFireball = false;
        }catch(EndOfIceBreathException e){
            iceBreath = null;
            isLocked = false;
            firesIceBreath = false;
        }catch(EndSingleAnimationException e){
            
            
            
        }
        graphics.drawRect(boundsBox.x, boundsBox.y, boundsBox.width, boundsBox.height);
        graphics.drawRect(leftRangeBox.x, leftRangeBox.y, leftRangeBox.width, leftRangeBox.height);
        graphics.drawRect(rightRangeBox.x, rightRangeBox.y, rightRangeBox.width, rightRangeBox.height);
        
    }
    
    @Override
    public void tick(){
        
        updateMovement();
        updateBounds();
        checkArenaCollisions();
        updateAnimations();
        //System.out.println("boundsBox.x: " + boundsBox.x + " boundsBox.y: " + boundsBox.y);
        //System.out.println("shieldAbsorbsDamage: " + shieldAbsorbsDamage);
        

    }
    
    public void updateEnemysList(ArrayList<Enemy> enemysList){
        this.enemysList = enemysList;
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
                isMoving = false;
            }
            }
            else{
                isAttacking = false;
                isIdle = false;
                isMoving = false;
                takesDamage = false;
            }
        }
        else{
            isLocked = false;
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
        attackAnimation.incrementTicks();
        hurtAnimation.incrementTicks();
        bloodAnimation.incrementTicks();
        dieAnimation.incrementTicks();
        magicShieldAnimation.incrementTicks();
        
        if(shieldAbsorbsDamage == true)
            magicShieldAbsorbAnimation.incrementTicks();
        else
            magicShieldAbsorbAnimation.reset();
        
        if(castsFireball == true)
            castFireballAnimation.incrementTicks();
        else
            castFireballAnimation.reset();
        
        if(castsIceBreath == true)
            castIceBreathAnimation.incrementTicks();
        else
            castIceBreathAnimation.reset();
        
        if(throwsFireball){
            for(Fireball f : fireballsList)
                f.tick();
        }
        
        try{
            if(firesIceBreath && iceBreath != null)
                iceBreath.tick();
        }catch(NullPointerException e){
            
        }
            
        
    }
    
    public void attack(){
        isAttacking = true;
    }
    
    private void dealDamage(){
        try{
            ArrayList<Enemy> attackedList = new ArrayList<>();
            attackedList = seekForCharactersInRange(enemysList);

            for(Enemy enemy : attackedList){
                enemy.takeDamage(10);
                //System.out.println("Battlemage attacks enemy");
            }
        }catch(NullPointerException e){
            
        }
    }
    
    public void takeDamage(int damage){
        
        if(magicShieldOn == false){
        
            health -= damage;
            
            if(health <= 0){
                isDying = true;
                takesDamage = false;
            }
            else
                takesDamage = true;
        }
        else
            shieldAbsorbsDamage = true;
    }
    
    public void setupMagicShield(){
        
        if(magicShieldOn == false)
            magicShieldOn = true;
        else
            magicShieldOn = false;
        
    }
    
    public void throwFireball(){
        stopMovement();
        isLocked = true;
        castsFireball = true;    
    }
    
    public void iceBreath(){
        stopMovement();
        isLocked = true;
        castsIceBreath = true;
        generateIceBreath();    
    }
    
    private void generateFireball(){
        try{
            fireballsList.add(new Fireball(originX, originY, 500, isHeadedRight, enemysList));
        }
        catch(IOException e){
            System.out.println("Problem with fireball files!");
        }
    }
    
    private void generateIceBreath(){
        try{
            if(isHeadedRight)
                iceBreath = new IceBreath(x + iceBreathRightPositionX, y + iceBreathPositionY, 400, true, enemysList);
            else
                iceBreath = new IceBreath(x + iceBreathLeftPositionX - (int)(1.5 * characterWidth), y + iceBreathPositionY, 400, false, enemysList);
        }
        catch(IOException e){
            System.out.println("Problem with iceBreath files!");
        }
    }
    
    private ArrayList<Enemy> seekForCharactersInRange(ArrayList<Enemy> allEnemysList){
        
        ArrayList<Enemy> tmp = new ArrayList<>();
        
        for(Enemy enemy : allEnemysList){
            
            if(isHeadedRight){
                if(rightRangeBox.contains(enemy.getOriginX(), enemy.getOriginY()))
                    tmp.add(enemy); 
                else
                    continue;
            } 
            else{
                if(leftRangeBox.contains(enemy.getOriginX(), enemy.getOriginY()))
                    tmp.add(enemy);
                else
                    continue;
            }
        }
        return tmp;
        
    }
    
    public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    

    

    
    
    
    
}
