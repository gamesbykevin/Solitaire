package com.gamesbykevin.solitaire.solitaire.littlespider;

import com.gamesbykevin.solitaire.shared.Shared;
import com.gamesbykevin.solitaire.solitaire.littlespider.LittleSpider.Key;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Little Spider unit test
 * @author GOD
 */
public final class LittleSpiderTest extends SolitaireTest
{
    private Solitaire littleSpider;
    
    public LittleSpiderTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        Solitaire littleSpider = new LittleSpider(Shared.INVISIBLE_PIXEL);
        
        //assume all holders have been added
        for (Key key : Key.values())
        {
            assertNotNull(littleSpider.getHolder(key));
        }
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Solitaire littleSpider = new LittleSpider(Shared.INVISIBLE_PIXEL);
        littleSpider.dispose();
        littleSpider = null;
        
        assertNull(littleSpider);
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //create new game
        littleSpider = new LittleSpider(Shared.INVISIBLE_PIXEL);
        
        //assume not null
        assertNotNull(littleSpider);
    }
    
    @After
    @Override
    public void tearDown() 
    {
        super.tearDown();
        
        littleSpider.dispose();
        littleSpider = null;
        
        assertNull(littleSpider);
    }
    
    @Test
    public void createTest() throws Exception
    {
        littleSpider.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        littleSpider.create(getEngine().getRandom());
        littleSpider.shuffle(getEngine().getRandom(), littleSpider.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        littleSpider.create(getEngine().getRandom());
        littleSpider.shuffle(getEngine().getRandom(), littleSpider.getHolder(Key.Deck));
        littleSpider.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        while (!littleSpider.isCreateComplete())
        {
            littleSpider.create(getEngine().getRandom());
        }
        
        while (!littleSpider.isShuffleComplete())
        {
            littleSpider.shuffle(getEngine().getRandom(), littleSpider.getHolder(Key.Deck));
        }
        
        while (!littleSpider.isDealComplete())
        {
            littleSpider.deal(getEngine().getTime());
        }
        
        //assume false because we haven't checked validate yet
        assertFalse(littleSpider.hasGameover());
        
        //validate
        littleSpider.validate();
        
        //remove all cards
        littleSpider.getHolder(Key.Playable1).removeAll();
        littleSpider.getHolder(Key.Playable2).removeAll();
        littleSpider.getHolder(Key.Playable3).removeAll();
        littleSpider.getHolder(Key.Playable4).removeAll();
        littleSpider.getHolder(Key.Playable5).removeAll();
        littleSpider.getHolder(Key.Playable6).removeAll();
        littleSpider.getHolder(Key.Playable7).removeAll();
        littleSpider.getHolder(Key.Playable8).removeAll();
        
        //validate again
        littleSpider.validate();
        
        //assume true because there are no cards
        assertTrue(littleSpider.hasGameover());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        littleSpider.create(getEngine().getRandom());
        littleSpider.shuffle(getEngine().getRandom(), littleSpider.getHolder(Key.Deck));
        littleSpider.deal(getEngine().getTime());
        littleSpider.validate();
        littleSpider.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        littleSpider.create(getEngine().getRandom());
        littleSpider.shuffle(getEngine().getRandom(), littleSpider.getHolder(Key.Deck));
        littleSpider.deal(getEngine().getTime());
        littleSpider.validate();
        littleSpider.update(getEngine());
        littleSpider.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        littleSpider.setCreateComplete(true);
        littleSpider.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        littleSpider.setCreateComplete(true);
        assertTrue(littleSpider.isCreateComplete());
        littleSpider.setCreateComplete(false);
        assertFalse(littleSpider.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        littleSpider.setShuffleComplete(true);
        littleSpider.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        littleSpider.setShuffleComplete(true);
        assertTrue(littleSpider.isShuffleComplete());
        littleSpider.setShuffleComplete(false);
        assertFalse(littleSpider.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        littleSpider.setDealComplete(true);
        littleSpider.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        littleSpider.setDealComplete(true);
        assertTrue(littleSpider.isDealComplete());
        littleSpider.setDealComplete(false);
        assertFalse(littleSpider.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        littleSpider.setGameover(true);
        littleSpider.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        littleSpider.setGameover(true);
        assertTrue(littleSpider.hasGameover());
        littleSpider.setGameover(false);
        assertFalse(littleSpider.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        littleSpider.setWinner(true);
        littleSpider.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        littleSpider.setWinner(true);
        assertTrue(littleSpider.isWinner());
        littleSpider.setWinner(false);
        assertFalse(littleSpider.isWinner());
    }
}