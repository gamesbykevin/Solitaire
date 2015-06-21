package com.gamesbykevin.solitaire.solitaire;

import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.shared.IElement;

import java.util.Random;

/**
 * The rules for each Solitaire game
 * @author GOD
 */
public interface ISolitaire extends IElement
{
    /**
     * Flag the deal as complete
     * @param dealComplete true = yes, false = no
     */
    public void setDealComplete(final boolean dealComplete);
    
    /**
     * Is the deal complete
     * @return true = yes, false = no
     */
    public boolean isDealComplete();
    
    /**
     * A method to deal the cards
     * @param time The time per each update (nanoseconds)
     * @throws Exception 
     */
    public void deal(final long time) throws Exception;
    
    /**
     * A method to shuffle the cards
     * @param random Object used to make random decisions
     * @param holder The holder containing the cards we want to shuffle
     * @throws Exception 
     */
    public void shuffle(final Random random, final Holder holder) throws Exception;
    
    /**
     * Flag the shuffle as complete
     * @param shuffleComplete true = yes, false = no
     */
    public void setShuffleComplete(final boolean shuffleComplete);
    
    /**
     * Is the shuffling complete
     * @return true = yes, false = no
     */
    public boolean isShuffleComplete();
    
    /**
     * A method to create the deck
     * @param random Object used to make random decisions
     * @throws Exception 
     */
    public void create(final Random random) throws Exception;
    
    /**
     * Flag the create as complete
     * @param createComplete true = yes, false = no
     */
    public void setCreateComplete(final boolean createComplete);
    
    /**
     * Is the create deck complete
     * @return true = yes, false = no
     */
    public boolean isCreateComplete();
    
    /**
     * A method to check for a winner, etc...
     */
    public void validate() throws Exception;
    
    /**
     * Set the game over
     * @param gameover set true if the game is over, false otherwise
     */
    public void setGameover(final boolean gameover);
    
    /**
     * Is the game over
     * @return true = yes, false = no
     */
    public boolean hasGameover();
    
    /**
     * Flag winner if the player won
     * @param winner true = win, false = lose
     */
    public void setWinner(final boolean winner);
    
    /**
     * Did the player win?
     * @return true = yes, false = no
     */
    public boolean isWinner();
}