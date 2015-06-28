package com.gamesbykevin.solitaire.solitaire.pyramid;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.card.Holder.StackType;

import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.pyramid.Pyramid.Key;
import static com.gamesbykevin.solitaire.solitaire.SolitaireTest.TEST_IMAGE;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pyramid Helper unit test
 * @author GOD
 */
public class PyramidHelperTest 
{
    //pyramid object
    private Solitaire pyramid;
    
    //default holder
    private Holder holder1;
    
    //test cards
    private Card card1, card2;
    
    @BeforeClass
    public static void setUpClass() 
    {
        assertTrue(PyramidHelper.SCORE_ACE == 1);
        assertTrue(PyramidHelper.SCORE_TWO == 2);
        assertTrue(PyramidHelper.SCORE_THREE == 3);
        assertTrue(PyramidHelper.SCORE_FOUR == 4);
        assertTrue(PyramidHelper.SCORE_FIVE == 5);
        assertTrue(PyramidHelper.SCORE_SIX == 6);
        assertTrue(PyramidHelper.SCORE_SEVEN == 7);
        assertTrue(PyramidHelper.SCORE_EIGHT == 8);
        assertTrue(PyramidHelper.SCORE_NINE == 9);
        assertTrue(PyramidHelper.SCORE_TEN == 10);
        assertTrue(PyramidHelper.SCORE_JACK == 11);
        assertTrue(PyramidHelper.SCORE_QUEEN == 12);
        assertTrue(PyramidHelper.SCORE_KING == 13);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() throws Exception
    {
        //create new instance
        pyramid = new Pyramid(TEST_IMAGE);
        
        //create holders
        holder1 = new Holder(StackType.Same);
        
        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Row_7_Column_3);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Key.Row_1_Column_1);
    }
    
    @After
    public void tearDown() 
    {
        pyramid.dispose();
        pyramid = null;
        
        holder1.dispose();
        holder1 = null;
        
        card1.dispose();
        card1 = null;
        
        card2.dispose();
        card2 = null;
    }
    
    @Test
    public void getScoreTest() throws Exception
    {
        holder1 = new Holder(StackType.Same);

        //create test cards
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Row_7_Column_3);
        card2 = new Card(Suit.Diamonds, Value.Jack, Back.Back1, Key.Row_1_Column_1);
        
        //add cards to holder
        holder1.add(card1);
        holder1.add(card2);
        
        //assume the score result
        assertTrue(PyramidHelper.getScore(holder1) == (PyramidHelper.SCORE_ACE + PyramidHelper.SCORE_JACK));
        
        for (Value value : Value.values())
        {
            //create card of value
            card1 = new Card(Suit.Clubs, value, Back.Back1, Key.Row_7_Column_3);
            
            //the expected score
            int score = 0;
            
            switch (value)
            {
                case Ace:
                    score = PyramidHelper.SCORE_ACE;
                    break;
                    
                case Two:
                    score = PyramidHelper.SCORE_TWO;
                    break;
                    
                case Three:
                    score = PyramidHelper.SCORE_THREE;
                    break;
                    
                case Four:
                    score = PyramidHelper.SCORE_FOUR;
                    break;
                    
                case Five:
                    score = PyramidHelper.SCORE_FIVE;
                    break;
                    
                case Six:
                    score = PyramidHelper.SCORE_SIX;
                    break;
                    
                case Seven:
                    score = PyramidHelper.SCORE_SEVEN;
                    break;
                    
                case Eight:
                    score = PyramidHelper.SCORE_EIGHT;
                    break;
                    
                case Nine:
                    score = PyramidHelper.SCORE_NINE;
                    break;
                    
                case Ten:
                    score = PyramidHelper.SCORE_TEN;
                    break;
                    
                case Jack:
                    score = PyramidHelper.SCORE_JACK;
                    break;
                    
                case Queen:
                    score = PyramidHelper.SCORE_QUEEN;
                    break;
                    
                case King:
                    score = PyramidHelper.SCORE_KING;
                    break;
            }
            
            assertTrue(PyramidHelper.getScore(card1) == score);
        }
    }
    
    @Test
    public void hasKeyTest() throws Exception
    {
        holder1 = new Holder(StackType.Same);
        
        //create test card
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Row_7_Column_3);
        
        //add card to holder
        holder1.add(card1);
        
        //assume we have this key since we just specified it
        assertTrue(PyramidHelper.hasKey(holder1, Key.Row_7_Column_3));
        
        //remove all cards
        holder1.removeAll();
        
        //create test card
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Row_3_Column_2);
        
        //add card to holder
        holder1.add(card1);
        
        //assume we don't have this key
        assertFalse(PyramidHelper.hasKey(holder1, Key.Row_7_Column_3));
        
        //remove all cards
        holder1.removeAll();
        
        //assume we don't have this key as there are no cards in the holder
        assertFalse(PyramidHelper.hasKey(holder1, Key.Row_7_Column_3));
    }
    
    @Test
    public void canSelectTest() throws Exception
    {
        holder1 = new Holder(StackType.Same);
        
        //create test card
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Row_7_Column_3);
        
        //add card to holder
        holder1.add(card1);
        
        //we only have 1 card that isn't a king
        assertTrue(PyramidHelper.canSelect(holder1));

        
        //create test card
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Row_7_Column_3);
        
        //add another card to holder
        holder1.add(card1);
        
        //we have reached our limit
        assertFalse(PyramidHelper.canSelect(holder1));
        
        
        //remove all cards
        holder1.removeAll();
        
        //create test card
        card1 = new Card(Suit.Clubs, Value.King, Back.Back1, Key.Row_7_Column_3);
        
        //add card to holder
        holder1.add(card1);
        
        //we only have 1 card, but it is a king, so we can't make any more selections
        assertFalse(PyramidHelper.canSelect(holder1));
    }
    
    @Test
    public void isBlockedTest() throws Exception
    {
        //create holder
        holder1 = new Holder(StackType.Same);
        
        //assert false since there are no existing cards
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_1_Column_1));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_2_Column_1));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_2_Column_2));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_3_Column_1));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_3_Column_2));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_3_Column_3));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_1));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_2));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_3));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_4));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_1));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_2));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_3));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_4));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_5));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_1));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_2));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_3));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_4));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_5));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_6));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_1));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_2));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_3));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_4));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_5));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_6));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_7));
        
        //add a card that will block
        card1 = new Card(Suit.Clubs, Value.Ace, Back.Back1, Key.Row_5_Column_1);
        holder1.add(card1);
        
        //assume this location is blocked because one of the holder cards is in front of this location
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_1));
        
        //assume this location is not blocked because there are no cards blocking this location
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_4));
        
        //now remove all cards
        holder1.removeAll();
        
        while(true)
        {
            if (!pyramid.isCreateComplete())
            {
                //create the deck
                pyramid.create(new Random());
            }
            else if (!pyramid.isShuffleComplete())
            {
                //shuffle it
                pyramid.shuffle(new Random(), pyramid.getHolder(Key.Deck));
            }
            else if (!pyramid.isDealComplete())
            {
                //deal the cards
                pyramid.deal(Timers.toNanoSeconds(1000L));
            }
            else
            {
                break;
            }
        }
        
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_1_Column_1));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_2_Column_1));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_2_Column_2));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_3_Column_1));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_3_Column_2));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_3_Column_3));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_1));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_2));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_3));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_4_Column_4));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_1));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_2));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_3));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_4));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_5_Column_5));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_1));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_2));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_3));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_4));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_5));
        assertTrue(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_6_Column_6));
        
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_1));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_2));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_3));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_4));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_5));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_6));
        assertFalse(PyramidHelper.isBlocked(pyramid, holder1, Key.Row_7_Column_7));
    }
}