package com.gamesbykevin.solitaire.stats;

import static com.gamesbykevin.solitaire.solitaire.SolitaireTest.TEST_IMAGE;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Stats unit test
 * @author GOD
 */
public final class StatsTest 
{
    private Stats stats;
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Stats stats = new Stats(100,100, TEST_IMAGE);
        
        assertNotNull(stats);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Stats stats = new Stats(100,100, TEST_IMAGE);
        stats.dispose();
        stats = null;
        
        assertNull(stats);
    }
    
    @Before
    public void setUp() throws Exception
    {
        stats = new Stats(100,100, TEST_IMAGE);
    }
    
    @After
    public void tearDown() 
    {
        stats.dispose();
        stats = null;
        
        assertNull(stats);
    }
    
    @Test
    public void getScoreTest() 
    {
        //assume true
        assertTrue(stats.getScore() == 0);
        
        //set new value
        stats.setScore(101);
        
        //assume true
        assertTrue(stats.getScore() == 101);
        
        //set new value
        stats.setScore(10);
        
        //assume false
        assertFalse(stats.getScore() == 11);
    }
    
    @Test
    public void setScoreTest() 
    {
        //set new value
        stats.setScore(101);
        
        //assume true
        assertTrue(stats.getScore() == 101);
        
        //set new value
        stats.setScore(222);
        
        //assume true
        assertTrue(stats.getScore() == 222);
        
        //set new value
        stats.setScore(222);
        
        //assume false
        assertFalse(stats.getScore() == 221);
    }
    
    @Test
    public void setMessageTest() 
    {
        stats.setMessage1(null);
        stats.setMessage2(null);
        stats.setMessage3(null);
        
        assertNull(stats.getMessage1());
        assertNull(stats.getMessage2());
        assertNull(stats.getMessage3());
        
        stats.setMessage1("1");
        stats.setMessage2("2");
        stats.setMessage3("3");
        
        assertNotNull(stats.getMessage1());
        assertNotNull(stats.getMessage2());
        assertNotNull(stats.getMessage3());
        
        assertEquals(stats.getMessage1(), "1");
        assertEquals(stats.getMessage2(), "2");
        assertEquals(stats.getMessage3(), "3");
    }
    
    @Test
    public void getTimerTest()
    {
        assertNotNull(stats.getTimer());
    }
    
    @Test
    public void flagChangeTest()
    {
        stats.flagChange();
    }
    
    @Test
    public void drawStatsTest() throws Exception
    {
        stats.drawStats(stats.getGraphics2D());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        stats.render();
        stats.flagChange();
        stats.render();
    }
}