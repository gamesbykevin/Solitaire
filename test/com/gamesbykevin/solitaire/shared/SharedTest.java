package com.gamesbykevin.solitaire.shared;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 * Shared unit test
 * @author GOD
 */
public class SharedTest 
{
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        assertTrue(Shared.CONTAINER_HEIGHT == Shared.ORIGINAL_HEIGHT);
        assertTrue(Shared.CONTAINER_WIDTH == Shared.ORIGINAL_WIDTH);
        assertTrue(Shared.DEFAULT_UPS > 0);
        assertNotNull(Shared.CURSOR);
        assertNotNull(Shared.INVISIBLE_PIXEL);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() 
    {
    }
    
    @After
    public void tearDown() 
    {
        
    }
    
    @Test
    public void test()
    {
        assertTrue(Shared.CONTAINER_HEIGHT == Shared.ORIGINAL_HEIGHT);
        assertTrue(Shared.CONTAINER_WIDTH == Shared.ORIGINAL_WIDTH);
        assertTrue(Shared.DEFAULT_UPS > 0);
        assertNotNull(Shared.CURSOR);
        assertNotNull(Shared.INVISIBLE_PIXEL);
    }
}