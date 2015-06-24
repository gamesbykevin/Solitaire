package com.gamesbykevin.solitaire.solitaire.goodmeasure;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.Back;
import com.gamesbykevin.solitaire.card.Card.Suit;
import com.gamesbykevin.solitaire.card.Card.Value;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Good Measure Helper unit test
 * @author GOD
 */
public final class GoodMeasureHelperTest 
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
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
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
    public void hasOrderTest() throws Exception
    {
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Two, Back.Back1, null);
        
        //assume false
        assertFalse(GoodMeasureHelper.hasOrder(card1, card2));
        
        //assume true
        assertTrue(GoodMeasureHelper.hasOrder(card2, card1));
    }
}