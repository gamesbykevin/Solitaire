package com.gamesbykevin.solitaire.solitaire.freecell;

import com.gamesbykevin.solitaire.card.Holder;
import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;
import com.gamesbykevin.solitaire.solitaire.klondike.KlondikeHelper;

/**
 * Free Cell Helper Methods
 * @author GOD
 */
public final class FreeCellHelper 
{
    /**
     * Does this holder have sequence?
     * @param holder The holder containing the cards we want to check
     * @param x x-coordinate where the mouse is
     * @param y y-coordinate where the mouse is
     * @return true if the cards are in sequence, false otherwise
     */
    public static final boolean hasSequence(final Holder holder, final int x, final int y) throws Exception
    {
        //if no cards return false
        if (holder.isEmpty())
            return false;
        
        //if the top card was selected, it is in sequence
        if (holder.getLastCard().hasLocation(x, y))
            return true;
        
        //check the cards
        for (int index = holder.getSize() - 2; index >= 0; index--)
        {
            //if we selected this card
            if (holder.getCard(index).hasLocation(x, y))
            {
                //check all below to verify sequence
                for (int index1 = index + 1; index1 < holder.getSize(); index1++)
                {
                    //check these cards currently
                    final Card card1 = holder.getCard(index1 - 1);
                    final Card card2 = holder.getCard(index1);
                    
                    //if these cards are not in sequence return false
                    if (!KlondikeHelper.canPlaceCardInPlayable(card1, card2))
                        return false;
                }
                
                //exit the loop
                break;
            }
        }
        
        //we have sequence
        return true;
    }
}