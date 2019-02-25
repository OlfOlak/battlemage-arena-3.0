package olafolak.battlemagearena30.models.sprites;

import java.awt.image.BufferedImage;

/**
 *
 * @author OlafPC
 */
public class SpriteSheet {
    
    private BufferedImage image;
    
    public SpriteSheet(BufferedImage image){
        this.image = image;
    }
    
    public BufferedImage grabImage(int col, int row, int width, int height){
        BufferedImage img = image.getSubimage(col, row, width, height);
        return img;
    }
}
