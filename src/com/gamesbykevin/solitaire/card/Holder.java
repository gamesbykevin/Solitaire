package com.gamesbykevin.solitaire.card;

import com.gamesbykevin.framework.resources.Disposable;

import com.gamesbykevin.solitaire.entity.Entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The holder can contain many cards
 * @author GOD
 */
public final class Holder extends Entity implements Disposable
{
    /**
     * How will the cards display in this holder
     */
    public enum StackType
    {
        /**
         * Place each card vertically on top of the previous
         */
        Vertical, 
        
        /**
         * Place each card horizontally to the previous
         */
        Horizontal, 
        
        /**
         * Place each card directly over the previous card
         */
        Same
    }
    
    /**
     * The way we want to stack the cards
     */
    private final StackType stackType;
    
    /**
     * The amount of space between each card
     */
    private static final double SPACE_RATIO = .2;
    
    /**
     * List of cards in this holder
     */
    private List<Card> cards;
    
    //temporary point
    private Point tmp;
    
    public Holder(final StackType stackType)
    {
        //track how we are storing the cards
        this.stackType = stackType;
        
        //create a new list to hold the cards
        this.cards = new ArrayList<>();
        
        //add mapping for place holder
        addAnimation(DEFAULT_ANIMATION_KEY, getX(6), getY(5), CARD_WIDTH, CARD_HEIGHT);
        
        //set this as the animation
        setAnimation(DEFAULT_ANIMATION_KEY);
    }
    
    /**
     * Do any of the cards in this holder have the location
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if at least 1 card contains the coordinates, false otherwise
     */
    @Override
    public boolean hasLocation(final int x, final int y)
    {
        //if the coordinates are inside the holder itself
        if (super.hasLocation(x, y))
            return true;
        
        for (int index = 0; index < getSize(); index++)
        {
            if (getCard(index).hasLocation(x, y))
                return true;
        }
        
        //no cards were found to contain the location
        return false;
    }
    
    /**
     * Get the stack type
     * @return The way we want to place the cards on this holder
     */
    private StackType getStackType()
    {
        return this.stackType;
    }
    
    /**
     * Add the card to this place holder
     * @param card The card we want to add
     */
    public void add(final Card card) throws Exception
    {
        //set the location
        card.setLocation(getDestinationPoint());
        
        //also store this as the starting location
        card.setStart();
        
        //add to list
        getCards().add(card);
    }
    
    /**
     * Set the current location for each card as the start location
     */
    public void setStart()
    {
        for (int index = 0; index < getSize(); index++)
        {
            getCard(index).setStart();
        }
    }
    
    /**
     * Add all cards to this holder.<br>
     * Once all cards are added to this holder, the specified holder will have all cards removed
     * @param holder The holder containing cards that we want to add to this holder
     */
    public void add(final Holder holder) throws Exception
    {
        for (int index = 0; index < holder.getSize(); index++)
        {
            //add card to holder
            add(holder.getCard(index));
        }
        
        for (int index = 0; index < getSize(); index++)
        {
            //remove card
            holder.remove(getCard(index));
        }
    }
    
    /**
     * Place the card at the specified index
     * @param index The location we want to place the card
     * @param card The card we want to place
     */
    public void set(final int index, final Card card)
    {
        getCards().set(index, card);
    }
    
    /**
     * Get the destination point.<br>
     * This will vary depending on the stack type.
     * @return The (x,y) coordinates where we want the card placed.
     * @throws Exception if the stack type is not defined
     */
    public Point getDestinationPoint() throws Exception
    {
        return (getDestinationPoint(getCards().size()));
    }
    
    /**
     * Get the destination point.<br>
     * This will vary depending on the stack type.
     * @param size The specified size we want to assign to get the destination point
     * @return The (x,y) coordinates where we want the card placed.
     * @throws Exception if the stack type is not defined
     */
    public Point getDestinationPoint(final int size) throws Exception
    {
        //create new point if not exists
        if (tmp == null)
            tmp = new Point();
        
        //set location to the current holder location
        tmp.setLocation(getX(), getY());
        
        /**
         * If a card exists we may alter the position
         */
        if (getFirstCard() != null)
        {
            switch (getStackType())
            {
                case Vertical:
                    tmp.y = (int)(getY() + (size * (getFirstCard().getHeight() * SPACE_RATIO)));
                    break;

                case Horizontal:
                    tmp.x = (int)(getX() + (size * (getFirstCard().getWidth() * SPACE_RATIO)));
                    break;

                //the coordinates won't change here
                case Same:
                    break;

                default:
                    throw new Exception("Type not caught here (" + getStackType().toString() + ")");
            }
        }
        
        //return the destination coordinate
        return tmp;
    }
    
    /**
     * Remove the card.<br>
     * If the card is not found nothing will happen
     * @param card The card we want to remove
     */
    public void remove(final Card card)
    {
        for (int i = 0; i < getCards().size(); i++)
        {
            if (getCards().get(i).getId() == card.getId())
                getCards().remove(i);
        }
    }
    
    /**
     * Is there a card here?<br>
     * False will be returned if no cards exist
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if there is a card at this location, false otherwise
     */
    public boolean hasCard(final int x, final int y)
    {
        //we can't have a card if none exist
        if (isEmpty())
            return false;
        
        return (getCard(x, y) != null);
    }
    
    /**
     * Get the top facing card at the specified location
     * @param x x-coordinate
     * @param y y-coordinate
     * @return The card at the specified coordinates, if not found null is returned
     */
    public Card getCard(final int x, final int y)
    {
        //start with the last card
        for (int i = getCards().size() - 1; i >= 0; i--)
        {
            //get the current card
            final Card card = getCards().get(i);
            
            //if the coordinate are inside the card return it
            if (card.hasLocation(x, y))
                return card;
        }
        
        //no cards were found
        return null;
    }
    
    /**
     * Get the first card in this place holder
     * @return The first card added to the list, if there are no cards null is returned
     */
    public Card getFirstCard()
    {
        //if there are no cards, return null
        if (getCards().isEmpty())
            return null;
        
        //get the first card in the list
        return getCard(0);
    }
    
    /**
     * Get the last (top) card in this place holder
     * @return The last card added to the list, if there are no cards null is returned
     */
    public Card getLastCard()
    {
        //if there are no cards, return null
        if (getCards().isEmpty())
            return null;
        
        //get the last card in the list
        return getCard(getCards().size() - 1);
    }
    
    /**
     * Get the size of the place holder
     * @return The number of cards in the place holder
     */
    public int getSize()
    {
        return getCards().size();
    }
    
    /**
     * Get the cards
     * @return The list of cards in this place holder
     */
    private List<Card> getCards()
    {
        return this.cards;
    }
    
    /**
     * Get the card
     * @param index The index in the list of the card we want
     * @return The card in this holder, if not found or empty null is returned
     */
    public Card getCard(final int index)
    {
        //if no cards exist return null
        if (isEmpty())
            return null;
        
        //if index out of range, return null
        if (index < 0 || index >= getCards().size())
            return null;
        
        return getCards().get(index);
    }
    
    /**
     * Set the destination where the cards should be.
     * @param holder The location of the holder where we want our cards to be
     * @param destinationHolderKey The key of the holder we want to add the cards to
     */
    public void setDestination(final Holder holder, final Object destinationHolderKey) throws Exception
    {
        //set the same destination for all cards
        for (int index = 0; index < getSize(); index++)
        {
            //each card will have a different destination
            final int size = holder.getSize() + index;
            
            //set the destination
            getCard(index).setDestination(holder.getDestinationPoint(size), destinationHolderKey);
        }
    }
    
    /**
     * Are all cards at the destination?<br>
     * If no cards are found true will be returned.
     * @return true if all cards are at their assigned destination, false otherwise
     */
    public boolean hasDestination()
    {
        for (int index = 0; index < getSize(); index++)
        {
            //if this card is not at the destination, return false
            if (!getCard(index).hasDestination())
                return false;
        }
        
        //all cards are at their destination
        return true;
    }
    
    /**
     * Move the cards toward their assigned destination
     * @param time Time duration per update
     */
    public void moveTowardsDestination(final long time)
    {
        //move the cards
        for (int index = 0; index < getSize(); index++)
        {
            if (!getCard(index).hasDestination())
            {
                getCard(index).moveTowardsDestination(time);
            }
        }
    }
    
    /**
     * Are there any cards in this holder?
     * @return true if at least 1 card exists, otherwise false
     */
    public boolean isEmpty()
    {
        return getCards().isEmpty();
    }
    
    /**
     * Update the location of all cards in the holder.<br>
     * The specified coordinates will be the anchor for the cards
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void updateLocation(final int x, final int y) throws Exception
    {
        super.setX(x);
        super.setY(y);
        
        //make sure cards exist
        if (!isEmpty())
        {
            //now update the cards
            for (int index = 0; index < getSize(); index++)
            {
                getCard(index).setLocation(getDestinationPoint(index));
            }
        }
    }
    
    /**
     * Recycle objects
     */
    @Override
    public void dispose()
    {
        super.dispose();
        
        if (cards != null)
        {
            for (int i = 0; i < cards.size(); i++)
            {
                cards.get(i).dispose();
                cards.set(i, null);
            }
            
            cards.clear();
            cards = null;
        }
    }
    
    /**
     * Draw each card in the place holder.
     * @param graphics Object where image is written to
     * @param image The sprite sheet with the image
     * @param drawOutline Do we draw the place holder outline
     * @throws Exception 
     */
    public void render(final Graphics graphics, final Image image, final boolean drawOutline) throws Exception
    {
        if (drawOutline)
            super.render(graphics, image);
        
        //draw each card
        for (int i = 0; i < getCards().size(); i++)
        {
            getCards().get(i).render(graphics, image);
        }
    }
    
    /**
     * Draw each card in the place holder, including the place holder outline.
     * @param graphics Object where image is written to
     * @param image The sprite sheet with the image
     * @throws Exception 
     */
    @Override
    public void render(final Graphics graphics, final Image image) throws Exception
    {
        this.render(graphics, image, true);
    }
}