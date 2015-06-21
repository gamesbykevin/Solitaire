package com.gamesbykevin.solitaire.card;

import com.gamesbykevin.solitaire.card.Card.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Card unit test
 * @author GOD
 */
public class CardTest 
{
    //test card
    private Card card;
    
    //default key to use
    private static final String DEFAULT_KEY = "Key";
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //check each card back
        for (Back back : Back.values())
        {
            //check each suit
            for (Suit suit : Suit.values())
            {
                //check each face value
                for (Value value : Value.values())
                {
                    //create new card
                    Card card = new Card(suit, value, back, DEFAULT_KEY);
                    
                    assertEquals(card.getSourceHolderKey(), DEFAULT_KEY);
                    assertTrue(card.hasSuit(suit));
                    assertTrue(card.hasValue(value));
                    assertNotNull(card.getSourceHolderKey());
                    assertTrue(card.hasAnimation(Mode.Face));
                    assertTrue(card.hasAnimation(Mode.Hide));
                }
            }
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception
    {
        //check each card back
        for (Back back : Back.values())
        {
            //check each suit
            for (Suit suit : Suit.values())
            {
                //check each face value
                for (Value value : Value.values())
                {
                    //create new card
                    Card card = new Card(suit, value, back, DEFAULT_KEY);
                    card.dispose();
                    card = null;
                }
            }
        }
    }
    
    @Before
    public void setUp() throws Exception
    {
        card = new Card(Suit.Clubs, Value.Ace, Back.Back11, null);
    }
    
    @After
    public void tearDown() 
    {
        card.dispose();
        
        assertNull(card.getDestinationHolderKey());
        assertNull(card.getSourceHolderKey());
        
        card = null;
    }

    @Test
    public void getSourceHolderKeyTest() throws Exception
    {
        card = new Card(Suit.Clubs, Value.Ace, Back.Back11, null);
        assertNull(card.getSourceHolderKey());
        
        card = new Card(Suit.Clubs, Value.Ace, Back.Back11, DEFAULT_KEY);
        assertNotNull(card.getSourceHolderKey());
        assertEquals(card.getSourceHolderKey(), DEFAULT_KEY);
    }
    
    @Test
    public void setSourceHolderKeyTest() throws Exception
    {
        card = new Card(Suit.Clubs, Value.Ace, Back.Back11, null);
        card.setSourceHolderKey(null);
        assertNull(card.getSourceHolderKey());
        card.setSourceHolderKey(DEFAULT_KEY);
        assertEquals(card.getSourceHolderKey(), DEFAULT_KEY);
    }
    
    @Test
    public void setStartTest()
    {
        card.setStart();
    }
    
    @Test
    public void setDestinationTest() throws Exception
    {
        card.setDestination(new Point(0,0), DEFAULT_KEY);
    }
    
    @Test 
    public void getDestinationHolderKeyTest() throws Exception
    {
        card.setDestination(new Point(0,0), DEFAULT_KEY);
        assertNotNull(card.getDestinationHolderKey());
        assertEquals(card.getDestinationHolderKey(), DEFAULT_KEY);
        card.setDestination(new Point(0,0), null);
        assertNull(card.getDestinationHolderKey());
    }
    
    @Test
    public void moveTowardsDestinationTest() throws Exception
    {
        final int startX = 0;
        final int startY = 0;
        
        final int x1 = 100;
        final int y1 = 75;
        
        card.setLocation(startX, startY);
        card.setDestination(new Point(x1,y1), DEFAULT_KEY);
        card.moveTowardsDestination(Card.MOVE_CARD_DURATION);
        assertTrue(card.hasDestination());
        assertTrue(card.getX() == x1);
        assertTrue(card.getY() == y1);
        
        final int x2 = 10;
        final int y2 = 10;
        
        card.setLocation(startX, startY);
        card.setDestination(new Point(x2,y2), DEFAULT_KEY);
        card.moveTowardsDestination(1);
        assertFalse(card.hasDestination());
        assertTrue(card.getX() != x2);
        assertTrue(card.getY() != y2);
    }
    
    @Test
    public void hasDestinationTest() throws Exception
    {
        final int destX = 100;
        final int destY = 33;
        
        card.setDestination(new Point(destX, destY), null);
        assertFalse(card.hasDestination());
        
        card.setX(destX);
        card.setY(destY);
        assertTrue(card.hasDestination());
    }
    
    @Test
    public void setHiddenTest()
    {
        card.setHidden(true);
        card.setHidden(false);
    }
    
    @Test
    public void isHiddenTest()
    {
        card.setHidden(true);
        assertTrue(card.isHidden());
        card.setHidden(false);
        assertFalse(card.isHidden());
    }
    
    @Test
    public void setSuitTest()
    {
        //set all suits
        for (Suit suit : Suit.values())
        {
            card.setSuit(suit);
        }
    }
    
    @Test
    public void setValueTest()
    {
        //set all values
        for (Value value : Value.values())
        {
            card.setValue(value);
        }
    }
    
    @Test
    public void getSuitTest() throws Exception
    {
        //set all suits
        for (Suit suit : Suit.values())
        {
            //create new card of suit
            card = new Card(suit, Value.Ace, Back.Back1, null);
            
            //make sure it is true
            assertTrue(card.getSuit() == suit);
            
            //set the suit
            card.setSuit(suit);
            
            //make sure it is true
            assertTrue(card.getSuit() == suit);
        }
    }
    
    @Test
    public void getValueTest() throws Exception
    {
        //set all values
        for (Value value : Value.values())
        {
            //create new card of value
            card = new Card(Suit.Clubs, value, Back.Back1, null);
            
            //make sure it is true
            assertTrue(card.getValue() == value);
            
            card.setValue(value);
            
            //make sure it is true
            assertTrue(card.getValue() == value);
        }
    }
    
    @Test
    public void hasSuitTest() throws Exception
    {
        //set all suits
        for (Suit suit : Suit.values())
        {
            Card tmp = new Card(suit, Value.Ace, Back.Back1, null);
            
            //create new card of suit
            card = new Card(suit, Value.Ace, Back.Back1, null);
            
            //assume true
            assertTrue(card.hasSuit(suit));
            
            //assume true
            assertTrue(card.hasSuit(tmp));
        }
        
    }
    
    @Test
    public void hasValueTest() throws Exception
    {
        //set all values
        for (Value value : Value.values())
        {
            Card tmp = new Card(Suit.Clubs, value, Back.Back1, null);
            
            //create new card of value
            card = new Card(Suit.Clubs, value, Back.Back1, null);
            
            //assume true
            assertTrue(card.hasValue(value));
            
            //assume true
            assertTrue(card.hasValue(tmp));
        }
    }
}