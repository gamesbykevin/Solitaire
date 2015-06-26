package com.gamesbykevin.solitaire.solitaire.golf;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.Value;

/**
 * Golf helper methods
 * @author GOD
 */
public final class GolfHelper 
{
    /**
     * Are these cards neighbors?<br>
     * We will compare the face values.
     * @param card1 Card we want to compare
     * @param card2 Card we want to compare
     * @return true if the face value of one card is within the other, false otherwise
     * @throws Exception will be thrown if a unspecified face value is not handled here
     */
    public static boolean isNeighbor(final Card card1, final Card card2) throws Exception
    {
        switch (card1.getValue())
        {
            case Ace:
                if (card2.hasValue(Value.King) || card2.hasValue(Value.Two))
                    return true;
                break;
                
            case Two:
                if (card2.hasValue(Value.Ace) || card2.hasValue(Value.Three))
                    return true;
                break;
                
            case Three:
                if (card2.hasValue(Value.Two) || card2.hasValue(Value.Four))
                    return true;
                break;
                
            case Four:
                if (card2.hasValue(Value.Three) || card2.hasValue(Value.Five))
                    return true;
                break;
                
            case Five:
                if (card2.hasValue(Value.Four) || card2.hasValue(Value.Six))
                    return true;
                break;
                
            case Six:
                if (card2.hasValue(Value.Five) || card2.hasValue(Value.Seven))
                    return true;
                break;
                
            case Seven:
                if (card2.hasValue(Value.Six) || card2.hasValue(Value.Eight))
                    return true;
                break;
                
            case Eight:
                if (card2.hasValue(Value.Seven) || card2.hasValue(Value.Nine))
                    return true;
                break;
                
            case Nine:
                if (card2.hasValue(Value.Eight) || card2.hasValue(Value.Ten))
                    return true;
                break;
                
            case Ten:
                if (card2.hasValue(Value.Nine) || card2.hasValue(Value.Jack))
                    return true;
                break;
                
            case Jack:
                if (card2.hasValue(Value.Ten) || card2.hasValue(Value.Queen))
                    return true;
                break;
                
            case Queen:
                if (card2.hasValue(Value.Jack) || card2.hasValue(Value.King))
                    return true;
                break;
                
            case King:
                if (card2.hasValue(Value.Queen) || card2.hasValue(Value.Ace))
                    return true;
                break;
                
            default:
                throw new Exception("Face value not handled here " + card1.getValue().toString());
        }
        
        //we didn't find a neighbor
        return false;
    }
}