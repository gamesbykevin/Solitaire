package com.gamesbykevin.solitaire.solitaire.poker;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Poker Helper unit test
 * @author GOD
 */
public final class PokerHelperTest 
{
    //test cards
    private Card card1, card2, card3, card4, card5;
    
    @BeforeClass
    public static void setUpClass() 
    {
        assertTrue(PokerHelper.SCORE_ROYAL_FLUSH == 4000);
        assertTrue(PokerHelper.SCORE_STRAIGHT_FLUSH == 3000);
        assertTrue(PokerHelper.SCORE_FOUR_OF_KIND == 1600);
        assertTrue(PokerHelper.SCORE_STRAIGHT == 1200);
        assertTrue(PokerHelper.SCORE_FULL_HOUSE == 1000);
        assertTrue(PokerHelper.SCORE_THREE_OF_KIND == 600);
        assertTrue(PokerHelper.SCORE_FLUSH == 500);
        assertTrue(PokerHelper.SCORE_TWO_PAIR == 300);
        assertTrue(PokerHelper.SCORE_ONE_PAIR == 100);
        
        assertTrue(PokerHelper.FOUR_OF_KIND_MATCH == 3);
        assertTrue(PokerHelper.THREE_OF_KIND_MATCH == 2);
        assertTrue(PokerHelper.PAIR_MATCH == 1);
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
        card3 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
    }
    
    @After
    public void tearDown() 
    {
        card1.dispose();
        card1 = null;
        card2.dispose();
        card2 = null;
        card3.dispose();
        card3 = null;
        card4.dispose();
        card4 = null;
        card5.dispose();
        card5 = null;
    }
    
    @Test
    public void isRoyalFlushTest() throws Exception
    {
        //create test cards where a suit is not matching
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //one suit isn't matching so false
        assertFalse(PokerHelper.isRoyalFlush(card1, card2, card3, card4, card5));
        
        //create test cards where all match and highest value
        card1 = new Card(Suit.Diamonds, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume true
        assertTrue(PokerHelper.isRoyalFlush(card1, card2, card3, card4, card5));
        
        //create test cards where all match and in order, but missing Ace
        card1 = new Card(Suit.Diamonds, Value.Nine, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false because royal flush has to be the highest ranked cards of a matching suit
        assertFalse(PokerHelper.isRoyalFlush(card1, card2, card3, card4, card5));
    }
    
    @Test
    public void isStraightFlushTest() throws Exception
    {
        //create test cards where a suit is not matching
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //one suit isn't matching so false
        assertFalse(PokerHelper.isStraightFlush(card1, card2, card3, card4, card5));
        
        //create test cards where all match and in order
        card1 = new Card(Suit.Diamonds, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume true
        assertTrue(PokerHelper.isStraightFlush(card1, card2, card3, card4, card5));
        
        //create test cards where all match and in order
        card1 = new Card(Suit.Diamonds, Value.Nine, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume true
        assertTrue(PokerHelper.isStraightFlush(card1, card2, card3, card4, card5));
        
        //create test cards where all match, but one is not in direct order
        card1 = new Card(Suit.Diamonds, Value.Nine, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Two, Back.Back1, null);
        
        //assume false because cards are not in direct order
        assertFalse(PokerHelper.isStraightFlush(card1, card2, card3, card4, card5));
    }
    
    @Test
    public void isFourOfKindTest() throws Exception
    {
        //create test cards where only 2 match
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //only 2 cards match, so false
        assertFalse(PokerHelper.isFourOfKind(card1, card2, card3, card4, card5));
        
        //create test cards where only 3 match
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Ace, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //only 3 cards match, so false
        assertFalse(PokerHelper.isFourOfKind(card1, card2, card3, card4, card5));
        
        //create test cards where 4 cards match
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Ace, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.Ace, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume true because 4 cards match
        assertTrue(PokerHelper.isFourOfKind(card1, card2, card3, card4, card5));
    }
    
    @Test
    public void isStraightTest() throws Exception
    {
        //create test cards where there isn't the correct order
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //not in order, so false
        assertFalse(PokerHelper.isStraight(card1, card2, card3, card4, card5));
        
        //create test cards where are in direct order (suit doesn't matter)
        card1 = new Card(Suit.Clubs, Value.Five, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Six, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Three, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.Four, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Two, Back.Back1, null);
        
        //card face value rank is in order, so true
        assertTrue(PokerHelper.isStraight(card1, card2, card3, card4, card5));

        //create test cards where one card is not in direct order
        card1 = new Card(Suit.Clubs, Value.Five, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Seven, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Three, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.Four, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Two, Back.Back1, null);
        
        //one card is not in direct order, so false
        assertFalse(PokerHelper.isStraight(card1, card2, card3, card4, card5));
    }
    
    @Test
    public void isFullHouseTest() throws Exception
    {
        //create test cards where there isn't a full house
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isFullHouse(card1, card2, card3, card4, card5));
        
        //create test cards for a two pair
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Ten, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isFullHouse(card1, card2, card3, card4, card5));
        
        //create test cards for a 3 of a kind and two pair, so the result will be true
        card1 = new Card(Suit.Clubs, Value.Five, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Four, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Four, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.Five, Back.Back1, null);
        card5 = new Card(Suit.Hearts, Value.Five, Back.Back1, null);
        
        //assume true
        assertTrue(PokerHelper.isFullHouse(card1, card2, card3, card4, card5));
    }
    
    @Test
    public void isThreeOfKindTest() throws Exception
    {
        //create test cards where there isn't 3 of a kind
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isThreeOfKind(card1, card2, card3, card4, card5));
        
        //create test cards where there isn't 3 of a kind
        card1 = new Card(Suit.Clubs, Value.Nine, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Ten, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.Nine, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isThreeOfKind(card1, card2, card3, card4, card5));
        
        //create test cards where there is 3 of a kind
        card1 = new Card(Suit.Clubs, Value.Nine, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Nine, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Ten, Back.Back1, null);
        card4 = new Card(Suit.Clubs, Value.Nine, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ace, Back.Back1, null);
        
        //assume true
        assertTrue(PokerHelper.isThreeOfKind(card1, card2, card3, card4, card5));
    }
    
    @Test
    public void isFlushTest() throws Exception
    {
        //create test cards where not all suits match
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isFlush(card1, card2, card3, card4, card5));
        
        //create test cards where 1 suit doesn't match
        card1 = new Card(Suit.Clubs, Value.Three, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isFlush(card1, card2, card3, card4, card5));
        
        //create test cards where all suits match
        card1 = new Card(Suit.Diamonds, Value.Three, Back.Back1, null);
        card2 = new Card(Suit.Diamonds, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume true
        assertTrue(PokerHelper.isFlush(card1, card2, card3, card4, card5));
    }
    
    @Test
    public void isTwoPairTest() throws Exception
    {
        //create test cards where there is only 1 pair
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isTwoPair(card1, card2, card3, card4, card5));
        
        //create test cards where there is only 3 of a kind
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.King, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isTwoPair(card1, card2, card3, card4, card5));
        
        //create test cards where there is only 4 of a kind
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.King, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.King, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isTwoPair(card1, card2, card3, card4, card5));
        
        //create test cards where there is 2 pair
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.King, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Jack, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume true
        assertTrue(PokerHelper.isTwoPair(card1, card2, card3, card4, card5));
    }
    
    @Test
    public void isOnePairTest() throws Exception
    {
        //create test cards where there is only 3 of a kind
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.King, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isOnePair(card1, card2, card3, card4, card5));
        
        //create test cards where there is only 4 of a kind
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.King, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.King, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume false
        assertFalse(PokerHelper.isOnePair(card1, card2, card3, card4, card5));
        
        //create test cards where there is 1 pair
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        card3 = new Card(Suit.Hearts, Value.Queen, Back.Back1, null);
        card4 = new Card(Suit.Diamonds, Value.King, Back.Back1, null);
        card5 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, null);
        
        //assume true
        assertTrue(PokerHelper.isOnePair(card1, card2, card3, card4, card5));
    }
}