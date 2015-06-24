package com.gamesbykevin.solitaire.solitaire.goodmeasure;

import com.gamesbykevin.solitaire.shared.Shared;
import com.gamesbykevin.solitaire.solitaire.goodmeasure.GoodMeasure;
import com.gamesbykevin.solitaire.solitaire.goodmeasure.GoodMeasure.Key;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Good Measure Solitaire unit test
 * @author GOD
 */
public class GoodMeasureTest extends SolitaireTest
{
    private Solitaire goodMeasure;
    
    public GoodMeasureTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        assertTrue(GoodMeasure.HOLDER_SIZE == 5);
        assertTrue(GoodMeasure.DEFAULT_HOLDER_SIZE == 1);
        
        Solitaire goodMeasure = new GoodMeasure(Shared.INVISIBLE_PIXEL);
        
        //assume all holders have been added
        for (Key key : Key.values())
        {
            assertNotNull(goodMeasure.getHolder(key));
        }
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Solitaire goodMeasure = new GoodMeasure(Shared.INVISIBLE_PIXEL);
        goodMeasure.dispose();
        goodMeasure = null;
        
        assertNull(goodMeasure);
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //create new solitaire game
        goodMeasure = new GoodMeasure(Shared.INVISIBLE_PIXEL);
        
        //assume not null
        assertNotNull(goodMeasure);
    }
    
    @After
    @Override
    public void tearDown()
    {
        super.tearDown();
        
        goodMeasure.dispose();
        goodMeasure = null;
    }
    
    @Test
    public void createTest() throws Exception
    {
        goodMeasure.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        goodMeasure.create(getEngine().getRandom());
        goodMeasure.shuffle(getEngine().getRandom(), goodMeasure.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        goodMeasure.create(getEngine().getRandom());
        goodMeasure.shuffle(getEngine().getRandom(), goodMeasure.getHolder(Key.Deck));
        goodMeasure.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        while (!goodMeasure.isCreateComplete())
        {
            goodMeasure.create(getEngine().getRandom());
        }
        
        while (!goodMeasure.isShuffleComplete())
        {
            goodMeasure.shuffle(getEngine().getRandom(), goodMeasure.getHolder(Key.Deck));
        }
        
        while (!goodMeasure.isDealComplete())
        {
            goodMeasure.deal(getEngine().getTime());
        }
        
        //assume false because we haven't checked validate yet
        assertFalse(goodMeasure.hasGameover());
        
        //validate
        goodMeasure.validate();
        
        //remove all cards
        goodMeasure.getHolder(Key.Playable1).removeAll();
        goodMeasure.getHolder(Key.Playable2).removeAll();
        goodMeasure.getHolder(Key.Playable3).removeAll();
        goodMeasure.getHolder(Key.Playable4).removeAll();
        goodMeasure.getHolder(Key.Playable5).removeAll();
        goodMeasure.getHolder(Key.Playable6).removeAll();
        goodMeasure.getHolder(Key.Playable7).removeAll();
        goodMeasure.getHolder(Key.Playable8).removeAll();
        goodMeasure.getHolder(Key.Playable9).removeAll();
        goodMeasure.getHolder(Key.Playable10).removeAll();
        
        //validate again
        goodMeasure.validate();
        
        //assume true because there are no cards
        assertTrue(goodMeasure.hasGameover());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        goodMeasure.create(getEngine().getRandom());
        goodMeasure.shuffle(getEngine().getRandom(), goodMeasure.getHolder(Key.Deck));
        goodMeasure.deal(getEngine().getTime());
        goodMeasure.validate();
        goodMeasure.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        goodMeasure.create(getEngine().getRandom());
        goodMeasure.shuffle(getEngine().getRandom(), goodMeasure.getHolder(Key.Deck));
        goodMeasure.deal(getEngine().getTime());
        goodMeasure.validate();
        goodMeasure.update(getEngine());
        goodMeasure.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        goodMeasure.setCreateComplete(true);
        goodMeasure.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        goodMeasure.setCreateComplete(true);
        assertTrue(goodMeasure.isCreateComplete());
        goodMeasure.setCreateComplete(false);
        assertFalse(goodMeasure.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        goodMeasure.setShuffleComplete(true);
        goodMeasure.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        goodMeasure.setShuffleComplete(true);
        assertTrue(goodMeasure.isShuffleComplete());
        goodMeasure.setShuffleComplete(false);
        assertFalse(goodMeasure.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        goodMeasure.setDealComplete(true);
        goodMeasure.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        goodMeasure.setDealComplete(true);
        assertTrue(goodMeasure.isDealComplete());
        goodMeasure.setDealComplete(false);
        assertFalse(goodMeasure.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        goodMeasure.setGameover(true);
        goodMeasure.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        goodMeasure.setGameover(true);
        assertTrue(goodMeasure.hasGameover());
        goodMeasure.setGameover(false);
        assertFalse(goodMeasure.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        goodMeasure.setWinner(true);
        goodMeasure.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        goodMeasure.setWinner(true);
        assertTrue(goodMeasure.isWinner());
        goodMeasure.setWinner(false);
        assertFalse(goodMeasure.isWinner());
    }
}