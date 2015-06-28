package com.gamesbykevin.solitaire.solitaire.bakers;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.klondike.KlondikeHelper;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Bakers Dozen Solitaire
 * @author GOD
 */
public final class Bakers extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Playable1, Playable2, Playable3, Playable4, Playable5, Playable6, 
        Playable7, Playable8, Playable9, Playable10, Playable11, Playable12, Playable13, 
        Deck, 
        
        Destination1, Destination2, Destination3, Destination4, 
    }
    
    //the amount of time to move the cards from one point to the next
    private static final long MOVE_CARD_DURATION = Timers.toNanoSeconds(100L);
    
    //the locations for each row
    private static final Point ROW_1_START_LOCATION = new Point(100, 100);
    private static final Point ROW_2_START_LOCATION = new Point(100, 350);
    
    /**
     * Where the destination holders will be placed
     */
    private static final Point DESTINATION_LOCATION = new Point(650, 100);
    
    /**
     * The size of each holder
     */
    protected static final int HOLDER_SIZE = 4;
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(-Card.ORIGINAL_CARD_WIDTH, -Card.ORIGINAL_CARD_HEIGHT);
    
    //new card size
    private static final float CARD_SIZE_RATIO = .45f;
    
    //the location of the stats window
    private static final Point STATS_LOCATION = new Point(475, 210);
    
    //score for each card placed
    private static final int POINTS_SCORE = 5;
    
    public Bakers(final Image image) throws Exception
    {
        super(image, CARD_SIZE_RATIO, Holder.StackType.Vertical);
        
        int index = 0;

        //width between each column
        final int PIXEL_WIDTH = (int)(getDefaultWidth() * 1.15);
    
        //width between each row
        final int PIXEL_HEIGHT = (int)(getDefaultHeight() * 1.15);
        
        int x = ROW_1_START_LOCATION.x;
        int y = ROW_1_START_LOCATION.y;
        
        for (int col = 0; col < 7; col++)
        {
            //add holder
            super.addHolder(Key.values()[index], x, y, Holder.StackType.Vertical);
            
            //modify next location
            x += PIXEL_WIDTH;
            
            index++;
        }
        
        x = ROW_2_START_LOCATION.x;
        y = ROW_2_START_LOCATION.y;
        
        for (int col = 0; col < 6; col++)
        {
            //add holder
            super.addHolder(Key.values()[index], x, y, Holder.StackType.Vertical);
            
            //modify next location
            x += PIXEL_WIDTH;
            
            index++;
        }
        
        //add deck
        super.addHolder(Key.Deck, DECK_START_LOCATION, Holder.StackType.Same);
            
        //add destinations
        super.addHolder(Key.Destination1, DESTINATION_LOCATION.x, DESTINATION_LOCATION.y + (0 * PIXEL_HEIGHT), Holder.StackType.Same);
        super.addHolder(Key.Destination2, DESTINATION_LOCATION.x, DESTINATION_LOCATION.y + (1 * PIXEL_HEIGHT), Holder.StackType.Same);
        super.addHolder(Key.Destination3, DESTINATION_LOCATION.x, DESTINATION_LOCATION.y + (2 * PIXEL_HEIGHT), Holder.StackType.Same);
        super.addHolder(Key.Destination4, DESTINATION_LOCATION.x, DESTINATION_LOCATION.y + (3 * PIXEL_HEIGHT), Holder.StackType.Same);
        
        //assign location
        super.getStats().setLocation(STATS_LOCATION);
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
                //add to our deck
                getHolder(Key.Deck).add(new Card(suit, value, back, Key.Deck, MOVE_CARD_DURATION));
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
        //if any of these still have cards, the game is not over
        if (!getHolder(Key.Playable1).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable2).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable3).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable4).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable5).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable6).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable7).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable8).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable9).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable10).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable11).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable12).isEmpty())
        {
            return;
        }
        else if (!getHolder(Key.Playable13).isEmpty())
        {
            return;
        }
        
        //flag game over
        super.setGameover(true);
        super.setWinner(true);
    }
    
    /**
     * Place the cards in their appropriate place
     * @param time The amount of time per update (nanoseconds)
     * @throws Exception 
     */
    @Override
    public void deal(final long time) throws Exception
    {
        //get the top card
        final Card card = getHolder(Key.Deck).getLastCard();
        
        if (!card.hasDestination())
        {
            for (Key key : Key.values())
            {
                //don't check the deck or destinations
                if (key == Key.Deck)
                    continue;
                if (key == Key.Destination1)
                    continue;
                if (key == Key.Destination2)
                    continue;
                if (key == Key.Destination3)
                    continue;
                if (key == Key.Destination4)
                    continue;

                //if we don't have the required size
                if (getHolder(key).getSize() < HOLDER_SIZE)
                {
                    //no need to hide
                    card.setHidden(false);
                    
                    //set destination
                    card.setDestination(getHolder(key).getPoint(), key);
                    
                    //update location
                    card.moveTowardsDestination(time);
                    
                    //no need to continue
                    break;
                }
            }
        }
        else
        {
            //set the destination as the source
            card.setSourceHolderKey(card.getDestinationHolderKey());
            
            //add it to the destination
            getHolder(card.getDestinationHolderKey()).add(card);
            
            //remove from existing holder
            getHolder(Key.Deck).remove(card);
            
            //if the holder is empty, we are done dealing
            if (getHolder(Key.Deck).isEmpty())
            {
                //flag complete
                setDealComplete(true);
            }
        }
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
            if (engine.getMouse().isMouseDragged())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;
                
                for (Key key : Key.values())
                {
                    //don't check the deck
                    if (key == Key.Deck)
                        continue;
                    
                    //don't check the destinations
                    if (key == Key.Destination1 || key == Key.Destination2 || 
                        key == Key.Destination3 || key == Key.Destination4)
                        continue;
                    
                    //did we click in this holder deck and are there cards here
                    if (getHolder(key).hasCard(x, y))
                    {
                        //update location
                        getDefaultHolder().setLocation(getHolder(key));
                        
                        //assign the selected cards
                        KlondikeHelper.assignCards(getDefaultHolder(), getHolder(key), key, x, y);
                    }
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
                
                //check if the cards can be placed, or should be brought back to their source
                if (!getDefaultHolder().isEmpty())
                {
                    //we can only place cards one at a time on the destinations
                    if (getDefaultHolder().getSize() == 1)
                    {
                        //check the destinations first
                        for (Key key : Key.values())
                        {
                            //only check the destination now
                            if (key != Key.Destination1 && key != Key.Destination2 && 
                                key != Key.Destination3 && key != Key.Destination4)
                                continue;

                            //if we have this selection 
                            if (getHolder(key).hasLocation(x, y))
                            {
                                //if we placed cards on holder no need to continue
                                if (KlondikeHelper.placeDestinationCards(getDefaultHolder(), getHolder(key), key))
                                    return;
                            }
                        }
                    }
                    
                    //now check the playable holder
                    for (Key key : Key.values())
                    {
                        //check the playable holders
                        if (key == Key.Destination1 || key == Key.Destination2 ||
                            key == Key.Destination3 || key == Key.Destination4 || key == Key.Deck)
                            continue;
                        
                        //if we have this selection 
                        if (getHolder(key).hasLocation(x, y))
                        {
                            //if we placed cards on holder no need to continue
                            if (KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(key), key))
                                return;
                        }
                    }
                    
                    //get the first card
                    final Card tmp = getDefaultHolder().getFirstCard();
                
                    //since the cards weren't placed correctly, we need to return them
                    getDefaultHolder().setDestination(getHolder(tmp.getSourceHolderKey()), tmp.getSourceHolderKey());

                    //set the start location for all
                    getDefaultHolder().setStart();
                }
            }
            else if (!engine.getMouse().isMouseDragged() && !engine.getMouse().isMouseReleased())
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

                        //if a card is placed on a destination holder, add to the score
                        if (isDestination(card.getDestinationHolderKey()))
                            super.getStats().setScore(super.getStats().getScore() + POINTS_SCORE);
                        
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
    
    /**
     * Is this the final destination for the card
     * @param key The key of the holder we want to check
     * @return true=yes, false=no
     */
    private boolean isDestination(final Object key)
    {
        return (key == Key.Destination1 || key == Key.Destination2 || key == Key.Destination3 || key == Key.Destination4);
    }
}