package com.gamesbykevin.solitaire.solitaire.freecell;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.entity.Entity;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.klondike.KlondikeHelper;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Free Cell Solitaire
 * @author GOD
 */
public final class FreeCell extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Temp1, 
        Temp2, 
        Temp3,
        Temp4,
        Destination1, 
        Destination2, 
        Destination3, 
        Destination4, 
        Playable1, 
        Playable2, 
        Playable3, 
        Playable4, 
        Playable5, 
        Playable6, 
        Playable7, 
        Playable8, 
        Deck, 
    }
    
    /**
     * The size limit of the temporary holder
     */
    protected static final int TEMP_HOLDER_LIMIT = 1;
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(-Card.ORIGINAL_CARD_WIDTH, -Card.ORIGINAL_CARD_HEIGHT);
    
    /**
     * The (x,y) locations for the temporary holders
     */
    private static final Point TEMP_START_LOCATION = new Point(70, 100);
    
    /**
     * The (x,y) locations for the destinations
     */
    private static final Point DESTINATION_START_LOCATION = new Point(445, 100);

    /**
     * The (x,y) locations for the playable holders
     */
    private static final Point PLAYABLE_START_LOCATION = new Point(75, 250);
    
    //keep track of destination for the deal
    private Key destination = Key.Playable1;
    
    //the amount of time until each card is placed
    private static final long CARD_MOVE_DURATION = Timers.toNanoSeconds(100L);
    
    public FreeCell(final Image image)
    {
        super(image, StackType.Vertical);
        
        int x = TEMP_START_LOCATION.x;
        
        //the width between each column
        final int COLUMN_WIDTH = (int)(getDefaultWidth() * 1.2);
    
        //setup temporary locations
        for (int index = 0; index < 4; index++)
        {
            super.addHolder(Key.values()[index], x, TEMP_START_LOCATION.y, StackType.Same);
            
            //increase the x-coordinate
            x += COLUMN_WIDTH;
        }
        
        x = DESTINATION_START_LOCATION.x;
        
        //setup destination locations
        for (int index = 4; index < 8; index++)
        {
            super.addHolder(Key.values()[index], x, DESTINATION_START_LOCATION.y, StackType.Same);
            
            //increase the x-coordinate
            x += COLUMN_WIDTH;
        }
        
        x = PLAYABLE_START_LOCATION.x;
        
        //setup playable locations
        for (int index = 8; index < 16; index++)
        {
            super.addHolder(Key.values()[index], x, PLAYABLE_START_LOCATION.y, StackType.Vertical);
            
            //increase the x-coordinate
            x += COLUMN_WIDTH;
        }
        
        //add the deck
        super.addHolder(Key.Deck, DECK_START_LOCATION, StackType.Same);
    }
    
    @Override
    public void shuffle(final Random random) throws Exception
    {
        super.shuffle(random, getHolder(Key.Deck));
    }
    
    /**
     * Check if we have a winner or if the game is over
     */
    @Override
    public void validate()
    {
        for (Key key : Key.values())
        {
            //don't check these
            if (key == Key.Destination1 || key == Key.Destination2 || 
                key == Key.Destination3 || key == Key.Destination4)
                continue;
            
            //the game isn't over if cards exist
            if (!getHolder(key).isEmpty())
                return;
        }
        
        //flag game over and winner
        super.setGameover(true);
        super.setWinner(true);
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
        Back back = Back.values()[random.nextInt(Back.values().length)];
        
        //klondie solitaire will have one complete deck
        for (Suit suit : Suit.values())
        {
            for (Value value : Value.values())
            {
                //add to our deck
                getHolder(Key.Deck).add(new Card(suit, value, back, Key.Deck, CARD_MOVE_DURATION));
            }
        }
        
        //flag create as completed
        setCreateComplete(true);
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
        
        //don't hide any cards
        card.setHidden(false);
        
        if (!card.hasDestination())
        {
            //set destination
            card.setDestination(getHolder(destination).getDestinationPoint(), destination);
            
            //now move the card towards the destination
            card.moveTowardsDestination(time);
        }
        else
        {
            //set the destination as the source
            card.setSourceHolderKey(card.getDestinationHolderKey());
            
            //add it to the destination
            getHolder(card.getDestinationHolderKey()).add(card);
            
            //remove from existing holder
            getHolder(Key.Deck).remove(card);
            
            //change the destination
            if (destination == Key.Playable1)
            {
                destination = Key.Playable2;
            }
            else if (destination == Key.Playable2)
            {
                destination = Key.Playable3;
            }
            else if (destination == Key.Playable3)
            {
                destination = Key.Playable4;
            }
            else if (destination == Key.Playable4)
            {
                destination = Key.Playable5;
            }
            else if (destination == Key.Playable5)
            {
                destination = Key.Playable6;
            }
            else if (destination == Key.Playable6)
            {
                destination = Key.Playable7;
            }
            else if (destination == Key.Playable7)
            {
                destination = Key.Playable8;
            }
            else if (destination == Key.Playable8)
            {
                destination = Key.Playable1;
            }
            
            //if no more cards, the deal is complete
            if (getHolder(Key.Deck).isEmpty())
                setDealComplete(true);
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
        //get the mouse location
        final int x = engine.getMouse().getLocation().x;
        final int y = engine.getMouse().getLocation().y;
        
        if (getDefaultHolder().isEmpty())
        {
            if (engine.getMouse().isMouseDragged())
            {
                //check the temporary holders
                for (Key key : Key.values())
                {
                    //only check the temp areas right now
                    if (key != Key.Temp1 && key != Key.Temp2 &&
                        key != Key.Temp3 && key != Key.Temp4)
                        continue;
                            
                    //only check if there are cards
                    if (getHolder(key).isEmpty())
                        continue;
                    
                    //if we have the location add to default holder
                    if (getHolder(key).hasLocation(x, y))
                    {
                        //update holder location
                        getDefaultHolder().setLocation(getHolder(key));
                        
                        //add cards to default holder
                        getDefaultHolder().add(getHolder(key));
                        
                        //remove from previous holder (there will be only 1 card
                        getHolder(key).removeAll();
                        
                        //no need to continue
                        return;
                    }
                }
                
                //check the playble areas
                for (Key key : Key.values())
                {
                    //only check the playable areas right now
                    if (key == Key.Destination1 || key == Key.Destination2 ||
                        key == Key.Destination3 || key == Key.Destination4)
                        continue;
                    
                    if (key == Key.Temp1 || key == Key.Temp2 ||
                        key == Key.Temp3 || key == Key.Temp4 || key == Key.Deck)
                        continue;
                    
                    //don't check if there are no cards
                    if (getHolder(key).isEmpty())
                        continue;
                    
                    //if we selected this loction with the mouse
                    if (getHolder(key).hasLocation(x, y))
                    {
                        //set the location
                        getDefaultHolder().setLocation(getHolder(key));
                        
                        //makes sure the cards are in order
                        if (FreeCellHelper.hasSequence(getHolder(key), x, y))
                            KlondikeHelper.assignCards(getDefaultHolder(), getHolder(key), key, x, y);
                        
                        //if there are cards now, no need to continue
                        if (!getDefaultHolder().isEmpty())
                            return;
                    }
                }
            }
        }
        else
        {
            //if the mouse is dragged, update the location
            if (engine.getMouse().isMouseDragged())
            {
                getDefaultHolder().updateLocation(x, y);
            }
            else if (engine.getMouse().isMouseReleased())
            {
                //set the start location for all in default holder
                getDefaultHolder().setStart();
                
                //make sure we only have 1 card if placing in the temporary
                if (getDefaultHolder().getSize() == TEMP_HOLDER_LIMIT)
                {
                    //check the temporary holders
                    for (Key key : Key.values())
                    {
                        //only check the temp areas right now
                        if (key != Key.Temp1 && key != Key.Temp2 &&
                            key != Key.Temp3 && key != Key.Temp4)
                            continue;

                        //can only place cards on empty holders
                        if (!getHolder(key).isEmpty())
                            continue;

                        //if we selected this loction with the mouse
                        if (getHolder(key).hasLocation(x, y))
                        {
                            //set the destination
                            getDefaultHolder().setDestination(getHolder(key), key);

                            //no need to continue
                            return;
                        }
                    }
                }
                
                //check the destination areas
                for (Key key : Key.values())
                {
                    //only check the destination areas right now
                    if (key != Key.Destination1 && key != Key.Destination2 &&
                        key != Key.Destination3 && key != Key.Destination4)
                        continue;
                    
                    //if we selected this loction with the mouse
                    if (getHolder(key).hasLocation(x, y))
                    {
                        //check if these card(s) were placed
                        if (KlondikeHelper.placeDestinationCards(getDefaultHolder(), getHolder(key), key))
                            return;
                    }
                }
                
                //check the playble areas
                for (Key key : Key.values())
                {
                    //only check the playable areas right now
                    if (key == Key.Destination1 || key == Key.Destination2 ||
                        key == Key.Destination3 || key == Key.Destination4)
                        continue;
                    
                    if (key == Key.Temp1 || key == Key.Temp2 ||
                        key == Key.Temp3 || key == Key.Temp4 || key == Key.Deck)
                        continue;
                    
                    //if we selected this loction with the mouse
                    if (getHolder(key).hasLocation(x, y))
                    {
                        //if this is an empty space, any card can be placed
                        if (getHolder(key).isEmpty())
                        {
                            //set the destination
                            getDefaultHolder().setDestination(getHolder(key), key);
                            
                            //no need to return
                            return;
                        }
                        else
                        {
                            //check if these card(s) were placed
                            if (KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(key), key))
                                return;
                        }
                    }
                }
                
                //get the first card
                final Card tmp = getDefaultHolder().getFirstCard();
                
                //set the destination for all cards back to the source
                getDefaultHolder().setDestination(getHolder(tmp.getSourceHolderKey()), tmp.getSourceHolderKey());
            }
            else
            {
                //if not at the destination return then
                if (!getDefaultHolder().hasDestination())
                {
                    getDefaultHolder().moveTowardsDestination(engine.getTime());
                }
                else
                {
                    //get the first card for reference
                    final Card card = getDefaultHolder().getFirstCard();

                    //add cards to the destination
                    getHolder(card.getDestinationHolderKey()).add(getDefaultHolder());

                    //now remove the cards from the default holder
                    getDefaultHolder().removeAll();
                    
                    //check if the game has ended
                    validate();
                }
            }
        }
    }
}