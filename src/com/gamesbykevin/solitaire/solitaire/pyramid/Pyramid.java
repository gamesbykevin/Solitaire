package com.gamesbykevin.solitaire.solitaire.pyramid;

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
 * The solitaire game Pyramid
 * @author GOD
 */
public final class Pyramid extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Row_1_Column_1,
        Row_2_Column_1, Row_2_Column_2,
        Row_3_Column_1, Row_3_Column_2, Row_3_Column_3,
        Row_4_Column_1, Row_4_Column_2, Row_4_Column_3, Row_4_Column_4,
        Row_5_Column_1, Row_5_Column_2, Row_5_Column_3, Row_5_Column_4, Row_5_Column_5,
        Row_6_Column_1, Row_6_Column_2, Row_6_Column_3, Row_6_Column_4, Row_6_Column_5, Row_6_Column_6,
        Row_7_Column_1, Row_7_Column_2, Row_7_Column_3, Row_7_Column_4, Row_7_Column_5, Row_7_Column_6, Row_7_Column_7,
        
        Deck,
        
        OptionalCard,
        
        Destination
    }
    
    /**
     * The number of cards to select
     */
    private static final int SELECT_LIMIT = 2;
    
    /**
     * The target score to remove the cards
     */
    private static final int MATCH_SCORE = 13;
    
    /**
     * The space to place between each card vertically
     */
    private static final double PLACE_CARD_HEIGHT_RATIO = .4;
    
    /**
     * The space to place between each card vertically
     */
    private static final double PLACE_CARD_WIDTH_RATIO = 1.1;
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(75, 100);
    
    /**
     * Where the optional cards are placed
     */
    private static final Point OPTIONAL_START_LOCATION = new Point(175, 100);
    
    /**
     * The destination will be off screen since we don't need it
     */
    private static final Point DESTINATION_LOCATION = new Point(-Card.ORIGINAL_CARD_WIDTH * 2, -Card.ORIGINAL_CARD_HEIGHT * 2);
    
    /**
     * The number of rows in the pyramid
     */
    protected static final int ROWS = 7;
    
    /**
     * Where the first card is placed
     */
    private static final Point ROW_1_COLUMN_1_START_LOCATION = new Point(425, 100);
    
    public Pyramid(final Image image)
    {
        //store the sprite sheet image
        super(image);
        
        //the total number of columns
        int cols = 1;
        
        //the place holder key index
        int index = 0;
        
        //there will be 7 rows of cards
        for (int row = 0; row < ROWS; row++)
        {
            //the y coordinate for this row
            final int y = (int)(ROW_1_COLUMN_1_START_LOCATION.y + (row * (Card.CARD_HEIGHT * PLACE_CARD_HEIGHT_RATIO)));
            
            //the start x coordinate
            int startX = (int)(ROW_1_COLUMN_1_START_LOCATION.x - (cols * (Card.CARD_WIDTH / 2)));
            
            for (int col = 0; col < cols; col++)
            {
                super.addHolder(Key.values()[index], startX, y, StackType.Same);
                
                startX += (Card.CARD_WIDTH * PLACE_CARD_WIDTH_RATIO);
                
                //change the index
                index++;
            }
            
            //increate the column total
            cols++;
        }
        
        //add deck holder
        addHolder(Key.Deck, DECK_START_LOCATION, StackType.Same);
        
        //add holder for cards to select
        addHolder(Key.OptionalCard, OPTIONAL_START_LOCATION, StackType.Same);
        
        //create holder for the destination
        addHolder(Key.Destination, DESTINATION_LOCATION, StackType.Same);
        
        //create the default holder that the player controls
        createDefaultHolder(Holder.StackType.Same);
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
     * Check if we have a winner or if the game is over
     */
    @Override
    public void validate()
    {
        if (!getDefaultHolder().isEmpty())
            return;
        
        for (Key key : Key.values())
        {
            if (key == Key.Deck)
                continue;
            if (key == Key.Destination)
                continue;
            if (key == Key.OptionalCard)
                continue;
            
            //if a card exists, the game hasn't ended
            if (!getHolder(key).isEmpty())
                return;
        }
        
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
            //the total number of columns
            int cols = 1;

            //the place holder key index
            int index = 0;

            //do we have a target
            boolean target = false;
            
            //there will be 7 rows of cards
            for (int row = 0; row < ROWS; row++)
            {
                for (int col = 0; col < cols; col++)
                {
                    Key key = Key.values()[index];
                    
                    if (getHolder(key).isEmpty())
                    {
                        card.setDestination(getHolder(key).getDestinationPoint(), key);
                        
                        //don't hide the card
                        card.setHidden(false);
                        
                        //mark at last row
                        row = ROWS;
                        
                        //we found a target
                        target = true;
                        
                        //exit loop
                        break;
                    }

                    //change the index
                    index++;
                }

                //increate the column total
                cols++;
            }
            
            //if we found a target
            if (target)
            {
                //now move the card towards the destination
                card.moveTowardsDestination(time);
            }
            else
            {
                //we didn't find a target so we are done dealing
                setDealComplete(true);
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
        if (engine.getMouse().isMouseDragged() || engine.getMouse().isMouseReleased())
        {
            //get the mouse location
            final int x = engine.getMouse().getLocation().x;
            final int y = engine.getMouse().getLocation().y;
            
            if (getHolder(Key.Deck).hasLocation(x, y))
            {
                //make sure cards exist
                if (!getHolder(Key.Deck).isEmpty())
                {
                    //get the card we want to move
                    final Card card = getHolder(Key.Deck).getLastCard();
                    
                    //show the card
                    card.setHidden(false);

                    //set this holder as the source
                    card.setSourceHolderKey(Key.OptionalCard);
                    
                    //add to optional holder
                    getHolder(Key.OptionalCard).add(card);
                    
                    //remove from deck
                    getHolder(Key.Deck).remove(card);
                }
            }
            else
            {
                //select cards until we have 2, or we seleced a king first
                if (getDefaultHolder().getSize() < SELECT_LIMIT || getDefaultHolder().getFirstCard().getValue() != Card.Value.King)
                {
                    //check the other holders
                    for (Key key : Key.values())
                    {
                        //never check the destination
                        if (key == Key.Destination)
                            continue;
                        
                        //if the mouse location is within this holder and a card exists
                        if (getHolder(key).hasLocation(x, y) && !getHolder(key).isEmpty())
                        {
                            //if we already have a card from this holder, we can't have another one
                            if (PyramidHelper.hasKey(getDefaultHolder(), key))
                                break;
                        
                            //if we can select this holder
                            if (PyramidHelper.canSelect(this, key))
                            {
                                //get the top card
                                final Card card = getHolder(key).getLastCard();
                                
                                //make sure we don't already have the card
                                if (!getDefaultHolder().hasCard(card))
                                {
                                    //mark as selected
                                    card.setSelected(true);
                                    
                                    //add card to the default holder
                                    getDefaultHolder().add(card);

                                    //remove from the previous
                                    getHolder(key).remove(card);

                                    //retain the location of the card
                                    card.setLocation(getHolder(key));
                                    
                                    //no need to continue
                                    break;
                                }
                            }
                            
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
                //check score if we select 2 cards or if the first card is a king
                if (getDefaultHolder().getSize() == SELECT_LIMIT || getDefaultHolder().getFirstCard().getValue() == Card.Value.King)
                {
                    //get the score
                    final int score = PyramidHelper.getScore(getDefaultHolder());
                    
                    //now handle the cards
                    for (int index = 0; index < getDefaultHolder().getSize(); index++)
                    {
                        final Card card = getDefaultHolder().getCard(index);
                        
                        if (score == MATCH_SCORE)
                        {
                            //add to the destination deck
                            getHolder(Key.Destination).add(card);

                            //remove from the source holder as well
                            getHolder(card.getSourceHolderKey()).remove(card);
                        }
                        else
                        {
                            //no longer mark this as selected
                            card.setSelected(false);
                            
                            //add back to the source
                            getHolder(card.getSourceHolderKey()).add(card);
                        }
                    }
                    
                    //now remove all cards from default holder
                    getDefaultHolder().removeAll();
                    
                    //validate and check if the game has ended
                    validate();
                }
            }
        }
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