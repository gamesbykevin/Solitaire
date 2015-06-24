package com.gamesbykevin.solitaire.solitaire.goodmeasure;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.Suit;
import com.gamesbykevin.solitaire.card.Card.Value;

/**
 * Good Measure Helper
 * @author GOD
 */
public final class GoodMeasureHelper 
{
    /**
     * Do we have order?
     * @param card1 Card 1 (parent) 
     * @param card2 Card 2 (child)
     * @return true if the child card (card2) contains the rank directly after the parent (card1)
     * @throws Exception 
     */
    public static boolean hasOrder(final Card card1, final Card card2) throws Exception
    {
        switch (card1.getValue())
        {
            //nothing comes after ace
            case Ace:
                return false;
                
            case Two:
                return (card2.hasValue(Value.Ace));
                
            case Three:
                return (card2.hasValue(Value.Two));
            
            case Four:
                return (card2.hasValue(Value.Three));
                
            case Five:
                return (card2.hasValue(Value.Four));
                
            case Six:
                return (card2.hasValue(Value.Five));
                
            case Seven:
                return (card2.hasValue(Value.Six));
            
            case Eight:
                return (card2.hasValue(Value.Seven));
                
            case Nine:
                return (card2.hasValue(Value.Eight));
                
            case Ten:
                return (card2.hasValue(Value.Nine));
                
            case Jack:
                return (card2.hasValue(Value.Ten));
            
            case Queen:
                return (card2.hasValue(Value.Jack));
                
            case King:
                return (card2.hasValue(Value.Queen));
                
            default:
                throw new Exception("Card value not handled here " + card1.getValue().toString());
        }
    }
}