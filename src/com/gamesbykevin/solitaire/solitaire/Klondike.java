package com.gamesbykevin.solitaire.solitaire;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import com.gamesbykevin.solitaire.engine.Engine;

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
    private enum HolderKey
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
        addHolder(HolderKey.Destination1, DESTINATION_LOCATION_1, StackType.Same);
        addHolder(HolderKey.Destination2, DESTINATION_LOCATION_2, StackType.Same);
        addHolder(HolderKey.Destination3, DESTINATION_LOCATION_3, StackType.Same);
        addHolder(HolderKey.Destination4, DESTINATION_LOCATION_4, StackType.Same);
        addHolder(HolderKey.Playable1, PLAYABLE_LOCATION_1, StackType.Vertical);
        addHolder(HolderKey.Playable2, PLAYABLE_LOCATION_2, StackType.Vertical);
        addHolder(HolderKey.Playable3, PLAYABLE_LOCATION_3, StackType.Vertical);
        addHolder(HolderKey.Playable4, PLAYABLE_LOCATION_4, StackType.Vertical);
        addHolder(HolderKey.Playable5, PLAYABLE_LOCATION_5, StackType.Vertical);
        addHolder(HolderKey.Playable6, PLAYABLE_LOCATION_6, StackType.Vertical);
        addHolder(HolderKey.Playable7, PLAYABLE_LOCATION_7, StackType.Vertical);
        addHolder(HolderKey.OptionalPile, OPTIONAL_PILE_START_LOCATION, StackType.Same);
        addHolder(HolderKey.Deck, DECK_START_LOCATION, StackType.Same);
        
        //create the default holder that the player controls
        createDefaultHolder(StackType.Vertical);
    }
    
    /**
     * Add card holder to the list
     * @param key Unique key to access the holder in the future
     * @param location The location (x,y) of the holder
     * @param type How will the cards look in the holder
     */
    private void addHolder(final HolderKey key, final Point location, final StackType type)
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
        if (!getHolder(HolderKey.Deck).isEmpty())
            return;
        if (!getHolder(HolderKey.OptionalPile).isEmpty())
            return;
        if (!getHolder(HolderKey.Playable1).isEmpty())
            return;
        if (!getHolder(HolderKey.Playable2).isEmpty())
            return;
        if (!getHolder(HolderKey.Playable3).isEmpty())
            return;
        if (!getHolder(HolderKey.Playable4).isEmpty())
            return;
        if (!getHolder(HolderKey.Playable5).isEmpty())
            return;
        if (!getHolder(HolderKey.Playable6).isEmpty())
            return;
        if (!getHolder(HolderKey.Playable7).isEmpty())
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
        //klondie solitaire will have one complete deck
        for (Suit suit : Suit.values())
        {
            for (Value value : Value.values())
            {
                //add to our deck
                getHolder(HolderKey.Deck).add(new Card(suit, value, Back.Back1, HolderKey.Deck));
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
        final Card card = getHolder(HolderKey.Deck).getLastCard();
        
        if (!card.hasDestination())
        {
            //now determine where it should be placed
            if (getHolder(HolderKey.Playable1).isEmpty())
            {
                //set the destination for this card
                card.setDestination(getHolder(HolderKey.Playable1).getDestinationPoint(), HolderKey.Playable1);
            }
            else if (getHolder(HolderKey.Playable2).getSize() <= getHolder(HolderKey.Playable1).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(HolderKey.Playable1).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(HolderKey.Playable2).getDestinationPoint(), HolderKey.Playable2);
            }
            else if (getHolder(HolderKey.Playable3).getSize() <= getHolder(HolderKey.Playable2).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(HolderKey.Playable2).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(HolderKey.Playable3).getDestinationPoint(), HolderKey.Playable3);
            }
            else if (getHolder(HolderKey.Playable4).getSize() <= getHolder(HolderKey.Playable3).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(HolderKey.Playable3).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(HolderKey.Playable4).getDestinationPoint(), HolderKey.Playable4);
            }
            else if (getHolder(HolderKey.Playable5).getSize() <= getHolder(HolderKey.Playable4).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(HolderKey.Playable4).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(HolderKey.Playable5).getDestinationPoint(), HolderKey.Playable5);
            }
            else if (getHolder(HolderKey.Playable6).getSize() <= getHolder(HolderKey.Playable5).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(HolderKey.Playable5).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(HolderKey.Playable6).getDestinationPoint(), HolderKey.Playable6);
            }
            else if (getHolder(HolderKey.Playable7).getSize() <= getHolder(HolderKey.Playable6).getSize())
            {
                //make the last card in the previous holder visible
                getHolder(HolderKey.Playable6).getLastCard().setHidden(false);
                
                //set the destination for this card
                card.setDestination(getHolder(HolderKey.Playable7).getDestinationPoint(), HolderKey.Playable7);
            }
            else
            {
                //make the last card in the previous holder visible
                getHolder(HolderKey.Playable7).getLastCard().setHidden(false);
                
                //assign remaining cards to their destination at the deck
                getHolder(HolderKey.Deck).setDestination(getHolder(HolderKey.Deck), HolderKey.Deck);
                
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
            getHolder(HolderKey.Deck).remove(card);
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
            shuffle(engine.getRandom(), getHolder(HolderKey.Deck));
            
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
                for (HolderKey key : HolderKey.values())
                {
                    //we can't take cards directly from the deck
                    if (key == HolderKey.Deck)
                        continue;
                    
                    //did we click in this holder deck
                    if (getHolder(key).hasCard(x, y))
                    {
                        //assign the selected cards
                        assignCards(getHolder(key), key, x, y);
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
                if (tmp.getSourceHolderKey() == HolderKey.Deck)
                    return;
                
                //determine if the card(s) can be added in these places
                if (getHolder(HolderKey.Destination1).hasLocation(x, y))
                {
                    placeDestinationCards(HolderKey.Destination1);
                }
                else if (getHolder(HolderKey.Destination2).hasLocation(x, y))
                {
                    placeDestinationCards(HolderKey.Destination2);
                }
                else if (getHolder(HolderKey.Destination3).hasLocation(x, y))
                {
                    placeDestinationCards(HolderKey.Destination3);
                }
                else if (getHolder(HolderKey.Destination4).hasLocation(x, y))
                {
                    placeDestinationCards(HolderKey.Destination4);
                }
                else if (getHolder(HolderKey.Playable1).hasLocation(x, y))
                {
                    placePlayableCards(HolderKey.Playable1);
                }
                else if (getHolder(HolderKey.Playable2).hasLocation(x, y))
                {
                    placePlayableCards(HolderKey.Playable2);
                }
                else if (getHolder(HolderKey.Playable3).hasLocation(x, y))
                {
                    placePlayableCards(HolderKey.Playable3);
                }
                else if (getHolder(HolderKey.Playable4).hasLocation(x, y))
                {
                    placePlayableCards(HolderKey.Playable4);
                }
                else if (getHolder(HolderKey.Playable5).hasLocation(x, y))
                {
                    placePlayableCards(HolderKey.Playable5);
                }
                else if (getHolder(HolderKey.Playable6).hasLocation(x, y))
                {
                    placePlayableCards(HolderKey.Playable6);
                }
                else if (getHolder(HolderKey.Playable7).hasLocation(x, y))
                {
                    placePlayableCards(HolderKey.Playable7);
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
                if (getHolder(HolderKey.Deck).hasLocation(x, y))
                {
                    //make sure there are cards
                    if (!getHolder(HolderKey.Deck).isEmpty())
                    {
                        //show this card
                        getHolder(HolderKey.Deck).getLastCard().setHidden(false);
                        
                        //set the default holder location
                        getDefaultHolder().setLocation(x, y);
                        
                        //assign the top card to the default holder
                        assignCards(getHolder(HolderKey.Deck), HolderKey.Deck, x, y);

                        //now set the optional pile as the destination
                        getDefaultHolder().setDestination(getHolder(HolderKey.OptionalPile), HolderKey.OptionalPile);
                    }
                    else
                    {
                        //if the deck is empty add the cards from the optional pile back to it
                        for (int index = getHolder(HolderKey.OptionalPile).getSize() - 1; index >= 0; index--)
                        {
                            //get the current card
                            final Card card = getHolder(HolderKey.OptionalPile).getCard(index);
                            
                            //add these cards to the deck
                            card.setDestination(getHolder(HolderKey.Deck).getDestinationPoint(), HolderKey.Deck);
                            
                            //hide the card(s) again
                            card.setHidden(true);
                            
                            //add to the deck
                            getHolder(HolderKey.Deck).add(card);
                        }
                        
                        //now remove from the optional pile
                        for (int index = 0; index < getHolder(HolderKey.Deck).getSize(); index++)
                        {
                            getHolder(HolderKey.OptionalPile).remove(getHolder(HolderKey.Deck).getCard(index));
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
     * Place the cards accordingly
     * @param key The holder we want to place the cards
     * @return true if a card(s) was placed, false otherwise
     * @throws Exception 
     */
    private boolean placeDestinationCards(final HolderKey key) throws Exception
    {
        if (!getHolder(key).isEmpty())
        {
            //set the current as the start location for all
            getDefaultHolder().setStart();

            //check if we can place the card first, before we place
            if (canPlaceCardInDestination(getHolder(key).getLastCard(), getDefaultHolder().getFirstCard()))
            {
                //set the destination for all cards
                getDefaultHolder().setDestination(getHolder(key), key);
                
                //a card was placed
                return true;
            }
        }
        else
        {
            //set the current as the start location for all
            getDefaultHolder().setStart();

            //only a king can be placed on an empty holder
            if (getDefaultHolder().getFirstCard().getValue() == Value.Ace)
            {
                //set the destination
                getDefaultHolder().setDestination(getHolder(key), key);
                
                //a card was placed
                return true;
            }
        }
        
        //no cards were placed
        return false;
    }
    
    /**
     * Place the cards accordingly
     * @param key The holder we want to place the cards
     * @return true if a card(s) was placed, false otherwise
     * @throws Exception 
     */
    private boolean placePlayableCards(final HolderKey key) throws Exception
    {
        if (!getHolder(key).isEmpty())
        {
            //set the current as the start location for all
            getDefaultHolder().setStart();

            //check if we can place the card first, before we place
            if (canPlaceCardInPlayable(getHolder(key).getLastCard(), getDefaultHolder().getFirstCard()))
            {
                //set the destination for all cards
                getDefaultHolder().setDestination(getHolder(key), key);
                
                //a card was placed
                return true;
            }
        }
        else
        {
            //set the current as the start location for all
            getDefaultHolder().setStart();

            //only a king can be placed on an empty holder
            if (getDefaultHolder().getFirstCard().getValue() == Value.King)
            {
                //set the destination
                getDefaultHolder().setDestination(getHolder(key), key);
                
                //a card was placed
                return true;
            }
        }
        
        //no cards were placed
        return false;
    }
    
    /**
     * Make sure all playable cards are visible
     */
    private void showPlayableCards()
    {
        if (!getHolder(HolderKey.OptionalPile).isEmpty())
            getHolder(HolderKey.OptionalPile).getLastCard().setHidden(false);
        if (!getHolder(HolderKey.Playable1).isEmpty())
            getHolder(HolderKey.Playable1).getLastCard().setHidden(false);
        if (!getHolder(HolderKey.Playable2).isEmpty())
            getHolder(HolderKey.Playable2).getLastCard().setHidden(false);
        if (!getHolder(HolderKey.Playable3).isEmpty())
            getHolder(HolderKey.Playable3).getLastCard().setHidden(false);
        if (!getHolder(HolderKey.Playable4).isEmpty())
            getHolder(HolderKey.Playable4).getLastCard().setHidden(false);
        if (!getHolder(HolderKey.Playable5).isEmpty())
            getHolder(HolderKey.Playable5).getLastCard().setHidden(false);
        if (!getHolder(HolderKey.Playable6).isEmpty())
            getHolder(HolderKey.Playable6).getLastCard().setHidden(false);
        if (!getHolder(HolderKey.Playable7).isEmpty())
            getHolder(HolderKey.Playable7).getLastCard().setHidden(false);
    }
    
    /**
     * Can the card be placed in the destination area?
     * @param card1 The existing card
     * @param card2 The card we want to add as a child
     * @return true if card2 can be placed as a child of card1, false otherwise
     * @throws Exception if unknown suit or face value
     */
    private boolean canPlaceCardInDestination(final Card card1, final Card card2) throws Exception
    {
        //if the suits do not match, return false
        if (card1.getSuit() != card2.getSuit())
            return false;
        
        switch (card1.getValue())
        {
            case Ace:
                if (card2.getValue() != Value.Two)
                    return false;
                break;
                
            case Two:
                if (card2.getValue() != Value.Three)
                    return false;
                break;
                
            case Three:
                if (card2.getValue() != Value.Four)
                    return false;
                break;
                
            case Four:
                if (card2.getValue() != Value.Five)
                    return false;
                break;
                
            case Five:
                if (card2.getValue() != Value.Six)
                    return false;
                break;
                
            case Six:
                if (card2.getValue() != Value.Seven)
                    return false;
                break;
                
            case Seven:
                if (card2.getValue() != Value.Eight)
                    return false;
                break;
                
            case Eight:
                if (card2.getValue() != Value.Nine)
                    return false;
                break;
                
            case Nine:
                if (card2.getValue() != Value.Ten)
                    return false;
                break;
                
            case Ten:
                if (card2.getValue() != Value.Jack)
                    return false;
                break;
                
            case Jack:
                if (card2.getValue() != Value.Queen)
                    return false;
                break;
                
            case Queen:
                if (card2.getValue() != Value.King)
                    return false;
                break;
                
            //nothing can be added to the king
            case King:
                return false;
                
            default:
                throw new Exception("Face value not found here " + card1.getValue().toString());
        }
        
        //we can place the card
        return true;
    }
    
    /**
     * Can the card be placed in the playable area?
     * @param card1 The existing card
     * @param card2 The card we want to add as a child
     * @return true if card2 can be placed as a child of card1, false otherwise
     * @throws Exception if unknown suit or face value
     */
    private boolean canPlaceCardInPlayable(final Card card1, final Card card2) throws Exception
    {
        //first make sure the suits are compatible
        switch (card1.getSuit())
        {
            case Clubs:
                switch (card2.getSuit())
                {
                    case Clubs:
                    case Spades:
                        return false;
                }
                break;
                
            case Diamonds:
                switch (card2.getSuit())
                {
                    case Diamonds:
                    case Hearts:
                        return false;
                }
                break;
                
            case Spades:
                switch (card2.getSuit())
                {
                    case Clubs:
                    case Spades:
                        return false;
                }
                break;
                
            case Hearts:
                switch (card2.getSuit())
                {
                    case Diamonds:
                    case Hearts:
                        return false;
                }
                break;
                
            default:
                throw new Exception("Suit not found here " + card1.getSuit().toString());
        }
        
        //now check the card value
        switch (card1.getValue())
        {
            //nothing can be add as a child of ace
            case Ace:
                return false;
                
            case Two:
                if (card2.getValue() != Value.Ace)
                    return false;
                break;
                
            case Three:
                if (card2.getValue() != Value.Two)
                    return false;
                break;
                
            case Four:
                if (card2.getValue() != Value.Three)
                    return false;
                break;
                
            case Five:
                if (card2.getValue() != Value.Four)
                    return false;
                break;
                
            case Six:
                if (card2.getValue() != Value.Five)
                    return false;
                break;
                
            case Seven:
                if (card2.getValue() != Value.Six)
                    return false;
                break;
                
            case Eight:
                if (card2.getValue() != Value.Seven)
                    return false;
                break;
                
            case Nine:
                if (card2.getValue() != Value.Eight)
                    return false;
                break;
                
            case Ten:
                if (card2.getValue() != Value.Nine)
                    return false;
                break;
                
            case Jack:
                if (card2.getValue() != Value.Ten)
                    return false;
                break;
                
            case Queen:
                if (card2.getValue() != Value.Jack)
                    return false;
                break;
                
            case King:
                if (card2.getValue() != Value.Queen)
                    return false;
                break;
                    
            default:
                throw new Exception("Face value not found here " + card1.getValue().toString());
        }
        
        //we can place the card
        return true;
    }
    
    /**
     * Assign the card(s) to the default place holder.<br>
     * The card found at (x, y) and all its children will be added to the default place holder
     * @param holder The holder we want to check
     * @param sourceHolderKey The source holder the cards came from
     * @param x x-coordinate
     * @param y y-coordinate
     * @throws Exception 
     */
    private void assignCards(final Holder holder, final HolderKey sourceHolderKey, final int x, final int y) throws Exception
    {
        for (int i = holder.getSize() - 1; i >= 0; i--)
        {
            //get the current card
            final Card card = holder.getCard(i);
            
            //if the card is hidden we can't select it
            if (card.isHidden())
                continue;
            
            //check if we found the parent card
            if (card.hasLocation(x, y))
            {
                //add this card and all children to the default holder
                for (int index = i; index < holder.getSize(); index++)
                {
                    //assign the source
                    holder.getCard(index).setSourceHolderKey(sourceHolderKey);
                    
                    //add to the default holder
                    getDefaultHolder().add(holder.getCard(index));
                }
                
                //now remove these cards from the existing holder
                for (int index = 0; index < getDefaultHolder().getSize(); index++)
                {
                    //remove from the existing holder
                    holder.remove(getDefaultHolder().getCard(index));
                }
                
                //we have our assigned cards, no need to continue
                return;
            }
        }
    }
    
    @Override
    public void render(final Graphics graphics) throws Exception
    {
        super.render(graphics);
    }
}