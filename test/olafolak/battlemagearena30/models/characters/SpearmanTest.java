/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olafolak.battlemagearena30.models.characters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import olafolak.battlemagearena30.models.game.Game;
import olafolak.battlemagearena30.models.world.Arena;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author OlafPC
 */
public class SpearmanTest {
    
    public SpearmanTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Arena arena = new Arena(1280, 768);
        //Game game = new Game();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void updateBoundsOriginXTest() {
        
        try {
            Spearman spearman = new Spearman(0, 0, 1, 100);
            int expected = 0 + (spearman.getCharacterWidth() / 2);
            assertEquals(expected, spearman.getOriginX());
        } catch (IOException ex) {
            Logger.getLogger(SpearmanTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    @Test
    public void updateBoundsOriginYTest() {
        
        try {
            Spearman spearman = new Spearman(0, 0, 1, 100);
            int expected = 0 + (spearman.getCharacterHeight() / 2);
            assertEquals(expected, spearman.getOriginY());
        } catch (IOException ex) {
            Logger.getLogger(SpearmanTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    @Test
    public void updateBoundsBaseXTest() {
        
        try {
            Spearman spearman = new Spearman(0, 0, 1, 100);
            int expected = 0 + (spearman.getCharacterWidth() / 2);
            assertEquals(expected, spearman.getOriginX());
        } catch (IOException ex) {
            Logger.getLogger(SpearmanTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    @Test
    public void updateBoundsBaseYTest() {
        
        try {
            Spearman spearman = new Spearman(0, 0, 1, 100);
            int expected = 0 + spearman.getCharacterHeight();
            assertEquals(expected, spearman.getBaseY());
        } catch (IOException ex) {
            Logger.getLogger(SpearmanTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
}
