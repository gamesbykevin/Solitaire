package com.gamesbykevin.solitaire.solitaire.pyramid;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.Value;
import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.pyramid.Pyramid.Key;

/**
 * Helper classes for Pyramid Solitaire
 * @author GOD
 */
public final class PyramidHelper 
{
    //the score for each card
    protected static final int SCORE_ACE   = 1;
    protected static final int SCORE_TWO   = 2;
    protected static final int SCORE_THREE = 3;
    protected static final int SCORE_FOUR  = 4;
    protected static final int SCORE_FIVE  = 5;
    protected static final int SCORE_SIX   = 6;
    protected static final int SCORE_SEVEN = 7;
    protected static final int SCORE_EIGHT = 8;
    protected static final int SCORE_NINE  = 9;
    protected static final int SCORE_TEN   = 10;
    protected static final int SCORE_JACK  = 11;
    protected static final int SCORE_QUEEN = 12;
    protected static final int SCORE_KING  = 13;
    
    protected static int getScore(final Holder holder) throws Exception
    {
        int score = 0;
        
        for (int index = 0; index < holder.getSize(); index++)
        {
            //add score
            score += getScore(holder.getCard(index));
        }
        
        //return the final score
        return score;
    }
    
    /**
     * Get the score for the card
     * @param card The card we want to check
     * @return The score for the specified card's face value
     * @throws Exception if the value specified is not handled
     */
    protected static int getScore(final Card card) throws Exception
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
                throw new Exception("Card value not found " + card.getValue().toString());
        }
    }
    
    /**
     * Do any of the cards have the specified source holder key?<br>
     * The purpose is to prevent the user from selecting the same holder
     * @param holder The holder we want to check
     * @param sourceHolderKey The key we are looking for
     * @return true if at least 1 card in the holder has the specified source key, false otherwise
     */
    protected static boolean hasKey(final Holder holder, final Key sourceHolderKey)
    {
        //check each card
        for (int index = 0; index < holder.getSize(); index++)
        {
            //if this card has the source key, return true
            if (holder.getCard(index).getSourceHolderKey() == sourceHolderKey)
                return true;
        }
        
        //the key was not found
        return false;
    }
    
    /**
     * Is the key of the specified holder blocked?<br>
     * Blocked means there are holders in front of it that have a card.
     * @param solitaire The game containing all holder objects
     * @param holder The holder containing existing selected cards
     * @param key The key of the holder we want to select
     * @return true if there are holders with cards blocking the specified holder, false otherwise
     */
    protected static boolean isBlocked(final Solitaire solitaire, final Holder holder, final Key key) throws Exception
    {
        switch (key)
        {
            //we can always select the options card (if cards exist)
            case OptionalCard:
                if (solitaire.getHolder(Key.OptionalCard).isEmpty())
                    return true;
                break;
                
            case Row_1_Column_1:
                if (!solitaire.getHolder(Key.Row_2_Column_1).isEmpty() || holder.hasSourceHolderKey(Key.Row_2_Column_1))
                    return true;
                if (!solitaire.getHolder(Key.Row_2_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_2_Column_1))
                    return true;
                break;
                
            case Row_2_Column_1:
                if (!solitaire.getHolder(Key.Row_3_Column_1).isEmpty() || holder.hasSourceHolderKey(Key.Row_3_Column_1))
                    return true;
                if (!solitaire.getHolder(Key.Row_3_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_3_Column_2))
                    return true;
                break;
                
            case Row_2_Column_2:
                if (!solitaire.getHolder(Key.Row_3_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_3_Column_2))
                    return true;
                if (!solitaire.getHolder(Key.Row_3_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_3_Column_3))
                    return true;
                break;
                
            case Row_3_Column_1:
                if (!solitaire.getHolder(Key.Row_4_Column_1).isEmpty() || holder.hasSourceHolderKey(Key.Row_4_Column_1))
                    return true;
                if (!solitaire.getHolder(Key.Row_4_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_4_Column_2))
                    return true;
                break;
                
            case Row_3_Column_2:
                if (!solitaire.getHolder(Key.Row_4_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_4_Column_2))
                    return true;
                if (!solitaire.getHolder(Key.Row_4_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_4_Column_3))
                    return true;
                break;
                
            case Row_3_Column_3:
                if (!solitaire.getHolder(Key.Row_4_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_4_Column_3))
                    return true;
                if (!solitaire.getHolder(Key.Row_4_Column_4).isEmpty() || holder.hasSourceHolderKey(Key.Row_4_Column_4))
                    return true;
                break;
                
            case Row_4_Column_1:
                if (!solitaire.getHolder(Key.Row_5_Column_1).isEmpty() || holder.hasSourceHolderKey(Key.Row_5_Column_1))
                    return true;
                if (!solitaire.getHolder(Key.Row_5_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_5_Column_2))
                    return true;
                break;
                
            case Row_4_Column_2:
                if (!solitaire.getHolder(Key.Row_5_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_5_Column_2))
                    return true;
                if (!solitaire.getHolder(Key.Row_5_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_5_Column_3))
                    return true;
                break;
                
            case Row_4_Column_3:
                if (!solitaire.getHolder(Key.Row_5_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_5_Column_3))
                    return true;
                if (!solitaire.getHolder(Key.Row_5_Column_4).isEmpty() || holder.hasSourceHolderKey(Key.Row_5_Column_4))
                    return true;
                break;
                
            case Row_4_Column_4:
                if (!solitaire.getHolder(Key.Row_5_Column_4).isEmpty() || holder.hasSourceHolderKey(Key.Row_5_Column_4))
                    return true;
                if (!solitaire.getHolder(Key.Row_5_Column_5).isEmpty() || holder.hasSourceHolderKey(Key.Row_5_Column_5))
                    return true;
                break;
                
            case Row_5_Column_1:
                if (!solitaire.getHolder(Key.Row_6_Column_1).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_1))
                    return true;
                if (!solitaire.getHolder(Key.Row_6_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_2))
                    return true;
                break;
                
            case Row_5_Column_2:
                if (!solitaire.getHolder(Key.Row_6_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_2))
                    return true;
                if (!solitaire.getHolder(Key.Row_6_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_3))
                    return true;
                break;
                
            case Row_5_Column_3:
                if (!solitaire.getHolder(Key.Row_6_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_3))
                    return true;
                if (!solitaire.getHolder(Key.Row_6_Column_4).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_4))
                    return true;
                break;
                
            case Row_5_Column_4:
                if (!solitaire.getHolder(Key.Row_6_Column_4).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_4))
                    return true;
                if (!solitaire.getHolder(Key.Row_6_Column_5).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_5))
                    return true;
                break;
                
            case Row_5_Column_5:
                if (!solitaire.getHolder(Key.Row_6_Column_5).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_5))
                    return true;
                if (!solitaire.getHolder(Key.Row_6_Column_6).isEmpty() || holder.hasSourceHolderKey(Key.Row_6_Column_6))
                    return true;
                break;
                
            case Row_6_Column_1:
                if (!solitaire.getHolder(Key.Row_7_Column_1).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_1))
                    return true;
                if (!solitaire.getHolder(Key.Row_7_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_2))
                    return true;
                break;
                
            case Row_6_Column_2:
                if (!solitaire.getHolder(Key.Row_7_Column_2).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_2))
                    return true;
                if (!solitaire.getHolder(Key.Row_7_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_3))
                    return true;
                break;
                
            case Row_6_Column_3:
                if (!solitaire.getHolder(Key.Row_7_Column_3).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_3))
                    return true;
                if (!solitaire.getHolder(Key.Row_7_Column_4).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_4))
                    return true;
                break;
                
            case Row_6_Column_4:
                if (!solitaire.getHolder(Key.Row_7_Column_4).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_4))
                    return true;
                if (!solitaire.getHolder(Key.Row_7_Column_5).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_5))
                    return true;
                break;
                
            case Row_6_Column_5:
                if (!solitaire.getHolder(Key.Row_7_Column_5).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_5))
                    return true;
                if (!solitaire.getHolder(Key.Row_7_Column_6).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_6))
                    return true;
                break;
                
            case Row_6_Column_6:
                if (!solitaire.getHolder(Key.Row_7_Column_6).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_6))
                    return true;
                if (!solitaire.getHolder(Key.Row_7_Column_7).isEmpty() || holder.hasSourceHolderKey(Key.Row_7_Column_7))
                    return true;
                break;
                
            //all holders on the front row are selectable
            case Row_7_Column_1:
            case Row_7_Column_2:
            case Row_7_Column_3:
            case Row_7_Column_4:
            case Row_7_Column_5:
            case Row_7_Column_6:
            case Row_7_Column_7:
                return false;
                
            default:
                throw new Exception("Holder not handled here " + key.toString());
        }
        
        //this holder is not blocked
        return false;
    }
    
    /**
     * Can we make another selection?<br>
     * We will check the size of the holder and if the first card is a king.
     * @param holder The holder containing cards we want to check.
     * @return true if we can select another card, false otherwise
     */
    protected static boolean canSelect(final Holder holder)
    {
        //if we meet/exceed the limit, we can't make a selection
        if (holder.getSize() >= Pyramid.SELECT_LIMIT)
            return false;
        
        //if the first card is a king, we can't make a selection
        if (!holder.isEmpty() && holder.getFirstCard().getValue() == Value.King)
            return false;
        
        //we can make a selection
        return true;
    }
}