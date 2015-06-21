package com.gamesbykevin.solitaire.solitaire.klondike;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.solitaire.Solitaire;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * The Classic solitaire game (Klondike )
 * @author GOD
 */
public class Klondike extends Solitaire
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
        OptionalPile 
    }
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(75, 100);
    
    /**
     * Where the optional cards are located
     */
    private static final Point OPTIONAL_PILE_START_LOCATION = new Point(175, 100);
    
    /**
     * The (x,y) locations for the destinations
     */
    private static final Point DESTINATION_LOCATION_1 = new Point(325, 100);
    private static final Point DESTINATION_LOCATION_2 = new Point(425, 100);
    private static final Point DESTINATION_LOCATION_3 = new Point(525, 100);
    private static final Point DESTINATION_LOCATION_4 = new Point(625, 100);

    /**
     * The (x,y) locations for the playable holders
     */
    private static final Point PLAYABLE_LOCATION_1 = new Point(75, 200);
    private static final Point PLAYABLE_LOCATION_2 = new Point(175, 200);
    private static final Point PLAYABLE_LOCATION_3 = new Point(275, 200);
    private static final Point PLAYABLE_LOCATION_4 = new Point(375, 200);
    private static final Point PLAYABLE_LOCATION_5 = new Point(475, 200);
    private static final Point PLAYABLE_LOCATION_6 = new Point(575, 200);
    private static final Point PLAYABLE_LOCATION_7 = new Point(675, 200);
    
    public Klondike(final Image image)
    {
        //store the sprite sheet image
        super(image);
        
        //add the holder locations in the game
        addHolder(Key.Destination1, DESTINATION_LOCATION_1, StackType.Same);
        addHolder(Key.Destination2, DESTINATION_LOCATION_2, StackType.Same);
        addHolder(Key.Destination3, DESTINATION_LOCATION_3, StackType.Same);
        addHolder(Key.Destination4, DESTINATION_LOCATION_4, StackType.Same);
        addHolder(Key.Playable1, PLAYABLE_LOCATION_1, StackType.Vertical);
        addHolder(Key.Playable2, PLAYABLE_LOCATION_2, StackType.Vertical);
        addHolder(Key.Playable3, PLAYABLE_LOCATION_3, StackType.Vertical);
        addHolder(Key.Playable4, PLAYABLE_LOCATION_4, StackType.Vertical);
        addHolder(Key.Playable5, PLAYABLE_LOCATION_5, StackType.Vertical);
        addHolder(Key.Playable6, PLAYABLE_LOCATION_6, StackType.Vertical);
        addHolder(Key.Playable7, PLAYABLE_LOCATION_7, StackType.Vertical);
        addHolder(Key.OptionalPile, OPTIONAL_PILE_START_LOCATION, StackType.Same);
        addHolder(Key.Deck, DECK_START_LOCATION, StackType.Same);
        
        //create the default holder that the player controls
        createDefaultHolder(StackType.Vertical);
    }
    
    /**
     * Add card holder to the list
     * @param key Unique key to access the holder in the future
     * @param location The location (x,y) of the holder
     * @param type How will the cards look in the holder
     */
    private void addHolder(final Key key, final Point location, final StackType type)
    {
        //create a holder and set location
        Holder holder = new Holder(type);
        holder.setLocation(location);
        
        //add to list
        super.addHolder(key, holder);
    }
    
    /**
     * Clean resources
     */
    @Override
    public void dispose()
    {
        super.dispose();
    }
    
    /**
     * Check if we have a winner or if the game is over
     */
    @Override
    public void validate()
    {
        if (!getDefaultHolder().isEmpty())
            return;
        if (!getHolder(Key.Deck).isEmpty())
            return;
        if (!getHolder(Key.OptionalPile).isEmpty())
            return;
        if (!getHolder(Key.Playable1).isEmpty())
            return;
        if (!getHolder(Key.Playable2).isEmpty())
            return;
        if (!getHolder(Key.Playable3).isEmpty())
            return;
        if (!getHolder(Key.Playable4).isEmpty())
            return;
        if (!getHolder(Key.Playable5).isEmpty())
            return;
        if (!getHolder(Key.Playable6).isEmpty())
            return;
        if (!getHolder(Key.Playable7).isEmpty())
            return;
        
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
                getHolder(Key.Deck).add(new Card(suit, value, back, Key.Deck));
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
        
        if (!card.hasDestination())
        {
            //now determine where it should be placed
            if (getHolder(Key.Playable1).isEmpty())
            {
                //set the destination for this card
                card.setDestination(getHolder(Key.Playable1).getDestinationPoint(), Key.Playable1);
            }
            else if (getHolder(Key.Playable2).getSize() <= getHolder(Key.Playable1).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(Key.Playable1).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(Key.Playable2).getDestinationPoint(), Key.Playable2);
            }
            else if (getHolder(Key.Playable3).getSize() <= getHolder(Key.Playable2).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(Key.Playable2).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(Key.Playable3).getDestinationPoint(), Key.Playable3);
            }
            else if (getHolder(Key.Playable4).getSize() <= getHolder(Key.Playable3).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(Key.Playable3).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(Key.Playable4).getDestinationPoint(), Key.Playable4);
            }
            else if (getHolder(Key.Playable5).getSize() <= getHolder(Key.Playable4).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(Key.Playable4).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(Key.Playable5).getDestinationPoint(), Key.Playable5);
            }
            else if (getHolder(Key.Playable6).getSize() <= getHolder(Key.Playable5).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(Key.Playable5).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(Key.Playable6).getDestinationPoint(), Key.Playable6);
            }
            else if (getHolder(Key.Playable7).getSize() <= getHolder(Key.Playable6).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(Key.Playable6).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(Key.Playable7).getDestinationPoint(), Key.Playable7);
            }
            else
            {
                //make the last card in the previous holder visible
                getHolder(Key.Playable7).getLastCard().setHidden(false);
                
                //assign remaining cards to their destination at the deck
                getHolder(Key.Deck).setDestination(getHolder(Key.Deck), Key.Deck);
                
                //flag deal complete
                setDealComplete(true);
                
                //no need to continue;
                return;
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
            
            //assign current location
            getDefaultHolder().setLocation(x, y);

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
            else
            {
                //if the deck was selected
                if (getHolder(Key.Deck).hasLocation(x, y))
                {
                    //make sure there are cards
                    if (!getHolder(Key.Deck).isEmpty())
                    {
                        //show this card
                        getHolder(Key.Deck).getLastCard().setHidden(false);
                        
                        //set the default holder location
                        getDefaultHolder().setLocation(getHolder(Key.Deck));
                        
                        //assign the top card to the default holder
                        KlondikeHelper.assignCards(getDefaultHolder(), getHolder(Key.Deck), Key.Deck, x, y);

                        //now set the optional pile as the destination
                        getDefaultHolder().setDestination(getHolder(Key.OptionalPile), Key.OptionalPile);
                    }
                    else
                    {
                        //if the deck is empty add the cards from the optional pile back to it
                        for (int index = getHolder(Key.OptionalPile).getSize() - 1; index >= 0; index--)
                        {
                            //get the current card
                            final Card card = getHolder(Key.OptionalPile).getCard(index);
                            
                            //add these cards to the deck
                            card.setDestination(getHolder(Key.Deck).getDestinationPoint(), Key.Deck);
                            
                            //hide the card(s) again
                            card.setHidden(true);
                            
                            //add to the deck
                            getHolder(Key.Deck).add(card);
                        }
                        
                        //now remove from the optional pile
                        for (int index = 0; index < getHolder(Key.Deck).getSize(); index++)
                        {
                            getHolder(Key.OptionalPile).remove(getHolder(Key.Deck).getCard(index));
                        }
                    }
                }
            }
        }
        else
        {
            //if cards exist
            if (!getDefaultHolder().isEmpty())
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
                    
                    //add cards back to the destination
                    getHolder(card.getDestinationHolderKey()).add(getDefaultHolder());
                    
                    //make sure all valid cards are displayed
                    showPlayableCards();
                    
                    //check if the game has ended
                    validate();
                }
            }
        }
    }
    
    /**
     * Make sure all playable cards are visible
     */
    private void showPlayableCards()
    {
        if (!getHolder(Key.OptionalPile).isEmpty())
            getHolder(Key.OptionalPile).getLastCard().setHidden(false);
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
    
    @Override
    public void render(final Graphics graphics) throws Exception
    {
        super.render(graphics);
    }
}