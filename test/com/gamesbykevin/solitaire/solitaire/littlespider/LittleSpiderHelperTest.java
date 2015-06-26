package com.gamesbykevin.solitaire.solitaire.littlespider;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Little Spider Helper Unit Test
 * @author GOD
 */
public final class LittleSpiderHelperTest 
{
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
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Hearts, Value.Six, Back.Back1, null);
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
    public void canPlaceCardInDestinationTest() throws Exception
    {
        for (Suit suit1 : Suit.values())
        {
            for (Value value1 : Value.values())
            {
                //create new card
                card1 = new Card(suit1, value1, Back.Back1, null);

                for (Suit suit2 : Suit.values())
                {
                    //don't check the same suit
                    if (suit1 == suit2)
                        continue;
                    
                    for (Value value2 : Value.values())
                    {
                        //don't check the same face value
                        if (value1 == value2)
                            continue;
                        
                        //create new card
                        card2 = new Card(suit2, value2, Back.Back1, null);
                        
                        assertFalse(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
                    }
                }
            }
        }
        
        
        card1 = new Card(Suit.Clubs, Value.Two, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Three, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Two, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Four, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Three, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Five, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Four, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Six, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Five, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Seven, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Six, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Eight, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Seven, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Nine, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Eight, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Ten, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Nine, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Jack, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Ten, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.Queen, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Jack, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, null);
        card2 = new Card(Suit.Clubs, Value.Queen, Back.Back1, null);
        assertTrue(LittleSpiderHelper.canPlaceCardInDestination(card1, card2));
    }
}