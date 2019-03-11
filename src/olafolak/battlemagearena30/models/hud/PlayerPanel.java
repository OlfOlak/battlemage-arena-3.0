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

/**
 *
 * @author OlafPC
 */
public class PlayerPanel {
    
    private int x;
    private int y;
    private int originX;
    private int originY;
    
    private int health;
    private int mana;
    private int maxHealth;
    private int maxMana;
    private int healthPotionCount;
    private int manaPotionCount;
    
    private boolean magicShieldCooldowns = false;
    private boolean fireballCooldowns = false;
    private boolean iceBreathCooldowns = false;
    
    private int magicShieldTimer = 0;
    private int magicShieldCooldown;
    private int magicShieldCooldownTimer = 0;
    private int fireballTimer = 0;
    private int fireballCooldown;
    private int fireballCooldownTimer = 0;
    private int iceBreathTimer = 0;
    private int iceBreathCooldown;
    private int iceBreathCooldownTimer = 0;
    
    private Rectangle casing;
    private Rectangle healthBar;
    private Rectangle manaBar;
    private Rectangle magicShieldIconShadow;
    private Rectangle fireballIconShadow;
    private Rectangle iceBreathIconShadow;
    
    
    private BufferedImage magicShieldIcon;
    private BufferedImage fireballIcon;
    private BufferedImage iceBreathIcon;
    private BufferedImage healthPotionIcon;
    private BufferedImage manaPotionIcon;
    
    private int width = (int)(0.2 * WINDOW_WIDTH);
    private int height = (int)(0.105 * WINDOW_HEIGHT);
    
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
    
    public void draw(Graphics graphics, Game observer){
        
        graphics.setColor(Color.BLACK);
        graphics.fillRect(casing.x, casing.y, casing.width, casing.height);
        graphics.setColor(Color.RED);
        graphics.fillRect(healthBar.x, healthBar.y, healthBar.width, healthBar.height);
        graphics.setColor(Color.BLUE);
        graphics.fillRect(manaBar.x, manaBar.y, manaBar.width, manaBar.height);
        
        
        graphics.drawImage(magicShieldIcon, x, y + (int)(0.5 * height), observer);
        graphics.drawImage(fireballIcon, x + (width / 5), y + (int)(0.5 * height), observer);
        graphics.drawImage(iceBreathIcon, x + 2 * (width / 5), y + (int)(0.5 * height), observer);
        graphics.drawImage(healthPotionIcon, x + 3 * (width / 5), y + (int)(0.5 * height), observer);
        graphics.drawImage(manaPotionIcon, x + 4 * (width / 5), y + (int)(0.5 * height), observer);
        
        graphics.setColor(new Color(0, 0, 0, 127));
        if(magicShieldCooldowns)
            graphics.fillRect(x, y + (int)(0.5 * height), magicShieldIcon.getWidth(), magicShieldIcon.getHeight());
        if(fireballCooldowns)
            graphics.fillRect(x + (width / 5), y + (int)(0.5 * height), fireballIcon.getWidth(), fireballIcon.getHeight());
        if(iceBreathCooldowns)
            graphics.fillRect(x + 2 * (width / 5), y + (int)(0.5 * height), iceBreathIcon.getWidth(), iceBreathIcon.getHeight());
                
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 15));
        graphics.drawString("1", x, y + (int)(0.5 * height) + 15);
        graphics.drawString("2", x + (width / 5), y + (int)(0.5 * height) + 15);
        graphics.drawString("3", x + 2 * (width / 5), y + (int)(0.5 * height) + 15);
        graphics.drawString("4", x + 3 * (width / 5), y + (int)(0.5 * height) + 15);
        graphics.drawString("5", x + 4 * (width / 5), y + (int)(0.5 * height) + 15);
        
        graphics.setColor(Color.BLACK);
    }
    
    public void tick(){
        if(magicShieldTimer == (magicShieldCooldown * 60)){
            magicShieldTimer = 0;
            magicShieldCooldowns = false;
        }
        if(fireballTimer == (fireballCooldown * 60)){
            fireballTimer = 0;
            fireballCooldowns = false;
        }
        if(iceBreathTimer == (iceBreathCooldown * 60)){
            iceBreathTimer = 0;
            iceBreathCooldowns = false;
        }
        
        if(magicShieldTimer % 60 == 0)
            magicShieldCooldownTimer++;
            
        
        if(magicShieldTimer != 0)
            magicShieldTimer++;
        if(fireballTimer != 0)
            fireballTimer++;
        if(iceBreathTimer != 0)
            iceBreathTimer++;
    }
    
    public void magicShieldCooldown(){
        magicShieldCooldowns = true;
        magicShieldTimer = 1;
    }
    
    public void fireballCooldown(){
        fireballCooldowns = true;
        fireballTimer = 1;
    }
    
    public void iceBreathCooldown(){
        iceBreathCooldowns= true;
        iceBreathTimer = 1;
    }
    
    
    
    
    
    
}
