package com.gamesbykevin.solitaire.solitaire.poker;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.card.Holder.*;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.shared.Shared;
import com.gamesbykevin.solitaire.solitaire.Solitaire;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Poker Solitaire
 * @author GOD
 */
public final class Poker extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Row1Column1, Row1Column2, Row1Column3, Row1Column4, Row1Column5,
        Row2Column1, Row2Column2, Row2Column3, Row2Column4, Row2Column5,
        Row3Column1, Row3Column2, Row3Column3, Row3Column4, Row3Column5,
        Row4Column1, Row4Column2, Row4Column3, Row4Column4, Row4Column5,
        Row5Column1, Row5Column2, Row5Column3, Row5Column4, Row5Column5,
        Deck,
    }
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(75, 90);
    
    /**
     * The location for row 1, column 1
     */
    private static final Point ROW_1_COLUMN_1_START_LOCATION = new Point(175, 90);
    
    //the new dimensions of the card
    private static final int NEW_CARD_WIDTH = (int)(Card.CARD_WIDTH * .85);
    private static final int NEW_CARD_HEIGHT = (int)(Card.CARD_HEIGHT * .85);
    
    /**
     * The width between each column
     */
    private static final int PIXEL_WIDTH = (int)(NEW_CARD_WIDTH * 1.15);
    
    /**
     * The width between each row
     */
    private static final int PIXEL_HEIGHT = (int)(NEW_CARD_HEIGHT * 1.05);
    
    /**
     * The dimensions of our poker board
     */
    protected static final int COLS = 5, ROWS = 5;
    
    //the amount of time to move the cards from one point to the next
    private static final long MOVE_CARD_DURATION = Timers.toNanoSeconds(200L);

    
    public Poker(final Image image)
    {
        super(image, StackType.Same);
        
        int index = 0;
        
        for (int row = 0; row < ROWS; row++)
        {
            final int y = ROW_1_COLUMN_1_START_LOCATION.y + (PIXEL_HEIGHT * row);
            
            for (int col = 0; col < COLS; col++)
            {
                final int x = ROW_1_COLUMN_1_START_LOCATION.x + (PIXEL_WIDTH * col);
                
                //add holder
                super.addHolder(Key.values()[index], x, y, StackType.Same);
                
                //set the dimensions
                super.getHolder(Key.values()[index]).setDimensions(NEW_CARD_WIDTH, NEW_CARD_HEIGHT);
                
                //increase the index
                index++;
            }
        }
        
        //add the deck
        super.addHolder(Key.Deck, DECK_START_LOCATION, StackType.Same);
        
        //set the dimensions
        super.getHolder(Key.Deck).setDimensions(NEW_CARD_WIDTH, NEW_CARD_HEIGHT);
    }
    
    @Override
    public void shuffle(final Random random) throws Exception
    {
        super.shuffle(random, getHolder(Key.Deck));
    }
    
    /**
     * Create the deck
     * @param random Object used to make random decisions
     * @throws Exception 
     */
    @Override
    public void create(final Random random) throws Exception
    {
        //pick random card cover
        Card.Back back = Card.Back.values()[random.nextInt(Card.Back.values().length)];
        
        //klondie solitaire will have one complete deck
        for (Card.Suit suit : Card.Suit.values())
        {
            for (Card.Value value : Card.Value.values())
            {
                //create a new card
                final Card card = new Card(suit, value, back, Key.Deck, MOVE_CARD_DURATION);
                
                //set the card dimensions
                card.setWidth(NEW_CARD_WIDTH);
                card.setHeight(NEW_CARD_HEIGHT);
                
                //add to our deck
                getHolder(Key.Deck).add(card);
            }
        }
        
        //flag create as completed
        setCreateComplete(true);
    }
    
    /**
     * Check if we have a winner or if the game is over
     */
    @Override
    public void validate() throws Exception
    {
        for (Key key : Key.values())
        {
            //don't check the deck
            if (key == Key.Deck)
                continue;
            
            //we will continue playing until all holders have a card
            if (getHolder(key).isEmpty())
                return;
        }
        
        int score = 0;
        
        //calculate the score for row 1
        score += getScore(
            getHolder(Key.Row1Column1).getFirstCard(), 
            getHolder(Key.Row1Column2).getFirstCard(), 
            getHolder(Key.Row1Column3).getFirstCard(), 
            getHolder(Key.Row1Column4).getFirstCard(), 
            getHolder(Key.Row1Column5).getFirstCard()
        );
        
        //calculate the score for row 2
        score += getScore(
            getHolder(Key.Row2Column1).getFirstCard(), 
            getHolder(Key.Row2Column2).getFirstCard(), 
            getHolder(Key.Row2Column3).getFirstCard(), 
            getHolder(Key.Row2Column4).getFirstCard(), 
            getHolder(Key.Row2Column5).getFirstCard()
        );
        
        //calculate the score for row 3
        score += getScore(
            getHolder(Key.Row3Column1).getFirstCard(), 
            getHolder(Key.Row3Column2).getFirstCard(), 
            getHolder(Key.Row3Column3).getFirstCard(), 
            getHolder(Key.Row3Column4).getFirstCard(), 
            getHolder(Key.Row3Column5).getFirstCard()
        );
        
        //calculate the score for row 4
        score += getScore(
            getHolder(Key.Row4Column1).getFirstCard(), 
            getHolder(Key.Row4Column2).getFirstCard(), 
            getHolder(Key.Row4Column3).getFirstCard(), 
            getHolder(Key.Row4Column4).getFirstCard(), 
            getHolder(Key.Row4Column5).getFirstCard()
        );
        
        //calculate the score for row 5
        score += getScore(
            getHolder(Key.Row5Column1).getFirstCard(), 
            getHolder(Key.Row5Column2).getFirstCard(), 
            getHolder(Key.Row5Column3).getFirstCard(), 
            getHolder(Key.Row5Column4).getFirstCard(), 
            getHolder(Key.Row5Column5).getFirstCard()
        );
        
        //calculate the score for column 1
        score += getScore(
            getHolder(Key.Row1Column1).getFirstCard(), 
            getHolder(Key.Row2Column1).getFirstCard(), 
            getHolder(Key.Row3Column1).getFirstCard(), 
            getHolder(Key.Row4Column1).getFirstCard(), 
            getHolder(Key.Row5Column1).getFirstCard()
        );
        
        //calculate the score for column 2
        score += getScore(
            getHolder(Key.Row1Column2).getFirstCard(), 
            getHolder(Key.Row2Column2).getFirstCard(), 
            getHolder(Key.Row3Column2).getFirstCard(), 
            getHolder(Key.Row4Column2).getFirstCard(), 
            getHolder(Key.Row5Column2).getFirstCard()
        );
        
        //calculate the score for column 3
        score += getScore(
            getHolder(Key.Row1Column3).getFirstCard(), 
            getHolder(Key.Row2Column3).getFirstCard(), 
            getHolder(Key.Row3Column3).getFirstCard(), 
            getHolder(Key.Row4Column3).getFirstCard(), 
            getHolder(Key.Row5Column3).getFirstCard()
        );
        
        //calculate the score for column 4
        score += getScore(
            getHolder(Key.Row1Column4).getFirstCard(), 
            getHolder(Key.Row2Column4).getFirstCard(), 
            getHolder(Key.Row3Column4).getFirstCard(), 
            getHolder(Key.Row4Column4).getFirstCard(), 
            getHolder(Key.Row5Column4).getFirstCard()
        );
        
        //calculate the score for column 5
        score += getScore(
            getHolder(Key.Row1Column5).getFirstCard(), 
            getHolder(Key.Row2Column5).getFirstCard(), 
            getHolder(Key.Row3Column5).getFirstCard(), 
            getHolder(Key.Row4Column5).getFirstCard(), 
            getHolder(Key.Row5Column5).getFirstCard()
        );
        
        if (Shared.DEBUG)
            System.out.println("Score = " + score);
        
        //flag game over
        super.setGameover(true);
        super.setWinner(true);
    }
    
    /**
     * Get the score for the 5 cards specified
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return The highest score for these cards
     * @throws Exception 
     */
    private int getScore(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //starting score
        int score = 0;
        
        if (PokerHelper.isRoyalFlush(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_ROYAL_FLUSH;
        }
        else if (PokerHelper.isStraightFlush(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_STRAIGHT_FLUSH;
        }
        else if (PokerHelper.isFourOfKind(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_FOUR_OF_KIND;
        }
        else if (PokerHelper.isStraight(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_STRAIGHT;
        }
        else if (PokerHelper.isFullHouse(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_FULL_HOUSE;
        }
        else if (PokerHelper.isThreeOfKind(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_THREE_OF_KIND;
        }
        else if (PokerHelper.isFlush(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_FLUSH;
        }
        else if (PokerHelper.isTwoPair(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_TWO_PAIR;
        }
        else if (PokerHelper.isOnePair(card1, card2, card3, card4, card5))
        {
            score += PokerHelper.SCORE_ONE_PAIR;
        }
        
        //return total score
        return score;
    }
    
    /**
     * Place the cards in their appropriate place
     * @param time The amount of time per update (nanoseconds)
     * @throws Exception 
     */
    @Override
    public void deal(final long time) throws Exception
    {
        for (int index = 0; index < getHolder(Key.Deck).getSize(); index++)
        {
            //we will display all cards
            getHolder(Key.Deck).getCard(index).setHidden(false);
        }
        
        //there will be no dealing of cards
        super.setDealComplete(true);
    }
    
    /**
     * Update the main game elements
     * @param engine Object containing all game objects
     * @throws Exception 
     */
    @Override
    public void update(final Engine engine) throws Exception
    {
        //if the default holder is empty, we can pick interact
        if (getDefaultHolder().isEmpty())
        {
            //check user input
            if (engine.getMouse().isMouseDragged() || engine.getMouse().isMousePressed())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;
                
                //if cards exist in the deck and we clicked it with the mouse
                if (!getHolder(Key.Deck).isEmpty() && getHolder(Key.Deck).hasLocation(x, y))
                {
                    //get the top card in the deck
                    final Card card = getHolder(Key.Deck).getLastCard();
                    
                    //make the location (x,y) match
                    getDefaultHolder().setLocation(card);
                    
                    //mark selected
                    card.setSelected(true);

                    //add to holder
                    getDefaultHolder().add(card);

                    //remove from deck
                    getHolder(Key.Deck).remove(card);
                }
            }
        }
        else
        {
            //check user input
            if (engine.getMouse().isMouseDragged())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;
                
                //update the location of the card
                getDefaultHolder().updateLocation(x, y);
            }
            else if (engine.getMouse().isMouseReleased())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;
                
                //set the start location
                getDefaultHolder().setStart();
                
                for (Key key : Key.values())
                {
                    //no need to check this
                    if (key == Key.Deck)
                        continue;
                    
                    //don't check holders with cards
                    if (!getHolder(key).isEmpty())
                        continue;
                    
                    if (getHolder(key).hasLocation(x, y))
                    {
                        //this will be the destination
                        getDefaultHolder().setDestination(getHolder(key), key);
                        
                        //no need to continue
                        return;
                    }
                }
                
                //none found, set the deck as the destination
                getDefaultHolder().setDestination(getHolder(Key.Deck), Key.Deck);
            }
            else if (!engine.getMouse().isMouseDragged() && !engine.getMouse().isMouseReleased() && !engine.getMouse().isMousePressed())
            {
                //if not at destination yet
                if (!getDefaultHolder().hasDestination())
                {
                    //move to the destination
                    getDefaultHolder().moveTowardsDestination(engine.getTime());
                }
                else
                {
                    //now handle the cards
                    for (int index = 0; index < getDefaultHolder().getSize(); index++)
                    {
                        final Card card = getDefaultHolder().getCard(index);

                        //de-select the card
                        card.setSelected(false);

                        //add to the destination holder
                        getHolder(card.getDestinationHolderKey()).add(card);
                    }

                    //now remove all cards from default holder
                    getDefaultHolder().removeAll();

                    //validate and check if the game has ended
                    validate();
                }
            }
        }
    }
}