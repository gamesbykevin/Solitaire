package com.gamesbykevin.solitaire.solitaire;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.engine.Engine;

import java.awt.Rectangle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Solitaire unit test
 * @author GOD
 */
public class SolitaireTest 
{
    private Solitaire solitaire;
    
    //we need a game engine to test
    private Engine engine;
    
    /**
     * The amount of time to deduct per update
     */
    private static final long DEFAULT_UPDATE_DURATION = Timers.toNanoSeconds(1000L);
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Engine engine = new Engine(new Rectangle(0,0,1,1), DEFAULT_UPDATE_DURATION);
        
        assertTrue(Solitaire.SHUFFLE_LIMIT == 3);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Engine engine = new Engine(new Rectangle(0,0,1,1), DEFAULT_UPDATE_DURATION);
        engine.dispose();
        engine = null;
    }
    
    @Before
    public void setUp() throws Exception
    {
        engine = new Engine(new Rectangle(0,0,1,1), DEFAULT_UPDATE_DURATION);
        
        assertNotNull(engine);
    }
    
    @After
    public void tearDown() 
    {
        engine.dispose();
        engine = null;
        
        assertNull(engine);
    }

    @Test
    public void hello() 
    {
        assertTrue(Solitaire.SHUFFLE_LIMIT == 3);
    }
    
    protected Engine getEngine() throws Exception
    {
        return this.engine;
    }
}