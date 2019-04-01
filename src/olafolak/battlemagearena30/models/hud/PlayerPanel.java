/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static olafolak.battlemagearena30.models.characters.Player.scale;
import olafolak.battlemagearena30.models.game.Game;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;
import olafolak.battlemagearena30.models.utilities.AudioPlayer;

/**
 * Describes the hud element that shows player's health, mana and avaiable spells.
 * @author OlfOlak
 */
public class PlayerPanel {
    
    // FIELDS.
    /** The x position of the panel on screen.**/
    private int x;
    /** The y position of the panel on screen.**/
    private int y;
    /** The x position of the center of the panel on screen.**/
    private int originX;
    /** The y position of the center of the panel on screen.**/
    private int originY;
    
    /** Stores current amount of player's health.**/
    private int health;
    /** Stores current amount of player's mana.**/
    private int mana;
    /** Stores the maximum amount of player's health.**/
    private int maxHealth;
    /** Stores the maximum amount of player's mana.**/
    private int maxMana;
    /** Stores current amount of health potions on player.**/
    private int healthPotionCount;
    /** Stores current amount of mana potions on player.**/
    private int manaPotionCount;
    
    /** Indicates if magic shield spell is cooling down (is avaiable to use).**/
    private boolean magicShieldCooldowns = false;
    /** Indicates if fireball spell is cooling down (is avaiable to use).**/
    private boolean fireballCooldowns = false;
    /** Indicates if ice breath spell is cooling down (is avaiable to use).**/
    private boolean iceBreathCooldowns = false;
    
    /** Ticks timer for magic shield spell.**/
    private int magicShieldTimer = 0;
    /** Number of seconds the magic shield cooldowns.**/
    private int magicShieldCooldown;
    /** Tick timer for magic shield spell's cooldown.**/
    private int magicShieldCooldownTimer = 0;
    /** Ticks timer for fireball spell.**/
    private int fireballTimer = 0;
    /** Number of seconds the fireball cooldowns.**/
    private int fireballCooldown;
    /** Tick timer for fireball spell's cooldown.**/
    private int fireballCooldownTimer = 0;
    /** Ticks timer ice breath spell.**/
    private int iceBreathTimer = 0;
    /** Number of seconds the ice breath spell cooldowns.**/
    private int iceBreathCooldown;
    /** Tick timer for ice breath spell's cooldown.**/
    private int iceBreathCooldownTimer = 0;
    
    /** Keeps the bounds of the whole panel.**/
    private Rectangle casing;
    /** Keeps the bounds of the health bar.**/
    private Rectangle healthBar;
    /** Keeps the bounds of the mana bar.**/
    private Rectangle manaBar;
    /** Keeps the bounds of the magic shield spell's icon.**/
    private Rectangle magicShieldIconShadow;
    /** Keeps the bounds of the fireball spell's icon.**/
    private Rectangle fireballIconShadow;
    /** Keeps the bounds of the ice breath spell's icon.**/
    private Rectangle iceBreathIconShadow;
    
    /** Keeps the image of the magic shield spell's icon.**/
    private BufferedImage magicShieldIcon;
    /** Keeps the image of the fireball spell's icon.**/
    private BufferedImage fireballIcon;
    /** Keeps the image of the ice breath spell's icon.**/
    private BufferedImage iceBreathIcon;
    /** Keeps the image of the health potion action's icon.**/
    private BufferedImage healthPotionIcon;
    /** Keeps the image of the mana potion action's icon.**/
    private BufferedImage manaPotionIcon;
    
    /** Playes the potion drinking sound.**/
    private AudioPlayer drinkSound;
    
    /** The width of the whole panel on screen.**/
    private int width = (int)(0.2 * WINDOW_WIDTH);
    /** The height of the whole panel on screen.**/
    private int height = (int)(0.105 * WINDOW_HEIGHT);
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param x sets the x position of panel.
     * @param y sets the y position of panel.
     * @param health sets the initial amount of player's health.
     * @param mana sets the initial amount of player's mana.
     * @param healthPotionCount sets the initial amount of health potions.
     * @param manaPotionCount sets the initial amount of mana potions.
     * @param shieldCooldown sets the cooldown of magic shield.
     * @param fireballCooldown sets the cooldown of fireball spell.
     * @param icebreathCooldown sets the cooldown of ice breath spell.
     */
    public PlayerPanel(int x,
            int y,
            int health,
            int mana,
            int healthPotionCount,
            int manaPotionCount,
            int shieldCooldown,
            int fireballCooldown,
            int icebreathCooldown){
        
        this.x = x;
        this.y = y;
        this.health = health;
        this.mana = mana;
        this.maxHealth = health;
        this.maxMana = mana;
        this.healthPotionCount = healthPotionCount;
        this.manaPotionCount = manaPotionCount;
        this.magicShieldCooldown = shieldCooldown;
        this.fireballCooldown = fireballCooldown;
        this.iceBreathCooldown = icebreathCooldown;
        
        /*try {
            drinkSound = new AudioPlayer("src/res/sounds/soundEffects/ambient/drink.wav", false);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        this.originX = x + (width / 2);
        this.originY = y + (height / 2);
        
        casing = new Rectangle(x, y, width, height);
        healthBar = new Rectangle(x, y, health * width / maxHealth, (int)(0.25 * height));
        manaBar = new Rectangle(x, y + (int)(0.25 * height), mana * width / maxMana, (int)(0.25 * height));
        
        
        try{
            magicShieldIcon = ImageIO.read(new File("src/res/hud/magicShieldIcon.png"));
            magicShieldIcon = scale(magicShieldIcon, width / 5, (int)(0.5 * height));
            fireballIcon = ImageIO.read(new File("src/res/hud/fireballIcon.png"));
            fireballIcon = scale(fireballIcon, width / 5, (int)(0.5 * height));
            iceBreathIcon = ImageIO.read(new File("src/res/hud/iceBreathIcon.png"));
            iceBreathIcon = scale(iceBreathIcon, width / 5, (int)(0.5 * height));
            healthPotionIcon = ImageIO.read(new File("src/res/hud/healthPotionIcon.png"));
            healthPotionIcon = scale(healthPotionIcon, width / 5, (int)(0.5 * height));
            manaPotionIcon = ImageIO.read(new File("src/res/hud/manaPotionIcon.png"));
            manaPotionIcon = scale(manaPotionIcon, width / 5, (int)(0.5 * height));
            
        }catch(IOException e){
            System.out.println("Icon files ioexception");
        }
        
        magicShieldIconShadow = new Rectangle(x, y + (int)(0.5 * height), magicShieldIcon.getWidth(), magicShieldIcon.getHeight());
        fireballIconShadow = new Rectangle(x, y + (int)(0.5 * height), fireballIcon.getWidth(), fireballIcon.getHeight());  
        iceBreathIconShadow = new Rectangle(x, y + (int)(0.5 * height), iceBreathIcon.getWidth(), iceBreathIcon.getHeight());  
    }
    
    // METHODS.
    /**
     * Draws all elements of panel on screen.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     */
    public void draw(Graphics graphics, Game observer){
        
        graphics.setColor(Color.BLACK);
        graphics.fillRect(casing.x, casing.y, casing.width, casing.height);
        graphics.setColor(Color.RED);
        graphics.fillRect(healthBar.x, healthBar.y, health * healthBar.width / maxHealth, healthBar.height);
        graphics.setColor(Color.BLUE);
        graphics.fillRect(manaBar.x, manaBar.y, mana * manaBar.width / maxMana, manaBar.height);
        
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 15));
        graphics.drawString(String.valueOf(health) + "/" + String.valueOf(maxHealth), x + healthBar.width / 2 - 30, y + 15);
        graphics.drawString(String.valueOf(mana) + "/" + String.valueOf(maxMana), x + healthBar.width / 2 - 30, y + healthBar.height + 15);
        
        
        graphics.drawImage(magicShieldIcon, x, y + (int)(0.5 * height), observer);
        graphics.drawImage(fireballIcon, x + (width / 5), y + (int)(0.5 * height), observer);
        graphics.drawImage(iceBreathIcon, x + 2 * (width / 5), y + (int)(0.5 * height), observer);
        graphics.drawImage(healthPotionIcon, x + 3 * (width / 5), y + (int)(0.5 * height), observer);
        graphics.drawImage(manaPotionIcon, x + 4 * (width / 5), y + (int)(0.5 * height), observer);
        
        
        if(magicShieldCooldowns){
            graphics.setColor(new Color(0, 0, 0, 127));
            graphics.fillRect(x, y + (int)(0.5 * height), magicShieldIcon.getWidth(), magicShieldIcon.getHeight());
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 25));
            graphics.drawString(String.valueOf(magicShieldCooldown - magicShieldCooldownTimer),
                x + (magicShieldIcon.getWidth() / 2) - 6,
                y + (int)(0.5 * height) + (magicShieldIcon.getHeight() / 2) + 12);
        }
        if(fireballCooldowns){
            graphics.setColor(new Color(0, 0, 0, 127));
            graphics.fillRect(x + (width / 5), y + (int)(0.5 * height), fireballIcon.getWidth(), fireballIcon.getHeight());
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 25));
            graphics.drawString(String.valueOf(fireballCooldown - fireballCooldownTimer),
                x + (fireballIcon.getWidth() / 2) - 6 + (width / 5),
                y + (int)(0.5 * height) + (fireballIcon.getHeight() / 2) + 12);
        }
        if(iceBreathCooldowns){
            graphics.setColor(new Color(0, 0, 0, 127));
            graphics.fillRect(x + 2 * (width / 5), y + (int)(0.5 * height), iceBreathIcon.getWidth(), iceBreathIcon.getHeight());
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 25));
            graphics.drawString(String.valueOf(iceBreathCooldown - iceBreathCooldownTimer),
                x + (iceBreathIcon.getWidth() / 2) - 6 + 2 * (width / 5),
                y + (int)(0.5 * height) + (iceBreathIcon.getHeight() / 2) + 12);
        }
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 15));
        graphics.drawString("1", x, y + (int)(0.5 * height) + 15);
        graphics.drawString("2", x + (width / 5), y + (int)(0.5 * height) + 15);
        graphics.drawString("3", x + 2 * (width / 5), y + (int)(0.5 * height) + 15);
        graphics.drawString("4", x + 3 * (width / 5), y + (int)(0.5 * height) + 15);
        graphics.drawString("5", x + 4 * (width / 5), y + (int)(0.5 * height) + 15);
        
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 25));
        graphics.drawString(String.valueOf(healthPotionCount),
            x + (iceBreathIcon.getWidth() / 2) - 6 + 3 * (width / 5),
            y + (int)(0.5 * height) + (iceBreathIcon.getHeight() / 2) + 12);
            
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 25));
        graphics.drawString(String.valueOf(manaPotionCount),
            x + (iceBreathIcon.getWidth() / 2) - 6 + 4 * (width / 5),
            y + (int)(0.5 * height) + (iceBreathIcon.getHeight() / 2) + 12);
        
        graphics.setColor(Color.BLACK);
    }
    
    /**
     * Clocking method for updating the timers and managing cooldowns.
     */
    public void tick(){
        if(magicShieldTimer == (magicShieldCooldown * 60)){
            magicShieldTimer = 0;
            magicShieldCooldowns = false;
            magicShieldCooldownTimer = 0;
        }
        if(fireballTimer == (fireballCooldown * 60)){
            fireballTimer = 0;
            fireballCooldowns = false;
            fireballCooldownTimer = 0;
        }
        if(iceBreathTimer == (iceBreathCooldown * 60)){
            iceBreathTimer = 0;
            iceBreathCooldowns = false;
            iceBreathCooldownTimer = 0;
        }
        
        if(magicShieldTimer != 0){
            magicShieldTimer++;
            
            if(magicShieldTimer % 60 == 0)
                magicShieldCooldownTimer++;
        }
        if(fireballTimer != 0){
            fireballTimer++;
            
            if(fireballTimer % 60 == 0)
                fireballCooldownTimer++;
        }
        if(iceBreathTimer != 0){
            iceBreathTimer++;
            
            if(iceBreathTimer % 60 == 0)
                iceBreathCooldownTimer++;
        }    
    }
    
    /**
     * Starts cooldown of magic shield spell.
     */
    public void magicShieldCooldown(){
        magicShieldCooldowns = true;
        magicShieldTimer = 1;
    }
    
    /**
     * Starts cooldown of fireball spell.
     */
    public void fireballCooldown(){
        fireballCooldowns = true;
        fireballTimer = 1;
    }
    
    /**
     * Starts cooldown of ice breath spell.
     */
    public void iceBreathCooldown(){
        iceBreathCooldowns= true;
        iceBreathTimer = 1;
    }
    
    /**
     * Deincrements health potion count if there are any avaiable.
     * @return true if there was at least 1 potion, false otherwise.
     */
    public boolean drinkHealthPotion(){
        
        if(healthPotionCount == 0)
            return false;
        else{
            healthPotionCount--;
            //drinkSound.getClip().loop(1);
            return true;
        }
    }
    
    /**
     * Deincrements mana potion count if there are any avaiable.
     * @return true if there was at least 1 potion, false otherwise.
     */
    public boolean drinkManaPotion(){
        
        if(manaPotionCount == 0)
            return false;
        else{
            manaPotionCount--;
            //drinkSound.getClip().loop(1);
            return true;
        }
    }
    
    /**
     * Updates players health and mana data.
     * @param health sets new amount of health.
     * @param mana sets new amount of mana.
     */
    public void updatePlayerData(int health, int mana){
        this.health = health;
        this.mana = mana;
    }
    
    // SETTERS AND GETTERS.

    public int getHealthPotionCount() {
        return healthPotionCount;
    }

    public void setHealthPotionCount(int healthPotionCount) {
        this.healthPotionCount = healthPotionCount;
    }

    public int getManaPotionCount() {
        return manaPotionCount;
    }

    public void setManaPotionCount(int manaPotionCount) {
        this.manaPotionCount = manaPotionCount;
    }

    public boolean isMagicShieldCooldowns() {
        return magicShieldCooldowns;
    }

    public void setMagicShieldCooldowns(boolean magicShieldCooldowns) {
        this.magicShieldCooldowns = magicShieldCooldowns;
    }

    public boolean isFireballCooldowns() {
        return fireballCooldowns;
    }

    public void setFireballCooldowns(boolean fireballCooldowns) {
        this.fireballCooldowns = fireballCooldowns;
    }

    public boolean isIceBreathCooldowns() {
        return iceBreathCooldowns;
    }

    public void setIceBreathCooldowns(boolean iceBreathCooldowns) {
        this.iceBreathCooldowns = iceBreathCooldowns;
    }

    
    
    
    
    
    
}
