package com.gamesbykevin.solitaire.solitaire.pyramidgolf;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.golf.GolfHelper;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Pyramid Gold Solitaire
 * @author GOD
 */
public final class PyramidGolf extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Pyramid_1_Row_1_Column_1,
        Pyramid_1_Row_2_Column_1, Pyramid_1_Row_2_Column_2,
        Pyramid_1_Row_3_Column_1, Pyramid_1_Row_3_Column_2, Pyramid_1_Row_3_Column_3,
        Pyramid_1_Row_4_Column_1, Pyramid_1_Row_4_Column_2, Pyramid_1_Row_4_Column_3, Pyramid_1_Row_4_Column_4,
        Pyramid_1_Row_5_Column_1, Pyramid_1_Row_5_Column_2, Pyramid_1_Row_5_Column_3, Pyramid_1_Row_5_Column_4, Pyramid_1_Row_5_Column_5,
        
        Pyramid_2_Row_1_Column_1,
        Pyramid_2_Row_2_Column_1, Pyramid_2_Row_2_Column_2,
        Pyramid_2_Row_3_Column_1, Pyramid_2_Row_3_Column_2, Pyramid_2_Row_3_Column_3,
        Pyramid_2_Row_4_Column_1, Pyramid_2_Row_4_Column_2, Pyramid_2_Row_4_Column_3, Pyramid_2_Row_4_Column_4,
        Pyramid_2_Row_5_Column_1, Pyramid_2_Row_5_Column_2, Pyramid_2_Row_5_Column_3, Pyramid_2_Row_5_Column_4, Pyramid_2_Row_5_Column_5,
        
        Deck,
        
        OptionalCard
    }
    
    /**
     * The number of rows in a pyramid
     */
    protected static final int ROWS = 5;
    
    /**
     * Where the first card is placed for pyramid 1
     */
    private static final Point PYRAMID_1_ROW_1_COLUMN_1_START_LOCATION = new Point(225, 100);
    
    /**
     * Where the first card is placed for pyramid 2
     */
    private static final Point PYRAMID_2_ROW_1_COLUMN_1_START_LOCATION = new Point(585, 100);
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(325, 400);
    
    /**
     * Where the optional cards are placed
     */
    private static final Point OPTIONAL_START_LOCATION = new Point(425, 400);
    
    /**
     * The space to place between each card vertically
     */
    private static final double PLACE_CARD_HEIGHT_RATIO = .4;
    
    /**
     * The space to place between each card vertically
     */
    private static final double PLACE_CARD_WIDTH_RATIO = 1.1;
    
    /**
     * Ratio of the default card size
     */
    private static final float CARD_SIZE_RATIO = .45f;
    
    //the amount of time to move the cards from one point to the next
    private static final long MOVE_CARD_DURATION = Timers.toNanoSeconds(175L);
    
    //the location of the stats window
    private static final Point STATS_LOCATION = new Point(50, 350);
    
    public PyramidGolf(final Image image) throws Exception
    {
        //store the sprite sheet image
        super(image, CARD_SIZE_RATIO, StackType.Same);
        
        //the total number of columns
        int cols = 1;
        
        //the place holder key index
        int index = 0;
        
        //there will be 5 rows of cards
        for (int row = 0; row < ROWS; row++)
        {
            //the y coordinate for this row
            final int y = (int)(PYRAMID_1_ROW_1_COLUMN_1_START_LOCATION.y + (row * (getDefaultHeight() * PLACE_CARD_HEIGHT_RATIO)));
            
            //the start x coordinate
            int startX = (int)(PYRAMID_1_ROW_1_COLUMN_1_START_LOCATION.x - (cols * (getDefaultWidth() / 2)));
            
            for (int col = 0; col < cols; col++)
            {
                //add holder
                super.addHolder(Key.values()[index], startX, y, StackType.Same);
                
                //adjust x-coordinate
                startX += (getDefaultWidth() * PLACE_CARD_WIDTH_RATIO);
                
                //change the index
                index++;
            }
            
            //increate the column total
            cols++;
        }
        
        //reset back to 1
        cols = 1;
        
        //there will be 5 rows of cards
        for (int row = 0; row < ROWS; row++)
        {
            //the y coordinate for this row
            final int y = (int)(PYRAMID_2_ROW_1_COLUMN_1_START_LOCATION.y + (row * (getDefaultHeight() * PLACE_CARD_HEIGHT_RATIO)));
            
            //the start x coordinate
            int startX = (int)(PYRAMID_2_ROW_1_COLUMN_1_START_LOCATION.x - (cols * (getDefaultWidth() / 2)));
            
            for (int col = 0; col < cols; col++)
            {
                //add holder
                super.addHolder(Key.values()[index], startX, y, StackType.Same);
                
                //adjust x-coordinate
                startX += (getDefaultWidth() * PLACE_CARD_WIDTH_RATIO);
                
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
        Back back = Back.values()[random.nextInt(Back.values().length)];
        
        //klondie solitaire will have one complete deck
        for (Suit suit : Suit.values())
        {
            for (Value value : Value.values())
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
        //did we resolve all existing cards
        boolean complete = true;
        
        for (Key key : Key.values())
        {
            //don't check these
            if (key == Key.Deck || key == Key.OptionalCard)
                continue;
            
            //if one holder has a card, we didn't solve the game
            if (!getHolder(key).isEmpty())
            {
                complete = false;
                break;
            }
        }
        
        //if we didn't complete, see if cards in the deck still exist
        if (!complete)
        {
            //if there are cards on the deck the game can't be over
            if (!getHolder(Key.Deck).isEmpty())
                return;
        }
        
        //as a last measure, make sure no more existing moves available
        for (Key key : Key.values())
        {
            //don't check these
            if (key == Key.Deck || key == Key.OptionalCard)
                continue;
            
            //don't check any without cards
            if (getHolder(key).isEmpty())
                continue;
            
            //make sure not blocked
            if (!PyramidGolfHelper.isBlocked(this, key))
            {
                //if these cards are neighbors, there is still a valid move and game is not over
                if (GolfHelper.isNeighbor(getHolder(key).getFirstCard(), getHolder(Key.OptionalCard).getLastCard()))
                    return;
            }
        }
        
        //flag game over and if we won
        super.setGameover(true);
        super.setWinner(complete);
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
            for (Key key : Key.values())
            {
                //we won't deal cards here
                if (key == Key.Deck || key == Key.OptionalCard)
                    continue;
                
                if (getHolder(key).isEmpty())
                {
                    //target this holder
                    card.setDestination(getHolder(key).getDestinationPoint(), key);

                    //now move the card towards the destination
                    card.moveTowardsDestination(time);
                    
                    //don't hide the card
                    card.setHidden(false);
                    
                    //no need to continue
                    return;
                }
            }
            
            //we didn't find a target so we are done dealing
            setDealComplete(true);
            
            //calculate score
            super.getStats().setScore(getScore());
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
        //if empty we can make a selection
        if (getDefaultHolder().isEmpty())
        {
            //check user input
            if (engine.getMouse().isMouseReleased())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;

                //check if we selected the deck
                if (getHolder(Key.Deck).hasLocation(x, y))
                {
                    //make sure cards exist
                    if (!getHolder(Key.Deck).isEmpty())
                    {
                        //set destination
                        setDestination(getHolder(Key.Deck).getLastCard(), Key.Deck, Key.OptionalCard);
                    }
                }
                else
                {
                    for (Key key : Key.values())
                    {
                        //don't check these
                        if (key == Key.Deck || key == Key.OptionalCard)
                            continue;
                        
                        //if the mouse location is within this holder and a card exists
                        if (getHolder(key).hasLocation(x, y) && !getHolder(key).isEmpty())
                        {
                            //make sure the card is not blocked
                            if (!PyramidGolfHelper.isBlocked(this, key))
                            {
                                //get the top card of the holder selected
                                final Card card = getHolder(key).getLastCard();
                                
                                //get the top card of the optional pile
                                final Card optional = getHolder(Key.OptionalCard).getLastCard();
                                
                                //now check if they are neighbors by the card's face value, if the card exists
                                if (optional == null || GolfHelper.isNeighbor(card, optional))
                                {
                                    //calculate score
                                    super.getStats().setScore(getScore());
                                    
                                    //set the destination
                                    setDestination(card, key, Key.OptionalCard);
                                    
                                    //no need to continue here
                                    return;
                                }
                            }
                            else
                            {
                                //play sound effect
                                engine.getResources().playInvalidCardAudio(engine.getRandom());
                            }
                        }
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
                //play sound effect
                engine.getResources().playPlaceCardAudio(engine.getRandom());
                
                //get the card
                final Card card = getDefaultHolder().getFirstCard();

                //de-select the card
                card.setSelected(false);

                //add to the destination holder
                getHolder(card.getDestinationHolderKey()).add(card);
                
                //remove all cards from the default holder
                getDefaultHolder().removeAll();
                
                //now check for game over
                validate();
            }
        }
    }
    
    /**
     * Get the score
     * @return The score will be the total number of cards remaining
     */
    private int getScore()
    {
        int score = 0;
        
        for (Key key : Key.values())
        {
            //don't check these
            if (key == Key.Deck || key == Key.OptionalCard)
                continue;
            
            //the score will be the total number of cards remaining
            score += getHolder(key).getSize();
        }
        
        return score;
    }
    
    /**
     * Set the destination of the specified card
     * @param card Card we want to move
     * @param source The holder where it came from
     * @param destination The destination holder
     * @throws Exception 
     */
    private void setDestination(final Card card, final Key source, final Key destination) throws Exception
    {
        //show the card
        card.setHidden(false);
        
        //mark as selected
        card.setSelected(true);

        //retain the location of the card
        card.setLocation(getHolder(source));
        
        //set the location of the holder
        getDefaultHolder().setLocation(card);
        
        //add card to the default holder
        getDefaultHolder().add(card);

        //set the destination
        getDefaultHolder().setDestination(getHolder(destination), destination);

        //remove from the previous holder
        getHolder(source).remove(card);
    }
    
    @Override
    public void render(final Graphics graphics) throws Exception
    {
        //render and draw the stats
        getStats().drawStats(graphics);
        
        for (Key key : Key.values())
        {
            getHolder(key).render(graphics, getImage(), getHolder(key).isEmpty() ? false : true);
        }
        
        //render selected cards (if exist)
        getDefaultHolder().render(graphics, getImage(), false);
    }
}