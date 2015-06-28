package com.gamesbykevin.solitaire.solitaire.blockten;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Holder.StackType;

import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.solitaire.Solitaire;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/**
 * Block Ten Solitaire
 * @author GOD
 */
public final class BlockTen extends Solitaire
{
    /**
     * The unique key to access each place holder
     */
    protected enum Key
    {
        Row_1_Column_1, Row_1_Column_2, Row_1_Column_3, Row_1_Column_4, 
        Row_2_Column_1, Row_2_Column_2, Row_2_Column_3, Row_2_Column_4, 
        Row_3_Column_1, Row_3_Column_2, Row_3_Column_3, Row_3_Column_4, 
        
        Destination, 
        
        Deck,
    }
    
    /**
     * Where the first card is placed for row 1
     */
    private static final Point ROW_1_START_LOCATION = new Point(250, 145);
    
    /**
     * Where the deck start is placed
     */
    private static final Point DECK_LOCATION = new Point(100, 245);
    
    /**
     * Where the deck start is placed
     */
    private static final Point DESTINATION_LOCATION = new Point(650, 245);
    
    //the amount of cards we can select at once
    protected static final int SELECTION_LIMIT = 2;
    
    //the dimensions of the playing area
    protected static final int ROWS = 3;
    protected static final int COLS = 4;
    
    //the pixel distance padding between each card
    private static final int PIXEL_WIDTH = 15;
    private static final int PIXEL_HEIGHT = 15;
    
    //the amount of time to move the cards from one point to the next
    private static final long MOVE_CARD_DURATION = Timers.toNanoSeconds(250L);
    
    //the location of the stats window
    private static final Point STATS_LOCATION = new Point(50, 350);
    
    //points to add for each card placed
    private static final int POINTS_SCORE = 10;
    
    public BlockTen(final Image image) throws Exception
    {
        //store the sprite sheet image
        super(image, StackType.Same);
        
        int index = 0;
        
        int y = ROW_1_START_LOCATION.y;
        
        for (int row = 0; row < ROWS; row++)
        {
            int x = ROW_1_START_LOCATION.x;
            
            for (int col = 0; col < COLS; col++)
            {
                //create holder
                super.addHolder(Key.values()[index], x, y, StackType.Same);
                
                //increase index
                index++;
                
                //increase x-coordinate
                x += getDefaultWidth() + PIXEL_WIDTH;
            }
            
            //increase y-coordinate
            y += getDefaultHeight() + PIXEL_HEIGHT;
        }
        
        //add deck and destination holders
        super.addHolder(Key.Deck,        DECK_LOCATION,        StackType.Same);
        super.addHolder(Key.Destination, DESTINATION_LOCATION, StackType.Same);
        
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
        //if the deck is not empty
        if (!getHolder(Key.Deck).isEmpty())
        {
            for (int index1 = 0; index1 < 12; index1++)
            {
                for (int index2 = 0; index2 < 12; index2++)
                {
                    //we don't want to check the same card
                    if (index1 == index2)
                        continue;

                    //get the score of the two cards
                    int score = BlockTenHelper.getScore(getHolder(Key.values()[index1]).getLastCard(), getHolder(Key.values()[index2]).getLastCard());

                    //if these two cards make a match, the game can continue
                    if (score == BlockTenHelper.GOAL_SCORE)
                        return;
                }
            }
            
            //if we made it here there are no more valid moves, game over we lose
            super.setGameover(true);
            super.setWinner(false);
            return;
        }
        
        //flag game over and if we won
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
        
        //if there are no more cards, the deal is complete
        if (getHolder(Key.Deck).isEmpty())
        {
            //if all holders have a card, the dealing is complete
            setDealComplete(true);

            //validate here and see if the game is over
            validate();
            
            //no need to continue;
            return;
        }
        
        //if not at destination yet
        if (!card.hasDestination())
        {
            for (Key key : Key.values())
            {
                //we won't deal cards here
                if (key == Key.Deck || key == Key.Destination)
                    continue;
                
                //only place cards where they are empty
                if (getHolder(key).isEmpty())
                {
                    //don't hide the card
                    card.setHidden(false);
                    
                    //target this holder
                    card.setDestination(getHolder(key).getDestinationPoint(), key);

                    //now move the card towards the destination
                    card.moveTowardsDestination(time);
                    
                    //no need to continue
                    return;
                }
            }
            
            //if all holders have a card, the dealing is complete
            setDealComplete(true);

            //validate here and see if all cards have been added
            validate();
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
        //if we can still make a selection
        if (getDefaultHolder().getSize() < SELECTION_LIMIT)
        {
            //check user input
            if (engine.getMouse().isMouseReleased())
            {
                //get the mouse location
                final int x = engine.getMouse().getLocation().x;
                final int y = engine.getMouse().getLocation().y;

                for (Key key : Key.values())
                {
                    //we can only select cards from the playable holder(s)
                    if (key == Key.Deck || key == Key.Destination)
                        continue;
                    
                    //if empty we can't select
                    if (getHolder(key).isEmpty())
                        continue;
                    
                    //get the top card in this holder
                    final Card card = getHolder(key).getLastCard();
                    
                    //if we clicked the top card in the holder and it isn't a ten
                    if (card.hasLocation(x, y) && !card.hasValue(Card.Value.Ten))
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
            //score the cards and make sure we reach the goal score
            if (BlockTenHelper.getScore(getDefaultHolder()) == BlockTenHelper.GOAL_SCORE)
            {
                //if we reached the goal move cards to destintion
                for (int index = 0; index < getDefaultHolder().getSize(); index++)
                {
                    //get the card
                    final Card card = getDefaultHolder().getCard(index);

                    //set the destination
                    card.setDestination(getHolder(Key.Destination).getDestinationPoint(), Key.Destination);
                }
            }
            else
            {
                //we did not reach the correct score, move cards back
                for (int index = 0; index < getDefaultHolder().getSize(); index++)
                {
                    //get the card
                    final Card card = getDefaultHolder().getCard(index);

                    //set the destination
                    card.setDestination(getHolder(card.getSourceHolderKey()).getDestinationPoint(), card.getSourceHolderKey());
                }
            }
                
            //now check if we are at the destination
            if (!getDefaultHolder().hasDestination())
            {
                //move towards destination
                getDefaultHolder().moveTowardsDestination(engine.getTime());
            }
            else
            {
                for (int index = 0; index < getDefaultHolder().getSize(); index++)
                {
                    //get the card
                    final Card card = getDefaultHolder().getCard(index);
                    
                    //if we have a 10 score match, increase score
                    if (card.getDestinationHolderKey() == Key.Destination)
                        super.getStats().setScore(getStats().getScore() + POINTS_SCORE);
                    
                    //no longer select the card
                    card.setSelected(false);
                    
                    //add card to destintion
                    getHolder(card.getDestinationHolderKey()).add(card);
                }
                
                //remove cards from default holder
                getDefaultHolder().removeAll();
                
                //set deal to false so 2 more cards can be added (if needed)
                setDealComplete(false);
            }
        }
    }
}