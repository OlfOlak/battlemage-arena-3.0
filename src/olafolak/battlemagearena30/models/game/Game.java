/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.utilities.KeyControl;
import olafolak.battlemagearena30.models.characters.Player;
import olafolak.battlemagearena30.models.characters.Character;
import olafolak.battlemagearena30.models.exceptions.EndOfBreakException;
import olafolak.battlemagearena30.models.exceptions.EndOfMagicShieldException;
import olafolak.battlemagearena30.models.exceptions.EnemyDiesException;
import olafolak.battlemagearena30.models.exceptions.PlayerDiesException;
import olafolak.battlemagearena30.models.exceptions.WaveEndedException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfCastFireballException;
import olafolak.battlemagearena30.models.exceptions.animationexceptions.EndOfCastIceBreathException;
import olafolak.battlemagearena30.models.hud.NextWaveCounter;
import olafolak.battlemagearena30.models.hud.PlayerPanel;
import olafolak.battlemagearena30.models.hud.ProgressPanel;
import olafolak.battlemagearena30.models.utilities.AudioPlayer;
import olafolak.battlemagearena30.models.world.Arena;


/**
 * Main class for setting, controlling and viewing actual game(fights, spawning enemys).
 * @author OlfOlak
 */
public class Game extends Canvas implements Runnable {

    
    // FIELDS.
    
    /** Static field for scaling the game window. **/
    public static final int SCALE = 5;
    /** Static field for window width constant. **/
    public static final double WIDTH = 256.0;
    /** Static field for window height constant. **/
    public static final double HEIGHT = WIDTH * 0.6;
    /** Static field for final window width constant. **/
    public static final double WINDOW_WIDTH = WIDTH * SCALE;
    /** Static field for final window height constant. **/
    public static final double WINDOW_HEIGHT = HEIGHT * SCALE;
    
    /** Game window title field. **/
    public final String TITLE = "Battlemage Arena 3.0";
    
    /** Determines if game is running. **/
    private boolean running = false;
    /** Determines if theres interwave break. **/
    private boolean breakRuns = false;
    
    /** Main thread field.**/
    private Thread thread;
    
    /** Enemys spawner field. **/
    private Spawner spawner;
    /** Player object field. **/
    public static Player player;
    /** Arena field.**/
    private Arena arena;
    /** Players panel field. **/
    private PlayerPanel playerPanel;
    /** Wave progress panel field. **/
    private ProgressPanel progressPanel;
    /** Break counter field. **/
    private NextWaveCounter nextWaveCounter;
    
    /** List of all spawned enemys. **/
    public ArrayList<Enemy> allEnemysList;
    /** List of all characters to render. **/
    private ArrayList<Character> renderQueue;
    
    /** Graphics instance for rendering. **/
    private Graphics graphics;
    /** Background image field. **/
    private BufferedImage background;
    
    /** Background music player. **/
    private AudioPlayer bgMusic;

    // METHODS.
    
    /**
     * Method initiating game with its components (Arena, enemys spawner, player, hud).
     */
    public void init(){
        requestFocus();
        addKeyListener(new KeyControl(this));
        try{
            background = new BufferedImage((int)WINDOW_WIDTH, (int)WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
            arena = new Arena((WINDOW_WIDTH), (WINDOW_HEIGHT));
            allEnemysList = new ArrayList<>();
            renderQueue = new ArrayList<>();
            spawner = new Spawner(1, allEnemysList);
            playerPanel = new PlayerPanel(0, 700, 100, 100, 1, 1, 5, 5, 5);
            progressPanel = new ProgressPanel((int)(WINDOW_WIDTH - (0.2 * WINDOW_WIDTH)), 700, 3, spawner.getWaveOverallProgress());
            nextWaveCounter = new NextWaveCounter(10);
            
            player = new Player(100, 100, 7, 100, 100);
            renderQueue.add(player);
            
            /*enemy = new FireMage(400, 600, 0, 150);
            allEnemysList.add(enemy);
            enemy = new Spearman(700, 300, 0, 150);
            allEnemysList.add(enemy);*/
            
            background = ImageIO.read(new File("src/res/world/arenaTiles/bg.png"));
            background = scale(background, 1280, 768);
            
            try{
                bgMusic = new AudioPlayer("src/res/sounds/music/bgMusic1.wav", false);
            }catch(Exception e){
                
            }
            //bgMusic.getClip().loop(1);
            
        } catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    /**
     * Starts game's main thread.
     */
    public synchronized void start(){
        
        if(running)
            return;
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    /**
     * Stops game's main thread.
     */
    private synchronized void stop() {
        
        if(!running)
            return;
        
        running = false;
        
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        System.exit(1);
    }
    
    /**
     * Run method that works out 60 fps and runs updating and rendering methods.
     */
    @Override
    public void run() {
        
        init();
        
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;
        
        while(running){
            
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            
            if(delta >= 1){
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                ticks = 0;
                timer = 0;
            }
            
        }
        stop();
        
    }
    
    /**
     * Clocking method that updates games data.
     */
    private void tick(){
        
        player.tick();
        player.updateEnemysList(allEnemysList);
        
        playerPanel.tick();
        playerPanel.updatePlayerData(player.getHealth(), player.getMana());
        
        if(!breakRuns){
            
            spawner.tick();
            
            try{
                spawner.run();
            }catch(WaveEndedException e){
                System.out.println("Start of break");
                nextWave();
                
            }
        }else{
            nextWaveCounter.tick();
            
            try{
                nextWaveCounter.run();
            }catch(EndOfBreakException e){
                System.out.println("End of break");
                breakRuns = false;
            }
        }
        
        
        // TODO: Delete updating with player parameter.
        for(Enemy e : allEnemysList){
            e.tick();
        }
        
    }
    
    /**
     * Rendering method that updates game graphics instance.
     */
    private void render(){
        
        BufferStrategy bs = this.getBufferStrategy();
        
        if(bs == null){
            createBufferStrategy(2);
            return;
        }
        
        graphics = bs.getDrawGraphics();
        
        // Drawing section.
        try{
            graphics.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            arena.draw(graphics, this);
            updateRenderQueue();
            for(Character c : renderQueue){
                if(c == player)
                    player.draw(graphics, this);
                else
                    c.draw(graphics, this);
            }
            
            playerPanel.draw(graphics, this);
            progressPanel.draw(graphics, this);
            
            if(breakRuns)
                nextWaveCounter.draw(graphics, this);
            
        }catch(EnemyDiesException e){
            allEnemysList.remove(e.getEnemy());
            spawner.addProgress(e.getEnemy().getProgressValue());
            progressPanel.addProgress(e.getEnemy().getProgressValue());
        }catch(PlayerDiesException e){
            player = null;
        }catch(EndOfCastFireballException e){
            playerPanel.fireballCooldown();
        }catch(EndOfCastIceBreathException e){
            playerPanel.iceBreathCooldown();
        }catch(Exception e){
            
        }
        
        
        
        // End of drawing section.
        
        
        graphics.dispose();
        bs.show();
        
    }
    
    /**
     * Method that check if any key is pressed.
     * @param e indicates which key has been pressed.
     */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        switch(key){
            case KeyEvent.VK_LEFT:
                player.setMovesLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovesRight(true);
                break;
            case KeyEvent.VK_UP:
                player.setMovesUp(true);
                break;
            case KeyEvent.VK_DOWN:
                player.setMovesDown(true);
                break;
            case KeyEvent.VK_SPACE:
                player.attack();
                break;
            case KeyEvent.VK_1:
                try{
                    if(!playerPanel.isMagicShieldCooldowns())
                        player.setupMagicShield();
                }catch(EndOfMagicShieldException ex){
                    playerPanel.magicShieldCooldown();
                }
                break;
            case KeyEvent.VK_2:
                if(!playerPanel.isFireballCooldowns())
                    player.throwFireball();
                break;
            case KeyEvent.VK_3:
                if(!playerPanel.isIceBreathCooldowns())
                    player.iceBreath();
                break;
            case KeyEvent.VK_4:
                if(playerPanel.drinkHealthPotion())
                    player.restoreHealth(30);
                break;
            case KeyEvent.VK_5:
                if(playerPanel.drinkManaPotion())
                    player.restoreMana(20);
                break;
            default:
                break;
        }
    }
    
    /**
     * Method that check if any of the pressed keys is being released.
     * @param e indicates which key has been released.
     */
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        switch(key){
            case KeyEvent.VK_LEFT:
                player.setMovesLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovesRight(false);
                break;
            case KeyEvent.VK_UP:
                player.setMovesUp(false);
                break;
            case KeyEvent.VK_DOWN:
                player.setMovesDown(false);
                break;
            default:
                break;
        }
    }
    
    /**
     * Static method for scaling input image to desired dimensions.
     * @param imageToScale input image.
     * @param dWidth output width of the image.
     * @param dHeight output height of the image.
     * @return scaled image.
     */
    private static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    
    /**
     * Method for switching order of the characters based on their y position.
     */
    private void updateRenderQueue(){
        renderQueue.clear();
        renderQueue.addAll(allEnemysList);
        renderQueue.add(player);
        Collections.sort(renderQueue);
    }
    
    /**
     * Method for setting up the game for the next wave.
     */
    private void nextWave(){
        spawner.nextWave();
        progressPanel.nextWave(spawner.getWaveOverallProgress());
        breakRuns = true;
    }

    // SETTERS AND GETTERS.
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }
    
    
    
    
}
