package com.gamesbykevin.solitaire.solitaire.littlespider;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.Value;

/**
 * Little Spider Helper methods
 * @author GOD
 */
public final class LittleSpiderHelper 
{
    /**
     * Can the card be placed in the destination area?
     * @param card1 The existing card
     * @param card2 The card we want to add as a child
     * @return true if card2 can be placed as a child of card1, false otherwise
     * @throws Exception if unknown suit or face value
     */
    public static boolean canPlaceCardInDestination(final Card card1, final Card card2) throws Exception
    {
        //if the suit does not match
        if (!card1.hasSuit(card2))
            return false;
        
        switch (card1.getValue())
        {
            //nothing can be added to the ace
            case Ace:
                return false;
                
            case Two:
                if (card2.getValue() != Value.Ace)
                    return false;
                break;
                
            case Three:
                if (card2.getValue() != Value.Two)
                    return false;
                break;
                
            case Four:
                if (card2.getValue() != Value.Three)
                    return false;
                break;
                
            case Five:
                if (card2.getValue() != Value.Four)
                    return false;
                break;
                
            case Six:
                if (card2.getValue() != Value.Five)
                    return false;
                break;
                
            case Seven:
                if (card2.getValue() != Value.Six)
                    return false;
                break;
                
            case Eight:
                if (card2.getValue() != Value.Seven)
                    return false;
                break;
                
            case Nine:
                if (card2.getValue() != Value.Eight)
                    return false;
                break;
                
            case Ten:
                if (card2.getValue() != Value.Nine)
                    return false;
                break;
                
            case Jack:
                if (card2.getValue() != Value.Ten)
                    return false;
                break;
                
            case Queen:
                if (card2.getValue() != Value.Jack)
                    return false;
                break;
                
            case King:
                if (card2.getValue() != Value.Queen)
                    return false;
                break;
                
            default:
                throw new Exception("Face value not found here " + card1.getValue().toString());
        }
        
        //we can place the card
        return true;
    }
}
