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
import olafolak.battlemagearena30.models.animations.AttackAnimation;
import olafolak.battlemagearena30.models.animations.IdleAnimation;
import olafolak.battlemagearena30.models.animations.WalkAnimation;
import static olafolak.battlemagearena30.models.characters.Player.scale;
import olafolak.battlemagearena30.models.exceptions.CharacterDiesException;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.sprites.BoundsBox;
import static olafolak.battlemagearena30.models.world.Arena.movementArea;

/**
 * Abstract class that describes widely understood character.
 * @author OlfOlak
 */
public abstract class Character implements CharacterInterface, Comparable<Character>{

    // FIELDS.
    // Technical fields.
    /** The x position of the visual representation.**/
    protected int x;
    /** The y position of the visual representation.**/
    protected int y;
    /** The x position of center of the visual representation.**/
    protected int originX;
    /** The y position of center of the visual representation.**/
    protected int originY;
    /** The x position of center point in the bottom of the visual representation.**/
    protected int baseX;
    /** The y position of center point in the bottom of the visual representation.**/
    protected int baseY;
    /** The speed of movement.**/
    protected int speed;
    /** Determines if character can move right.**/
    protected boolean canGoRight = true;
    /** Determines if character can move left.**/
    protected boolean canGoLeft = true;
    /** Determines if character can move up.**/
    protected boolean canGoUp = true;
    /** Determines if character can move down.**/
    protected boolean canGoDown = true;
    /** Indicates that character is on the move to the left.**/
    protected boolean movesLeft = false;
    /** Indicates that character is on the move to the right.**/
    protected boolean movesRight = false;
    /** Indicates that character is on the move up.**/
    protected boolean movesUp = false;
    /** Indicates that character is on the move down.**/
    protected boolean movesDown = false;
    /** Indicates that character is not moving at the moment.**/
    protected boolean isIdle = true;
    /** Indicates that character is moving at the moment.**/
    protected boolean isMoving = false;
    /** Indicates that character cannot move.**/
    protected boolean isLocked = false;
    /** Indicates direction which the character is headed to.**/
    protected boolean isHeadedRight = true;
    /** Indicates that character does the attacking action.**/
    protected boolean isAttacking = false;
    /** Indicates that character does the dying action.**/
    protected boolean isDying = false;
    /** Indicates that character does the receiving damage action.**/
    protected boolean takesDamage = false;
    /** Stores animation of idle action.**/
    protected IdleAnimation idleAnimation;
    /** Stores animation of walk action.**/
    protected WalkAnimation walkAnimation;
    /** Stores animation of attacking action.**/
    protected AttackAnimation attackAnimation;
    /** The frame of visual representation of the character.**/
    protected BoundsBox boundsBox;
    /** Indicates the area of possible hurting the enemy on the left of the character.**/
    protected BoundsBox leftRangeBox;
    /** Indicates the area of possible hurting the enemy on the right of the character.**/
    protected BoundsBox rightRangeBox;
    /** Consists the dimensioning of the character's health bar.**/
    protected BoundsBox healthBar;
    /** Bottom line of the bounds box.**/
    protected BoundsBox baseline;
    
    // Attribute fields.
    /** Current amount of character's health.**/
    protected int health;
    /** Maximum amount of character's health.**/
    protected int maxHealth;
    
    // Bounds.
    /** The width of the visual representation.**/
    private int characterWidth = 50;
    /** The height of the visual representation.**/
    private int characterHeight = 50;
    /** The width of the range box.**/
    protected int meleeRangeX = (int)(0.4 * characterWidth);
    /** The height of the range box.**/
    protected int meleeRangeY = (int)(0.4 * characterHeight);
    /** The width of the health bar.**/
    protected int healthBarWidth = characterWidth;
    /** The height of the health bar.**/
    protected int healthBarHeight = (int)(0.1 * characterHeight);

    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param x sets the x position of the visual representation.
     * @param y sets the y position of the visual representation.
     * @param speed sets the speed of the character.
     * @param health sets amount of maximum health of the character.
     * @throws IOException if there is problem with reading animation files.
     */
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

    // METHODS.
    /**
     * Empty drawing method to be overloaded.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     * @throws CharacterDiesException when character dies.
     */
    public void draw(Graphics graphics, Game observer) throws Exception{
        
    }

    /**
     * Clocking method for updating characters data.
     */
    public void tick(){

    }
    
    /**
     * Stops the character's current movement.
     */
    protected void stopMovement(){
        movesLeft = false;
        movesRight = false;
        movesUp = false;
        movesDown = false;
    }
    
    /**
     * Updates bounds being relative to the character's posision (x,y).
     */
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
    
    /**
     * Checks if character has came across arena's movement borders.
     */
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
    
    /**
     * Scales input image to given width and height.
     * @param srcImg image to be scaled.
     * @param w output width of the scaled image.
     * @param h output height of the scaled image.
     * @return scaled image.
     */
    protected BufferedImage getScaledImage(Image srcImg, int w, int h){

        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    /**
     * Loads images from given source to be animations frames.
     * @param source file source of the images to be loaded.
     * @param filename general name of every's image file.
     * @param fileCount number of file to be loaded, counted from 1.
     * @param width width of the loaded image.
     * @param height height of the loaded image/
     * @return list of images.
     * @throws IOException when there is problem with reading any image file.
     */
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
    
    /**
     * Overriden method from Comparable Interface enabling comparison of the character type objects by their y position.
     * @param ch character type object to be compared to.
     * @return 0 if objects on the same y position, 1 if compared objects is higher placed, -1 if lower placed.
     */
    @Override 
    public int compareTo(Character ch){
        if(y == ch.getY())  
            return 0;  
        else if(y > ch.getY())  
            return 1;  
        else  
            return -1;  
    }

    // SETTERS AND GETTERS.    

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
