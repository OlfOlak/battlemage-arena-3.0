/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import olafolak.battlemagearena30.models.animations.Animation;
import olafolak.battlemagearena30.models.effects.Fireball;
import olafolak.battlemagearena30.models.effects.IceBreath;
import olafolak.battlemagearena30.models.exceptions.EndOfFireballException;
import olafolak.battlemagearena30.models.exceptions.EndOfIceBreathException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.exceptions.PlayerDiesException;
import olafolak.battlemagearena30.models.game.Game;

/**
 *
 * @author OlafPC
 */
public class Player extends Character implements CharacterInterface{
    
    // Technical fields.
    private Animation hurtRightAnimation;
    private Animation hurtLeftAnimation;
    private Animation bloodAnimation;
    private Animation dieRightAnimation;
    private Animation dieLeftAnimation;
    private Animation magicShieldAnimation;
    private Animation magicShieldAbsorbAnimation;
    private Animation castFireballAnimation;
    private Animation castIceBreathAnimation;
    private boolean magicShieldOn = false;
    private boolean shieldAbsorbsDamage = false;
    private boolean castsFireball = false;
    private boolean throwsFireball = false;
    private boolean castsIceBreath = false;
    private boolean firesIceBreath = false;
    
    private ArrayList<Fireball> fireballsList;
    private IceBreath iceBreath;
    
    private ArrayList<Enemy> enemysList;
    
    
    // Attribute fields.
    private int magicPower;
    

    
    // Constructors.
    public Player(int x, int y, int speed, int health, int magicPower) throws IOException{
        super(x, y, speed, health);
        this.magicPower = magicPower;

        fireballsList = new ArrayList<>();
        enemysList = new ArrayList<>();
        
        idleRightAnimation = new Animation(60, 0.85, getAnimationFrames("src/res/sprites/elfmage", "idle_right", 5, 100, 100), 0);
        idleLeftAnimation = new Animation(60, 0.85, getAnimationFrames("src/res/sprites/elfmage", "idle_left", 5, 100, 100), 0);
        walkRightAnimation = new Animation(60, 0.2, getAnimationFrames("src/res/sprites/elfmage", "walk_right", 5, 100, 100), 0);
        walkLeftAnimation = new Animation(60, 0.2, getAnimationFrames("src/res/sprites/elfmage", "walk_left", 5, 100, 100), 0);
        attackRightAnimation = new Animation (60, 0.2, getAnimationFrames("src/res/sprites/elfmage", "attack_right", 5, 100, 100), 1);
        attackLeftAnimation = new Animation (60, 0.2, getAnimationFrames("src/res/sprites/elfmage", "attack_left", 5, 100, 100), 1); 
        hurtRightAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/sprites/elfmage", "hurt_right", 5, 100, 100), 1);
        hurtLeftAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/sprites/elfmage", "hurt_left", 5, 100, 100), 1);
        bloodAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/blood", "blood", 6, 100, 100), 1);
        dieRightAnimation = new Animation(60, 0.7, getAnimationFrames("src/res/sprites/elfmage", "die_right", 5, 100, 100), 1);
        dieLeftAnimation = new Animation(60, 0.7, getAnimationFrames("src/res/sprites/elfmage", "die_left", 5, 100, 100), 1);
        magicShieldAnimation = new Animation(60, 0.5, getAnimationFrames("src/res/effects/magicShield", "magicShield", 5, 420, 412), 0);
        magicShieldAbsorbAnimation = new Animation(60, 0.1, getAnimationFrames("src/res/effects/magicShield", "magicShield_absorb", 7, 426, 412), 1);
        castFireballAnimation = new Animation(60, 1, getAnimationFrames("src/res/effects/fireball", "cast", 11, 150, 150), 1);
        castIceBreathAnimation = new Animation(60, 1, getAnimationFrames("src/res/effects/iceBreath", "iceBreath_cast", 13, 150, 150), 1);
        
    }
    
    // Methods.
    @Override
    public void draw(Graphics graphics, Game observer) throws PlayerDiesException{
        
        try{
            
            /*switch(animState){
                case 0:
                    if(isHeadedRight == true)
                        idleRightAnimation.run(x, y, graphics, observer);
                    else
                        idleLeftAnimation.run(x, y, graphics, observer);
                    break;
                case 1:
                    if(isHeadedRight == true)
                        walkRightAnimation.run(x, y, graphics, observer);
                    else
                        walkLeftAnimation.run(x, y, graphics, observer);
                    break;
                case 2:
                    if(isHeadedRight == true)
                        attackRightAnimation.run(x, y, graphics, observer);
                    else
                        attackLeftAnimation.run(x, y, graphics, observer);
                    break;
                case 3:
                    if(isHeadedRight == true){
                        hurtRightAnimation.run(x, y, graphics, observer);
                    }
                    else{
                        hurtLeftAnimation.run(x, y, graphics, observer);
                    }
                    bloodAnimation.run(x, y, graphics, observer);
                    break;
                case 4:
                    if(isHeadedRight == true)
                        dieRightAnimation.run(x, y, graphics, observer);
                    else
                        dieLeftAnimation.run(x, y, graphics, observer);
                    break;
                default:
                    break;
            }*/
            
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
            if(magicShieldOn)
                magicShieldAnimation.run(originX - 213, originY - 206, graphics, observer);
            
            if(shieldAbsorbsDamage)
                magicShieldAbsorbAnimation.run(originX - 213, originY - 206, graphics, observer);
            
            
            if(castsFireball){
                if(isHeadedRight)
                    castFireballAnimation.run(x + 20 - 75, y + 70 - 75, graphics, observer);    
                else
                    castFireballAnimation.run(x + 80 - 75, y + 70 - 75, graphics, observer);  
            }
            
            if(castsIceBreath){
                
                if(isHeadedRight)
                    castIceBreathAnimation.run(x + 20 - 75, y + 70 - 75, graphics, observer);    
                else
                    castIceBreathAnimation.run(x + 80 - 75, y + 70 - 75, graphics, observer);
            }
                    
            if(throwsFireball){
                for(Fireball f : fireballsList)
                    f.draw(graphics, observer);
            }
            
            if(firesIceBreath){
                iceBreath.draw(graphics, observer);
            }
            
        }catch(EndSingleAnimationException e){
            if(isDying){
                dieLeftAnimation.reset();
                dieRightAnimation.reset();
                throw new PlayerDiesException(this);
            }
            if(takesDamage){
                takesDamage = false;
                hurtRightAnimation.reset();
                hurtLeftAnimation.reset();
                bloodAnimation.reset();
                hurtRightAnimation.reset();
                hurtLeftAnimation.reset();
                bloodAnimation.reset();
            }
            if(isAttacking){
                isAttacking = false;
                attackRightAnimation.reset();
                attackLeftAnimation.reset();
            }
            if(magicShieldOn){
                shieldAbsorbsDamage = false;
                magicShieldAbsorbAnimation.reset();
            }
            if(castsFireball){
                isLocked = false;
                castsFireball = false;
                throwsFireball = true;
                generateFireball();    
            }
            if(castsIceBreath){
                castsIceBreath = false;
                firesIceBreath = true;    
            }
        }catch(EndOfFireballException e){
            fireballsList.remove(e.getFireball());
            if(fireballsList.isEmpty())
                throwsFireball = false;
        }catch(EndOfIceBreathException e){
            iceBreath = null;
            isLocked = false;
            firesIceBreath = false;
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
        //System.out.println("magicShieldOn: " + magicShieldOn);
        //System.out.println("shieldAbsorbsDamage: " + shieldAbsorbsDamage);
        
        
        
    }
    
    public void updateEnemysList(ArrayList<Enemy> enemysList){
        this.enemysList = enemysList;
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
        attackRightAnimation.incrementTicks();
        attackLeftAnimation.incrementTicks();
        hurtRightAnimation.incrementTicks();
        hurtLeftAnimation.incrementTicks();
        bloodAnimation.incrementTicks();
        dieRightAnimation.incrementTicks();
        dieLeftAnimation.incrementTicks();
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
    
    public void attack(ArrayList<Enemy> allEnemysList){
        
        try{
            ArrayList<Enemy> attackedList = new ArrayList<>();
            attackedList = seekForCharactersInRange(allEnemysList);

            for(Enemy enemy : attackedList){
                enemy.takeDamage(10);
                //System.out.println("Battlemage attacks enemy");
            }
        }catch(NullPointerException e){
            
        }finally{
            animState = 2;
            isAttacking = true;
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
            fireballsList.add(new Fireball(x + 20, y + 50, 500, isHeadedRight, enemysList));
        }
        catch(IOException e){
            System.out.println("Problem with fireball files!");
        }
    }
    
    private void generateIceBreath(){
        try{
            if(isHeadedRight)
                iceBreath = new IceBreath(x + 5, y + 40, 400, true, enemysList);
            else
                iceBreath = new IceBreath(x + 95 - 150, y + 40, 400, false, enemysList);
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
