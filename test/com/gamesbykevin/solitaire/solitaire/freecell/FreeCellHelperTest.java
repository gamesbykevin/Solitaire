package com.gamesbykevin.solitaire.solitaire.freecell;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.solitaire.freecell.FreeCell.Key;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Free Cell Helper Unit Test
 * @author GOD
 */
public class FreeCellHelperTest 
{
    //default holder
    private Holder holder;
    
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
        //create holders
        holder = new Holder(Holder.StackType.Same);
        holder.setDimensions(Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT);
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Key.Playable1);
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
    public void hasSequenceTest() throws Exception
    {
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Key.Playable1);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true because the first card selected is the top card
        assertTrue(FreeCellHelper.hasSequence(holder, 0, 0));
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Key.Playable1);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //update location
        card1.setLocation(Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT);
        card2.setLocation(Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT + 100);
        
        //assume false because the cards aren't in order
        assertFalse(FreeCellHelper.hasSequence(holder, Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT + 1));
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Jack, Back.Back1, Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Ten, Back.Back1, Key.Playable1);
        
        holder.removeAll();
        holder.add(card1);
        holder.add(card2);
        
        //assume true
        assertTrue(FreeCellHelper.hasSequence(holder, 0, 0));
    }
}