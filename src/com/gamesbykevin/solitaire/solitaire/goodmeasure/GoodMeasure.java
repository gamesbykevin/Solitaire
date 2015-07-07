package com.gamesbykevin.solitaire.solitaire.goodmeasure;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.klondike.KlondikeHelper;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Good Measure Solitaire
 * @author GOD
 */
public final class GoodMeasure extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Playable1, Playable2, Playable3, Playable4, Playable5, 
        Playable6, Playable7, Playable8, Playable9, Playable10, 
        Deck, 
        
        Destination1, Destination2, Destination3, Destination4, 
    }
    
    /**
     * The number of cards in the default holder
     */
    protected static final int DEFAULT_HOLDER_SIZE = 1;
    
    /**
     * The number of cards in each playable holder
     */
    protected static final int HOLDER_SIZE = 5;
    
    //the amount of time to move the cards from one point to the next
    private static final long MOVE_CARD_DURATION = Timers.toNanoSeconds(150L);
    
    //the locations for each row
    private static final Point ROW_1_START_LOCATION = new Point(90, 90);
    private static final Point ROW_2_START_LOCATION = new Point(90, 350);
    
    /**
     * Where the destination holders will be placed
     */
    private static final Point DESTINATION_LOCATION = new Point(650, 90);
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(-Card.ORIGINAL_CARD_WIDTH, -Card.ORIGINAL_CARD_HEIGHT);
    
    //the location of the stats window
    private static final Point STATS_LOCATION = new Point(440, 210);
    
    //points to add for each card placed
    private static final int POINTS_SCORE = 10;
    
    public GoodMeasure(final Image image) throws Exception
    {
        super(image, StackType.Same);
        
        int index = 0;
        
        int x = ROW_1_START_LOCATION.x;
        int y = ROW_1_START_LOCATION.y;
        
        //The width between each column
        final int PIXEL_WIDTH = (int)(getDefaultWidth() * 1.15);

        //The distance between each row
        final int PIXEL_HEIGHT = (int)(getDefaultHeight() * 1.15);
        
        for (int col = 0; col < 5; col++)
        {
            //add holder
            super.addHolder(Key.values()[index], x, y, StackType.Vertical);
            
            //modify next location
            x += PIXEL_WIDTH;
            
            index++;
        }
        
        x = ROW_2_START_LOCATION.x;
        y = ROW_2_START_LOCATION.y;
        
        for (int col = 0; col < 5; col++)
        {
            //add holder
            super.addHolder(Key.values()[index], x, y, StackType.Vertical);
            
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
                //add new card to our deck
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
        
        if (card.hasValue(Value.Ace))
        {
            if (getHolder(Key.Destination1).isEmpty())
            {
                //display card
                card.setHidden(false);
                
                //set the source
                card.setSourceHolderKey(Key.Destination1);
                
                //add card to holder
                getHolder(Key.Destination1).add(card);
                
                //remove from existing holder
                getHolder(Key.Deck).remove(card);
                
                //no need to continue
                return;
            }
            else if (getHolder(Key.Destination2).isEmpty())
            {
                //display card
                card.setHidden(false);
                
                //set the source
                card.setSourceHolderKey(Key.Destination2);
                
                //add card to holder
                getHolder(Key.Destination2).add(card);
                
                //remove from existing holder
                getHolder(Key.Deck).remove(card);
                
                //no need to continue
                return;
            }
        }
        
        if (!card.hasDestination())
        {
            for (Key key : Key.values())
            {
                //don't check the deck or destinations
                if (key == Key.Deck)
                    continue;
                if (key == Key.Destination1 || key == Key.Destination2 || key == Key.Destination3 || key == Key.Destination4)
                    continue;

                //if the next card is a king
                if (card.hasValue(Value.King))
                {
                    //make sure we aren't already on the last playable holder
                    if (key != Key.Playable10)
                    {
                        //if a king already exists here, skip to the next holder
                        if (getHolder(key).hasValue(Value.King))
                            continue;
                    }
                }
                
                //if we don't have the required size
                if (getHolder(key).getSize() < HOLDER_SIZE)
                {
                    //if not empty show the card
                    if (!getHolder(key).isEmpty())
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
            
            //if the deck is empty, we are done dealing
            if (getHolder(Key.Deck).isEmpty())
            {
                //now check each holder and make sure the kings are moved to the first card in that holder
                for (Key key : Key.values())
                {
                    //don't check the deck or destinations
                    if (key == Key.Deck)
                        continue;
                    if (key == Key.Destination1 || key == Key.Destination2 || key == Key.Destination3 || key == Key.Destination4)
                        continue;
                    
                    for (int index = 0; index < getHolder(key).getSize(); index++)
                    {
                        Card card1 = getHolder(key).getCard(index);
                        
                        //if this card is a king, swap with first card
                        if (card1.hasValue(Value.King))
                        {
                            //first card and make both visible
                            Card card2 = getHolder(key).getFirstCard();
                            card2.setHidden(false);
                            card1.setHidden(false);
                            
                            int x = (int)card2.getX();
                            int y = (int)card2.getY();
                            
                            //swap locations
                            card2.setLocation(card1);
                            card1.setLocation(x, y);
                            
                            //now swap cards
                            getHolder(key).set(index, card2);
                            getHolder(key).set(0,     card1);
                            
                            //done checking this holder, skip to next
                            break;
                        }
                    }
                }
                
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
        //if the default holder is empty, we can interact
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
                    if (key == Key.Destination1 || key == Key.Destination2)
                        continue;
                    if (key == Key.Destination3 || key == Key.Destination4)
                        continue;
                    
                    //make sure there are cards to select
                    if (!getHolder(key).isEmpty())
                    {
                        //check to see if we selected the last card
                        if (getHolder(key).getLastCard().hasLocation(x, y))
                        {
                            //update location
                            getDefaultHolder().setLocation(getHolder(key));

                            final Card card = getHolder(key).getLastCard();
                            
                            //set the source key
                            card.setSourceHolderKey(key);
                            
                            //add to default
                            getDefaultHolder().add(card);
                            
                            //remove from previous
                            getHolder(key).remove(card);
                        }
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
                    //check the destinations first
                    for (Key key : Key.values())
                    {
                        //only check the destination now
                        if (key != Key.Destination1 && key != Key.Destination2 && 
                            key != Key.Destination3 && key != Key.Destination4)
                            continue;
                        
                        //if we have this holder in our (x,y) coordinates
                        if (getHolder(key).hasLocation(x, y))
                        {
                            //if the destination is empty and we don't have an ace, skip to next
                            if (getHolder(key).isEmpty() && !getDefaultHolder().getFirstCard().hasValue(Value.Ace))
                                continue;
                            
                            //make sure the ranks are in order and of same suit
                            if (getHolder(key).isEmpty() && getDefaultHolder().getFirstCard().hasValue(Value.Ace) || 
                                KlondikeHelper.canPlaceCardInDestination(getHolder(key).getLastCard(), getDefaultHolder().getFirstCard()))
                            {
                                //set the start location
                                getDefaultHolder().setStart();
                                
                                //then set the destination
                                getDefaultHolder().setDestination(getHolder(key), key);
                                
                                //no need to continue
                                return;
                            }
                        }
                    }
                    
                    //now check the playable holder
                    for (Key key : Key.values())
                    {
                        //only check the playable holders
                        if (key == Key.Destination1 || key == Key.Destination2 ||
                            key == Key.Destination3 || key == Key.Destination4 || key == Key.Deck)
                            continue;
                        
                        //if we have this selection and a card exists as we can't place a card on empty playable holder per rules
                        if (getHolder(key).hasLocation(x, y) && !getHolder(key).isEmpty())
                        {
                            //make sure the rank is in order, don't worry about suit
                            if (GoodMeasureHelper.hasOrder(getHolder(key).getLastCard(), getDefaultHolder().getFirstCard()))
                            {
                                //set the start location
                                getDefaultHolder().setStart();
                                
                                //then set the destination
                                getDefaultHolder().setDestination(getHolder(key), key);
                                
                                //no need to continue
                                return;
                            }
                        }
                    }
                    
                    //play sound effect
                    engine.getResources().playInvalidCardAudio(engine.getRandom());
                
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
                    //play sound effect
                    engine.getResources().playPlaceCardAudio(engine.getRandom());
                    
                    //now handle the cards
                    for (int index = 0; index < getDefaultHolder().getSize(); index++)
                    {
                        final Card card = getDefaultHolder().getCard(index);

                        //if destination add score
                        if (isDestination(card.getDestinationHolderKey()))
                            super.getStats().setScore(super.getStats().getScore() + POINTS_SCORE);
                        
                        //add to the destination holder
                        getHolder(card.getDestinationHolderKey()).add(card);
                    }

                    //now remove all cards from default holder
                    getDefaultHolder().removeAll();

                    //show playable cards
                    showPlayableCards();
                    
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
    
    /**
     * Make sure the top card in each playable holder is visible
     */
    private void showPlayableCards() throws Exception
    {
        if (!getHolder(Key.Playable1).isEmpty())
            getHolder(Key.Playable1).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable2).isEmpty())
            getHolder(Key.Playable2).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable3).isEmpty())
            getHolder(Key.Playable3).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable4).isEmpty())
            getHolder(Key.Playable4).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable5).isEmpty())
            getHolder(Key.Playable5).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable6).isEmpty())
            getHolder(Key.Playable6).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable7).isEmpty())
            getHolder(Key.Playable7).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable8).isEmpty())
            getHolder(Key.Playable8).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable9).isEmpty())
            getHolder(Key.Playable9).getLastCard().setHidden(false);
        if (!getHolder(Key.Playable10).isEmpty())
            getHolder(Key.Playable10).getLastCard().setHidden(false);
    }
}