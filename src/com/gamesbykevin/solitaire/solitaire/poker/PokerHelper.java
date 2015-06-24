package com.gamesbykevin.solitaire.solitaire.poker;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Poker helper methods
 * @author GOD
 */
public final class PokerHelper 
{
    //the score points for each type of hand
    protected static final int SCORE_ROYAL_FLUSH = 4000;
    protected static final int SCORE_STRAIGHT_FLUSH = 3000;
    protected static final int SCORE_FOUR_OF_KIND = 1600;
    protected static final int SCORE_STRAIGHT = 1200;
    protected static final int SCORE_FULL_HOUSE = 1000;
    protected static final int SCORE_THREE_OF_KIND = 600;
    protected static final int SCORE_FLUSH = 500;
    protected static final int SCORE_TWO_PAIR = 300;
    protected static final int SCORE_ONE_PAIR = 100;
    
    /**
     * The required number of cards to match for four of a kind
     */
    protected static final int FOUR_OF_KIND_MATCH = 3;
    
    /**
     * The required number of cards to match for three of a kind
     */
    protected static final int THREE_OF_KIND_MATCH = 2;
    
    /**
     * The required number of cards to match for a pair
     */
    protected static final int PAIR_MATCH = 1;

    /**
     * Do we have a royal flush?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if all cards are match the same suit and are the highest ranking cards of that suit, false otherwise
     */
    public static boolean isRoyalFlush(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5)
    {
        //if the suits don't match we can't have a royal flush
        if (!isMatchingSuit(card1, card2, card3, card4, card5))
            return false;
        
        //if none of the cards is Ten, we can't have a royal flush
        if (!card1.hasValue(Value.Ten) && !card2.hasValue(Value.Ten) && !card3.hasValue(Value.Ten) && !card4.hasValue(Value.Ten) && !card5.hasValue(Value.Ten))
            return false;
        
        //if none of the cards is Jack, we can't have a royal flush
        if (!card1.hasValue(Value.Jack) && !card2.hasValue(Value.Jack) && !card3.hasValue(Value.Jack) && !card4.hasValue(Value.Jack) && !card5.hasValue(Value.Jack))
            return false;
        
        //if none of the cards is Queen, we can't have a royal flush
        if (!card1.hasValue(Value.Queen) && !card2.hasValue(Value.Queen) && !card3.hasValue(Value.Queen) && !card4.hasValue(Value.Queen) && !card5.hasValue(Value.Queen))
            return false;
        
        //if none of the cards is King, we can't have a royal flush
        if (!card1.hasValue(Value.King) && !card2.hasValue(Value.King) && !card3.hasValue(Value.King) && !card4.hasValue(Value.King) && !card5.hasValue(Value.King))
            return false;
        
        //if none of the cards is King, we can't have a royal flush
        if (!card1.hasValue(Value.Ace) && !card2.hasValue(Value.Ace) && !card3.hasValue(Value.Ace) && !card4.hasValue(Value.Ace) && !card5.hasValue(Value.Ace))
            return false;
        
        //we have a royal flush
        return true;
    }
    
    /**
     * Do we have a straight flush?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if all cards are match the same suit and are in order, but don't have to bethe highest ranking cards of that suit, false otherwise
     * @throws Exception 
     */
    public static boolean isStraightFlush(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //if the suits don't match we can't have a straight flush
        if (!isMatchingSuit(card1, card2, card3, card4, card5))
            return false;
        
        //if the cards are not in direct order, we can't have a straight flush
        if (!isStraight(card1, card2, card3, card4, card5))
            return false;
        
        //we have a straight flush
        return true;
    }
    
    /**
     * Do we have four of a kind?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if 4 of the 5 cards have the same face value, false otherwise
     */
    public static boolean isFourOfKind(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //get an ordered list of these cards
        List<Card> cards = getOrderedList(card1, card2, card3, card4, card5);
        
        //count the number of matches
        int count = 0;
        
        //check the list for matches
        for (int index = 1; index < cards.size(); index++)
        {
            //if the cards match increase the count
            if (cards.get(index - 1).hasValue(cards.get(index)))
            {
                count++;
            }
            else
            {
                //if the cards don't match lets see if the current count matches four of a kind
                if (count == FOUR_OF_KIND_MATCH)
                    return true;
                
                //if we don't have for of a kind reset the count
                count = 0;
            }
        }
        
        //check if the final count matches
        return (count == FOUR_OF_KIND_MATCH);
    }
    
    /**
     * Do we have a straight?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if the rank values of all cards are in direct order (regardless of suit) with no matching face values, false otherwise
     */
    public static final boolean isStraight(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //get an ordered list of these cards
        List<Card> cards = getOrderedList(card1, card2, card3, card4, card5);
        
        //now make sure the ranks are directly in order
        for (int index = 1; index < cards.size(); index++)
        {
            //get the next cards
            final Card tmp1 = cards.get(index - 1);
            final Card tmp2 = cards.get(index);
            
            //if the first card is an Ace then the next card will not be in direct order
            if (getNextValue(tmp1) == null)
                return false;
            
            //if the next rank value is not equal to the rank value of our second card, the cards aren't in direct order
            if (getNextValue(tmp1) != tmp2.getValue())
                return false;
        }
        
        //we have a straight
        return true;
    }
    
    /**
     * Do we have a full house?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if the hand has 3 cards of a kind, and a pair otherwise false is returned
     * @throws Exception 
     */
    public static final boolean isFullHouse(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //get an ordered list of these cards
        List<Card> cards = getOrderedList(card1, card2, card3, card4, card5);
        
        //do we have three of a kind
        boolean threeOfKind = false;
        
        //do we have a pair
        boolean onePair = false;
        
        //count the number of matches
        int count = 0;
        
        //now make sure the face values match accordingly
        for (int index = 1; index < cards.size(); index++)
        {
            //if the cards match increase the count
            if (cards.get(index - 1).hasValue(cards.get(index)))
            {
                count++;
            }
            else
            {
                //if the cards don't match lets see if the current count matches one of these
                if (count == THREE_OF_KIND_MATCH)
                    threeOfKind = true;
                if (count == PAIR_MATCH)
                    onePair = true;
                
                //reset the count
                count = 0;
            }
        }
        
        //if the loop is over check if the current count matches one of these
        if (count == THREE_OF_KIND_MATCH)
            threeOfKind = true;
        if (count == PAIR_MATCH)
            onePair = true;
        
        //if we have a pair match, and three cards match we have a full house
        return (threeOfKind && onePair);
    }
    
    /**
     * Do we have three of a kind?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if 3 cards have the same face value, false otherwise
     * @throws Exception 
     */
    public static final boolean isThreeOfKind(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //get an ordered list of these cards
        List<Card> cards = getOrderedList(card1, card2, card3, card4, card5);
        
        //count the number of matches
        int count = 0;
        
        //now make sure the face values match accordingly
        for (int index = 1; index < cards.size(); index++)
        {
            //if the cards match increase the count
            if (cards.get(index - 1).hasValue(cards.get(index)))
            {
                count++;
            }
            else
            {
                //if the number of matches is three of kind return true
                if (count == THREE_OF_KIND_MATCH)
                    return true;
                
                //reset the count
                count = 0;
            }
        }
        
        //check if the final count matches
        return (count == THREE_OF_KIND_MATCH);
    }
    
    /**
     * Do we have a flush?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if all cards have the same suit, false otherwise
     * @throws Exception 
     */
    public static final boolean isFlush(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //if the suits don't match we can't have a flush
        if (!isMatchingSuit(card1, card2, card3, card4, card5))
            return false;
        
        //all suits match, we have a flush
        return true;
    }
    
    /**
     * Do we have a two pair?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if 2 pairs of cards have the same face value, false otherwise
     * @throws Exception 
     */
    public static final boolean isTwoPair(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //get an ordered list of these cards
        List<Card> cards = getOrderedList(card1, card2, card3, card4, card5);
        
        //total number of pairs
        int pairTotal = 0;
        
        //count the number of matches
        int count = 0;
        
        //now make sure the face values match accordingly
        for (int index = 1; index < cards.size(); index++)
        {
            //if the cards match increase the count
            if (cards.get(index - 1).hasValue(cards.get(index)))
            {
                count++;
            }
            else
            {
                //if the number of matches is a pair add to total
                if (count == PAIR_MATCH)
                    pairTotal++;
                
                //reset the count
                count = 0;
            }
        }
        
        //if the number of matches is a pair add to total
        if (count == PAIR_MATCH)
            pairTotal++;
        
        //check if the final count of pairs matches 2
        return (pairTotal == 2);
    }
    
    /**
     * Do we have 1 pair?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true if 2 cards have the same face value, false otherwise
     * @throws Exception 
     */
    public static final boolean isOnePair(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //get an ordered list of these cards
        List<Card> cards = getOrderedList(card1, card2, card3, card4, card5);
        
        //total number of pairs
        int pairTotal = 0;
        
        //count the number of matches
        int count = 0;
        
        //now make sure the face values match accordingly
        for (int index = 1; index < cards.size(); index++)
        {
            //if the cards match increase the count
            if (cards.get(index - 1).hasValue(cards.get(index)))
            {
                count++;
            }
            else
            {
                //if the number of matches is a pair add to total
                if (count == PAIR_MATCH)
                    pairTotal++;
                
                //reset the count
                count = 0;
            }
        }
        
        //if the number of matches is a pair add to total
        if (count == PAIR_MATCH)
            pairTotal++;
        
        //check if the final count of pairs matches 1
        return (pairTotal == 1);
    }
    
    /**
     * Get a order list of the cards.
     * @param card1
     * @param card2
     * @param card3
     * @param card4
     * @param card5
     * @return List of cards sorted by face value in ascending order
     * @throws Exception 
     */
    private static final List<Card> getOrderedList(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) throws Exception
    {
        //create list
        List<Card> cards = new ArrayList<>();
        
        //add cards to the list
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        
        //now sort the list by the cards face value
        for (int index = 0; index < cards.size(); index++)
        {
            for (int i = 1; i < cards.size(); i++)
            {
                //get the two cards
                final Card tmp1 = cards.get(i - 1);
                final Card tmp2 = cards.get(i);
                
                //if the first value is greater than the next
                if (getNumericValue(tmp1) > getNumericValue(tmp2))
                {
                    //swap the cards
                    cards.set(i,     tmp1);
                    cards.set(i - 1, tmp2);
                }
            }
        }
        
        return cards;
    }
    
    /**
     * Get the next rank value of the card.<br>
     * If the card is an Ace null will be returned because there is no rank after Ace
     * @param card The card we want to check
     * @return The next rank after the card specified
     * @throws Exception if the specified card face value is not handled here
     */
    private static final Card.Value getNextValue(final Card card) throws Exception
    {
        switch (card.getValue())
        {
            case Two:
                return Value.Three;
                
            case Three:
                return Value.Four;
                
            case Four:
                return Value.Five;
                
            case Five:
                return Value.Six;
                
            case Six:
                return Value.Seven;
                
            case Seven:
                return Value.Eight;
                
            case Eight:
                return Value.Nine;
                
            case Nine:
                return Value.Ten;
                
            case Ten:
                return Value.Jack;
                
            case Jack:
                return Value.Queen;
                
            case Queen:
                return Value.King;
                
            case King:
                return Value.Ace;
                
            case Ace:
                return null;
                
            default:
                throw new Exception("Face value not handled here " + card.getValue().toString());
        }
    }
    
    /**
     * Get the numeric value of the face card.<br>
     * This will make it easier to order, sort
     * @param card The face card we want to check
     * @return The numeric value of the face card
     * @throws Exception if the specified card face value is not handled here
     */
    private static final int getNumericValue(final Card card) throws Exception
    {
        switch (card.getValue())
        {
            case Two:
                return 2;
                
            case Three:
                return 3;
                
            case Four:
                return 4;
                
            case Five:
                return 5;
                
            case Six:
                return 6;
                
            case Seven:
                return 7;
                
            case Eight:
                return 8;
                
            case Nine:
                return 9;
                
            case Ten:
                return 10;
                
            case Jack:
                return 11;
                
            case Queen:
                return 12;
                
            case King:
                return 13;
                
            case Ace:
                return 14;
                
            default:
                throw new Exception("Face value not handled here " + card.getValue().toString());
        }
    }
    
    /**
     * Do the suits of all cards match?
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @param card4 Card 4
     * @param card5 Card 5
     * @return true = yes, false = no
     */
    private static boolean isMatchingSuit(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5)
    {
        //if the suit of one card does not match, return false
        if (!card1.hasSuit(card2) || !card1.hasSuit(card3) || !card1.hasSuit(card4) || !card1.hasSuit(card5))
            return false;
        if (!card2.hasSuit(card3) || !card2.hasSuit(card4) || !card2.hasSuit(card5))
            return false;
        if (!card3.hasSuit(card4) || !card3.hasSuit(card5))
            return false;
        if (!card4.hasSuit(card5))
            return false;
        
        //the suits match
        return true;
    }
}