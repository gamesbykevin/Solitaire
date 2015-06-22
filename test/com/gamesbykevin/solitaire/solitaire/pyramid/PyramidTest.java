package com.gamesbykevin.solitaire.solitaire.pyramid;

import com.gamesbykevin.solitaire.solitaire.pyramid.Pyramid.Key;
import com.gamesbykevin.solitaire.shared.Shared;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pyramid unit test
 * @author GOD
 */
public final class PyramidTest extends SolitaireTest
{
    private Solitaire pyramid;
    
    public PyramidTest()
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        //there will be 7 rows in the game
        assertTrue(Pyramid.ROWS == 7);
        
        Solitaire pyramid = new Pyramid(Shared.INVISIBLE_PIXEL);
        
        //assume all animations have been added
        for (Key key : Key.values())
        {
            assertNotNull(pyramid.getHolder(key));
        }
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Solitaire pyramid = new Pyramid(Shared.INVISIBLE_PIXEL);
        pyramid.dispose();
        pyramid = null;
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //create new instance
        pyramid = new Pyramid(Shared.INVISIBLE_PIXEL);
        
        //assume not null
        assertNotNull(pyramid);
    }
    
    @After
    @Override
    public void tearDown() 
    {
        super.tearDown();
        
        pyramid.dispose();
        pyramid = null;
    }

    @Test
    public void createTest() throws Exception
    {
        pyramid.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        pyramid.create(getEngine().getRandom());
        pyramid.shuffle(getEngine().getRandom(), pyramid.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        pyramid.create(getEngine().getRandom());
        pyramid.shuffle(getEngine().getRandom(), pyramid.getHolder(Key.Deck));
        pyramid.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        pyramid.create(getEngine().getRandom());
        pyramid.shuffle(getEngine().getRandom(), pyramid.getHolder(Key.Deck));
        pyramid.deal(getEngine().getTime());
        
        //assume false because we haven't checked validate yet
        assertFalse(pyramid.hasGameover());
        
        pyramid.validate();
        
        //assume true because there are no cards
        assertTrue(pyramid.hasGameover());
        
    }
    
    @Test
    public void renderTest() throws Exception
    {
        pyramid.create(getEngine().getRandom());
        pyramid.shuffle(getEngine().getRandom(), pyramid.getHolder(Key.Deck));
        pyramid.deal(getEngine().getTime());
        pyramid.validate();
        pyramid.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        pyramid.create(getEngine().getRandom());
        pyramid.shuffle(getEngine().getRandom(), pyramid.getHolder(Key.Deck));
        pyramid.deal(getEngine().getTime());
        pyramid.validate();
        pyramid.update(getEngine());
        pyramid.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        pyramid.setCreateComplete(true);
        pyramid.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        pyramid.setCreateComplete(true);
        assertTrue(pyramid.isCreateComplete());
        pyramid.setCreateComplete(false);
        assertFalse(pyramid.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        pyramid.setShuffleComplete(true);
        pyramid.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        pyramid.setShuffleComplete(true);
        assertTrue(pyramid.isShuffleComplete());
        pyramid.setShuffleComplete(false);
        assertFalse(pyramid.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        pyramid.setDealComplete(true);
        pyramid.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        pyramid.setDealComplete(true);
        assertTrue(pyramid.isDealComplete());
        pyramid.setDealComplete(false);
        assertFalse(pyramid.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        pyramid.setGameover(true);
        pyramid.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        pyramid.setGameover(true);
        assertTrue(pyramid.hasGameover());
        pyramid.setGameover(false);
        assertFalse(pyramid.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        pyramid.setWinner(true);
        pyramid.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        pyramid.setWinner(true);
        assertTrue(pyramid.isWinner());
        pyramid.setWinner(false);
        assertFalse(pyramid.isWinner());
    }
}