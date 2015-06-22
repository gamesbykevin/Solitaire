package com.gamesbykevin.solitaire.solitaire.klondike;

import com.gamesbykevin.solitaire.card.Card;
import com.gamesbykevin.solitaire.card.Card.Value;
import com.gamesbykevin.solitaire.card.Holder;

/**
 * This class will provide convenient methods to manage our game
 * @author GOD
 */
public final class KlondikeHelper 
{
    /**
     * Assign the card(s) to the default place holder.<br>
     * The card found at (x, y) and all its children will be added to the default place holder
     * @param defaultHolder The default holder we want to add our cards to
     * @param holder The holder we want to check
     * @param sourceHolderKey The source holder the cards came from
     * @param x x-coordinate
     * @param y y-coordinate
     * @throws Exception 
     */
    public static void assignCards(final Holder defaultHolder, final Holder holder, final Object sourceHolderKey, final int x, final int y) throws Exception
    {
        for (int i = holder.getSize() - 1; i >= 0; i--)
        {
            //get the current card
            final Card card = holder.getCard(i);
            
            //if the card is hidden we can't select it
            if (card.isHidden())
                continue;
            
            //check if we found the parent card
            if (card.hasLocation(x, y))
            {
                //add this card and all children to the default holder
                for (int index = i; index < holder.getSize(); index++)
                {
                    //assign the source
                    holder.getCard(index).setSourceHolderKey(sourceHolderKey);
                    
                    //add to the default holder
                    defaultHolder.add(holder.getCard(index));
                }
                
                //now remove these cards from the existing holder
                for (int index = 0; index < defaultHolder.getSize(); index++)
                {
                    //remove from the existing holder
                    holder.remove(defaultHolder.getCard(index));
                }
                
                //we have our assigned cards, no need to continue
                return;
            }
        }
    }
    
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
            case Ace:
                if (card2.getValue() != Value.Two)
                    return false;
                break;
                
            case Two:
                if (card2.getValue() != Value.Three)
                    return false;
                break;
                
            case Three:
                if (card2.getValue() != Value.Four)
                    return false;
                break;
                
            case Four:
                if (card2.getValue() != Value.Five)
                    return false;
                break;
                
            case Five:
                if (card2.getValue() != Value.Six)
                    return false;
                break;
                
            case Six:
                if (card2.getValue() != Value.Seven)
                    return false;
                break;
                
            case Seven:
                if (card2.getValue() != Value.Eight)
                    return false;
                break;
                
            case Eight:
                if (card2.getValue() != Value.Nine)
                    return false;
                break;
                
            case Nine:
                if (card2.getValue() != Value.Ten)
                    return false;
                break;
                
            case Ten:
                if (card2.getValue() != Value.Jack)
                    return false;
                break;
                
            case Jack:
                if (card2.getValue() != Value.Queen)
                    return false;
                break;
                
            case Queen:
                if (card2.getValue() != Value.King)
                    return false;
                break;
                
            //nothing can be added to the king
            case King:
                return false;
                
            default:
                throw new Exception("Face value not found here " + card1.getValue().toString());
        }
        
        //we can place the card
        return true;
    }
    
    /**
     * Can the card be placed in the playable area?
     * @param card1 The existing card
     * @param card2 The card we want to add as a child
     * @return true if card2 can be placed as a child of card1, false otherwise
     * @throws Exception if unknown suit or face value
     */
    public static boolean canPlaceCardInPlayable(final Card card1, final Card card2) throws Exception
    {
        //first make sure the suits are compatible
        switch (card1.getSuit())
        {
            case Clubs:
                switch (card2.getSuit())
                {
                    case Clubs:
                    case Spades:
                        return false;
                }
                break;
                
            case Diamonds:
                switch (card2.getSuit())
                {
                    case Diamonds:
                    case Hearts:
                        return false;
                }
                break;
                
            case Spades:
                switch (card2.getSuit())
                {
                    case Clubs:
                    case Spades:
                        return false;
                }
                break;
                
            case Hearts:
                switch (card2.getSuit())
                {
                    case Diamonds:
                    case Hearts:
                        return false;
                }
                break;
                
            default:
                throw new Exception("Suit not found here " + card1.getSuit().toString());
        }
        
        //now check the card value
        switch (card1.getValue())
        {
            //nothing can be add as a child of ace
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
    
    /**
     * Place the cards accordingly
     * @param sourceHolder The holder containing the cards we want to place
     * @param destinationHolder The holder where we want to place the sourceHolderCards
     * @param key The holder we want to place the cards
     * @return true if a card(s) was placed, false otherwise
     * @throws Exception 
     */
    public static boolean placePlayableCards(final Holder sourceHolder, final Holder destinationHolder, final Object key) throws Exception
    {
        if (!destinationHolder.isEmpty())
        {
            //set the current as the start location for all
            sourceHolder.setStart();

            //check if we can place the card first, before we place
            if (KlondikeHelper.canPlaceCardInPlayable(destinationHolder.getLastCard(), sourceHolder.getFirstCard()))
            {
                //set the destination for all cards
                sourceHolder.setDestination(destinationHolder, key);
                
                //a card was placed
                return true;
            }
        }
        else
        {
            //set the current as the start location for all
            sourceHolder.setStart();

            //only a king can be placed on an empty holder
            if (sourceHolder.getFirstCard().getValue() == Value.King)
            {
                //set the destination
                sourceHolder.setDestination(destinationHolder, key);
                
                //a card was placed
                return true;
            }
        }
        
        //no cards were placed
        return false;
    }
    
    /**
     * Place the cards accordingly
     * @param sourceHolder The holder containing the cards we want to place
     * @param destinationHolder The holder where we want to place the sourceHolderCards
     * @param key The holder we want to place the cards
     * @return true if a card(s) was placed, false otherwise
     * @throws Exception 
     */
    public static boolean placeDestinationCards(final Holder sourceHolder, final Holder destinationHolder, final Object key) throws Exception
    {
        if (!destinationHolder.isEmpty())
        {
            //set the current as the start location for all
            sourceHolder.setStart();

            //check if we can place the card first, before we place
            if (KlondikeHelper.canPlaceCardInDestination(destinationHolder.getLastCard(), sourceHolder.getFirstCard()))
            {
                //set the destination for all cards
                sourceHolder.setDestination(destinationHolder, key);
                
                //a card was placed
                return true;
            }
        }
        else
        {
            //set the current as the start location for all
            sourceHolder.setStart();

            //only a king can be placed on an empty holder
            if (sourceHolder.getFirstCard().getValue() == Value.Ace)
            {
                //set the destination
                sourceHolder.setDestination(destinationHolder, key);
                
                //a card was placed
                return true;
            }
        }
        
        //no cards were placed
        return false;
    }
}