package com.gamesbykevin.solitaire.solitaire.klondike;

import com.gamesbykevin.solitaire.shared.Shared;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;
import com.gamesbykevin.solitaire.solitaire.klondike.Klondike.Key;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Klondike unit test
 * @author GOD
 */
public class KlondikeTest extends SolitaireTest
{
    private Solitaire klondike;
    
    public KlondikeTest()
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        Solitaire klondike = new Klondike(Shared.INVISIBLE_PIXEL);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Solitaire klondike = new Klondike(Shared.INVISIBLE_PIXEL);
        klondike.dispose();
        klondike = null;
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //create new instance
        klondike = new Klondike(Shared.INVISIBLE_PIXEL);
        
        //assume this is not null
        assertNotNull(klondike);
    }
    
    @After
    @Override
    public void tearDown() 
    {
        super.tearDown();
        
        klondike.dispose();
        klondike = null;
    }
    
    @Test
    public void createTest() throws Exception
    {
        klondike.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        klondike.create(getEngine().getRandom());
        klondike.shuffle(getEngine().getRandom(), klondike.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        klondike.create(getEngine().getRandom());
        klondike.shuffle(getEngine().getRandom(), klondike.getHolder(Key.Deck));
        klondike.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        klondike.create(getEngine().getRandom());
        klondike.shuffle(getEngine().getRandom(), klondike.getHolder(Key.Deck));
        klondike.deal(getEngine().getTime());
        klondike.validate();
    }
    
    @Test
    public void renderTest() throws Exception
    {
        klondike.create(getEngine().getRandom());
        klondike.shuffle(getEngine().getRandom(), klondike.getHolder(Key.Deck));
        klondike.deal(getEngine().getTime());
        klondike.validate();
        klondike.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        klondike.create(getEngine().getRandom());
        klondike.shuffle(getEngine().getRandom(), klondike.getHolder(Key.Deck));
        klondike.deal(getEngine().getTime());
        klondike.validate();
        klondike.update(getEngine());
        klondike.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        klondike.setCreateComplete(true);
        klondike.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        klondike.setCreateComplete(true);
        assertTrue(klondike.isCreateComplete());
        klondike.setCreateComplete(false);
        assertFalse(klondike.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        klondike.setShuffleComplete(true);
        klondike.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        klondike.setShuffleComplete(true);
        assertTrue(klondike.isShuffleComplete());
        klondike.setShuffleComplete(false);
        assertFalse(klondike.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        klondike.setDealComplete(true);
        klondike.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        klondike.setDealComplete(true);
        assertTrue(klondike.isDealComplete());
        klondike.setDealComplete(false);
        assertFalse(klondike.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        klondike.setGameover(true);
        klondike.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        klondike.setGameover(true);
        assertTrue(klondike.hasGameover());
        klondike.setGameover(false);
        assertFalse(klondike.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        klondike.setWinner(true);
        klondike.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        klondike.setWinner(true);
        assertTrue(klondike.isWinner());
        klondike.setWinner(false);
        assertFalse(klondike.isWinner());
    }
}