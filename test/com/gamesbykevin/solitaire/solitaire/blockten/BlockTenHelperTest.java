package com.gamesbykevin.solitaire.solitaire.blockten;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.card.Holder.StackType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Block Ten Helper unit test
 * @author GOD
 */
public final class BlockTenHelperTest 
{
    private Holder holder;
    
    private Card card1, card2;
    
    @BeforeClass
    public static void setUpClass() 
    {
        //assume true
        assertTrue(BlockTenHelper.GOAL_SCORE == 10);
        assertTrue(BlockTenHelper.SCORE_ACE == 1);
        assertTrue(BlockTenHelper.SCORE_TWO == 2);
        assertTrue(BlockTenHelper.SCORE_THREE == 3);
        assertTrue(BlockTenHelper.SCORE_FOUR == 4);
        assertTrue(BlockTenHelper.SCORE_FIVE == 5);
        assertTrue(BlockTenHelper.SCORE_SIX == 6);
        assertTrue(BlockTenHelper.SCORE_SEVEN == 7);
        assertTrue(BlockTenHelper.SCORE_EIGHT == 8);
        assertTrue(BlockTenHelper.SCORE_NINE == 9);
        assertTrue(BlockTenHelper.SCORE_TEN == 0);
        assertTrue(BlockTenHelper.SCORE_JACK == 5);
        assertTrue(BlockTenHelper.SCORE_QUEEN == 5);
        assertTrue(BlockTenHelper.SCORE_KING == 5);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() throws Exception
    {
        holder = new Holder(StackType.Same);
        
        card1 = new Card(Suit.Clubs, Value.Four, Back.Back13, null);
        card2 = new Card(Suit.Clubs, Value.Four, Back.Back13, null);
    }
    
    @After
    public void tearDown() 
    {
        holder.dispose();
        holder = null;
        
        card1.dispose();
        card1 = null;
        
        card2.dispose();
        card2 = null;
    }
    
    @Test
    public void getScoreTest() throws Exception
    {
        card1.setValue(Value.Ace);
        card2.setValue(Value.Nine);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertTrue(BlockTenHelper.getScore(holder) == BlockTenHelper.GOAL_SCORE);
        

        card1.setValue(Value.Two);
        card2.setValue(Value.Eight);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertTrue(BlockTenHelper.getScore(holder) == BlockTenHelper.GOAL_SCORE);
        
        
        card1.setValue(Value.Three);
        card2.setValue(Value.Seven);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertTrue(BlockTenHelper.getScore(holder) == BlockTenHelper.GOAL_SCORE);
        
        
        card1.setValue(Value.Four);
        card2.setValue(Value.Six);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertTrue(BlockTenHelper.getScore(holder) == BlockTenHelper.GOAL_SCORE);
        
        
        card1.setValue(Value.Five);
        card2.setValue(Value.Five);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertTrue(BlockTenHelper.getScore(holder) == BlockTenHelper.GOAL_SCORE);
        
        
        card1.setValue(Value.Jack);
        card2.setValue(Value.Queen);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertFalse(BlockTenHelper.getScore(holder) == BlockTenHelper.GOAL_SCORE);
        
        
        card1.setValue(Value.Queen);
        card2.setValue(Value.Queen);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertTrue(BlockTenHelper.getScore(holder) == BlockTenHelper.GOAL_SCORE);
        
        
        card1.setValue(Value.King);
        card2.setValue(Value.King);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertTrue(BlockTenHelper.getScore(holder) == BlockTenHelper.GOAL_SCORE);
    }
}