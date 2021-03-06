package com.gamesbykevin.solitaire.solitaire.golf;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Holder.StackType;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Golf Solitaire
 * @author GOD
 */
public final class Golf extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Deck,
        GarbagePile,
        
        Column1, Column2, Column3, Column4,
        Column5, Column6, Column7,
    }

    /**
     * The location for column 1 holder
     */
    private static final Point COLUMN_1_START_LOCATION = new Point(125, 100);
    
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_START_LOCATION = new Point(300, 400);
    
    /**
     * Where the garbage pile is placed
     */
    private static final Point GARBAGE_PILE_START_LOCATION = new Point(400, 400);
    
    /**
     * The number of cards per column upon initial deal
     */
    protected static final int COLUMN_LIMIT = 5;
    
    //the location of the stats window
    private static final Point STATS_LOCATION = new Point(50, 350);
    
    public Golf(final Image image) throws Exception
    {
        //store the sprite sheet image
        super(image, StackType.Same);
        
        //add deck holder
        addHolder(Key.Deck, DECK_START_LOCATION, StackType.Same);
        
        //add garbage pile holder
        addHolder(Key.GarbagePile, GARBAGE_PILE_START_LOCATION, StackType.Same);
        
        //The amount of pixels between each columns
        final int EACH_COLUMN_WIDTH = (int)(getDefaultWidth() * 1.25);
    
        //add columns
        addHolder(Key.Column1, COLUMN_1_START_LOCATION.x + (0 * EACH_COLUMN_WIDTH), COLUMN_1_START_LOCATION.y, StackType.Vertical);
        addHolder(Key.Column2, COLUMN_1_START_LOCATION.x + (1 * EACH_COLUMN_WIDTH), COLUMN_1_START_LOCATION.y, StackType.Vertical);
        addHolder(Key.Column3, COLUMN_1_START_LOCATION.x + (2 * EACH_COLUMN_WIDTH), COLUMN_1_START_LOCATION.y, StackType.Vertical);
        addHolder(Key.Column4, COLUMN_1_START_LOCATION.x + (3 * EACH_COLUMN_WIDTH), COLUMN_1_START_LOCATION.y, StackType.Vertical);
        addHolder(Key.Column5, COLUMN_1_START_LOCATION.x + (4 * EACH_COLUMN_WIDTH), COLUMN_1_START_LOCATION.y, StackType.Vertical);
        addHolder(Key.Column6, COLUMN_1_START_LOCATION.x + (5 * EACH_COLUMN_WIDTH), COLUMN_1_START_LOCATION.y, StackType.Vertical);
        addHolder(Key.Column7, COLUMN_1_START_LOCATION.x + (6 * EACH_COLUMN_WIDTH), COLUMN_1_START_LOCATION.y, StackType.Vertical);
        
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
    public void validate() throws Exception
    {
        //if all columns are empty we won
        if (getHolder(Key.Column1).isEmpty() && 
            getHolder(Key.Column2).isEmpty() && 
            getHolder(Key.Column3).isEmpty() && 
            getHolder(Key.Column4).isEmpty() && 
            getHolder(Key.Column5).isEmpty() && 
            getHolder(Key.Column6).isEmpty() && 
            getHolder(Key.Column7).isEmpty())
        {
            //flag game over and loser
            super.setGameover(true);
            super.setWinner(true);
            return;
        }
        
        //if there are cards in the deck, the game can't be over
        if (!getHolder(Key.Deck).isEmpty())
            return;
        
        //there has to be cards in the garbage pile or else valid move(s) still exist
        if (!getHolder(Key.GarbagePile).isEmpty())
        {
            //get the top card in the garbage pile
            final Card card = getHolder(Key.GarbagePile).getLastCard();
            
            //if the garbage card is a neighbor to this column, the game isn't over
            if (!getHolder(Key.Column1).isEmpty() && GolfHelper.isNeighbor(card, getHolder(Key.Column1).getLastCard()))
                return;
            if (!getHolder(Key.Column2).isEmpty() && GolfHelper.isNeighbor(card, getHolder(Key.Column2).getLastCard()))
                return;
            if (!getHolder(Key.Column3).isEmpty() && GolfHelper.isNeighbor(card, getHolder(Key.Column3).getLastCard()))
                return;
            if (!getHolder(Key.Column4).isEmpty() && GolfHelper.isNeighbor(card, getHolder(Key.Column4).getLastCard()))
                return;
            if (!getHolder(Key.Column5).isEmpty() && GolfHelper.isNeighbor(card, getHolder(Key.Column5).getLastCard()))
                return;
            if (!getHolder(Key.Column6).isEmpty() && GolfHelper.isNeighbor(card, getHolder(Key.Column6).getLastCard()))
                return;
            if (!getHolder(Key.Column7).isEmpty() && GolfHelper.isNeighbor(card, getHolder(Key.Column7).getLastCard()))
                return;

            //flag game over and loser
            super.setGameover(true);
            super.setWinner(false);
        }
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
            if (getHolder(Key.Column1).getSize() < COLUMN_LIMIT)
            {
                card.setDestination(getHolder(Key.Column1).getDestinationPoint(), Key.Column1);
            }
            else if (getHolder(Key.Column2).getSize() < COLUMN_LIMIT)
            {
                card.setDestination(getHolder(Key.Column2).getDestinationPoint(), Key.Column2);
            }
            else if (getHolder(Key.Column3).getSize() < COLUMN_LIMIT)
            {
                card.setDestination(getHolder(Key.Column3).getDestinationPoint(), Key.Column3);
            }
            else if (getHolder(Key.Column4).getSize() < COLUMN_LIMIT)
            {
                card.setDestination(getHolder(Key.Column4).getDestinationPoint(), Key.Column4);
            }
            else if (getHolder(Key.Column5).getSize() < COLUMN_LIMIT)
            {
                card.setDestination(getHolder(Key.Column5).getDestinationPoint(), Key.Column5);
            }
            else if (getHolder(Key.Column6).getSize() < COLUMN_LIMIT)
            {
                card.setDestination(getHolder(Key.Column6).getDestinationPoint(), Key.Column6);
            }
            else if (getHolder(Key.Column7).getSize() < COLUMN_LIMIT)
            {
                card.setDestination(getHolder(Key.Column7).getDestinationPoint(), Key.Column7);
            }
            else
            {
                //the holders have the specified card limit
                setDealComplete(true);

                //calculate score
                super.getStats().setScore(getScore());
                
                //no need to continue
                return;
            }
            
            //don't hide the card
            card.setHidden(false);
            
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
        //if the default holder is empty, we can pick cards
        if (getDefaultHolder().isEmpty())
        {
            //check user input
            if (engine.getMouse().isMouseReleased())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;

                //check which holder the user selected
                if (!getHolder(Key.Deck).isEmpty() && getHolder(Key.Deck).getLastCard().hasLocation(x, y))
                {
                    startMove(Key.Deck, Key.GarbagePile);
                }
                else
                {
                    for (Key key : Key.values())
                    {
                        //skip these holders
                        if (key == Key.Deck)
                            continue;
                        if (key == Key.GarbagePile)
                            continue;
                        
                        //skip if there are no cards here
                        if (getHolder(key).isEmpty())
                            continue;
                        
                        //did the user select this last card
                        if (getHolder(key).getLastCard().hasLocation(x, y))
                        {
                            //if the garbage pile is empty the any card can be selected
                            if (getHolder(Key.GarbagePile).isEmpty())
                            {
                                startMove(key, Key.GarbagePile);
                            }
                            else
                            {
                                //get the selected card
                                final Card card = getHolder(key).getLastCard();

                                //get the top card in the garbage pile
                                final Card tmp = getHolder(Key.GarbagePile).getLastCard();
                                
                                //make sure these are neighbors
                                if (GolfHelper.isNeighbor(card, tmp))
                                {
                                    //start the move
                                    startMove(key, Key.GarbagePile);
                                }
                                else
                                {
                                    //play sound effect
                                    engine.getResources().playInvalidCardAudio(engine.getRandom());
                                }
                            }
                            
                            //no need to continue
                            break;
                        }
                    }
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
                //play sound effect
                engine.getResources().playPlaceCardAudio(engine.getRandom());
                
                //now handle the cards
                for (int index = 0; index < getDefaultHolder().getSize(); index++)
                {
                    final Card card = getDefaultHolder().getCard(index);

                    //de-select the card
                    card.setSelected(false);
                    
                    //add to the destination holder
                    getHolder(card.getDestinationHolderKey()).add(card);
                }

                //calculate score
                super.getStats().setScore(getScore());
                
                //now remove all cards from default holder
                getDefaultHolder().removeAll();

                //validate and check if the game has ended
                validate();
            }
        }
    }
    
    /**
     * Get the score
     * @return The number of remaining cards, the lower the score the better
     */
    private int getScore()
    {
        return (getHolder(Key.Column1).getSize() + getHolder(Key.Column2).getSize() + getHolder(Key.Column3).getSize() + 
                getHolder(Key.Column4).getSize() + getHolder(Key.Column5).getSize() + getHolder(Key.Column6).getSize() + 
                getHolder(Key.Column7).getSize());
    }
    
    /**
     * Start setting up the move
     * @param source The source holder
     * @param destination The destination holder
     * @throws Exception 
     */
    private void startMove(final Key source, final Key destination) throws Exception
    {
        //get the top card
        final Card card = getHolder(source).getLastCard();

        //make the location (x,y) match
        getDefaultHolder().setLocation(card);

        //set destination
        card.setDestination(getHolder(destination).getDestinationPoint(), destination);

        //no need to hide
        card.setHidden(false);

        //mark selected
        card.setSelected(true);
        
        //add to holder
        getDefaultHolder().add(card);

        //remove from deck
        getHolder(source).remove(card);
    }
}