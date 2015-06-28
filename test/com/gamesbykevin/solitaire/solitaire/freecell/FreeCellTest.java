package com.gamesbykevin.solitaire.solitaire.freecell;


import com.gamesbykevin.solitaire.solitaire.freecell.FreeCell.Key;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.SolitaireTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Free Cell unit test
 * @author GOD
 */
public final class FreeCellTest extends SolitaireTest
{
    private Solitaire freeCell;
    
    public FreeCellTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        assertTrue(FreeCell.TEMP_HOLDER_LIMIT == 1);
        
        Solitaire freeCell = new FreeCell(TEST_IMAGE);
        
        //assume all holders have been added
        for (Key key : Key.values())
        {
            assertNotNull(freeCell.getHolder(key));
        }
        
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        Solitaire freeCell = new FreeCell(TEST_IMAGE);
        freeCell.dispose();
        freeCell = null;
        
        //assume null
        assertNull(freeCell);
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        //create new game
        freeCell = new FreeCell(TEST_IMAGE);
        
        //assume not null
        assertNotNull(freeCell);
    }
    
    @After
    @Override
    public void tearDown() 
    {
        super.tearDown();
        
        //recycle object
        freeCell.dispose();
        freeCell = null;
        
        //assume null
        assertNull(freeCell);
    }
    
    @Test
    public void createTest() throws Exception
    {
        freeCell.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        freeCell.create(getEngine().getRandom());
        freeCell.shuffle(getEngine().getRandom(), freeCell.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        freeCell.create(getEngine().getRandom());
        freeCell.shuffle(getEngine().getRandom(), freeCell.getHolder(Key.Deck));
        freeCell.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        while (!freeCell.isCreateComplete())
        {
            freeCell.create(getEngine().getRandom());
        }
        
        while (!freeCell.isShuffleComplete())
        {
            freeCell.shuffle(getEngine().getRandom(), freeCell.getHolder(Key.Deck));
        }
        
        while (!freeCell.isDealComplete())
        {
            freeCell.deal(getEngine().getTime());
        }
        
        //assume false because we haven't checked validate yet
        assertFalse(freeCell.hasGameover());
        
        //validate
        freeCell.validate();
        
        //remove all cards
        freeCell.getHolder(Key.Deck).removeAll();
        freeCell.getHolder(Key.Temp1).removeAll();
        freeCell.getHolder(Key.Temp2).removeAll();
        freeCell.getHolder(Key.Temp3).removeAll();
        freeCell.getHolder(Key.Temp4).removeAll();
        freeCell.getHolder(Key.Playable1).removeAll();
        freeCell.getHolder(Key.Playable2).removeAll();
        freeCell.getHolder(Key.Playable3).removeAll();
        freeCell.getHolder(Key.Playable4).removeAll();
        freeCell.getHolder(Key.Playable5).removeAll();
        freeCell.getHolder(Key.Playable6).removeAll();
        freeCell.getHolder(Key.Playable7).removeAll();
        freeCell.getHolder(Key.Playable8).removeAll();
        
        //validate again
        freeCell.validate();
        
        //assume true because there are no cards
        assertTrue(freeCell.hasGameover());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        freeCell.create(getEngine().getRandom());
        freeCell.shuffle(getEngine().getRandom(), freeCell.getHolder(Key.Deck));
        freeCell.deal(getEngine().getTime());
        freeCell.validate();
        freeCell.render(TEST_IMAGE.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        freeCell.create(getEngine().getRandom());
        freeCell.shuffle(getEngine().getRandom(), freeCell.getHolder(Key.Deck));
        freeCell.deal(getEngine().getTime());
        freeCell.validate();
        freeCell.update(getEngine());
        freeCell.render(TEST_IMAGE.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        freeCell.setCreateComplete(true);
        freeCell.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        freeCell.setCreateComplete(true);
        assertTrue(freeCell.isCreateComplete());
        freeCell.setCreateComplete(false);
        assertFalse(freeCell.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        freeCell.setShuffleComplete(true);
        freeCell.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        freeCell.setShuffleComplete(true);
        assertTrue(freeCell.isShuffleComplete());
        freeCell.setShuffleComplete(false);
        assertFalse(freeCell.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        freeCell.setDealComplete(true);
        freeCell.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        freeCell.setDealComplete(true);
        assertTrue(freeCell.isDealComplete());
        freeCell.setDealComplete(false);
        assertFalse(freeCell.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        freeCell.setGameover(true);
        freeCell.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        freeCell.setGameover(true);
        assertTrue(freeCell.hasGameover());
        freeCell.setGameover(false);
        assertFalse(freeCell.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        freeCell.setWinner(true);
        freeCell.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        freeCell.setWinner(true);
        assertTrue(freeCell.isWinner());
        freeCell.setWinner(false);
        assertFalse(freeCell.isWinner());
    }
}