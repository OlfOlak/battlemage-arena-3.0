/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import olafolak.battlemagearena30.models.characters.Enemy;
import olafolak.battlemagearena30.models.utilities.KeyControl;
import olafolak.battlemagearena30.models.characters.Player;
import olafolak.battlemagearena30.models.exceptions.EnemyDiesException;
import olafolak.battlemagearena30.models.exceptions.PlayerDiesException;
import olafolak.battlemagearena30.models.world.Arena;


/**
 *
 * @author OlafPC
 */
public class Game extends Canvas implements Runnable {

    public static final int SCALE = 10;
    public static final double WIDTH = 256.0;
    public static final double HEIGHT = WIDTH * 0.6;
    public static final double WINDOW_WIDTH = WIDTH * SCALE;
    public static final double WINDOW_HEIGHT = HEIGHT * SCALE;
    
    public final String TITLE = "Battlemage Arena 3.0";
    
    private boolean running = false;
    private Thread thread;
    
    private BufferedImage background = new BufferedImage((int)WINDOW_WIDTH, (int)WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
    
    private Player player;
    private Enemy enemy;
    private Arena arena;
    
    private Graphics graphics;
    
    public ArrayList<Enemy> allEnemysList;
    
    
    public void init(){
        requestFocus();
        addKeyListener(new KeyControl(this));
        try{
            allEnemysList = new ArrayList<>();
            player = new Player(100, 100, 7, 10000, 100);
            enemy = new Enemy(400, 600, 1, 150, player);
            allEnemysList.add(enemy);
            //enemy = new Enemy(500, 300, 0, 100, player);
            //allEnemysList.add(enemy);
            //enemy = new Enemy(700, 300, 1, 100, player);
            //allEnemysList.add(enemy);
            arena = new Arena((WINDOW_WIDTH), (WINDOW_HEIGHT));
            

        } catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    public synchronized void start(){
        
        if(running)
            return;
        
        running = true;
        thread = new Thread(this);
        thread.start();
        
    }
    
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
                //System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
            
        }
        stop();
        
    }
    
        
    
    
    private void tick(){
        
        player.tick();
        player.updateEnemysList(allEnemysList);
        
        for(Enemy e : allEnemysList){
            e.tick(player);
        }
        
    }
    
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
            
            for(Enemy e : allEnemysList)
                e.draw(graphics, this);
            player.draw(graphics, this);
            
        }catch(EnemyDiesException e){
            allEnemysList.remove(e.getEnemy());
        }catch(PlayerDiesException e){
            player = null;
        }
        
        
        
        // End of drawing section.
        
        
        graphics.dispose();
        bs.show();
        
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        /*if(key == KeyEvent.VK_LEFT)
            player.moveLeft();
        else if(key == KeyEvent.VK_RIGHT)
            player.moveRight();
        else if(key == KeyEvent.VK_UP)
            player.moveUp();
        else if(key == KeyEvent.VK_DOWN)
            player.moveDown();
        else if(key == KeyEvent.VK_LEFT && key == KeyEvent.VK_UP)
            player.moveLeftUp();
        else if(key == KeyEvent.VK_LEFT && key == KeyEvent.VK_DOWN)
            player.moveLeftDown();
        else if(key == KeyEvent.VK_RIGHT && key == KeyEvent.VK_UP)
            player.moveRightUp();
        else if(key == KeyEvent.VK_RIGHT && key == KeyEvent.VK_DOWN)
            player.moveRightDown();*/
        
        
        switch(key){
            case KeyEvent.VK_LEFT:
                //player.setInMotionX('-');
                player.setMovesLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                //player.setInMotionX('+');
                player.setMovesRight(true);
                break;
            case KeyEvent.VK_UP:
                player.setMovesUp(true);
                //player.setInMotionY('-');
                //player.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                player.setMovesDown(true);
                //player.setInMotionY('+');
                //player.moveDown();
                break;
            case KeyEvent.VK_SPACE:
                //player.setIsAttacking(true);
                player.attack(allEnemysList);
                break;
            case KeyEvent.VK_1:
                player.setupMagicShield();
                break;
            case KeyEvent.VK_2:
                player.throwFireball();
                break;
            case KeyEvent.VK_3:
                player.iceBreath();
                break;
            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        switch(key){
            case KeyEvent.VK_LEFT:
                player.setMovesLeft(false);
                //player.setVelX(0);
                
                //player.goIdle();
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovesRight(false);
                //player.setVelX(0);
                //player.goIdle();
                break;
            case KeyEvent.VK_UP:
                player.setMovesUp(false);
                //player.setVelY(0);
                //player.goIdle();
                break;
            case KeyEvent.VK_DOWN:
                player.setMovesDown(false);
                //player.setVelY(0);
                //player.goIdle();
                break;
            default:
                break;
        }
    }

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

    public void setBackground(BufferedImage background) {
        this.background = background;
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
