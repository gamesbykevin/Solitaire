package com.gamesbykevin.solitaire.solitaire.pyramid;

import com.gamesbykevin.solitaire.card.Card;
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
    private static final int SCORE_ACE   = 1;
    private static final int SCORE_TWO   = 2;
    private static final int SCORE_THREE = 3;
    private static final int SCORE_FOUR  = 4;
    private static final int SCORE_FIVE  = 5;
    private static final int SCORE_SIX   = 6;
    private static final int SCORE_SEVEN = 7;
    private static final int SCORE_EIGHT = 8;
    private static final int SCORE_NINE  = 9;
    private static final int SCORE_TEN   = 10;
    private static final int SCORE_JACK  = 11;
    private static final int SCORE_QUEEN = 12;
    private static final int SCORE_KING  = 13;
    
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
    private static int getScore(final Card card) throws Exception
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
     * Can we select the placeholder?
     * @param solitaire The game containing all holder objects
     * @param key The key of the holder we want to select
     * @return true if there are no holders blocking the specified, false otherwise
     */
    protected static boolean canSelect(final Solitaire solitaire, final Key key) throws Exception
    {
        switch (key)
        {
            //we can always select the options card (if cards exist)
            case OptionalCard:
                if (solitaire.getHolder(Key.OptionalCard).isEmpty())
                    return false;
                break;
                
            case Row_1_Column_1:
                if (!solitaire.getHolder(Key.Row_2_Column_1).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_2_Column_2).isEmpty())
                    return false;
                break;
                
            case Row_2_Column_1:
                if (!solitaire.getHolder(Key.Row_3_Column_1).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_3_Column_2).isEmpty())
                    return false;
                break;
                
            case Row_2_Column_2:
                if (!solitaire.getHolder(Key.Row_3_Column_2).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_3_Column_3).isEmpty())
                    return false;
                break;
                
            case Row_3_Column_1:
                if (!solitaire.getHolder(Key.Row_4_Column_1).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_4_Column_2).isEmpty())
                    return false;
                break;
                
            case Row_3_Column_2:
                if (!solitaire.getHolder(Key.Row_4_Column_2).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_4_Column_3).isEmpty())
                    return false;
                break;
                
            case Row_3_Column_3:
                if (!solitaire.getHolder(Key.Row_4_Column_3).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_4_Column_4).isEmpty())
                    return false;
                break;
                
            case Row_4_Column_1:
                if (!solitaire.getHolder(Key.Row_5_Column_1).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_5_Column_2).isEmpty())
                    return false;
                break;
                
            case Row_4_Column_2:
                if (!solitaire.getHolder(Key.Row_5_Column_2).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_5_Column_3).isEmpty())
                    return false;
                break;
                
            case Row_4_Column_3:
                if (!solitaire.getHolder(Key.Row_5_Column_3).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_5_Column_4).isEmpty())
                    return false;
                break;
                
            case Row_4_Column_4:
                if (!solitaire.getHolder(Key.Row_5_Column_4).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_5_Column_5).isEmpty())
                    return false;
                break;
                
            case Row_5_Column_1:
                if (!solitaire.getHolder(Key.Row_6_Column_1).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_6_Column_2).isEmpty())
                    return false;
                break;
                
            case Row_5_Column_2:
                if (!solitaire.getHolder(Key.Row_6_Column_2).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_6_Column_3).isEmpty())
                    return false;
                break;
                
            case Row_5_Column_3:
                if (!solitaire.getHolder(Key.Row_6_Column_3).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_6_Column_4).isEmpty())
                    return false;
                break;
                
            case Row_5_Column_4:
                if (!solitaire.getHolder(Key.Row_6_Column_4).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_6_Column_5).isEmpty())
                    return false;
                break;
                
            case Row_5_Column_5:
                if (!solitaire.getHolder(Key.Row_6_Column_5).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_6_Column_6).isEmpty())
                    return false;
                break;
                
            case Row_6_Column_1:
                if (!solitaire.getHolder(Key.Row_7_Column_1).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_7_Column_2).isEmpty())
                    return false;
                break;
                
            case Row_6_Column_2:
                if (!solitaire.getHolder(Key.Row_7_Column_2).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_7_Column_3).isEmpty())
                    return false;
                break;
                
            case Row_6_Column_3:
                if (!solitaire.getHolder(Key.Row_7_Column_3).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_7_Column_4).isEmpty())
                    return false;
                break;
                
            case Row_6_Column_4:
                if (!solitaire.getHolder(Key.Row_7_Column_4).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_7_Column_5).isEmpty())
                    return false;
                break;
                
            case Row_6_Column_5:
                if (!solitaire.getHolder(Key.Row_7_Column_5).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_7_Column_6).isEmpty())
                    return false;
                break;
                
            case Row_6_Column_6:
                if (!solitaire.getHolder(Key.Row_7_Column_6).isEmpty())
                    return false;
                if (!solitaire.getHolder(Key.Row_7_Column_7).isEmpty())
                    return false;
                break;
                
            //all holders on the front row are selectable
            case Row_7_Column_1:
            case Row_7_Column_2:
            case Row_7_Column_3:
            case Row_7_Column_4:
            case Row_7_Column_5:
            case Row_7_Column_6:
            case Row_7_Column_7:
                return true;
                
            default:
                throw new Exception("Holder not handled here " + key.toString());
        }
        
        //we can select the holder
        return true;
    }
}