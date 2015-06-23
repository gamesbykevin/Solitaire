package com.gamesbykevin.solitaire.solitaire.bakers;

import com.gamesbykevin.solitaire.shared.Shared;
import com.gamesbykevin.solitaire.solitaire.bakers.Bakers.Key;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Bakers unit test
 * @author GOD
 */
public final class BakersTest extends SolitaireTest
{
    private Solitaire bakers;
    
    public BakersTest()
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        //assume true
        assertTrue(Bakers.HOLDER_SIZE == 4);
        
        Solitaire bakers = new Bakers(Shared.INVISIBLE_PIXEL);
        
        //assume all holders have been added
        for (Key key : Key.values())
        {
            assertNotNull(bakers.getHolder(key));
        }
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Solitaire bakers = new Bakers(Shared.INVISIBLE_PIXEL);
        bakers.dispose();
        bakers = null;
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //create new solitaire game
        bakers = new Bakers(Shared.INVISIBLE_PIXEL);
        
        //assume not null
        assertNotNull(bakers);
    }
    
    @After
    @Override
    public void tearDown()
    {
        super.tearDown();
        
        bakers.dispose();
        bakers = null;
    }
    
    @Test
    public void createTest() throws Exception
    {
        bakers.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        bakers.create(getEngine().getRandom());
        bakers.shuffle(getEngine().getRandom(), bakers.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        bakers.create(getEngine().getRandom());
        bakers.shuffle(getEngine().getRandom(), bakers.getHolder(Key.Deck));
        bakers.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        while (!bakers.isCreateComplete())
        {
            bakers.create(getEngine().getRandom());
        }
        
        while (!bakers.isShuffleComplete())
        {
            bakers.shuffle(getEngine().getRandom(), bakers.getHolder(Key.Deck));
        }
        
        while (!bakers.isDealComplete())
        {
            bakers.deal(getEngine().getTime());
        }
        
        //assume false because we haven't checked validate yet
        assertFalse(bakers.hasGameover());
        
        //validate
        bakers.validate();
        
        for (Key key : Key.values())
        {
            //don't check these
            if (key == Key.Destination1 || key == Key.Destination2 ||
                key == Key.Destination3 || key == Key.Destination4 || key == Key.Deck)
            
            //remove all cards
            bakers.getHolder(key).removeAll();
        }
        
        //validate again
        bakers.validate();
        
        //assume true because there are no cards
        assertTrue(bakers.hasGameover());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        bakers.create(getEngine().getRandom());
        bakers.shuffle(getEngine().getRandom(), bakers.getHolder(Key.Deck));
        bakers.deal(getEngine().getTime());
        bakers.validate();
        bakers.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        bakers.create(getEngine().getRandom());
        bakers.shuffle(getEngine().getRandom(), bakers.getHolder(Key.Deck));
        bakers.deal(getEngine().getTime());
        bakers.validate();
        bakers.update(getEngine());
        bakers.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        bakers.setCreateComplete(true);
        bakers.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        bakers.setCreateComplete(true);
        assertTrue(bakers.isCreateComplete());
        bakers.setCreateComplete(false);
        assertFalse(bakers.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        bakers.setShuffleComplete(true);
        bakers.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        bakers.setShuffleComplete(true);
        assertTrue(bakers.isShuffleComplete());
        bakers.setShuffleComplete(false);
        assertFalse(bakers.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        bakers.setDealComplete(true);
        bakers.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        bakers.setDealComplete(true);
        assertTrue(bakers.isDealComplete());
        bakers.setDealComplete(false);
        assertFalse(bakers.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        bakers.setGameover(true);
        bakers.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        bakers.setGameover(true);
        assertTrue(bakers.hasGameover());
        bakers.setGameover(false);
        assertFalse(bakers.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        bakers.setWinner(true);
        bakers.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        bakers.setWinner(true);
        assertTrue(bakers.isWinner());
        bakers.setWinner(false);
        assertFalse(bakers.isWinner());
    }
}