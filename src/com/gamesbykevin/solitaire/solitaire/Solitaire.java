package com.gamesbykevin.solitaire.solitaire;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.entity.Entity;

import java.awt.Graphics;

import java.awt.Image;
import java.util.HashMap;
import java.util.Random;

/**
 * Each solitaire class will inherit from here
 * @author GOD
 */
public abstract class Solitaire extends Entity implements ISolitaire
{
    //places where the cards can be placed
    private HashMap<Object, Holder> holders;
    
    //the default holder for the cards the human can control
    private Holder holder;
    
    /**
     * The number of times to shuffle the deck
     */
    protected static final int SHUFFLE_LIMIT = 3;
    
    //are we finished dealing
    private boolean dealComplete = false;
    
    //are we finished creating a deck
    private boolean createComplete = false;
    
    //are we finished shuffling
    private boolean shuffleComplete = false;
    
    //is the game over
    private boolean gameover = false;
    
    //did the player win
    private boolean winner = false;
    
    /**
     * Create our solitaire game
     * @param image The sprite sheet containing the card graphics
     */
    protected Solitaire(final Image image)
    {
        //create container for all of the holders
        this.holders = new HashMap<>();
        
        //store the sprite sheet
        super.setImage(image);
    }
    
    /**
     * Get the default holder.
     * @return The default holder containing cards for the human to control
     */
    protected Holder getDefaultHolder()
    {
       return this.holder; 
    }
    
    protected void createDefaultHolder(final Holder.StackType stackType)
    {
        this.holder = new Holder(stackType);
    }
    
    /**
     * Get the specified holder
     * @param key The key of the holder we want
     * @return The holder containing the cards
     */
    protected Holder getHolder(final Object key)
    {
        return holders.get(key);
    }
    
    /**
     * Add holder to container
     * @param key The key to access this holder
     * @param holder The holder we want to add
     */
    protected void addHolder(final Object key, final Holder holder)
    {
        holders.put(key, holder);
    }
    
    /**
     * Shuffle the deck
     */
    @Override
    public void shuffle(final Random random, final Holder holder) throws Exception
    {
        //shuffle this many times
        for (int count = 0; count < SHUFFLE_LIMIT; count++)
        {
            //check each card in the default holder (a.k.a our deck)
            for (int i = 0; i < holder.getSize(); i++)
            {
                //get the current card
                final Card card = holder.getCard(i);

                //choose a random index (could be the same as the current)
                final int randomIndex = random.nextInt(holder.getSize());

                //get the second card
                final Card tmp = holder.getCard(randomIndex);

                //now swap cards
                holder.set(i, tmp);
                holder.set(randomIndex, card);
            }
        }
        
        //flag the shuffle as completed
        this.setShuffleComplete(true);
    }
    
    /**
     * Flag the create as complete
     * @param createComplete true = yes, false = no
     */
    @Override
    public void setCreateComplete(final boolean createComplete)
    {
        this.createComplete = createComplete;
    }

    /**
     * Is the create deck complete?
     * @return true = yes, false = no
     */
    @Override
    public boolean isCreateComplete()
    {
        return this.createComplete;
    }
    
    /**
     * Flag the shuffle as complete
     * @param shuffleComplete true = yes, false = no
     */
    @Override
    public void setShuffleComplete(final boolean shuffleComplete)
    {
        this.shuffleComplete = shuffleComplete;
    }
    
    /**
     * Is the shuffling complete?
     * @return true = yes, false = no
     */
    @Override
    public boolean isShuffleComplete()
    {
        return this.shuffleComplete;
    }
    
    /**
     * Flag the deal as complete
     * @param dealComplete true = yes, false = no
     */
    @Override
    public void setDealComplete(final boolean dealComplete)
    {
        this.dealComplete = dealComplete;
    }
    
    /**
     * Is the deal complete?
     * @return true = yes, false = no
     */
    @Override
    public boolean isDealComplete()
    {
        return this.dealComplete;
    }
        
    /**
     * Set the game over
     * @param gameover set true if the game is over, false otherwise
     */
    @Override
    public void setGameover(final boolean gameover)
    {
        this.gameover = gameover;
    }
    
    /**
     * Is the game over
     * @return true = yes, false = no
     */
    @Override
    public boolean hasGameover()
    {
        return this.gameover;
    }
    
    /**
     * Flag winner if the player won
     * @param winner true = win, false = lose
     */
    @Override
    public void setWinner(final boolean winner)
    {
        this.winner = winner;
    }
    
    /**
     * Did the player win?
     * @return true = yes, false = no
     */
    @Override
    public boolean isWinner()
    {
        return this.winner;
    }
    
    @Override
    public void dispose()
    {
        //call parent dispose
        super.dispose();
        
        if (holders != null)
        {
            for (Object key : holders.keySet())
            {
                holders.get(key).dispose();
                holders.put(key, null);
            }
            
            holders.clear();
            holders = null;
        }
    }
    
    /**
     * Render our game
     * @param graphics Object used to write image
     * @throws Exception 
     */
    @Override
    public void render(final Graphics graphics) throws Exception
    {
        //render all of our holders
        for (Holder tmp : holders.values())
        {
            tmp.render(graphics, getImage());
        }
        
        if (getDefaultHolder() != null && !getDefaultHolder().isEmpty())
            getDefaultHolder().render(graphics, getImage(), false);
    }
}