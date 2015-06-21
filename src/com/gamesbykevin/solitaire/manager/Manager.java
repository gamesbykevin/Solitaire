package com.gamesbykevin.solitaire.manager;

import com.gamesbykevin.solitaire.solitaire.klondike.Klondike;
import com.gamesbykevin.solitaire.engine.Engine;
import com.gamesbykevin.solitaire.menu.CustomMenu;
import com.gamesbykevin.solitaire.menu.CustomMenu.*;
import com.gamesbykevin.solitaire.resources.GameAudio;
import com.gamesbykevin.solitaire.resources.GameFont;
import com.gamesbykevin.solitaire.resources.GameImages;
import com.gamesbykevin.solitaire.solitaire.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that contains all of the game elements
 * @author GOD
 */
public final class Manager implements IManager
{
    //background and table image to display
    private Image background, table;
    
    //the location for each image
    private Point backgroundLocation, tableLocation;
    
    //our solitaire game
    private Solitaire solitaire;
    
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
        if (this.solitaire == null)
            this.solitaire = new Klondike(engine.getResources().getGameImage(GameImages.Keys.Cards));
        
        //get the difficulty
        //final int difficultyIndex = engine.getMenu().getOptionSelectionIndex(LayerKey.Options, OptionKey.Difficulty);
        
        //get the game mode
        //final int modeIndex = engine.getMenu().getOptionSelectionIndex(LayerKey.Options, OptionKey.Mode);
        
        if (background == null)
        {
            List<GameImages.Keys> options = new ArrayList<>();
            options.add(GameImages.Keys.Background1);
            options.add(GameImages.Keys.Background2);
            options.add(GameImages.Keys.Background3);
            options.add(GameImages.Keys.Background4);
            options.add(GameImages.Keys.Background5);
            options.add(GameImages.Keys.Background6);
            options.add(GameImages.Keys.Background7);
            
            //the background image will be determined by multi-player
            this.background = engine.getResources().getGameImage(options.get(engine.getRandom().nextInt(options.size())));
            //this.background = engine.getResources().getGameImage(GameImages.Keys.Cards);
        }
        
        if (table == null)
        {
            //store the table image
            this.table = engine.getResources().getGameImage(GameImages.Keys.Table);
        }
        
        //the center position of the screen
        final int centerX = engine.getScreen().x + (engine.getScreen().width / 2);
        final int centerY = engine.getScreen().y + (engine.getScreen().height / 2);
        
        if (backgroundLocation == null)
            this.backgroundLocation = new Point(centerX - (background.getWidth(null) / 2), centerY - (background.getHeight(null) / 2));
        if (tableLocation == null)
            this.tableLocation = new Point(centerX - (table.getWidth(null) / 2), centerY - (table.getHeight(null) / 2));
    }
    
    /**
     * Free up resources
     */
    @Override
    public void dispose()
    {
        try
        {
            if (background != null)
            {
                background.flush();
                background = null;
            }
        
            if (solitaire != null)
            {
                solitaire.dispose();
                solitaire = null;
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
        if (solitaire != null)
            solitaire.update(engine);
    }
    
    /**
     * Draw all of our application elements
     * @param graphics Graphics object used for drawing
     */
    @Override
    public void render(final Graphics graphics) throws Exception
    {
        //draw the background
        graphics.drawImage(background, backgroundLocation.x, backgroundLocation.y, null);
        
        //draw the table
        graphics.drawImage(table, tableLocation.x, tableLocation.y, null);
        
        if (solitaire != null)
            solitaire.render(graphics);
    }
}