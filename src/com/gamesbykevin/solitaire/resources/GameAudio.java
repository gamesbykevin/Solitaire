package com.gamesbykevin.solitaire.resources;

import com.gamesbykevin.framework.resources.*;

/**
 * All audio for game
 * @author GOD
 */
public final class GameAudio extends AudioManager
{
    //description for progress bar
    private static final String DESCRIPTION = "Loading Audio Resources";
    
    /**
     * These are the keys used to access the resources and need to match the id in the xml file
     */
    public enum Keys
    {
        Deal, 
        Place1, Place2, Place3, Place4,
        Place5, Place6, Place7, Place8,
        Place9, Place10, Place11,
        Invalid1, Invalid2, Invalid3, Invalid4, Invalid5, 
        Win, Lose
    }
    
    public GameAudio() throws Exception
    {
        super(Resources.XML_CONFIG_GAME_AUDIO);
        
        //the description that will be displayed for the progress bar
        super.setProgressDescription(DESCRIPTION);
        
        if (Keys.values().length < 1)
            super.increase();
    }
}