package com.gamesbykevin.solitaire.card;

import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import static com.gamesbykevin.solitaire.solitaire.SolitaireTest.TEST_IMAGE;

import java.awt.Graphics;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Holder unit test
 * @author GOD
 */
public class HolderTest 
{
    private Holder holder;
    
    //test card
    private Card card;
        
    //default key to use
    private static final String DEFAULT_KEY = "Key";
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        for (StackType type : StackType.values())
        {
            Holder holder = new Holder(type);
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        for (StackType type : StackType.values())
        {
            Holder holder = new Holder(type);
            holder.dispose();
            holder = null;
        }
        
    }
    
    @Before
    public void setUp() throws Exception
    {
        holder = new Holder(StackType.Same);
        
        //create test card
        card = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
    }
    
    @After
    public void tearDown()
    {
        holder.dispose();
        holder = null;
    }
    
    @Test
    public void hasLocationTest() throws Exception
    {
        holder = new Holder(StackType.Same);
        holder.setDimensions(Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT);
        
        assertTrue(holder.hasLocation(0, 0));
        
        final int x = 100;
        final int y = 88;
        
        holder.setLocation(x, y);
        
        assertTrue(holder.hasLocation(x, y));
    }
    
    @Test
    public void addTest() throws Exception
    {
        Card card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        
        holder = new Holder(StackType.Same);
        holder.add(card1);
        
        Card card2 = new Card(Suit.Diamonds, Value.Ace, Back.Back1, null);
        holder.add(card2);
        
        assertFalse(holder.isEmpty());
        assertTrue(holder.getSize() == 2);
        
        //add the other holders cards to this one
        Holder holder2 = new Holder(StackType.Same);
        holder2.add(holder);
        
        assertTrue(holder.isEmpty());
        assertTrue(holder.getSize() == 0);
        
        assertFalse(holder2.isEmpty());
        assertTrue(holder2.getSize() == 2);
    }
    
    @Test
    public void setStartTest() throws Exception
    {
        holder = new Holder(StackType.Same);
        holder.setStart();
        
        holder.add(card);
        holder.setStart();
    }
    
    @Test
    public void setTest() throws Exception
    {
        Card card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        Card card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        
        holder = new Holder(StackType.Same);
        holder.add(card1);
        
        //assume the cards are the same
        assertTrue(holder.getCard(0).getId() == card1.getId());
        
        //now set the new card
        holder.set(0, card2);
        
        //assume the new cards are the same
        assertTrue(holder.getCard(0).getId() == card2.getId());
    }
    
    @Test
    public void getDestinationPointTest() throws Exception
    {
        for (StackType type : StackType.values())
        {
            //create a new holder
            holder = new Holder(type);

            //since there are no cards, the point should be (0,0)
            assertTrue(holder.getDestinationPoint().x == 0);
            assertTrue(holder.getDestinationPoint().y == 0);
            
            //since there are no cards, the point should be (0,0)
            assertTrue(holder.getDestinationPoint(4).x == 0);
            assertTrue(holder.getDestinationPoint(3).y == 0);
        }
        
        //horizontal will have the same y, but different x since at least 1 card exists
        holder = new Holder(StackType.Horizontal);
        holder.setDimensions(Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT);        
        
        holder.add(card);
        assertTrue(holder.getDestinationPoint().x > holder.getX());
        assertTrue(holder.getDestinationPoint().y == holder.getY());
        
        //vertical will have the same x, but different y since at least 1 card exists
        holder = new Holder(StackType.Vertical);
        holder.setDimensions(Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT);
        
        holder.add(card);
        assertTrue(holder.getDestinationPoint().x == holder.getX());
        assertTrue(holder.getDestinationPoint().y > holder.getY());
    }
    
    @Test
    public void removeTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        
        //add card to holder
        holder.add(card);
        
        //assume the holder is not empty
        assertFalse(holder.isEmpty());
        
        //now remove the card
        holder.remove(card);
        
        //now assume the holder is empty
        assertTrue(holder.isEmpty());
    }
    
    @Test
    public void removeAllTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        
        //add card to holder
        holder.add(card);
        
        Card card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        Card card2 = new Card(Suit.Spades, Value.Ace, Back.Back1, null);
        
        //add more cards
        holder.add(card1);
        holder.add(card2);
        
        //assume the holder is not empty
        assertFalse(holder.isEmpty());
        
        //remove all cards
        holder.removeAll();
        
        //now assume all cards are gone
        assertTrue(holder.isEmpty());
    }
    
    @Test
    public void hasCardTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        holder.setDimensions(Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT);
        
        //no cards have been added yet
        assertFalse(holder.hasCard(card));
        
        final int x1 = 10;
        final int y1 = 10;
        
        //we have no cards so this should be false
        assertFalse(holder.hasCard(x1, y1));
        
        //now add card
        card.setLocation(0, 0);
        holder.add(card);
        
        //assume the card itself is here, regardless of coordinates
        assertTrue(holder.hasCard(card));
        
        //assume card is there
        assertTrue(holder.hasCard(x1, y1));
        
        final int x2 = 1000;
        final int y2 = 1000;
        
        //assume coordinates are out of card range
        assertFalse(holder.hasCard(x2, y2));
    }
    
    @Test
    public void getCardTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        holder.setDimensions(Card.ORIGINAL_CARD_WIDTH, Card.ORIGINAL_CARD_HEIGHT);
        assertNull(holder.getCard(0, 0));
        assertNull(holder.getCard(0));
        
        final int x1 = 10;
        final int y1 = 10;
        
        card.setLocation(0, 0);
        holder.add(card);
        assertNotNull(holder.getCard(x1, y1));
        assertNotNull(holder.getCard(0));
        assertNull(holder.getCard(1));
    }
    
    @Test
    public void setDestinationTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        
        //set the destination as holder 2
        Holder holder2 = new Holder(StackType.Same);
        holder2.setLocation(500, 197);
        holder.setDestination(holder2, DEFAULT_KEY);
        
        //add a card
        holder.add(card);
        holder.setDestination(holder2, DEFAULT_KEY);
    }
    
    @Test
    public void hasDestinationTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        
        //assume at destination even though no cards exist
        assertTrue(holder.hasDestination());
        
        final int x = 100;
        final int y = 250;
        
        //create another holder
        Holder holder2 = new Holder(StackType.Same);
        
        //update the location
        holder2.setLocation(x, y);
        
        //add card to holder
        holder.add(card);
        
        //now set the destination
        holder.setDestination(holder2, DEFAULT_KEY);
        
        //we don't have the new holder location
        assertFalse(holder.hasDestination());
        
        //now update the card location to the holder's
        holder.getCard(0).setLocation(x, y);
        
        //assume true now
        assertTrue(holder.hasDestination());
    }
    
    @Test
    public void moveTowardsDestinationTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        
        final int x = 100;
        final int y = 250;
        
        //create another holder
        Holder holder2 = new Holder(StackType.Same);
        
        //update the location
        holder2.setLocation(x, y);
        
        //add card to holder
        holder.add(card);
        
        //now set the destination
        holder.setDestination(holder2, DEFAULT_KEY);
        
        //assume we are not there yet
        assertFalse(holder.hasDestination());
        
        //update a small little
        holder.moveTowardsDestination(1);
        
        //assume we still are not there yet
        assertFalse(holder.hasDestination());
        
        //now update to make the timer complete
        holder.moveTowardsDestination(Card.MOVE_CARD_DURATION);
        
        //now we have made it
        assertTrue(holder.hasDestination());
    }
    
    @Test
    public void isEmptyTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        
        //assume it is empty
        assertTrue(holder.isEmpty());
        
        //add card
        holder.add(card);
        
        //assume it is not empty
        assertFalse(holder.isEmpty());
        
        //remove card
        holder.remove(card);
        
        //assume it is empty again
        assertTrue(holder.isEmpty());
    }
    
    @Test
    public void updateLocationTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        
        final int x = 100;
        final int y = 250;
        
        //assume at origin
        assertTrue(holder.getX() == 0);
        assertTrue(holder.getY() == 0);
        
        //update location
        holder.updateLocation(x, y);
        
        //assume at new location
        assertTrue(holder.getX() == x);
        assertTrue(holder.getY() == y);
    }
    
    @Test
    public void renderTest() throws Exception
    {
        //create our holder
        holder = new Holder(StackType.Vertical);
        
        //need dimensions before render
        holder.setDimensions(100, 100);
        
        Graphics graphics = TEST_IMAGE.createGraphics();
        
        //render without having a card
        holder.render(graphics, TEST_IMAGE);
        holder.render(graphics, TEST_IMAGE, true);
        holder.render(graphics, TEST_IMAGE, false);
        
        //now add card
        holder.add(card);
        
        //render with a card
        holder.render(graphics, TEST_IMAGE);
        holder.render(graphics, TEST_IMAGE, true);
        holder.render(graphics, TEST_IMAGE, false);
    }
    
    @Test
    public void hasSourceHolderKeyTest() throws Exception
    {
        holder = new Holder(StackType.Same);
        
        //create test card
        card = new Card(Suit.Clubs, Value.Ace, Back.Back1, DEFAULT_KEY);
        
        //add card
        holder.add(card);
        
        //assume we have the holder key
        assertTrue(holder.hasSourceHolderKey(DEFAULT_KEY));
        
        //remove all cards
        holder.removeAll();
        
        //assume we won't have the holder key since the holder is empty
        assertFalse(holder.hasSourceHolderKey(DEFAULT_KEY));
        
        
        //remove all cards
        holder.removeAll();
        
        //create test card
        card = new Card(Suit.Clubs, Value.Ace, Back.Back1, null);
        
        //add card
        holder.add(card);
        
        //assume we won't have the holder key
        assertFalse(holder.hasSourceHolderKey(DEFAULT_KEY));
    }
    
    @Test
    public void hasValueTest() throws Exception
    {
        //create a new holder
        holder = new Holder(StackType.Same);
        
        //create test card
        card = new Card(Suit.Clubs, Value.Ace, Back.Back1, DEFAULT_KEY);
        
        //add card to holder
        holder.add(card);
        
        //assume true
        assertTrue(holder.hasValue(Value.Ace));
        
        //assume not true
        assertFalse(holder.hasValue(Value.Two));
        
        //suit doesn't count, but just check (in case)
        for (Suit suit : Suit.values())
        {
            for (Value value : Value.values())
            {
                //remove all cards
                holder.removeAll();

                //create test card
                card = new Card(suit, value, Back.Back1, DEFAULT_KEY);

                //add card to holder
                holder.add(card);

                //assume true
                assertTrue(holder.hasValue(value));
            }
        }
    }
    
    @Test
    public void hasSuitTest() throws Exception
    {
        //create a new holder
        holder = new Holder(StackType.Same);
        
        //create test card
        card = new Card(Suit.Clubs, Value.Ace, Back.Back1, DEFAULT_KEY);
        
        //add card to holder
        holder.add(card);
        
        //assume true
        assertTrue(holder.hasSuit(Suit.Clubs));
        
        //assume not true
        assertFalse(holder.hasSuit(Suit.Hearts));
        
        //suit doesn't count, but just check (in case)
        for (Suit suit : Suit.values())
        {
            for (Value value : Value.values())
            {
                //remove all cards
                holder.removeAll();

                //create test card
                card = new Card(suit, value, Back.Back1, DEFAULT_KEY);

                //add card to holder
                holder.add(card);

                //assume true
                assertTrue(holder.hasSuit(suit));
            }
        }
    }
}