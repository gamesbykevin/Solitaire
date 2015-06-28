package com.gamesbykevin.solitaire.solitaire.yukon;


import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;
import com.gamesbykevin.solitaire.solitaire.yukon.Yukon;
import com.gamesbykevin.solitaire.solitaire.yukon.Yukon.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Yukon unit test
 * @author GOD
 */
public final class YukonTest extends SolitaireTest
{
    private Solitaire yukon;
    
    public YukonTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        Solitaire yukon = new Yukon(TEST_IMAGE);
        
        //assume all holders have been added
        for (Key key : Key.values())
        {
            assertNotNull(yukon.getHolder(key));
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Solitaire yukon = new Yukon(TEST_IMAGE);
        yukon.dispose();
        yukon = null;
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //create new object
        yukon = new Yukon(TEST_IMAGE);
        
        //object has now been created
        assertNotNull(yukon);
    }
    
    @After
    @Override
    public void tearDown() 
    {
        super.tearDown();
        
        yukon.dispose();
        yukon = null;
    }
    
    @Test
    public void createTest() throws Exception
    {
        yukon.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        yukon.create(getEngine().getRandom());
        yukon.shuffle(getEngine().getRandom(), yukon.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        yukon.create(getEngine().getRandom());
        yukon.shuffle(getEngine().getRandom(), yukon.getHolder(Key.Deck));
        yukon.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        while (!yukon.isCreateComplete())
        {
            yukon.create(getEngine().getRandom());
        }
        
        while (!yukon.isShuffleComplete())
        {
            yukon.shuffle(getEngine().getRandom(), yukon.getHolder(Key.Deck));
        }
        
        while (!yukon.isDealComplete())
        {
            yukon.deal(getEngine().getTime());
        }
        
        //assume false because we haven't checked validate yet
        assertFalse(yukon.hasGameover());
        
        //remove all cards
        for (Key key : Key.values())
        {
            yukon.getHolder(key).removeAll();
        }
        
        //validate
        yukon.validate();
        
        //assume true because there are no cards
        assertTrue(yukon.hasGameover());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        yukon.create(getEngine().getRandom());
        yukon.shuffle(getEngine().getRandom(), yukon.getHolder(Key.Deck));
        yukon.deal(getEngine().getTime());
        yukon.validate();
        yukon.render(TEST_IMAGE.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        yukon.create(getEngine().getRandom());
        yukon.shuffle(getEngine().getRandom(), yukon.getHolder(Key.Deck));
        yukon.deal(getEngine().getTime());
        yukon.validate();
        yukon.update(getEngine());
        yukon.render(TEST_IMAGE.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        yukon.setCreateComplete(true);
        yukon.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        yukon.setCreateComplete(true);
        assertTrue(yukon.isCreateComplete());
        yukon.setCreateComplete(false);
        assertFalse(yukon.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        yukon.setShuffleComplete(true);
        yukon.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        yukon.setShuffleComplete(true);
        assertTrue(yukon.isShuffleComplete());
        yukon.setShuffleComplete(false);
        assertFalse(yukon.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        yukon.setDealComplete(true);
        yukon.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        yukon.setDealComplete(true);
        assertTrue(yukon.isDealComplete());
        yukon.setDealComplete(false);
        assertFalse(yukon.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        yukon.setGameover(true);
        yukon.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        yukon.setGameover(true);
        assertTrue(yukon.hasGameover());
        yukon.setGameover(false);
        assertFalse(yukon.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        yukon.setWinner(true);
        yukon.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        yukon.setWinner(true);
        assertTrue(yukon.isWinner());
        yukon.setWinner(false);
        assertFalse(yukon.isWinner());
    }
}