package com.gamesbykevin.solitaire.solitaire.golf;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.solitaire.golf.Golf.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Golf Helper unit test
 * @author GOD
 */
public class GolfHelperTest 
{
    //test cards
    private Card card1, card2;
    
    @BeforeClass
    public static void setUpClass() 
    {
        
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() throws Exception
    {
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Column1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Key.Column2);
    }
    
    @After
    public void tearDown() 
    {
        card1.dispose();
        card1 = null;
        
        card2.dispose();
        card2 = null;
    }
    
    @Test
    public void isNeighborTest() throws Exception
    {
        //suits don't matter, just change the face value
        card1.setValue(Value.Ace);
        card2.setValue(Value.Two);
        
        assertTrue(GolfHelper.isNeighbor(card1, card2));
        
        //suits don't matter, just change the face value
        card1.setValue(Value.Ace);
        card2.setValue(Value.King);
        
        assertTrue(GolfHelper.isNeighbor(card1, card2));
        
        //suits don't matter, just change the face value
        card1.setValue(Value.Five);
        card2.setValue(Value.Four);
        
        assertTrue(GolfHelper.isNeighbor(card1, card2));
        
        //suits don't matter, just change the face value
        card1.setValue(Value.Nine);
        card2.setValue(Value.Seven);
        
        assertFalse(GolfHelper.isNeighbor(card1, card2));
    }
}