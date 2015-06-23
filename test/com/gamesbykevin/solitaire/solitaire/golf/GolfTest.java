package com.gamesbykevin.solitaire.solitaire.golf;

import com.gamesbykevin.solitaire.shared.Shared;
import com.gamesbykevin.solitaire.solitaire.golf.Golf.Key;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Golf unit test
 * @author GOD
 */
public final class GolfTest extends SolitaireTest
{
    private Solitaire golf;
    
    public GolfTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        Solitaire golf = new Golf(Shared.INVISIBLE_PIXEL);
        
        //assume all holders have been added
        for (Key key : Key.values())
        {
            assertNotNull(golf.getHolder(key));
        }
        
        //assume game will be 5 columns
        assertTrue(Golf.COLUMN_LIMIT == 5);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Solitaire golf = new Golf(Shared.INVISIBLE_PIXEL);
        golf.dispose();
        golf = null;
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //create new game
        golf = new Golf(Shared.INVISIBLE_PIXEL);
        
        //object now exists
        assertNotNull(golf);
    }
    
    @After
    @Override
    public void tearDown() 
    {
        super.tearDown();
        
        golf.dispose();
        golf = null;
    }
    
    @Test
    public void createTest() throws Exception
    {
        golf.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        golf.create(getEngine().getRandom());
        golf.shuffle(getEngine().getRandom(), golf.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        golf.create(getEngine().getRandom());
        golf.shuffle(getEngine().getRandom(), golf.getHolder(Key.Deck));
        golf.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        while (!golf.isCreateComplete())
        {
            golf.create(getEngine().getRandom());
        }
        
        while (!golf.isShuffleComplete())
        {
            golf.shuffle(getEngine().getRandom(), golf.getHolder(Key.Deck));
        }
        
        while (!golf.isDealComplete())
        {
            golf.deal(getEngine().getTime());
        }
        
        //assume false because we haven't checked validate yet
        assertFalse(golf.hasGameover());
        
        //remove all cards
        for (Key key : Key.values())
        {
            golf.getHolder(key).removeAll();
        }
        
        //validate
        golf.validate();
        
        //assume true because there are no cards
        assertTrue(golf.hasGameover());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        golf.create(getEngine().getRandom());
        golf.shuffle(getEngine().getRandom(), golf.getHolder(Key.Deck));
        golf.deal(getEngine().getTime());
        golf.validate();
        golf.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        golf.create(getEngine().getRandom());
        golf.shuffle(getEngine().getRandom(), golf.getHolder(Key.Deck));
        golf.deal(getEngine().getTime());
        golf.validate();
        golf.update(getEngine());
        golf.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        golf.setCreateComplete(true);
        golf.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        golf.setCreateComplete(true);
        assertTrue(golf.isCreateComplete());
        golf.setCreateComplete(false);
        assertFalse(golf.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        golf.setShuffleComplete(true);
        golf.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        golf.setShuffleComplete(true);
        assertTrue(golf.isShuffleComplete());
        golf.setShuffleComplete(false);
        assertFalse(golf.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        golf.setDealComplete(true);
        golf.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        golf.setDealComplete(true);
        assertTrue(golf.isDealComplete());
        golf.setDealComplete(false);
        assertFalse(golf.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        golf.setGameover(true);
        golf.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        golf.setGameover(true);
        assertTrue(golf.hasGameover());
        golf.setGameover(false);
        assertFalse(golf.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        golf.setWinner(true);
        golf.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        golf.setWinner(true);
        assertTrue(golf.isWinner());
        golf.setWinner(false);
        assertFalse(golf.isWinner());
    }
}