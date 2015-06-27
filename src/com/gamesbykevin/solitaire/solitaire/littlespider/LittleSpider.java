package com.gamesbykevin.solitaire.solitaire.littlespider;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.golf.GolfHelper;
import com.gamesbykevin.solitaire.solitaire.klondike.KlondikeHelper;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Little Spider Solitaire
 * @author GOD
 */
public final class LittleSpider extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Playable1, Playable2, Playable3, Playable4, 
        Playable5, Playable6, Playable7, Playable8, 
        
        Destination1, Destination2, Destination3, Destination4, 
        
        Deck,
    }
    
    /**
     * The number of cards in each playable holder
     */
    protected static final int HOLDER_SIZE = 6;
    
    //the amount of time to move the cards from one point to the next
    private static final long MOVE_CARD_DURATION = Timers.toNanoSeconds(150L);
    
    /**
     * Where the first card is placed for row 1
     */
    private static final Point ROW_1_START_LOCATION = new Point(75, 60);
    
    /**
     * Where the first card is placed for row 2
     */
    private static final Point ROW_2_START_LOCATION = new Point(275, 245);
    
    /**
     * Where the first card is placed for row 3
     */
    private static final Point ROW_3_START_LOCATION = new Point(75, 350);
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(75, 245);
    
    public LittleSpider(final Image image)
    {
        //store the sprite sheet image
        super(image, StackType.Same);
        
        int startX = ROW_1_START_LOCATION.x;
        int startY = ROW_1_START_LOCATION.y;
        
        //The amount of pixels between any given holder on the same row
        final int HOLDER_SPACE_WIDTH = (int)(getDefaultWidth() * 5.0);

        //Adjusted card dimensions
        final int NEW_CARD_HEIGHT = (int)(getDefaultHeight() * .95);
        
        //setup row 1 holders
        for (int index = 0; index < 2; index++)
        {
            //add holder at location
            super.addHolder(Key.values()[index], startX, startY, StackType.Horizontal);
                
            //space each holder out
            startX += HOLDER_SPACE_WIDTH;
        }
        
        startX = ROW_1_START_LOCATION.x;
        startY = ROW_1_START_LOCATION.y + NEW_CARD_HEIGHT;
        
        //setup row 1 holders
        for (int index = 2; index < 4; index++)
        {
            //add holder at location
            super.addHolder(Key.values()[index], startX, startY, StackType.Horizontal);
                
            //space each holder out
            startX += HOLDER_SPACE_WIDTH;
        }
        
        startX = ROW_2_START_LOCATION.x;
        startY = ROW_2_START_LOCATION.y;
        
        //setup row 2 holders
        for (int index = 8; index < 12; index++)
        {
            //add holder at location
            super.addHolder(Key.values()[index], startX, startY, StackType.Same);
            
            //space each holder out
            startX += (getDefaultWidth() * 1.25);
        }
        
        startX = ROW_3_START_LOCATION.x;
        startY = ROW_3_START_LOCATION.y;
        
        //setup row 3 holders
        for (int index = 4; index < 6; index++)
        {
            //add holder at location
            super.addHolder(Key.values()[index], startX, startY, StackType.Horizontal);
            
            //space each holder out
            startX += HOLDER_SPACE_WIDTH;
        }
        
        startX = ROW_3_START_LOCATION.x;
        startY = ROW_3_START_LOCATION.y + NEW_CARD_HEIGHT;
        
        //setup row 3 holders
        for (int index = 6; index < 8; index++)
        {
            //add holder at location
            super.addHolder(Key.values()[index], startX, startY, StackType.Horizontal);
            
            //space each holder out
            startX += HOLDER_SPACE_WIDTH;
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
        //if any cards exist here the game is not over
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
        
        //flag game over and if we won
        super.setGameover(true);
        super.setWinner(true);
    }
    
    /**
     * Make sure we can place the ace here while dealing
     * @param card Card we want to place
     * @return true if card was placed, false otherwise
     */
    private boolean placeAce(final Card card) throws Exception
    {
        //if we already have cards placed here, return false
        if (!getHolder(Key.Destination1).isEmpty() && !getHolder(Key.Destination2).isEmpty())
            return false;
        
        //if this is not empty
        if (!getHolder(Key.Destination3).isEmpty())
        {
            //make sure another holder doesn't have the same suit
            if (card.hasSuit(getHolder(Key.Destination3).getFirstCard()))
                return false;
        }
        
        //if this is not empty
        if (!getHolder(Key.Destination4).isEmpty())
        {
            //make sure another holder doesn't have the same suit
            if (card.hasSuit(getHolder(Key.Destination4).getFirstCard()))
                return false;
        }
        
        if (getHolder(Key.Destination1).isEmpty())
        {
            //display card
            card.setHidden(false);

            //set the destination as the source
            card.setSourceHolderKey(Key.Destination1);

            //add it to the destination
            getHolder(Key.Destination1).add(card);

            //remove from existing holder
            getHolder(Key.Deck).remove(card);
        }
        else if (getHolder(Key.Destination2).isEmpty())
        {
            //display card
            card.setHidden(false);

            //set the destination as the source
            card.setSourceHolderKey(Key.Destination2);

            //add it to the destination
            getHolder(Key.Destination2).add(card);

            //remove from existing holder
            getHolder(Key.Deck).remove(card);
        }
        
        //card was placed successfully
        return true;
    }
    
    /**
     * Make sure we can place the king here while dealing
     * @param card Card we want to place
     * @return true if we can place, false otherwise
     */
    private boolean placeKing(final Card card) throws Exception
    {
        //if we already have cards placed here, return false
        if (!getHolder(Key.Destination3).isEmpty() && !getHolder(Key.Destination4).isEmpty())
            return false;
        
        //if this is not empty
        if (!getHolder(Key.Destination1).isEmpty())
        {
            //make sure another holder doesn't have the same suit
            if (card.hasSuit(getHolder(Key.Destination1).getFirstCard()))
                return false;
        }
        
        //if this is not empty
        if (!getHolder(Key.Destination2).isEmpty())
        {
            //make sure another holder doesn't have the same suit
            if (card.hasSuit(getHolder(Key.Destination2).getFirstCard()))
                return false;
        }
        
        if (getHolder(Key.Destination3).isEmpty())
        {
            //display card
            card.setHidden(false);

            //set the destination as the source
            card.setSourceHolderKey(Key.Destination3);

            //add it to the destination
            getHolder(Key.Destination3).add(card);

            //remove from existing holder
            getHolder(Key.Deck).remove(card);
        }
        else if (getHolder(Key.Destination4).isEmpty())
        {
            //display card
            card.setHidden(false);

            //set the destination as the source
            card.setSourceHolderKey(Key.Destination4);

            //add it to the destination
            getHolder(Key.Destination4).add(card);

            //remove from existing holder
            getHolder(Key.Deck).remove(card);
        }
        
        //card was placed successfully
        return true;
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
        
        //if not at destination yet
        if (!card.hasDestination())
        {
            if (card.hasValue(Value.Ace))
            {
                //if we were able to place no need to continue
                if (placeAce(card))
                    return;
            }
            else if (card.hasValue(Value.King))
            {
                //if we were able to place no need to continue
                if (placeKing(card))
                    return;
            }
            
            for (Key key : Key.values())
            {
                //we won't deal cards here
                if (key == Key.Deck)
                    continue;
                if (key == Key.Destination1 || key == Key.Destination2)
                    continue;
                if (key == Key.Destination3 || key == Key.Destination4)
                    continue;
                
                if (getHolder(key).getSize() < HOLDER_SIZE)
                {
                    //don't hide the card
                    card.setHidden(getHolder(key).getSize() < HOLDER_SIZE - 1);
                    
                    //target this holder
                    card.setDestination(getHolder(key).getDestinationPoint(), key);

                    //now move the card towards the destination
                    card.moveTowardsDestination(time);
                    
                    
                    //no need to continue
                    return;
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
            
            //if no more cards in the deck, the deal is complete
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
        //if empty we can make a selection
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
                    //we can only select cards from the playable holder(s)
                    if (key == Key.Deck)
                        continue;
                    if (key == Key.Destination1 || key == Key.Destination2)
                        continue;
                    if (key == Key.Destination3 || key == Key.Destination4)
                        continue;
                    
                    //if empty we can't select
                    if (getHolder(key).isEmpty())
                        continue;
                    
                    //get the top card in this holder
                    final Card card = getHolder(key).getLastCard();
                    
                    //if we clicked the top card in the holder
                    if (card.hasLocation(x, y))
                    {
                        //mark it as selected
                        card.setSelected(true);
                        
                        //make the location (x,y) match
                        getDefaultHolder().setLocation(card);
                        
                        //add to default holder
                        getDefaultHolder().add(card);
                        
                        //remove card from source
                        getHolder(key).remove(card);
                    }
                }
            }
        }
        else
        {
            if (engine.getMouse().isMouseDragged())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;
                
                //update card location
                getDefaultHolder().updateLocation(x, y);
            }
            else if (engine.getMouse().isMouseReleased())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;
                
                //if mouse is released check if we can place the card
                getDefaultHolder().setStart();
                
                //we want to check the destination here
                for (Key key : Key.values())
                {
                    if (key == Key.Deck)
                        continue;
                    
                    if (key != Key.Destination1 && 
                        key != Key.Destination2 && 
                        key != Key.Destination3 && 
                        key != Key.Destination4)
                        continue;
                    
                    
                    //make sure we want this card
                    if (getHolder(key).getFirstCard().hasLocation(x, y))
                    {
                        //get the last card to determine how we are to build up this pile
                        final Card card = getHolder(key).getLastCard();
                    
                        //check the first card placed to determine if valid card
                        if (getHolder(key).getFirstCard().hasValue(Value.Ace))
                        {
                            //make sure we can place the card here
                            if (KlondikeHelper.canPlaceCardInDestination(card, getDefaultHolder().getFirstCard()))
                            {
                                //set the destination
                                getDefaultHolder().setDestination(getHolder(key), key);

                                //no need to continue
                                return;
                            }
                        }
                        else if (getHolder(key).getFirstCard().hasValue(Value.King))
                        {
                            //make sure we can place the card here
                            if (LittleSpiderHelper.canPlaceCardInDestination(card, getDefaultHolder().getFirstCard()))
                            {
                                //set the destination
                                getDefaultHolder().setDestination(getHolder(key), key);

                                //no need to continue
                                return;
                            }
                        }
                    }
                }
                
                //now check the playable holders
                for (Key key : Key.values())
                {
                    if (key == Key.Deck)
                        continue;
                    if (key == Key.Destination1 || key == Key.Destination2)
                        continue;
                    if (key == Key.Destination3 || key == Key.Destination4)
                        continue;
                    
                    //don't check empty holders
                    if (getHolder(key).isEmpty())
                        continue;
                    
                    //get the top card in this holder
                    final Card card = getHolder(key).getLastCard();
                    
                    //if we wanted to place on this card
                    if (card.hasLocation(x, y))
                    {
                        //make sure the card rank is a neighbor, regardless of suit
                        if (GolfHelper.isNeighbor(card, getDefaultHolder().getFirstCard()))
                        {
                            //set the destination
                            getDefaultHolder().setDestination(getHolder(key), key);

                            //no need to continue
                            return;
                        }
                    }
                }
            }
            else
            {
                //if we are not yet at the destination
                if (!getDefaultHolder().hasDestination())
                {
                    getDefaultHolder().moveTowardsDestination(engine.getTime());
                }
                else
                {
                    //get the card
                    final Card card = getDefaultHolder().getFirstCard();

                    //de-select the card
                    card.setSelected(false);

                    //add to the destination holder
                    getHolder(card.getDestinationHolderKey()).add(card);

                    //remove all cards from the default holder
                    getDefaultHolder().removeAll();

                    //make sure at least the top card is playable
                    showPlayableCards();

                    //now check for game over
                    validate();
                }
            }
        }
    }
    
    /**
     * Make sure at least the top card is playable
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
        if (!getHolder(Key.Playable8).isEmpty())
            getHolder(Key.Playable8).getLastCard().setHidden(false);
    }
    
    @Override
    public void render(final Graphics graphics) throws Exception
    {
        for (Key key : Key.values())
        {
            getHolder(key).render(graphics, getImage(), getHolder(key).isEmpty() ? false : true);
        }
        
        //render selected cards (if exist)
        getDefaultHolder().render(graphics, getImage(), false);
    }
}