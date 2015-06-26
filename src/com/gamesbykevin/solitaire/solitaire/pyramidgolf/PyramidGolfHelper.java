package com.gamesbykevin.solitaire.solitaire.pyramidgolf;

import com.gamesbykevin.solitaire.solitaire.Solitaire;
import com.gamesbykevin.solitaire.solitaire.pyramidgolf.PyramidGolf.Key;

/**
 * Pyramid Golf Helper Methods
 * @author GOD
 */
public final class PyramidGolfHelper 
{
    /**
     * Is this card holder blocked by other cards?
     * @param solitaire Object containing all card holders
     * @param key The key of the holder we want to select
     * @return true if there is a card holder in front of this with at least 1 card, false otherwise
     * @throws Exception If the key card holder is not handled here
     */
    protected static boolean isBlocked(final Solitaire solitaire, final Key key) throws Exception
    {
        switch (key)
        {
            case Pyramid_1_Row_1_Column_1:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_2_Column_1).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_2_Column_2).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_2_Column_1:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_3_Column_1).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_3_Column_2).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_2_Column_2:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_3_Column_2).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_3_Column_3).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_3_Column_1:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_4_Column_1).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_4_Column_2).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_3_Column_2:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_4_Column_2).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_4_Column_3).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_3_Column_3:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_4_Column_3).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_4_Column_4).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_4_Column_1:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_5_Column_1).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_5_Column_2).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_4_Column_2:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_5_Column_2).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_5_Column_3).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_4_Column_3:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_5_Column_3).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_5_Column_4).isEmpty())
                    return true;
                break;
            
            case Pyramid_1_Row_4_Column_4:
                if (!solitaire.getHolder(Key.Pyramid_1_Row_5_Column_4).isEmpty() || !solitaire.getHolder(Key.Pyramid_1_Row_5_Column_5).isEmpty())
                    return true;
                break;
            
                //front row is never blocked
            case Pyramid_1_Row_5_Column_1:
            case Pyramid_1_Row_5_Column_2:
            case Pyramid_1_Row_5_Column_3:
            case Pyramid_1_Row_5_Column_4:
            case Pyramid_1_Row_5_Column_5:
                return false;
            
                
            case Pyramid_2_Row_1_Column_1:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_2_Column_1).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_2_Column_2).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_2_Column_1:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_3_Column_1).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_3_Column_2).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_2_Column_2:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_3_Column_2).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_3_Column_3).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_3_Column_1:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_4_Column_1).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_4_Column_2).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_3_Column_2:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_4_Column_2).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_4_Column_3).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_3_Column_3:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_4_Column_3).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_4_Column_4).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_4_Column_1:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_5_Column_1).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_5_Column_2).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_4_Column_2:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_5_Column_2).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_5_Column_3).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_4_Column_3:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_5_Column_3).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_5_Column_4).isEmpty())
                    return true;
                break;
            
            case Pyramid_2_Row_4_Column_4:
                if (!solitaire.getHolder(Key.Pyramid_2_Row_5_Column_4).isEmpty() || !solitaire.getHolder(Key.Pyramid_2_Row_5_Column_5).isEmpty())
                    return true;
                break;
            
                //front row is never blocked
            case Pyramid_2_Row_5_Column_1:
            case Pyramid_2_Row_5_Column_2:
            case Pyramid_2_Row_5_Column_3:
            case Pyramid_2_Row_5_Column_4:
            case Pyramid_2_Row_5_Column_5:
                return false;
                
                
            default:
                throw new Exception("Card holder key not found here '" + key.toString() + "'");
        }
        
        //we did not find anything blocking the holder
        return false;
    }
}
