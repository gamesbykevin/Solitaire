package com.gamesbykevin.solitaire.manager;

import com.gamesbykevin.solitaire.engine.Engine;
import java.awt.Rectangle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Manager unit test
 * @author GOD
 */
public class ManagerTest 
{
    private Manager manager;
    private Engine engine;
    
    @BeforeClass
    public static void setUpClass() 
    {
        
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() throws Exception
    {
        engine = new Engine(new Rectangle(0,0,0,0), 1);
        
        //continue loop until resources have been loaded
        while(engine.getResources() == null || engine.getResources().isLoading())
        {
            engine.update(null);
        }
        
        manager = new Manager(engine);
    }
    
    @After
    public void tearDown() 
    {
        
    }
    
    @Test
    public void hello() 
    {
        
    }
}