package olafolak.battlemagearena30.models.characters;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import olafolak.battlemagearena30.controllers.GameController;
import olafolak.battlemagearena30.models.sprites.Sprite;
import olafolak.battlemagearena30.models.animations.Animation;
import static olafolak.battlemagearena30.models.characters.Player.scale;
import olafolak.battlemagearena30.models.exceptions.CharacterDiesException;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.utilities.ImageFlip;

public abstract class Character implements CharacterInterface{

    // Technical fields.
    protected int x;
    protected int y;
    protected int originX;
    protected int originY;
    protected int speed;
    protected boolean canGoRight = true;
    protected boolean canGoLeft = true;
    protected boolean canGoUp = true;
    protected boolean canGoDown = true;
    protected boolean movesLeft = false;
    protected boolean movesRight = false;
    protected boolean movesUp = false;
    protected boolean movesDown = false;
    protected boolean isIdle = true;
    protected boolean isMoving = false;
    protected boolean isLocked = false;
    protected boolean isHeadedRight = true;
    protected boolean isAttacking = false;
    protected boolean isDying = false;
    protected boolean takesDamage = false;
    protected int animState = 0;
    protected BufferedImage image;
    protected Animation idleRightAnimation;
    protected Animation idleLeftAnimation;
    protected Animation walkRightAnimation;
    protected Animation walkLeftAnimation;
    protected Animation attackRightAnimation;
    protected Animation attackLeftAnimation;
    protected Rectangle boundsBox;
    protected Rectangle leftRangeBox;
    protected Rectangle rightRangeBox;
    protected Rectangle healthBar;
    protected Sprite sprite;


    // Attribute fields.
    protected int health;
    protected int maxHealth;
    protected int meleeRangeX;
    protected int meleeRangeY;

    // Constructors.
    public Character(int x, int y, int speed, int health) throws IOException {
        
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;

        meleeRangeX = 40;
        meleeRangeY = 40;

        boundsBox = new Rectangle(x, y, 100, 100);
        leftRangeBox = new Rectangle(x - (meleeRangeX / 2), y + 50 - (meleeRangeY / 2), meleeRangeX, meleeRangeY);
        rightRangeBox = new Rectangle(x + 100 - (meleeRangeX / 2), y + 50 - (meleeRangeY / 2), meleeRangeX, meleeRangeY);
        healthBar = new Rectangle(x, y - 10, health * 100 / maxHealth, 10);

    }

    // Methods.
    public void draw(Graphics graphics, Game observer) throws CharacterDiesException{
        
    }

    public void tick(){

    }
    
    protected void stopMovement(){
        movesLeft = false;
        movesRight = false;
        movesUp = false;
        movesDown = false;
    }
    
    protected void updateBounds(){
        
        originX = x + 50;
        originY = y + 50;
        
        boundsBox.setBounds(x, y, 100, 100);
        leftRangeBox.setBounds(x - (meleeRangeX / 2), y + 50 - (meleeRangeY / 2), meleeRangeX, meleeRangeY);
        rightRangeBox.setBounds(x + 100 - (meleeRangeX / 2), y + 50 - (meleeRangeY / 2), meleeRangeX, meleeRangeY);
        healthBar.setBounds(x, y - 10, health * 100 / maxHealth, 10);
        
    }
    
    protected void checkArenaCollisions(){
        
        if(x >= 1180)
            canGoRight = false;
        if(x <= 0)
            canGoLeft = false;
        if(y >= 640)
            canGoDown = false;
        if(y <= 20)
            canGoUp = false;

    }
    

    protected BufferedImage getScaledImage(Image srcImg, int w, int h){

        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    protected ArrayList<BufferedImage> getAnimationFrames(String source, String filename, int fileCount, int width, int height) throws IOException{
        
        ArrayList<BufferedImage> tmp = new ArrayList<>();
        //int width = 60;
        //int height = 36;
        
        BufferedImage tmpImage = ImageIO.read(new File(source + '/' + filename + "_1.png"));
        tmpImage = scale(tmpImage, width, height);
        tmp.add(tmpImage);
        
        for(int i = 1; i < fileCount; i++){

            tmpImage = ImageIO.read(new File(source + '/' + filename + '_' + String.valueOf(i) + ".png")); 
            tmpImage = scale(tmpImage, width, height);
            tmp.add(tmpImage);
        }

        return tmp;
    }

    // Setters and getters.    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isCanGoRight() {
        return canGoRight;
    }

    public void setCanGoRight(boolean canGoRight) {
        this.canGoRight = canGoRight;
    }

    public boolean isCanGoLeft() {
        return canGoLeft;
    }

    public void setCanGoLeft(boolean canGoLeft) {
        this.canGoLeft = canGoLeft;
    }

    public boolean isCanGoUp() {
        return canGoUp;
    }

    public void setCanGoUp(boolean canGoUp) {
        this.canGoUp = canGoUp;
    }

    public boolean isCanGoDown() {
        return canGoDown;
    }

    public void setCanGoDown(boolean canGoDown) {
        this.canGoDown = canGoDown;
    }

    public boolean isMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(boolean movesLeft) {
        if(!isLocked)
            this.movesLeft = movesLeft;
    }

    public boolean isMovesRight() {
        return movesRight;
    }

    public void setMovesRight(boolean movesRight) {
        if(!isLocked)
            this.movesRight = movesRight;
    }

    public boolean isMovesUp() {
        return movesUp;
    }

    public void setMovesUp(boolean movesUp) {
        if(!isLocked)
            this.movesUp = movesUp;
    }

    public boolean isMovesDown() {
        return movesDown;
    }

    public void setMovesDown(boolean movesDown) {
        if(!isLocked)
            this.movesDown = movesDown;
    }

    public boolean isIsHeadedRight() {
        return isHeadedRight;
    }

    public void setIsHeadedRight(boolean isHeadedRight) {
        this.isHeadedRight = isHeadedRight;
    }

    public boolean isIsAttacking() {
        return isAttacking;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isIsDying() {
        return isDying;
    }

    public void setIsDying(boolean isDying) {
        this.isDying = isDying;
    }

    public boolean isTakesDamage() {
        return takesDamage;
    }

    public void setTakesDamage(boolean takesDamage) {
        this.takesDamage = takesDamage;
    }

    public int getAnimState() {
        return animState;
    }

    public void setAnimState(int animState) {
        this.animState = animState;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

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

    public Rectangle getBoundsBox() {
        return boundsBox;
    }

    public void setBoundsBox(Rectangle boundsBox) {
        this.boundsBox = boundsBox;
    }

    public Rectangle getLeftRangeBox() {
        return leftRangeBox;
    }

    public void setLeftRangeBox(Rectangle leftRangeBox) {
        this.leftRangeBox = leftRangeBox;
    }

    public Rectangle getRightRangeBox() {
        return rightRangeBox;
    }

    public void setRightRangeBox(Rectangle rightRangeBox) {
        this.rightRangeBox = rightRangeBox;
    }

    public Rectangle getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(Rectangle healthBar) {
        this.healthBar = healthBar;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMeleeRangeX() {
        return meleeRangeX;
    }

    public void setMeleeRangeX(int meleeRangeX) {
        this.meleeRangeX = meleeRangeX;
    }

    public int getMeleeRangeY() {
        return meleeRangeY;
    }

    public void setMeleeRangeY(int meleeRangeY) {
        this.meleeRangeY = meleeRangeY;
    }
    
        
        
	
        
        
}
