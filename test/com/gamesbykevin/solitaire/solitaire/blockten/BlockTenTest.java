package com.gamesbykevin.solitaire.solitaire.blockten;

import com.gamesbykevin.solitaire.solitaire.blockten.BlockTen.Key;
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
 * Block Ten unit test
 * @author GOD
 */
public final class BlockTenTest extends SolitaireTest
{
    private Solitaire blockTen;
    
    public BlockTenTest() 
    {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        //assume true
        assertTrue(BlockTen.COLS == 4);
        assertTrue(BlockTen.ROWS == 3);
        assertTrue(BlockTen.SELECTION_LIMIT == 2);
        
        Solitaire blockTen = new BlockTen(Shared.INVISIBLE_PIXEL);
        
        //assume all holders have been added
        for (Key key : Key.values())
        {
            assertNotNull(blockTen.getHolder(key));
        }
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Solitaire blockTen = new BlockTen(Shared.INVISIBLE_PIXEL);
        
        blockTen.dispose();
        blockTen = null;
        
        assertNull(blockTen);
    }
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        blockTen = new BlockTen(Shared.INVISIBLE_PIXEL);
        
        assertNotNull(blockTen);
    }
    
    @After
    @Override
    public void tearDown()
    {
        super.tearDown();
        
        blockTen.dispose();
        blockTen = null;
        
        assertNull(blockTen);
    }
    
    @Test
    public void createTest() throws Exception
    {
        blockTen.create(getEngine().getRandom());
    }
    
    @Test
    public void shuffleTest() throws Exception
    {
        blockTen.create(getEngine().getRandom());
        blockTen.shuffle(getEngine().getRandom(), blockTen.getHolder(Key.Deck));
    }
    
    @Test
    public void dealTest() throws Exception
    {
        blockTen.create(getEngine().getRandom());
        blockTen.shuffle(getEngine().getRandom(), blockTen.getHolder(Key.Deck));
        blockTen.deal(getEngine().getTime());
    }
    
    @Test
    public void validateTest() throws Exception
    {
        while (!blockTen.isCreateComplete())
        {
            blockTen.create(getEngine().getRandom());
        }
        
        while (!blockTen.isShuffleComplete())
        {
            blockTen.shuffle(getEngine().getRandom(), blockTen.getHolder(Key.Deck));
        }
        
        while (!blockTen.isDealComplete())
        {
            blockTen.deal(getEngine().getTime());
        }
        
        //assume false because we haven't checked validate yet
        assertFalse(blockTen.hasGameover());
        
        //validate
        blockTen.validate();
        
        //remove all cards
        blockTen.getHolder(Key.Deck).removeAll();
        
        //validate again
        blockTen.validate();
        
        //assume true because there are no cards
        assertTrue(blockTen.hasGameover());
    }
    
    @Test
    public void renderTest() throws Exception
    {
        blockTen.create(getEngine().getRandom());
        blockTen.shuffle(getEngine().getRandom(), blockTen.getHolder(Key.Deck));
        blockTen.deal(getEngine().getTime());
        blockTen.validate();
        blockTen.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void updateTest() throws Exception
    {
        blockTen.create(getEngine().getRandom());
        blockTen.shuffle(getEngine().getRandom(), blockTen.getHolder(Key.Deck));
        blockTen.deal(getEngine().getTime());
        blockTen.validate();
        blockTen.update(getEngine());
        blockTen.render(Shared.INVISIBLE_PIXEL.createGraphics());
    }
    
    @Test
    public void setCreateCompleteTest()
    {
        blockTen.setCreateComplete(true);
        blockTen.setCreateComplete(false);
    }

    @Test
    public void isCreateCompleteTest()
    {
        blockTen.setCreateComplete(true);
        assertTrue(blockTen.isCreateComplete());
        blockTen.setCreateComplete(false);
        assertFalse(blockTen.isCreateComplete());
    }
    
    @Test
    public void setShuffleCompleteTest()
    {
        blockTen.setShuffleComplete(true);
        blockTen.setShuffleComplete(false);
    }
    
    @Test
    public void isShuffleCompleteTest()
    {
        blockTen.setShuffleComplete(true);
        assertTrue(blockTen.isShuffleComplete());
        blockTen.setShuffleComplete(false);
        assertFalse(blockTen.isShuffleComplete());
    }
    
    @Test
    public void setDealCompleteTest()
    {
        blockTen.setDealComplete(true);
        blockTen.setDealComplete(false);
    }
    
    @Test
    public void isDealCompleteTest()
    {
        blockTen.setDealComplete(true);
        assertTrue(blockTen.isDealComplete());
        blockTen.setDealComplete(false);
        assertFalse(blockTen.isDealComplete());
    }
        
    @Test
    public void setGameoverTest()
    {
        blockTen.setGameover(true);
        blockTen.setGameover(false);
    }
    
    @Test
    public void hasGameoverTest()
    {
        blockTen.setGameover(true);
        assertTrue(blockTen.hasGameover());
        blockTen.setGameover(false);
        assertFalse(blockTen.hasGameover());
    }
    
    @Test
    public void setWinnerTest()
    {
        blockTen.setWinner(true);
        blockTen.setWinner(false);
    }
    
    @Test
    public void isWinnerTest()
    {
        blockTen.setWinner(true);
        assertTrue(blockTen.isWinner());
        blockTen.setWinner(false);
        assertFalse(blockTen.isWinner());
    }
}