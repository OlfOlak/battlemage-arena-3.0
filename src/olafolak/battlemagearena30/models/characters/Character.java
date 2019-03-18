package olafolak.battlemagearena30.models.characters;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import olafolak.battlemagearena30.models.sprites.Sprite;
import olafolak.battlemagearena30.models.animations.Animation;
import olafolak.battlemagearena30.models.animations.AttackAnimation;
import olafolak.battlemagearena30.models.animations.IdleAnimation;
import olafolak.battlemagearena30.models.animations.WalkAnimation;
import static olafolak.battlemagearena30.models.characters.Player.scale;
import olafolak.battlemagearena30.models.exceptions.CharacterDiesException;
import olafolak.battlemagearena30.models.game.Game;
import static olafolak.battlemagearena30.models.game.Game.*;
import olafolak.battlemagearena30.models.sprites.BoundsBox;
import olafolak.battlemagearena30.models.world.Arena;
import static olafolak.battlemagearena30.models.world.Arena.movementArea;


public abstract class Character implements CharacterInterface, Comparable<Character>{

    // Technical fields.
    protected int x;
    protected int y;
    protected int originX;
    protected int originY;
    protected int baseX;
    protected int baseY;
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
    protected IdleAnimation idleAnimation;
    protected WalkAnimation walkAnimation;
    protected AttackAnimation attackAnimation;
    protected Animation idleRightAnimation;
    protected Animation idleLeftAnimation;
    protected Animation walkRightAnimation;
    protected Animation walkLeftAnimation;
    protected Animation attackRightAnimation;
    protected Animation attackLeftAnimation;
    protected BoundsBox boundsBox;
    protected BoundsBox leftRangeBox;
    protected BoundsBox rightRangeBox;
    protected BoundsBox healthBar;
    protected BoundsBox baseline;
    protected Sprite sprite;
    
    // Attribute fields.
    protected int health;
    protected int maxHealth;
    
    // Bounds.
    private int characterWidth = 50;
    private int characterHeight = 50;
    protected int meleeRangeX = (int)(0.4 * characterWidth);
    protected int meleeRangeY = (int)(0.4 * characterHeight);
    protected int healthBarWidth = characterWidth;
    protected int healthBarHeight = (int)(0.1 * characterHeight);

    // Constructors.
    public Character(int x, int y, int speed, int health) throws IOException {
        
        this.x = x;
        this.y = y;
        this.originX = x + (characterWidth / 2);
        this.originY = y + (characterHeight / 2);
        this.baseX = originX;
        this.baseY = originY + (characterHeight / 2);
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;

        boundsBox = new BoundsBox(originX, originY, characterWidth, characterHeight);
        leftRangeBox = new BoundsBox(x, originY, meleeRangeX, meleeRangeY);
        rightRangeBox = new BoundsBox(x + characterWidth, originY, meleeRangeX, meleeRangeY);
        healthBar = new BoundsBox(originX, y - (healthBarHeight / 2), health * healthBarWidth / maxHealth, healthBarHeight);
        baseline = new BoundsBox(baseX, baseY, characterWidth, 1);

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
        
        originX = x + (characterWidth / 2);
        originY = y + (characterHeight / 2);
        baseX = originX;
        baseY = originY + (characterHeight / 2);
        
        boundsBox.setBoundsByOrigin(originX, originY, characterWidth, characterHeight);
        leftRangeBox.setBoundsByOrigin(x, originY, meleeRangeX, meleeRangeY);
        rightRangeBox.setBoundsByOrigin(x + characterWidth, originY, meleeRangeX, meleeRangeY);
        healthBar.setBounds(x, y - healthBarHeight, health * healthBarWidth / maxHealth, healthBarHeight);
        
    }
    
    protected void checkArenaCollisions(){

        if(baseX >= (movementArea.x + movementArea.width))
            canGoRight = false;
        if(baseX <= movementArea.x)
            canGoLeft = false;
        if(baseY >= (movementArea.y + movementArea.height))
            canGoDown = false;
        if(baseY <= movementArea.y)
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
    
    @Override 
    public int compareTo(Character ch){
        if(y == ch.getY())  
            return 0;  
        else if(y > ch.getY())  
            return 1;  
        else  
            return -1;  
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
        this.movesLeft = movesLeft;
    }

    public boolean isMovesRight() {
        return movesRight;
    }

    public void setMovesRight(boolean movesRight) {
        this.movesRight = movesRight;
    }

    public boolean isMovesUp() {
        return movesUp;
    }

    public void setMovesUp(boolean movesUp) {
        this.movesUp = movesUp;
    }

    public boolean isMovesDown() {
        return movesDown;
    }

    public void setMovesDown(boolean movesDown) {
        this.movesDown = movesDown;
    }

    public boolean isIsIdle() {
        return isIdle;
    }

    public void setIsIdle(boolean isIdle) {
        this.isIdle = isIdle;
    }

    public boolean isIsMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
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

    public BoundsBox getBoundsBox() {
        return boundsBox;
    }

    public void setBoundsBox(BoundsBox boundsBox) {
        this.boundsBox = boundsBox;
    }

    public BoundsBox getLeftRangeBox() {
        return leftRangeBox;
    }

    public void setLeftRangeBox(BoundsBox leftRangeBox) {
        this.leftRangeBox = leftRangeBox;
    }

    public BoundsBox getRightRangeBox() {
        return rightRangeBox;
    }

    public void setRightRangeBox(BoundsBox rightRangeBox) {
        this.rightRangeBox = rightRangeBox;
    }

    public BoundsBox getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(BoundsBox healthBar) {
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

    public int getHealthBarWidth() {
        return healthBarWidth;
    }

    public void setHealthBarWidth(int healthBarWidth) {
        this.healthBarWidth = healthBarWidth;
    }

    public int getHealthBarHeight() {
        return healthBarHeight;
    }

    public void setHealthBarHeight(int healthBarHeight) {
        this.healthBarHeight = healthBarHeight;
    }

    public int getBaseX() {
        return baseX;
    }

    public void setBaseX(int baseX) {
        this.baseX = baseX;
    }

    public int getBaseY() {
        return baseY;
    }

    public void setBaseY(int baseY) {
        this.baseY = baseY;
    }

    public IdleAnimation getIdleAnimation() {
        return idleAnimation;
    }

    public void setIdleAnimation(IdleAnimation idleAnimation) {
        this.idleAnimation = idleAnimation;
    }

    public WalkAnimation getWalkAnimation() {
        return walkAnimation;
    }

    public void setWalkAnimation(WalkAnimation walkAnimation) {
        this.walkAnimation = walkAnimation;
    }

    public AttackAnimation getAttackAnimation() {
        return attackAnimation;
    }

    public void setAttackAnimation(AttackAnimation attackAnimation) {
        this.attackAnimation = attackAnimation;
    }

    public BoundsBox getBaseline() {
        return baseline;
    }

    public void setBaseline(BoundsBox baseline) {
        this.baseline = baseline;
    }

    public int getCharacterWidth() {
        return characterWidth;
    }

    public void setCharacterWidth(int characterWidth) {
        this.characterWidth = characterWidth;
    }

    public int getCharacterHeight() {
        return characterHeight;
    }

    public void setCharacterHeight(int characterHeight) {
        this.characterHeight = characterHeight;
    }
    
    
    
    
    
    
    
        
        
	
        
        
}
