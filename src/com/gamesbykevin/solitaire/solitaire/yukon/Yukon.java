package com.gamesbykevin.solitaire.solitaire.yukon;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import com.gamesbykevin.solitaire.solitaire.klondike.KlondikeHelper;
import com.gamesbykevin.solitaire.solitaire.Solitaire;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Yukon Solitaire
 * @author GOD
 */
public final class Yukon extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
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
        Deck, 
    }
    
    //the amount of space to put between each playable column
    private static final int PIXEL_WIDTH = (int)(CARD_WIDTH * 1.2);
    
    /**
     * The (x,y) locations for the playable holders
     */
    private static final Point PLAYABLE_LOCATION_1 = new Point(165, 100);
    
    /**
     * The (x,y) locations for the destinations
     */
    private static final Point DESTINATION_LOCATION_1 = new Point(75, 100);
    private static final Point DESTINATION_LOCATION_2 = new Point(75, 200);
    private static final Point DESTINATION_LOCATION_3 = new Point(75, 300);
    private static final Point DESTINATION_LOCATION_4 = new Point(75, 400);

    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(-Card.ORIGINAL_CARD_WIDTH, -Card.ORIGINAL_CARD_HEIGHT);
    
    public Yukon(final Image image)
    {
        super(image, StackType.Vertical);
        
        //add the holder locations in the game
        addHolder(Key.Destination1, DESTINATION_LOCATION_1, StackType.Same);
        addHolder(Key.Destination2, DESTINATION_LOCATION_2, StackType.Same);
        addHolder(Key.Destination3, DESTINATION_LOCATION_3, StackType.Same);
        addHolder(Key.Destination4, DESTINATION_LOCATION_4, StackType.Same);
        addHolder(Key.Playable1, PLAYABLE_LOCATION_1.x + (0 * PIXEL_WIDTH), PLAYABLE_LOCATION_1.y, StackType.Vertical);
        addHolder(Key.Playable2, PLAYABLE_LOCATION_1.x + (1 * PIXEL_WIDTH), PLAYABLE_LOCATION_1.y, StackType.Vertical);
        addHolder(Key.Playable3, PLAYABLE_LOCATION_1.x + (2 * PIXEL_WIDTH), PLAYABLE_LOCATION_1.y, StackType.Vertical);
        addHolder(Key.Playable4, PLAYABLE_LOCATION_1.x + (3 * PIXEL_WIDTH), PLAYABLE_LOCATION_1.y, StackType.Vertical);
        addHolder(Key.Playable5, PLAYABLE_LOCATION_1.x + (4 * PIXEL_WIDTH), PLAYABLE_LOCATION_1.y, StackType.Vertical);
        addHolder(Key.Playable6, PLAYABLE_LOCATION_1.x + (5 * PIXEL_WIDTH), PLAYABLE_LOCATION_1.y, StackType.Vertical);
        addHolder(Key.Playable7, PLAYABLE_LOCATION_1.x + (6 * PIXEL_WIDTH), PLAYABLE_LOCATION_1.y, StackType.Vertical);
        addHolder(Key.Deck, DECK_START_LOCATION, StackType.Same);
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
                getHolder(Key.Deck).add(new Card(suit, value, back, Key.Deck));
            }
        }
        
        //flag create as completed
        setCreateComplete(true);
    }
    
    /**
     * Check if we have a winner or if the game is over
     */
    @Override
    public void validate()
    {
        //if there are any existing playable cards check again
        if (!getHolder(Key.Playable1).isEmpty() || !getHolder(Key.Playable2).isEmpty())
            return;
        if (!getHolder(Key.Playable3).isEmpty() || !getHolder(Key.Playable4).isEmpty())
            return;
        if (!getHolder(Key.Playable5).isEmpty() || !getHolder(Key.Playable6).isEmpty())
            return;
        if (!getHolder(Key.Playable7).isEmpty())
            return;
        
        //flag game over and winner
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
            if (getHolder(Key.Playable1).getSize() < 1)
            {
                card.setDestination(getHolder(Key.Playable1).getDestinationPoint(), Key.Playable1);
                
                //don't hide
                card.setHidden(false);
            }
            else if (getHolder(Key.Playable2).getSize() < 6)
            {
                card.setHidden(getHolder(Key.Playable2).getSize() < 1);
                card.setDestination(getHolder(Key.Playable2).getDestinationPoint(), Key.Playable2);
            }
            else if (getHolder(Key.Playable3).getSize() < 7)
            {
                card.setHidden(getHolder(Key.Playable3).getSize() < 2);
                card.setDestination(getHolder(Key.Playable3).getDestinationPoint(), Key.Playable3);
            }
            else if (getHolder(Key.Playable4).getSize() < 8)
            {
                card.setHidden(getHolder(Key.Playable4).getSize() < 3);
                card.setDestination(getHolder(Key.Playable4).getDestinationPoint(), Key.Playable4);
            }
            else if (getHolder(Key.Playable5).getSize() < 9)
            {
                card.setHidden(getHolder(Key.Playable5).getSize() < 4);
                card.setDestination(getHolder(Key.Playable5).getDestinationPoint(), Key.Playable5);
            }
            else if (getHolder(Key.Playable6).getSize() < 10)
            {
                card.setHidden(getHolder(Key.Playable6).getSize() < 5);
                card.setDestination(getHolder(Key.Playable6).getDestinationPoint(), Key.Playable6);
            }
            else if (getHolder(Key.Playable7).getSize() < 11)
            {
                card.setHidden(getHolder(Key.Playable7).getSize() < 6);
                card.setDestination(getHolder(Key.Playable7).getDestinationPoint(), Key.Playable7);
            }
            
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
            
            //if the holder is empty, we are done dealing
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
        //if the game is over no need to continue
        if (hasGameover())
            return;
        
        if (!isCreateComplete())
        {
            //create the deck
            create(engine.getRandom());
            
            //no need to continue
            return;
        }
        else if (!isShuffleComplete())
        {
            //shuffle it
            shuffle(engine.getRandom(), getHolder(Key.Deck));
            
            //no need to continue
            return;
        }
        else if (!isDealComplete())
        {
            //deal the cards
            deal(engine.getTime());
            
            //no need to continue
            return;
        }
        
        //check user input
        if (engine.getMouse().isMouseDragged() && !engine.getMouse().isMouseReleased())
        {
            //get the mouse location
            final int x = engine.getMouse().getLocation().x;
            final int y = engine.getMouse().getLocation().y;

            //update card location
            getDefaultHolder().updateLocation(x, y);
            
            if (getDefaultHolder().isEmpty())
            {
                for (Key key : Key.values())
                {
                    //we can't take cards directly from the deck
                    if (key == Key.Deck)
                        continue;
                    
                    //did we click in this holder deck and are there cards here
                    if (getHolder(key).hasCard(x, y))
                    {
                        //assign the selected cards
                        KlondikeHelper.assignCards(getDefaultHolder(), getHolder(key), key, x, y);
                    }
                }
            }
            else
            {
                //update the location
                getDefaultHolder().updateLocation(x, y);
            }
        }
        else if (engine.getMouse().isMouseReleased())
        {
            //get the mouse location
            final int x = engine.getMouse().getLocation().x;
            final int y = engine.getMouse().getLocation().y;
            
            //check if the cards can be placed, or should be brought back to their source
            if (!getDefaultHolder().isEmpty())
            {
                //get the first card
                final Card tmp = getDefaultHolder().getFirstCard();
                
                //don't continue if the card is from the deck
                if (tmp.getSourceHolderKey() == Key.Deck)
                    return;
                
                //determine if the card(s) can be added in these places
                if (getHolder(Key.Destination1).hasLocation(x, y))
                {
                    KlondikeHelper.placeDestinationCards(getDefaultHolder(), getHolder(Key.Destination1), Key.Destination1);
                }
                else if (getHolder(Key.Destination2).hasLocation(x, y))
                {
                    KlondikeHelper.placeDestinationCards(getDefaultHolder(), getHolder(Key.Destination2), Key.Destination2);
                }
                else if (getHolder(Key.Destination3).hasLocation(x, y))
                {
                    KlondikeHelper.placeDestinationCards(getDefaultHolder(), getHolder(Key.Destination3), Key.Destination3);
                }
                else if (getHolder(Key.Destination4).hasLocation(x, y))
                {
                    KlondikeHelper.placeDestinationCards(getDefaultHolder(), getHolder(Key.Destination4), Key.Destination4);
                }
                else if (getHolder(Key.Playable1).hasLocation(x, y))
                {
                    KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(Key.Playable1), Key.Playable1);
                }
                else if (getHolder(Key.Playable2).hasLocation(x, y))
                {
                    KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(Key.Playable2), Key.Playable2);
                }
                else if (getHolder(Key.Playable3).hasLocation(x, y))
                {
                    KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(Key.Playable3), Key.Playable3);
                }
                else if (getHolder(Key.Playable4).hasLocation(x, y))
                {
                    KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(Key.Playable4), Key.Playable4);
                }
                else if (getHolder(Key.Playable5).hasLocation(x, y))
                {
                    KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(Key.Playable5), Key.Playable5);
                }
                else if (getHolder(Key.Playable6).hasLocation(x, y))
                {
                    KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(Key.Playable6), Key.Playable6);
                }
                else if (getHolder(Key.Playable7).hasLocation(x, y))
                {
                    KlondikeHelper.placePlayableCards(getDefaultHolder(), getHolder(Key.Playable7), Key.Playable7);
                }
                else
                {
                    //set the destination for all cards
                    getDefaultHolder().setDestination(getHolder(tmp.getSourceHolderKey()), tmp.getSourceHolderKey());

                    //set the start location for all
                    getDefaultHolder().setStart();
                }
            }
        }
        else
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
                    
                    //add to the destination holder
                    getHolder(card.getDestinationHolderKey()).add(card);
                }

                //make sure the top cards are visible
                showPlayableCards();
                
                //now remove all cards from default holder
                getDefaultHolder().removeAll();

                //validate and check if the game has ended
                validate();
            }
        }
    }
    
    /**
     * Make sure all playable cards are visible
     */
    private void showPlayableCards()
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
    }
}