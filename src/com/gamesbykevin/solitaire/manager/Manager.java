package com.gamesbykevin.solitaire.manager;

import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.menu.CustomMenu;
import com.gamesbykevin.solitaire.menu.CustomMenu.*;
import com.gamesbykevin.solitaire.resources.GameAudio;
import com.gamesbykevin.solitaire.resources.GameFont;
import com.gamesbykevin.solitaire.resources.GameImages;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that contains all of the game elements
 * @author GOD
 */
public final class Manager implements IManager
{
    //background image to display
    private Image image;
    
    /**
     * Constructor for Manager, this is the point where we load any menu option configurations
     * @param engine Engine for our game that contains all objects needed
     * @throws Exception 
     */
    public Manager(final Engine engine) throws Exception
    {
        //set the audio depending on menu setting
        engine.getResources().setAudioEnabled(engine.getMenu().getOptionSelectionIndex(LayerKey.Options, OptionKey.Sound) == CustomMenu.SOUND_ENABLED);
    }
    
    @Override
    public void reset(final Engine engine) throws Exception
    {
        //get the difficulty
        //final int difficultyIndex = engine.getMenu().getOptionSelectionIndex(LayerKey.Options, OptionKey.Difficulty);
        
        //get the game mode
        //final int modeIndex = engine.getMenu().getOptionSelectionIndex(LayerKey.Options, OptionKey.Mode);
        
        //the background image will be determined by multi-player
        //this.image = engine.getResources().getGameImage((!multiplayer) ? GameImages.Keys.Background1Player : GameImages.Keys.Background2Player);
        
    }
    
    /**
     * Free up resources
     */
    @Override
    public void dispose()
    {
        try
        {
            if (image != null)
            {
                image.flush();
                image = null;
            }
            
            //recycle objects
            super.finalize();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Update all elements
     * @param engine Our game engine
     * @throws Exception 
     */
    @Override
    public void update(final Engine engine) throws Exception
    {
        
    }
    
    /**
     * Draw all of our application elements
     * @param graphics Graphics object used for drawing
     */
    @Override
    public void render(final Graphics graphics) throws Exception
    {
        graphics.drawImage(image, 0, 0, null);
    }
}