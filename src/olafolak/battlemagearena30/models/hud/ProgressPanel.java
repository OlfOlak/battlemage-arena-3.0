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
 *
 * @author OlafPC
 */
public class ProgressPanel {
    
    private int x;
    private int y;
    
    private int wavesCount = 1;
    private int wavesMax;
    
    private int waveProgress = 0;
    private int waveOverallProgress;
    
    private Rectangle casing;
    private Rectangle progressBar;
    
    private int width = (int)(0.2 * WINDOW_WIDTH);
    private int height = (int)(0.105 * WINDOW_HEIGHT);
    
    public ProgressPanel(int x, int y, int wavesMax, int waveOverallProgress){
        this.x = x;
        this.y = y;
        this.wavesMax = wavesMax;
        this.waveOverallProgress = waveOverallProgress;
        
        casing = new Rectangle(x, y, width, height);
        progressBar = new Rectangle(x, y, width, (int)(0.4 * height));
    }
    
    public void draw(Graphics graphics, Game observer){
        
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x, y, width, height);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 15));
        graphics.drawString("Wave progress:", x, y + 15);
        graphics.setColor(Color.YELLOW);
        graphics.drawRect(x, y + 17, width, (int)(0.4 * height));
        graphics.fillRect(x, y + 17, waveProgress * width / waveOverallProgress, (int)(0.4 * height));
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 25));
        graphics.drawString("Waves: " + String.valueOf(wavesCount) + "/" + String.valueOf(wavesMax), x, y + (int)(0.4 * height) + 30 + 13);
        graphics.setColor(Color.BLACK);
    }
    
    public void addProgress(int progress){
        waveProgress += progress;
    }
    
}
