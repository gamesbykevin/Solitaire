package com.gamesbykevin.solitaire.resources;

import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.main.Main;
import com.gamesbykevin.solitaire.resources.GameAudio;
import com.gamesbykevin.solitaire.resources.GameFont;
import com.gamesbykevin.solitaire.resources.GameImages;
import com.gamesbykevin.solitaire.resources.GameText;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Resources unit test
 * @author GOD
 */
public class ResourcesTest 
{
    private static final JPanel panel = new JPanel();
    private static final Main main = new Main(60, panel);
    private Engine engine;
    private Resources resources;
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Resources resources = new Resources();
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() throws Exception
    {
        //create new engine
        engine = new Engine(new Rectangle(0,0,100,100), 0);
        
        //create new resources object
        resources = new Resources();
    }
    
    @After
    public void tearDown() 
    {
        engine.dispose();
        engine = null;
        
        resources.dispose();
        resources = null;
    }
    
    /**
     * Continue loop through until all resources are loaded
     * @throws Exception 
     */
    private void load() throws Exception
    {
        //continue updating until loading resources is complete
        while(resources.isLoading())
        {
            resources.update();
        }
    }
    
    @Test
    public void updateTest() throws Exception
    {
        resources.update();
    }
    
    @Test
    public void isLoadingTest() throws Exception
    {
        assertTrue(resources.isLoading());
        
        //continue updating until loading resources is complete
        load();
        
        assertFalse(resources.isLoading());
    }
    
    @Test
    public void stopAllSoundTest()
    {
        resources.stopAllSound();
    }
    
    @Test
    public void isAudioEnabledTest()
    {
        assertTrue(resources.isAudioEnabled());
    }
    
    @Test
    public void setAudioEnabled()
    {
        resources.setAudioEnabled(true);
        assertTrue(resources.isAudioEnabled());
        resources.setAudioEnabled(false);
        assertFalse(resources.isAudioEnabled());
    }
    
    @Test
    public void getGameImageTest()
    {
        //resources.getGameImage(this);
    }
    
    @Test
    public void getGameTextTest()
    {
        //resources.getGameText();
    }
    
    @Test
    public void playGameAudioTest()
    {
        //resources.playGameAudio(object, false);
    }
    
    @Test
    public void stopGameAudioTest()
    {
        //resources.stopGameAudio(object);
    }
    
    @Test
    public void getFontTest() throws Exception
    {
        //continue updating until loading resources is complete
        load();
        
        assertNotNull(resources.getFont(GameFont.Keys.Default));
    }
    
    @Test
    public void setFontTest() throws Exception
    {
        //continue updating until loading resources is complete
        load();
        
        resources.setFont(GameFont.Keys.Default, null);
    }
    
    @Test
    public void renderTest() throws Exception
    {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.createGraphics();
        
        resources.render(graphics, engine.getScreen());
        
        //render while the resource loading is taking place
        while(resources.isLoading())
        {
            resources.render(graphics, engine.getScreen());
            resources.update();
        }
    }
}