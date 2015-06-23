package com.gamesbykevin.solitaire.solitaire.klondike;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Klondike Helper unit test
 * @author GOD
 */
public class KlondikeHelperTest 
{
    //default holder
    private Holder holder1, holder2;
    
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
        holder1 = new Holder(Holder.StackType.Same);
        holder2 = new Holder(Holder.StackType.Same);
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Klondike.Key.Playable1);
    }
    
    @After
    public void tearDown() 
    {
        
    }

    @Test
    public void assignCardsTest() throws Exception
    {
        final int x = 0;
        final int y = 0;
        final int x1 = 300;
        final int y1 = 300;
        
        card1.setLocation(x, y);
        card2.setLocation(x1, y1);
        
        //add cards to this holder
        holder1.add(card1);
        holder1.add(card2);
        
        KlondikeHelper.assignCards(holder2, holder1, Klondike.Key.Playable1, x, y);
        
        //assume the cards did not transfer because they are hidden
        assertTrue(holder2.isEmpty());
        assertTrue(holder1.getSize() == 2);
        
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //set location, and do not hide so we can select
        card1.setLocation(x, y);
        card1.setHidden(false);
        card2.setLocation(x1, y1);
        card2.setHidden(false);
        
        //add cards to this holder
        holder1.add(card1);
        
        //move the holder
        holder1.setLocation(x1, y1);
        holder1.add(card2);
        
        KlondikeHelper.assignCards(holder2, holder1, Klondike.Key.Playable1, x, y);
        
        //assume the cards did not transfer because they are hidden
        assertTrue(holder2.getSize() == 2);
        assertTrue(holder1.isEmpty());
        
        holder1.removeAll();
        holder2.removeAll();
        
        //set previous location
        card1.setLocation(x, y);
        
        //set new location
        card2.setLocation(x1, y1);
        
        //add card1 first, so we can test if only the child is found
        holder1.setLocation(x, y);
        holder1.add(card1);
        holder1.setLocation(x1, y1);
        holder1.add(card2);
        
        //sssign cards
        KlondikeHelper.assignCards(holder2, holder1, Klondike.Key.Playable1, x1, y1);
        
        //now verify that only the child card was added
        assertTrue(holder2.getSize() == 1);
        
        //assume this card is the one in holder 2
        assertTrue(holder2.hasCard(card2));
        
        //meaning that the other should have the 1 card remaining
        assertTrue(holder1.getSize() == 1);
        
        //assume this card is the one in holder 1
        assertTrue(holder1.hasCard(card1));
    }
    
    @Test
    public void canPlaceCardInDestinationTest() throws Exception
    {
        //first test where the suit does not match
        for (Suit suit : Suit.values())
        {
            for (Value value : Value.values())
            {
                //create card of specified suit/value
                card1 = new Card(suit, value, Back.Back1, Klondike.Key.Playable1);
                
                for (Suit suit1 : Suit.values())
                {
                    //don't check if the suits don't match
                    if (card1.hasSuit(suit1))
                        continue;
                    
                    for (Value value1 : Value.values())
                    {
                        //create card of specified suit/value
                        card2 = new Card(suit1, value1, Back.Back1, Klondike.Key.Playable1);
                        
                        //assume false just by the suits not matching
                        assertFalse(KlondikeHelper.canPlaceCardInDestination(card1, card2));
                    }
                }
            }
        }
        
        //now test where suits are the same
        for (Suit suit : Suit.values())
        {
            for (int index = 0; index < Value.values().length - 1; index++)
            {
                //set 1 card the next face value so they will be in order and succeed our test
                card1 = new Card(suit, Value.values()[index], Back.Back1, Klondike.Key.Playable1);
                card2 = new Card(suit, Value.values()[index + 1], Back.Back1, Klondike.Key.Playable1);
                
                //assume we can place
                assertTrue(KlondikeHelper.canPlaceCardInDestination(card1, card2));
            }
            
            for (int index = 0; index < Value.values().length - 1; index++)
            {
                //now set the first card to the next value (the reverse of above) to cause the test result to false
                card1 = new Card(suit, Value.values()[index + 1], Back.Back1, Klondike.Key.Playable1);
                card2 = new Card(suit, Value.values()[index], Back.Back1, Klondike.Key.Playable1);
                
                //assume we can place
                assertFalse(KlondikeHelper.canPlaceCardInDestination(card1, card2));
            }
        }
    }
    
    @Test
    public void canPlaceCardInPlayableTest() throws Exception
    {
        for (Suit suit : Suit.values())
        {
            switch (suit)
            {
                case Spades:
                case Clubs:
                    
                    //make the suits black
                    card1 = new Card(suit, Value.Ace, Back.Back1, Klondike.Key.Playable1);
                    card2 = new Card(Suit.Spades, Value.Jack, Back.Back1, Klondike.Key.Playable1);
                    assertFalse(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    card2 = new Card(Suit.Clubs, Value.Jack, Back.Back1, Klondike.Key.Playable1);
                    assertFalse(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    break;
                    
                case Hearts:
                case Diamonds:
                    
                    //make the suits red
                    card1 = new Card(suit, Value.Ace, Back.Back1, Klondike.Key.Playable1);
                    card2 = new Card(Suit.Hearts, Value.Jack, Back.Back1, Klondike.Key.Playable1);
                    assertFalse(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Klondike.Key.Playable1);
                    assertFalse(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    break;
            }
        }
        
        for (Suit suit : Suit.values())
        {
            switch (suit)
            {
                case Spades:
                case Clubs:
                    
                    //the suits are compatible, but the face value is off
                    card1 = new Card(suit, Value.Ace, Back.Back1, Klondike.Key.Playable1);
                    card2 = new Card(Suit.Hearts, Value.Jack, Back.Back1, Klondike.Key.Playable1);
                    assertFalse(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Klondike.Key.Playable1);
                    assertFalse(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    
                    //now that the suits are compatible, lets make the face value compatible
                    for (int index = 0; index < Value.values().length - 1; index++)
                    {
                        card1 = new Card(suit, Value.values()[index + 1], Back.Back1, Klondike.Key.Playable1);
                        card2 = new Card(Suit.Hearts, Value.values()[index], Back.Back1, Klondike.Key.Playable1);
                        
                        //assume this is valid
                        assertTrue(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                        
                        card1 = new Card(suit, Value.values()[index + 1], Back.Back1, Klondike.Key.Playable1);
                        card2 = new Card(Suit.Diamonds, Value.values()[index], Back.Back1, Klondike.Key.Playable1);
                        
                        //assume this is valid
                        assertTrue(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    }
                    break;
                    
                case Hearts:
                case Diamonds:
                    
                    //the suits are compatible, but the face value is off
                    card1 = new Card(suit, Value.Ace, Back.Back1, Klondike.Key.Playable1);
                    card2 = new Card(Suit.Clubs, Value.Jack, Back.Back1, Klondike.Key.Playable1);
                    assertFalse(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    card2 = new Card(Suit.Spades, Value.Jack, Back.Back1, Klondike.Key.Playable1);
                    assertFalse(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    
                    //now that the suits are compatible, lets make the face value compatible
                    for (int index = 0; index < Value.values().length - 1; index++)
                    {
                        card1 = new Card(suit, Value.values()[index + 1], Back.Back1, Klondike.Key.Playable1);
                        card2 = new Card(Suit.Clubs, Value.values()[index], Back.Back1, Klondike.Key.Playable1);
                        
                        //assume this is valid
                        assertTrue(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                        
                        card1 = new Card(suit, Value.values()[index + 1], Back.Back1, Klondike.Key.Playable1);
                        card2 = new Card(Suit.Spades, Value.values()[index], Back.Back1, Klondike.Key.Playable1);
                        
                        //assume this is valid
                        assertTrue(KlondikeHelper.canPlaceCardInPlayable(card1, card2));
                    }
                    break;
            }
        }
    }
    
    @Test
    public void placePlayableCardsTest() throws Exception
    {
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder1.add(card1);
        holder1.add(card2);
        
        //assume false because destination holder is empty and the source holder is not a king
        assertFalse(KlondikeHelper.placePlayableCards(holder1, holder2, Klondike.Key.Deck));
        
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.King, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder1.add(card1);
        holder1.add(card2);
        
        //assume true because destination holder is empty and the first card in the source holder is a king
        assertTrue(KlondikeHelper.placePlayableCards(holder1, holder2, Klondike.Key.Deck));
        
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder2.add(card1);
        holder1.add(card2);
        
        //assume true because destination holder is empty and the first card in the source holder is a king
        assertTrue(KlondikeHelper.placePlayableCards(holder1, holder2, Klondike.Key.Deck));
        
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Queen, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder2.add(card2);
        holder1.add(card1);
        
        //assume true because destination holder is empty and the first card in the source holder is a king
        assertFalse(KlondikeHelper.placePlayableCards(holder1, holder2, Klondike.Key.Deck));
    }
    
    @Test
    public void placeDestinationCardsTest() throws Exception
    {
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder1.add(card1);
        holder2.add(card2);
        
        //assume false because cards are different suits
        assertFalse(KlondikeHelper.placeDestinationCards(holder1, holder2, Klondike.Key.Deck));
        
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Three, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder1.add(card1);
        holder1.add(card2);
        
        //assume false because the destination is empty and the card placed is not an ace
        assertFalse(KlondikeHelper.placeDestinationCards(holder1, holder2, Klondike.Key.Deck));
        
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder1.add(card1);
        holder1.add(card2);
        
        //assume false because the destination is empty and the card placed is not an ace
        assertTrue(KlondikeHelper.placeDestinationCards(holder1, holder2, Klondike.Key.Deck));
        
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Clubs, Value.Two, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder1.add(card1);
        holder2.add(card2);
        
        //assume false because cards are not in the correct order
        assertFalse(KlondikeHelper.placeDestinationCards(holder1, holder2, Klondike.Key.Deck));
        
        //remove all cards
        holder1.removeAll();
        holder2.removeAll();
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Two, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Klondike.Key.Playable1);
        
        //source
        holder1.add(card1);
        holder2.add(card2);
        
        //assume true because cards are the same suit and the are not in the correct order
        assertTrue(KlondikeHelper.placeDestinationCards(holder1, holder2, Klondike.Key.Deck));
    }
    
    @Test
    public void showPlayableCardTest() throws Exception
    {
        //remove all cards
        holder1.removeAll();
        
        //create test cards and hide
        card1 = new Card(Suit.Clubs, Value.Two, Back.Back1, Klondike.Key.Playable1);
        card2 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Klondike.Key.Playable1);
        card1.setHidden(true);
        card2.setHidden(true);
        
        //add cards to holder
        holder1.add(card1);
        holder1.add(card2);
        
        KlondikeHelper.showPlayableCard(holder1);
        
        //assume the following
        assertTrue(holder1.getFirstCard().isHidden());
        assertFalse(holder1.getLastCard().isHidden());
    }
}