package com.gamesbykevin.solitaire.manager;

import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.shared.IElement;


/**
 * IManager interface will also include a way to reset the game
 * @author GOD
 */
public interface IManager extends IElement
{
    /**
     * Provide a way to reset the game elements
     * @param engine The Engine containing resources etc... if needed
     * @throws Exception 
     */
    public void reset(final Engine engine) throws Exception;
}