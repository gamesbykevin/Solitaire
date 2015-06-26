package com.gamesbykevin.solitaire.solitaire.blockten;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.card.Holder;

/**
 * Block Ten Helper Methods
 * @author GOD
 */
public class BlockTenHelper 
{
    //the score we are looking for
    protected static final int GOAL_SCORE = 10;
    
    //the different score for each card
    protected static final int SCORE_ACE = 1;
    protected static final int SCORE_TWO = 2;
    protected static final int SCORE_THREE = 3;
    protected static final int SCORE_FOUR = 4;
    protected static final int SCORE_FIVE = 5;
    protected static final int SCORE_SIX = 6;
    protected static final int SCORE_SEVEN = 7;
    protected static final int SCORE_EIGHT = 8;
    protected static final int SCORE_NINE = 9;
    
    //we can't score ten hence the game is called block ten
    protected static final int SCORE_TEN = 0;
    
    protected static final int SCORE_JACK = 5;
    protected static final int SCORE_QUEEN = 5;
    protected static final int SCORE_KING = 5;
    
    /**
     * Get the score of the cards in the holder
     * @param holder Holder containing cards we want to score
     * @return The total score of the cards
     * @throws Exception If the incorrect card size is provided or if there is an issue scoring
     */
    protected static final int getScore(final Holder holder) throws Exception
    {
        if (holder.getSize() != BlockTen.SELECTION_LIMIT)
            throw new Exception("Holder amount is not the correct size " + holder.getSize());
        
        //if one card is the below they all have to be the same face value, or else we will not count the score
        if (holder.hasValue(Value.Jack) || holder.hasValue(Value.Queen) || holder.hasValue(Value.King))
        {
            //check each card to make sure all have the same face value
            for (int index = 1; index < holder.getSize(); index++)
            {
                //get the 2 cards
                final Card card1 = holder.getCard(index);
                final Card card2 = holder.getCard(index - 1);
                
                //if the cards are not the same return SCORE_TEN aka 0
                if (!card1.hasValue(card2))
                    return SCORE_TEN;
            }
        }
        
        //our score
        int score = 0;
        
        //check each card to make sure all have the same face value
        for (int index = 0; index < holder.getSize(); index++)
        {
            //add the card score to the total
            score += getScore(holder.getCard(index));
        }
        
        //return our calculated score
        return score;
    }
    
    /**
     * Get the score of the card
     * @param card The card we want to check
     * @return The score of the card
     * @throws Exception If the card value is not scored here
     */
    private static final int getScore(final Card card) throws Exception
    {
        switch (card.getValue())
        {
            case Ace:
                return SCORE_ACE;
            
            case Two:
                return SCORE_TWO;
                
            case Three:
                return SCORE_THREE;
                
            case Four:
                return SCORE_FOUR;
            
            case Five:
                return SCORE_FIVE;
                
            case Six:
                return SCORE_SIX;
                
            case Seven:
                return SCORE_SEVEN;
            
            case Eight:
                return SCORE_EIGHT;
                
            case Nine:
                return SCORE_NINE;
                
            case Ten:
                return SCORE_TEN;
            
            case Jack:
                return SCORE_JACK;
                
            case Queen:
                return SCORE_QUEEN;
                
            case King:
                return SCORE_KING;
                
            default:
                throw new Exception("Card value not found here " + card.getValue());
        }
    }
}