package com.gamesbykevin.solitaire.solitaire.poker;

import com.gamesbykevin.solitaire.shared.Shared;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.poker.Poker;
import com.gamesbykevin.solitaire.solitaire.poker.Poker.Key;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Poker unit test
 * @author GOD
 */
public final class PokerTest extends SolitaireTest
{
    private Solitaire poker;
    
    public PokerTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        Solitaire poker = new Poker(Shared.INVISIBLE_PIXEL);
        
        //assume all holders have been added
        for (Key key : Key.values())
        {
            assertNotNull(poker.getHolder(key));
        }
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Solitaire poker = new Poker(Shared.INVISIBLE_PIXEL);
        poker.dispose();
        poker = null;
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //object not created yet
        assertNull(poker);
        
        //create new object
        poker = new Poker(Shared.INVISIBLE_PIXEL);
        
        //our object now exists
        assertNotNull(poker);
    }
    
    @After
    @Override
    public void tearDown() 
    {
        super.tearDown();
        
        poker.dispose();
        poker = null;
    }
    
    @Test
    public void createTest() throws Exception
    {
        poker.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        poker.create(getEngine().getRandom());
        poker.shuffle(getEngine().getRandom(), poker.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        poker.create(getEngine().getRandom());
        poker.shuffle(getEngine().getRandom(), poker.getHolder(Key.Deck));
        poker.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        while (!poker.isCreateComplete())
        {
            poker.create(getEngine().getRandom());
        }
        
        while (!poker.isShuffleComplete())
        {
            poker.shuffle(getEngine().getRandom(), poker.getHolder(Key.Deck));
        }
        
        while (!poker.isDealComplete())
        {
            poker.deal(getEngine().getTime());
        }
        
        //assume false because we haven't checked validate yet
        assertFalse(poker.hasGameover());
        
        //remove all cards
        for (Key key : Key.values())
        {
            //don't check the deck
            if (key == Key.Deck)
                continue;
            
            //add to place holder
            if (poker.getHolder(key).isEmpty())
                poker.getHolder(key).add(poker.getHolder(Key.Deck).getLastCard());
            
            //remove from deck
            poker.getHolder(Key.Deck).remove(poker.getHolder(Key.Deck).getLastCard());
        }
        
        //validate
        poker.validate();
        
        //assume true because a card has been placed in every holder
        assertTrue(poker.hasGameover());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        poker.create(getEngine().getRandom());
        poker.shuffle(getEngine().getRandom(), poker.getHolder(Key.Deck));
        poker.deal(getEngine().getTime());
        poker.validate();
        poker.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        poker.create(getEngine().getRandom());
        poker.shuffle(getEngine().getRandom(), poker.getHolder(Key.Deck));
        poker.deal(getEngine().getTime());
        poker.validate();
        poker.update(getEngine());
        poker.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        poker.setCreateComplete(true);
        poker.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        poker.setCreateComplete(true);
        assertTrue(poker.isCreateComplete());
        poker.setCreateComplete(false);
        assertFalse(poker.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        poker.setShuffleComplete(true);
        poker.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        poker.setShuffleComplete(true);
        assertTrue(poker.isShuffleComplete());
        poker.setShuffleComplete(false);
        assertFalse(poker.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        poker.setDealComplete(true);
        poker.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        poker.setDealComplete(true);
        assertTrue(poker.isDealComplete());
        poker.setDealComplete(false);
        assertFalse(poker.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        poker.setGameover(true);
        poker.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        poker.setGameover(true);
        assertTrue(poker.hasGameover());
        poker.setGameover(false);
        assertFalse(poker.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        poker.setWinner(true);
        poker.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        poker.setWinner(true);
        assertTrue(poker.isWinner());
        poker.setWinner(false);
        assertFalse(poker.isWinner());
    }
}