package com.gamesbykevin.solitaire.card;

import com.gamesbykevin.framework.resources.Disposable;
import com.gamesbykevin.framework.util.Timer;
import com.gamesbykevin.framework.util.Timers;

import com.gamesbykevin.solitaire.entity.Entity;

import java.awt.Point;

/**
 * This class represents a card
 * @author GOD
 */
public class Card extends Entity implements Disposable
{
    /**
     * The key for hiding the card and displaying
     */
    private enum Mode
    {
        //animation for face value card
        Face, 
        
        //value for the back of the card when hidden
        Hide
    }
    
    /**
     * Each suit
     */
    public enum Suit
    {
        Clubs, Spades, Hearts, Diamonds
    }
    
    /**
     * Each face value
     */
    public enum Value
    {
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
    }
    
    /**
     * The different animations for the back side of the cards
     */
    public enum Back
    {
        Back1, Back2, Back3,
        Back4, Back5, Back6,
        Back7, Back8, Back9,
        Back10, Back11, Back12,
        Back13, Back14, Back15
    }
    
    /**
     * The suit of the card
     */
    private Suit suit;
    
    /**
     * The face value of the card
     */
    private Value value;
    
    /**
     * Do we hide the card
     */
    private boolean hide = false;
    
    //the location the card needs to be at
    private Point destination;
    
    //the starting location
    private Point start;
    
    //the identifier of the holder we want to put the card in
    private Object destinationHolderKey;
    
    //the identifier of the soure where the card is coming from
    private Object sourceHolderKey;
    
    //the timer to track placing the card at the destination
    private Timer timer;
    
    /**
     * The amount of time it will take to place the card at the destination
     */
    private static final long MOVE_CARD_DURATION = Timers.toNanoSeconds(175L);
    
    public Card(final Suit suit, final Value value, final Back back, final Object sourceHolderKey) throws Exception
    {
        //create a new destination
        this.destination = new Point();
        
        //store the source holder for this card
        setSourceHolderKey(sourceHolderKey);
        
        //store the starting location
        this.start = new Point();
        
        //create new timer of specified duration
        this.timer = new Timer(MOVE_CARD_DURATION);
        this.timer.reset();
        
        /**
         * Assign the appropriate card values
         */
        setSuit(suit);
        setValue(value);
        
        /**
         * Setup the card face animation
         */
        setupAnimation();
        
        /**
         * Setup the back of the card animation
         */
        setupBackAnimation(back);
        
        //set the default animation
        setHidden(true);
    }
    
    /**
     * Get the source holder key
     * @return The key identifying the holder the card came from
     */
    public Object getSourceHolderKey()
    {
        return this.sourceHolderKey;
    }
    
    /**
     * Set the source holder key
     * @param sourceHolderKey The key identifying the holder the card came from
     */
    public final void setSourceHolderKey(final Object sourceHolderKey)
    {
        this.sourceHolderKey = sourceHolderKey;
    }
    
    /**
     * Get the timer
     * @return The timer representing the time to place the card
     */
    private Timer getTimer()
    {
        return this.timer;
    }
    
    /**
     * Get the starting location 
     * @return The starting (x,y) location
     */
    private Point getStart()
    {
        return this.start;
    }
    
    /**
     * Assign the current location to the starting location
     */
    public void setStart()
    {
        getStart().x = (int)getX();
        getStart().y = (int)getY();
    }
    
    /**
     * Set the destination where the card should be.
     * @param destinationPoint The destination (x,y)
     * @param destinationHolderKey The key of the holder we want to add the card to
     */
    public void setDestination(final Point destinationPoint, final Object destinationHolderKey) throws Exception
    {
        //assign the next destination point in the holder
        getDestination().setLocation(destinationPoint);
        
        //store the key of the holder we want to add the card to
        this.destinationHolderKey = destinationHolderKey;
    }

    /**
     * Get the destination holder key
     * @return The key of the holder we want to add the card to
     */
    public Object getDestinationHolderKey()
    {
        return this.destinationHolderKey;
    }
    
    /**
     * Move the card toward its assigned destination
     * @param time Time duration per update
     */
    public void moveTowardsDestination(final long time)
    {
        //update timer
        getTimer().update(time);
        
        //how far are we from destination
        final double xDistance = (getX() < getDestination().x) ? getDestination().x - getStart().x : getStart().x - getDestination().x;
        final double yDistance = (getY() < getDestination().y) ? getDestination().y - getStart().y : getStart().y - getDestination().y;
        
        //if time passed place card at destination
        if (getTimer().hasTimePassed())
        {
            //also reset timer
            getTimer().reset();
            
            setX(getDestination().x);
            setY(getDestination().y);
        }
        else
        {
            //update the location based on the progress of the timer
            if (getX() < getDestination().x)
            {
                setX(getStart().x + (getTimer().getProgress() * xDistance));
            }
            else
            {
                setX(getStart().x - (getTimer().getProgress() * xDistance));
            }
            
            if (getY() < getDestination().y)
            {
                setY(getStart().y + (getTimer().getProgress() * yDistance));
            }
            else
            {
                setY(getStart().y - (getTimer().getProgress() * yDistance));
            }
        }
    }
    
    /**
     * Get the destination
     * @return The (x,y) coordinate where the card should be at
     */
    private Point getDestination()
    {
        return this.destination;
    }
    
    /**
     * Is this card at the absolute location?
     * @return true if the current location matches the start location and destination location, false otherwise
     */
    public boolean hasAbsoluteLocation()
    {
        return (getStart().equals(getDestination()) && getStart().x == getX() && getStart().y == getY());
    }
    
    /**
     * Is this card at the destination
     * @return true if the (x,y) coordinates match the destination (x,y) coordinates, false otherwise
     */
    public boolean hasDestination()
    {
        return (getDestination().x == getX() && getDestination().y == getY());
    }
    
    /**
     * Set the back animation of the card, for when the card is hidden
     * @param back The animation key for the back of the card
     */
    private void setupBackAnimation(final Back back) throws Exception
    {
        switch (back)
        {
            case Back1:
                addAnimation(Mode.Hide, getX(6), getY(0), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back2:
                addAnimation(Mode.Hide, getX(7), getY(0), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back3:
                addAnimation(Mode.Hide, getX(8), getY(0), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back4:
                addAnimation(Mode.Hide, getX(6), getY(1), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back5:
                addAnimation(Mode.Hide, getX(7), getY(1), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back6:
                addAnimation(Mode.Hide, getX(8), getY(1), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back7:
                addAnimation(Mode.Hide, getX(6), getY(2), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back8:
                addAnimation(Mode.Hide, getX(7), getY(2), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back9:
                addAnimation(Mode.Hide, getX(8), getY(2), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back10:
                addAnimation(Mode.Hide, getX(6), getY(3), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back11:
                addAnimation(Mode.Hide, getX(7), getY(3), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back12:
                addAnimation(Mode.Hide, getX(8), getY(3), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back13:
                addAnimation(Mode.Hide, getX(6), getY(4), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back14:
                addAnimation(Mode.Hide, getX(7), getY(4), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            case Back15:
                addAnimation(Mode.Hide, getX(8), getY(4), CARD_WIDTH, CARD_HEIGHT);
                break;
                
            default:
                throw new Exception("Card back not found here (" + back.toString() + ")");
        }
    }
    
    /**
     * Setup the animation for the face value of the card
     * @throws Exception if animation was not mapped
     */
    private void setupAnimation() throws Exception
    {
        switch (getSuit())
        {
            case Hearts:
                
                switch (getValue())
                {
                    case Ace:
                        addAnimation(Mode.Face, getX(1), getY(7), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Two:
                        addAnimation(Mode.Face, getX(5), getY(2), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Three:
                        addAnimation(Mode.Face, getX(2), getY(5), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Four:
                        addAnimation(Mode.Face, getX(2), getY(4), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Five:
                        addAnimation(Mode.Face, getX(2), getY(3), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Six:
                        addAnimation(Mode.Face, getX(2), getY(2), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Seven:
                        addAnimation(Mode.Face, getX(2), getY(1), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Eight:
                        addAnimation(Mode.Face, getX(2), getY(0), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Nine:
                        addAnimation(Mode.Face, getX(1), getY(9), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Ten:
                        addAnimation(Mode.Face, getX(1), getY(8), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Jack:
                        addAnimation(Mode.Face, getX(1), getY(6), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Queen:
                        addAnimation(Mode.Face, getX(1), getY(4), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case King:
                        addAnimation(Mode.Face, getX(1), getY(5), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    default:
                        throw new Exception("Card face value not found here (" + getValue().toString() + ")");
                }
                break;
                
            case Spades:
                
                switch (getValue())
                {
                    case Ace:
                        addAnimation(Mode.Face, getX(0), getY(3), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Two:
                        addAnimation(Mode.Face, getX(1), getY(2), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Three:
                        addAnimation(Mode.Face, getX(1), getY(1), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Four:
                        addAnimation(Mode.Face, getX(1), getY(0), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Five:
                        addAnimation(Mode.Face, getX(0), getY(9), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Six:
                        addAnimation(Mode.Face, getX(0), getY(8), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Seven:
                        addAnimation(Mode.Face, getX(0), getY(7), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Eight:
                        addAnimation(Mode.Face, getX(0), getY(6), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Nine:
                        addAnimation(Mode.Face, getX(0), getY(5), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Ten:
                        addAnimation(Mode.Face, getX(0), getY(4), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Jack:
                        addAnimation(Mode.Face, getX(0), getY(2), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Queen:
                        addAnimation(Mode.Face, getX(0), getY(0), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case King:
                        addAnimation(Mode.Face, getX(0), getY(1), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    default:
                        throw new Exception("Card face value not found here (" + getValue().toString() + ")");
                }
                break;
                
            case Diamonds:
                
                switch (getValue())
                {
                    case Ace:
                        addAnimation(Mode.Face, getX(3), getY(0), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Two:
                        addAnimation(Mode.Face, getX(3), getY(9), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Three:
                        addAnimation(Mode.Face, getX(3), getY(8), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Four:
                        addAnimation(Mode.Face, getX(3), getY(7), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Five:
                        addAnimation(Mode.Face, getX(3), getY(6), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Six:
                        addAnimation(Mode.Face, getX(3), getY(5), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Seven:
                        addAnimation(Mode.Face, getX(3), getY(4), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Eight:
                        addAnimation(Mode.Face, getX(3), getY(3), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Nine:
                        addAnimation(Mode.Face, getX(3), getY(2), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Ten:
                        addAnimation(Mode.Face, getX(3), getY(1), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Jack:
                        addAnimation(Mode.Face, getX(2), getY(9), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Queen:
                        addAnimation(Mode.Face, getX(2), getY(7), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case King:
                        addAnimation(Mode.Face, getX(2), getY(8), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    default:
                        throw new Exception("Card face value not found here (" + getValue().toString() + ")");
                }
                break;
                
            case Clubs:
                
                switch (getValue())
                {
                    case Ace:
                        addAnimation(Mode.Face, getX(4), getY(3), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Two:
                        addAnimation(Mode.Face, getX(2), getY(6), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Three:
                        addAnimation(Mode.Face, getX(5), getY(1), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Four:
                        addAnimation(Mode.Face, getX(5), getY(0), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Five:
                        addAnimation(Mode.Face, getX(4), getY(9), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Six:
                        addAnimation(Mode.Face, getX(4), getY(8), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Seven:
                        addAnimation(Mode.Face, getX(4), getY(7), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Eight:
                        addAnimation(Mode.Face, getX(4), getY(6), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Nine:
                        addAnimation(Mode.Face, getX(4), getY(5), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Ten:
                        addAnimation(Mode.Face, getX(4), getY(4), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Jack:
                        addAnimation(Mode.Face, getX(4), getY(2), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case Queen:
                        addAnimation(Mode.Face, getX(4), getY(0), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    case King:
                        addAnimation(Mode.Face, getX(4), getY(1), CARD_WIDTH, CARD_HEIGHT);
                        break;
                        
                    default:
                        throw new Exception("Card face value not found here (" + getValue().toString() + ")");
                }
                break;
                
            default:
                throw new Exception("Suit not found here (" + getSuit().toString() + ")");
        }
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
    }
    
    /**
     * Set the card hidden.
     * This will also update the appropriate animation
     * @param hide true=yes, false=no
     */
    public final void setHidden(final boolean hide)
    {
        //flag hidden
        this.hide = hide;
        
        //if we are hiding the card, set the animation
        super.setAnimation(isHidden() ? Mode.Hide : Mode.Face);
    }
    
    /**
     * Is this card hidden?
     * @return true=yes, false=no
     */
    public boolean isHidden()
    {
        return this.hide;
    }
    
    /**
     * Assign the suit of the card
     * @param suit The suit. "Clubs", "Hearts", etc...
     */
    public final void setSuit(final Suit suit)
    {
        this.suit = suit;
    }
    
    /**
     * Assign the face value of the card
     * @param value Ace, Two, King, Ten, etc.....
     */
    public final void setValue(final Value value)
    {
        this.value = value;
    }
    
    /**
     * Get the suit of the card
     * @return The suit. "Clubs", "Hearts", etc...
     */
    public Suit getSuit()
    {
        return this.suit;
    }
    
    /**
     * Get the face value of the card
     * @return Ace, Two, King, Ten, etc.....
     */
    public Value getValue()
    {
        return this.value;
    }
    
    /**
     * Does this card have a matching suit?
     * @param suit The suit we want to check
     * @return true if matching, false otherwise
     */
    public boolean hasSuit(final Suit suit)
    {
        return (getSuit() == suit);
    }
    
    /**
     * Does this card have a matching face value?
     * @param value The face value we want to check
     * @return true if matching, false otherwise
     */
    public boolean hasValue(final Value value)
    {
        return (getValue() == value);
    }
}