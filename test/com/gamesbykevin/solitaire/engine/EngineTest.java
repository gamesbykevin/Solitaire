package com.gamesbykevin.solitaire.engine;

import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.main.Main;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JApplet;
import javax.swing.JPanel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * unit test Engine class
 * @author GOD
 */
public class EngineTest 
{
    private Engine engine;
    
    private JPanel panel;
    
    private MouseEvent event;
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Engine engine = new Engine(null, 0);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Engine engine = new Engine(null, 0);
        engine.dispose();
        engine = null;
    }
    
    @Before
    public void setUp() throws Exception
    {
        engine = new Engine(null, 1);
        panel = new JPanel();
        event = new MouseEvent(panel, 0, 0, 0, 100, 100, 1, false);
    }
    
    @After
    public void tearDown() 
    {
        engine.dispose();
        
        assertNull(engine.getResources());
        assertNull(engine.getKeyboard());
        assertNull(engine.getMouse());
        assertNull(engine.getManager());
        assertNull(engine.getMenu());
        assertNull(engine.getRandom());
        assertNull(engine.getScreen());
        engine = null;
    }
    
    @Test
    public void getScreenTest() throws Exception
    {
        assertNull(engine.getScreen());
        engine = new Engine(new Rectangle(0,0,0,0), 0);
        assertNotNull(engine.getScreen());
    }
    
    @Test
    public void getSeedTest()
    {
        assertTrue(engine.getSeed() > 0);
    }
    
    @Test
    public void getTimeTest()
    {
        assertTrue(engine.getTime() > 0);
    }
    
    @Test
    public void resetTest() throws Exception
    {
        engine.reset();
        
        assertNull(engine.getManager());
    }
    
    @Test
    public void getRandomTest()
    {
        assertNotNull(engine.getRandom());
    }
    
    @Test
    public void getManagerTest()
    {
        assertNull(engine.getManager());
    }
    
    @Test
    public void getMenuTest()
    {
        assertNull(engine.getMenu());
    }
    
    @Test
    public void getResourcesTest()
    {
        assertNull(engine.getResources());
    }
    
    @Test
    public void keyReleasedTest()
    {
        
        assertFalse(engine.getKeyboard().isKeyReleased());
        
        KeyEvent key = new KeyEvent(panel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_UP,'Z');
        
        engine.keyReleased(key);
        
        assertTrue(engine.getKeyboard().isKeyReleased());
    }
    
    @Test
    public void keyPressedTest()
    {
        assertFalse(engine.getKeyboard().isKeyPressed());
        
        KeyEvent key = new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP,'Z');
        
        engine.keyPressed(key);
        
        assertTrue(engine.getKeyboard().isKeyPressed());
    }
    
    @Test
    public void mouseClickedTest()
    {
        assertFalse(engine.getMouse().isMouseClicked());
        engine.mouseClicked(event);
        assertTrue(engine.getMouse().isMouseClicked());
    }
    
    @Test
    public void mousePressedTest()
    {
        assertFalse(engine.getMouse().isMousePressed());
        engine.mousePressed(event);
        assertTrue(engine.getMouse().isMousePressed());
    }
    
    @Test
    public void mouseReleasedTest()
    {
        assertFalse(engine.getMouse().isMouseReleased());
        engine.mouseReleased(event);
        assertTrue(engine.getMouse().isMouseReleased());
    }
    
    @Test
    public void mouseEnteredTest()
    {
        assertFalse(engine.getMouse().hasMouseEntered());
        engine.mouseEntered(event);
        assertTrue(engine.getMouse().hasMouseEntered());
    }
    
    @Test
    public void mouseExitedTest()
    {
        assertFalse(engine.getMouse().hasMouseExited());
        engine.mouseExited(event);
        assertTrue(engine.getMouse().hasMouseExited());
    }
    
    @Test
    public void mouseMovedTest()
    {
        assertFalse(engine.getMouse().hasMouseMoved());
        engine.mouseMoved(event);
        assertTrue(engine.getMouse().hasMouseMoved());
    }
    
    @Test
    public void mouseDraggedTest()
    {
        assertFalse(engine.getMouse().isMouseDragged());
        engine.mouseDragged(event);
        assertTrue(engine.getMouse().isMouseDragged());
    }
    
    @Test
    public void getMouseTest()
    {
        assertNotNull(engine.getMouse());
    }
    
    @Test
    public void getKeyboardTest()
    {
        assertNotNull(engine.getKeyboard());
    }
}