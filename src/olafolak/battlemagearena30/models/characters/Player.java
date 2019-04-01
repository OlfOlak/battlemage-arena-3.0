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
import javax.sound.sampled.Clip;
import olafolak.battlemagearena30.models.animations.*;
import olafolak.battlemagearena30.models.effects.Fireball;
import olafolak.battlemagearena30.models.effects.IceBreath;
import olafolak.battlemagearena30.models.exceptions.EndOfFireballException;
import olafolak.battlemagearena30.models.exceptions.EndOfIceBreathException;
import olafolak.battlemagearena30.models.exceptions.EndOfMagicShieldException;
import olafolak.battlemagearena30.models.exceptions.EndSingleAnimationException;
import olafolak.battlemagearena30.models.exceptions.PlayerDiesException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.*;
import olafolak.battlemagearena30.models.game.Game;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;
import olafolak.battlemagearena30.models.utilities.AudioPlayer;

/**
 * Player class for extending the character class by functionality characteristic for player.
 * @author OlfOlak
 */
public class Player extends Character implements CharacterInterface{
    
    // FIELDS.
    // Technical fields.
    /** Stores animation of magic shield action.**/
    private Animation magicShieldAnimation;
    /** Stores animation of magic shield absorbtion action.**/
    private MagicShieldAbsorbAnimation magicShieldAbsorbAnimation;
    /** Stores animation of casting fireball action.**/
    private CastFireballAnimation castFireballAnimation;
    /** Stores animation of casting ice breath action.**/
    private CastIceBreathAnimation castIceBreathAnimation;
    /** Stores animation of taking damage action.**/
    private GetHurtAnimation hurtAnimation;
    /** Stores animation of death action.**/
    private DieAnimation dieAnimation;
    /** Stores blood effet animation.**/
    private BloodAnimation bloodAnimation;
    
    /** Plays casting spell sound.**/
    private AudioPlayer castSound;
    /** Plays sword swish sound.**/
    private AudioPlayer swordAttackSound;
    /** Plays taking damage sound.**/
    private AudioPlayer hurtSound;
    /** Plays magic shield on sound.**/
    private AudioPlayer magicShieldSound;
    /** Plays magic shield absorbsion sound.**/
    private AudioPlayer magicShieldAbsorbSound;
    /** Plays walking sound.**/
    private AudioPlayer walkSound;
    
    
    /** Indicates if magic shield is on.**/
    private boolean magicShieldOn = false;
    /** Indicates if magic shield is absorbing damage.**/
    private boolean shieldAbsorbsDamage = false;
    /** Indicates if character is casting fireball spell.**/
    private boolean castsFireball = false;
    /** Indicates if character is throwing fireball.**/
    private boolean throwsFireball = false;
    /** Indicates if character is casting ice breath spell.**/
    private boolean castsIceBreath = false;
    /** Indicates if character is firing ice breath.**/
    private boolean firesIceBreath = false;
    
    /** List of currently casted and flying fireball in game.**/
    private ArrayList<Fireball> fireballsList;
    /** References currently fired ice breath spell.**/
    private IceBreath iceBreath;
    
    /** Liast of enemys currently in the game.**/
    private ArrayList<Enemy> enemysList;
    
    // Attribute fields.
    /** Current amount of player's mana.**/
    private int mana;
    /** Maximum amount of player's mana.**/
    private int maxMana;
    
    // Bounds.
    /** The width of the visual representation.**/
    public static int characterWidth = (int)(WINDOW_WIDTH * 50 / 1280);
    /** The height of the visual representation.**/
    public static int characterHeight = (int)(WINDOW_HEIGHT * 50 / 768);
    /** The width of the magic shields spell's visual representation.**/
    private int magicShieldWidth = (int)(4.2 * characterWidth);
    /** The height of the magic shields spell's visual representation.**/
    private int magicShieldHeight = (int)(4.12 * characterHeight);
    /** The width of the magic shields absorbtion's visual representation.**/
    private int magicShieldAbsorbWidth = (int)(4.26 * characterWidth);
    /** The height of the magic shields absorbtion's visual representation.**/
    private int magicShieldAbsorbHeight = (int)(4.12 * characterHeight);
    /** The width of the fireball cast's visual representation.**/
    private int castFireballWidth = (int)(1.5 * characterWidth);
    /** The height of the fireball cast's visual representation.**/
    private int castFireballHeight = (int)(1.5 * characterHeight);
    /** The width of the ice breath cast's visual representation.**/
    private int castIceBreathWidth = (int)(1.5 * characterWidth);
    /** The height of the ice breath cast's visual representation.**/
    private int castIceBreathHeight = (int)(1.5 * characterHeight);
    /** The x position of ice breath spell's visual representation if character headed right.**/
    private int iceBreathRightPositionX = (int)(0.05 * characterWidth);
    /** The x position of ice breath spell's visual representation if character headed left.**/
    private int iceBreathLeftPositionX = (int)(0.95 * characterWidth);
    /** The y position of ice breath spells's visual representation.**/
    private int iceBreathPositionY = (int)(0.4 * characterHeight);
    
    // CONSTRUCTORS.
    
    /**
     * Basic constructor.
     * @param x sets the x position of the visual representation.
     * @param y sets the y position of the visual representation.
     * @param speed sets the speed of the character.
     * @param health sets the maximum amount of health of the character.
     * @param mana sets the maximum amount of mana of the character.
     * @throws IOException if there is problem with reading animation files.
     */
    public Player(int x, int y, int speed, int health, int mana) throws IOException{
        super(x, y, speed, health);
        this.mana = mana;
        this.maxMana = mana;

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
        
        try{
            castSound = new AudioPlayer("src/res/sounds/soundEffects/spells/castSpell.wav", false);
            swordAttackSound = new AudioPlayer("src/res/sounds/soundEffects/combat/swordSwish.wav", false);
            hurtSound = new AudioPlayer("src/res/sounds/soundEffects/combat/meleeHit.wav", false);
            magicShieldSound = new AudioPlayer("src/res/sounds/soundEffects/spells/magicShield.wav", false);
            walkSound = new AudioPlayer("src/res/sounds/soundEffects/ambient/mud02.wav", false);
            magicShieldAbsorbSound = new AudioPlayer("src/res/sounds/soundEffects/spells/magicShieldAbsorb.wav", false);
            
    
        }catch(Exception e){
            //System.out.println("Exception catched");
        }
        
    }

    

    // METHODS.

    /**
     * Overriden drawing method that switches animations and runs them.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws PlayerDiesException when the player dies.
     * @throws EndOfCastFireballException when the player ends fireball casting action.
     * @throws EndOfCastIceBreathException when the player ends ice breath casting action.
     */
    @Override
    public void draw(Graphics graphics, Game observer) 
            throws PlayerDiesException,
            EndOfCastFireballException,
            EndOfCastIceBreathException{
        
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
            throw new EndOfCastFireballException();
        }catch(EndOfCastIceBreathException e){
            castsIceBreath = false;
            firesIceBreath = true;  
            throw new EndOfCastIceBreathException();
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
    
    /**
     * Clocking method for updating enemys data.
     */
    @Override
    public void tick(){
        updateMovement();
        updateBounds();
        checkArenaCollisions();
        updateAnimations();
        //System.out.println("boundsBox.x: " + boundsBox.x + " boundsBox.y: " + boundsBox.y);
        
    }

    /**
     * Updates current list of enemy objects in game.
     * @param enemysList input list for update.
     */
    public void updateEnemysList(ArrayList<Enemy> enemysList){
        this.enemysList = enemysList;
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
                        }
                    }
                    else{
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
        }
        
        if(walkSound != null){
            if(isMoving){
                walkSound.getClip().loop(Clip.LOOP_CONTINUOUSLY);
                walkSound.play();
            }else{
                walkSound.pause();
            }
        }
        
    }
    
    /**
     * Clocks animations.
     */
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
    
    /**
     * Brings up the attack action procedure.
     * Sets the attack action flage and plays attack sound.
     */
    public void attack(){
        isAttacking = true;
        swordAttackSound.getClip().loop(1);
    }
    
    /**
     * Seeks the enemys in range and deals them damage.
     */
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
    
    /**
     * Decreases health by received damage and checks if enemy is not to be dead.
     * @param damage amount of health to be decreased by.
     */
    public void takeDamage(int damage){
        
        if(magicShieldOn == false){
        
            health -= damage;
            
            if(health <= 0){
                isDying = true;
                takesDamage = false;
            }
            else
                takesDamage = true;
            hurtSound.getClip().loop(1);
        }
        else{
            shieldAbsorbsDamage = true;
            magicShieldAbsorbSound.getClip().loop(1);
            System.out.println("Shield absorb!");
        }
    }
    
    /**
     * Restores player's health after taking the health potion.
     * @param restoration amount of health to be restored.
     */
    public void restoreHealth(int restoration){
        if((maxHealth - health) < restoration)
            health = maxHealth;
        else
            health += restoration;
    }
    
    /**
     * Restores player's mana after taking the health potion.
     * @param restoration amount of mana to be restored.
     */
    public void restoreMana(int restoration){
        if((maxMana - mana) < restoration)
            mana = maxMana;
        else
            mana += restoration;
    }
    
    /**
     * Brings up the procedure of setting up or dropping the magic shield.
     * Sets magic shield flag and plays proper audio.
     * @throws EndOfMagicShieldException if magic shield was dropped.
     */
    public void setupMagicShield() throws EndOfMagicShieldException{
        
        if(!magicShieldOn){
            magicShieldOn = true;
            magicShieldSound.getClip().loop(Clip.LOOP_CONTINUOUSLY);
            magicShieldSound.play();
        }else{
            magicShieldOn = false;
            magicShieldSound.pause();
            throw new EndOfMagicShieldException();
        }

    }
    
    /**
     * Locks player's movement.
     * Sets proper flags on.
     * Decreases player's mana.
     * Plays casting sound.
     */
    public void throwFireball(){
        stopMovement();
        isLocked = true;
        castsFireball = true; 
        mana -= 20;
        
        castSound.getClip().loop(1);
        
    }
    
    /**
     * Locks player's movement.
     * Sets proper flags on.
     * Decreases player's mana.
     * Plays casting sound.
     */
    public void iceBreath(){
        stopMovement();
        isLocked = true;
        castsIceBreath = true;
        generateIceBreath();
        mana -= 20;
        castSound.getClip().loop(1);
    }
    
    /**
     * Initializes new fireball object and adds it to fireballs list.
     */
    private void generateFireball(){
        try{
            fireballsList.add(new Fireball(originX, originY, 500, isHeadedRight, enemysList));
        }
        catch(IOException e){
            System.out.println("Problem with fireball files!");
        }
    }
    
    /**
     * Initializes new ice breath object.
     */
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
    
    /**
     * Runs through the in game enemys list and checks if any contains in player's range box.
     * @param allEnemysList list of all enemy objects in game.
     * @return list of enemys that contain in range box.
     */
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
    
    /**
     * Scales the input image to input dimensions.
     * @param imageToScale the image to be scaled.
     * @param dWidth the width of the output image.
     * @param dHeight the height of the output image.
     * @return scaled image.
     */
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
    
    // SETTERS AND GETTERS.

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    

    

    
    
    
    
}
