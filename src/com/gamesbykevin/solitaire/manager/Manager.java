package com.gamesbykevin.solitaire.manager;

import com.gamesbykevin.framework.util.Timers;
import com.gamesbykevin.solitaire.solitaire.bakers.Bakers;
import com.gamesbykevin.solitaire.solitaire.blockten.BlockTen;
import com.gamesbykevin.solitaire.solitaire.freecell.FreeCell;
import com.gamesbykevin.solitaire.solitaire.golf.Golf;
import com.gamesbykevin.solitaire.solitaire.goodmeasure.GoodMeasure;
import com.gamesbykevin.solitaire.solitaire.klondike.Klondike;
import com.gamesbykevin.solitaire.solitaire.littlespider.LittleSpider;
import com.gamesbykevin.solitaire.solitaire.poker.Poker;
import com.gamesbykevin.solitaire.solitaire.pyramid.Pyramid;
import com.gamesbykevin.solitaire.solitaire.pyramidgolf.PyramidGolf;
import com.gamesbykevin.solitaire.solitaire.yukon.Yukon;
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
    
    //keep track of time to update timer
    private long previous;
    
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
        //if (this.solitaire == null)
        
        //see what game we selected
        final int gameSelection = engine.getMenu().getOptionSelectionIndex(LayerKey.Options, OptionKey.Game);
        
        switch (gameSelection)
        {
            case CustomMenu.GAME_CLASSIC:
                this.solitaire = new Klondike(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Classic Solitaire");
                break;
            
            case CustomMenu.GAME_POKER:
                this.solitaire = new Poker(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Poker Solitaire");
                break;
                
            case CustomMenu.GAME_GOLF:
                this.solitaire = new Golf(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Golf Solitaire");
                break;
                
            case CustomMenu.GAME_PYRAMID:
                this.solitaire = new Pyramid(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Pyramid Solitaire");
                break;
                
            case CustomMenu.GAME_PYRAMID_GOLF:
                this.solitaire = new PyramidGolf(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Pyramid Golf Solitaire");
                break;
                
            case CustomMenu.GAME_BAKERS:
                this.solitaire = new Bakers(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Bakers Solitaire");
                break;
                
            case CustomMenu.GAME_YUKON:
                this.solitaire = new Yukon(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Yukon Solitaire");
                break;
                
            case CustomMenu.GAME_LITTLE_SPIDER:
                this.solitaire = new LittleSpider(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Little Spider Solitaire");
                break;
                
            case CustomMenu.GAME_FREE_CELL:
                this.solitaire = new FreeCell(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Free Cell Solitaire");
                break;
                
            case CustomMenu.GAME_BLOCK_TEN:
                this.solitaire = new BlockTen(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Block Ten Solitaire");
                break;
                
            case CustomMenu.GAME_GOOD_MEASURE:
                this.solitaire = new GoodMeasure(engine.getResources().getGameImage(GameImages.Keys.Cards));
                this.solitaire.getStats().setMessage3("Good Measure Solitaire");
                break;
                
            default:
                throw new Exception("Game selection is not found here " + gameSelection);
        }
        
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
        {
            //if the game is over no need to continue
            if (solitaire.hasGameover())
            {
                solitaire.getStats().setMessage1(solitaire.isWinner() ? "Game Over, Win" : "Game Over, Lose");
            }
            else
            {
                if (!solitaire.isCreateComplete())
                {
                    solitaire.getStats().setMessage1("Creating Deck");
                    solitaire.getStats().setMessage2("Hit 'Esc' for menu");
                    solitaire.create(engine.getRandom());
                }
                else if (!solitaire.isShuffleComplete())
                {
                    solitaire.getStats().setMessage1("Shuffling Deck");
                    solitaire.shuffle(engine.getRandom());
                    
                    //play sound effect
                    engine.getResources().playGameAudio(GameAudio.Keys.Deal, true);
                }
                else if (!solitaire.isDealComplete())
                {
                    solitaire.getStats().setMessage1("Dealing Cards");
                    solitaire.deal(engine.getTime());

                    if (solitaire.isDealComplete())
                    {
                        solitaire.getStats().setMessage1("Game Start");
                        engine.getResources().stopGameAudio(GameAudio.Keys.Deal);
                    }
                    
                    if (solitaire.hasGameover())
                    {
                        //stop all current sound
                        engine.getResources().stopAllSound();
                        
                        //play win or lost sound effect
                        engine.getResources().playGameAudio(solitaire.isWinner() ? GameAudio.Keys.Win : GameAudio.Keys.Lose);
                    }
                }
                else
                {
                    //update main game
                    solitaire.update(engine);

                    //update game timer
                    solitaire.getStats().getTimer().update(engine.getTime());

                    //if 1 second has passed update time and render a new image
                    if (System.nanoTime() - previous >= Timers.NANO_SECONDS_PER_SECOND)
                    {
                        solitaire.getStats().flagChange();
                        previous = System.nanoTime();
                    }
                    
                    if (solitaire.hasGameover())
                    {
                        //stop all current sound
                        engine.getResources().stopAllSound();
                        
                        //play win or lost sound effect
                        engine.getResources().playGameAudio(solitaire.isWinner() ? GameAudio.Keys.Win : GameAudio.Keys.Lose);
                    }
                    else
                    {
                        //if we have to deal play sound effect
                        if (!solitaire.isDealComplete())
                            engine.getResources().playGameAudio(GameAudio.Keys.Deal, true);
                    }
                }
            }
        }
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