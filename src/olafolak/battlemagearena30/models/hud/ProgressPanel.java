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
import olafolak.battlemagearena30.models.game.Game;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_HEIGHT;
import static olafolak.battlemagearena30.models.game.Game.WINDOW_WIDTH;

/**
 * Describes the panel with information about current wave progress and amount of wave left.
 * @author OlfOlak
 */
public class ProgressPanel {
    
    // FIELDS.
    /** The x position of the panel on screen.**/
    private int x;
    /** The y position of the panel on screen.**/
    private int y;
    
    /** Number of current wave.**/
    private int wavesCount = 1;
    /** Maximum amount of waves in current round.**/
    private int wavesMax;
    
    /** Current wave progress.**/
    private int waveProgress = 0;
    /** Maximum amount of progress in current wave.**/
    private int waveOverallProgress;
    
    /** Keeps the bounds of the panel.**/
    private Rectangle casing;
    /** Keeps the bounds of the progress bar.**/
    private Rectangle progressBar;
    
    /** The width of the whole panel on screen.**/
    private int width = (int)(0.2 * WINDOW_WIDTH);
    /** The height of the whole panel on screen.**/
    private int height = (int)(0.105 * WINDOW_HEIGHT);
    
    
    // CONSTRUCTORS.
    /**
     * Basic constructor.
     * @param x sets the x position of panel.
     * @param y sets the y position of panel.
     * @param wavesMax sets the maximum number of waves.
     * @param waveOverallProgress sets the amount of progress to gain in order to win the wave.
     */
    public ProgressPanel(int x, int y, int wavesMax, int waveOverallProgress){
        this.x = x;
        this.y = y;
        this.wavesMax = wavesMax;
        this.waveOverallProgress = waveOverallProgress;
        
        casing = new Rectangle(x, y, width, height);
        progressBar = new Rectangle(x, y, width, (int)(0.4 * height));
    }
    
    // METHODS.
    /**
     * Draws all elements of panel on screen.
     * @param graphics target graphics to be drawed on.
     * @param observer context of the drawed graphics.
     */
    public void draw(Graphics graphics, Game observer){
        
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x, y, width, height);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 15));
        graphics.drawString("Wave progress:", x, y + 15);
        graphics.setColor(Color.YELLOW);
        graphics.drawRect(x, y + 17, width, (int)(0.4 * height));
        try{
            graphics.fillRect(x, y + 17, waveProgress * width / waveOverallProgress, (int)(0.4 * height));
        }catch(Exception e){
            
        }
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 25));
        graphics.drawString("Waves: " + String.valueOf(wavesCount) + "/" + String.valueOf(wavesMax), x, y + (int)(0.4 * height) + 30 + 13);
        graphics.setColor(Color.BLACK);
    }
    
    /**
     * Increments current wave's progress.
     * @param progress amount of progress to add.
     */
    public void addProgress(int progress){
        waveProgress += progress;
    }
    
    /**
     * Sets the panel up to the next wave.
     * @param waveOverallProgress next wave's amount progress to gain.
     */
    public void nextWave(int waveOverallProgress){
        wavesCount++;
        waveProgress = 0;
        this.waveOverallProgress = waveOverallProgress;
    }
    
    // SETTERS AND GETTERS.
    
}
