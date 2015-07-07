package com.gamesbykevin.solitaire.entity;

import com.gamesbykevin.framework.base.Animation;
import com.gamesbykevin.framework.base.Sprite;
import com.gamesbykevin.framework.resources.Disposable;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Common used functions
 * @author GOD
 */
public abstract class Entity extends Sprite implements Disposable
{
    /**
     * Original width of a card
     */
    public static final int ORIGINAL_CARD_WIDTH = 140;
    
    /**
     * Original height of a card
     */
    public static final int ORIGINAL_CARD_HEIGHT = 190;
    
    /**
     * No time delay
     */
    public static final long DELAY_NONE = 0;
    
    /**
     * Default animation key to use
     */
    public static final String DEFAULT_ANIMATION_KEY = "Default";
    
    protected Entity()
    {
        //create the spritesheet
        super.createSpriteSheet();
    }
    
    /**
     * Do we have this location?
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if the (x,y) coordinate is inside the card, false otherwise
     */
    public boolean hasLocation(final int x, final int y)
    {
        //get the original location
        final double originalX = getX();
        final double originalY = getY();
        
        //offset location
        setX(originalX - (getWidth() / 2));
        setY(originalY - (getHeight() / 2));
        
        //is the coordinate contained in this rectangle
        final boolean contains = getRectangle().contains(x, y);
        
        //restore location
        setX(originalX);
        setY(originalY);
        
        //return result
        return (contains);
    }
    
    /**
     * Reset the current animation
     */
    public void resetAnimation() throws Exception
    {
        super.getSpriteSheet().reset();
    }
    
    /**
     * Does this animation exist in the sprite sheet?
     * @param key The key we are looking for
     * @return True if the animation already exists in the sprite sheet, false otherwise
     */
    public boolean hasAnimation(final Object key) throws Exception
    {
        return (getSpriteSheet().getSpriteSheetAnimation(key) != null);
    }
    
    /**
     * Assign the current animation.<br>
     * @param key The key of the animation
     */
    public void setAnimation(final Object key) throws Exception
    {
        super.getSpriteSheet().setCurrent(key);
    }
    
    /**
     * Add animation to sprite sheet (single frame)
     * @param key Object used to identify the animation
     * @param x starting x-coordinate of animation
     * @param y starting y-coordinate of animation
     * @param w width of animation
     * @param h height of animation
     */
    protected void addAnimation(final Object key, final int x, final int y, final int w, final int h) throws Exception
    {
        //add animation to spritesheet
        super.getSpriteSheet().add(new Animation(x, y, w, h, DELAY_NONE), key);
        
        //if no current animation set, set this as a default
        if (getSpriteSheet().getCurrent() == null)
            setAnimation(key);
    }
    
    /**
     * Get the x-coordinate
     * @param col Column on sprite sheet
     * @return The x-coordinate
     */
    protected int getX(final int col)
    {
        return (col * ORIGINAL_CARD_WIDTH);
    }
    
    /**
     * Get the y-coordinate
     * @param row Row on sprite sheet
     * @return The y-coordinate
     */
    protected int getY(final int row)
    {
        return (row * ORIGINAL_CARD_HEIGHT);
    }
    
    /**
     * Render the card
     * @param graphics Object where image is written to
     * @param image The sprite sheet with the image
     * @throws Exception 
     */
    public void render(final Graphics graphics, final Image image) throws Exception
    {
        //get original location
        final double x = getX();
        final double y = getY();
        
        //offset location
        super.setX(x - (getWidth() / 2));
        super.setY(y - (getHeight() / 2));
        
        //render card
        super.draw(graphics, image);
        
        //reset location
        super.setX(x);
        super.setY(y);
    }
}