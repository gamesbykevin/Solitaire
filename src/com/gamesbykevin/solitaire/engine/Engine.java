package com.gamesbykevin.solitaire.engine;

import com.gamesbykevin.solitaire.resources.GameFont;
import com.gamesbykevin.solitaire.resources.Resources;
import com.gamesbykevin.solitaire.main.Main;
import com.gamesbykevin.solitaire.manager.Manager;
import com.gamesbykevin.solitaire.menu.CustomMenu;
import com.gamesbykevin.solitaire.shared.Shared;

import com.gamesbykevin.framework.input.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public final class Engine implements KeyListener, MouseMotionListener, MouseListener, IEngine 
{
    //access this menu here
    private CustomMenu menu;
    
    //our object that will contain all of the game resources
    private Resources resources;
    
    //mouse object that will be recording mouse input
    private Mouse mouse;
    
    //keyboard object that will be recording key input
    private Keyboard keyboard;
    
    //object containing all of the game elements
    private Manager manager;
    
    //object used to make random decisions
    private Random random;
    
    //default font
    private Font font;
    
    //seed for the Random object
    private final long seed = System.nanoTime();
    
    //the game font size
    public static final float GAME_FONT_SIZE = 14f;
    
    /**
     * The dimensions of our screen
     */
    private Rectangle screen;
    
    /**
     * The time in nanoseconds per update
     */
    private final long time;
    
    /**
     * The Engine that runs and contains the game/menu objects
     * @param screen Dimensions of the window
     * @param time The time in nanoseconds per update
     * @throws Exception 
     */
    public Engine(final Rectangle screen, final long time) throws Exception
    {
        //reference to parent class
        this.screen = screen;
        
        //the time in nanoseconds per update
        this.time = time;
        
        //object used to track mouse input
        this.mouse = new Mouse();
        
        //object used to track keyboard input
        this.keyboard = new Keyboard();
        
        //create new Random object
        random = new Random(seed);
        
        //display seed if debugging
        if (Shared.DEBUG)
            System.out.println("Seed = " + seed);
    }
    
    /**
     * Get the time
     * @return The duration of each update in relation to updates-per-second
     */
    public long getTime()
    {
        return this.time;
    }
    
    /**
     * Proper house-keeping
     */
    @Override
    public void dispose()
    {
        try
        {
            screen = null;
            
            if (getResources() != null)
            {
                resources.dispose();
                resources = null;
            }
            
            if (getMenu() != null)
            {
                menu.dispose();
                menu = null;
            }
            
            if (getMouse() != null)
            {
                mouse.dispose();
                mouse = null;
            }
            
            if (getKeyboard() != null)
            {
                keyboard.dispose();
                keyboard = null;
            }
            
            if (getManager() != null)
            {
                manager.dispose();
                manager = null;
            }
            
            random = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Main main) throws Exception
    {
        if (getMenu() == null)
        {
            //create new menu
            menu = new CustomMenu(getScreen());

            //reset mouse and keyboard input
            resetInput();
        }
        else
        {
            //does the menu have focus
            if (!getMenu().hasFocus())
            {
                //reset mouse and keyboard input
                resetInput();
            }

            //update the menu
            getMenu().update(this, main);

            //if the menu is finished and the window has focus
            if (getMenu().hasFinished() && getMenu().hasFocus())
            {
                //if our resources object is empty create a new one
                if (getResources() == null)
                    this.resources = new Resources();

                //check if we are still loading resources
                if (getResources().isLoading())
                {
                    //load resources
                    getResources().update();
                }
                else
                {
                    //create new manager because at this point our resources have loaded
                    if (getManager() == null)
                    {
                        createManager();
                        getManager().reset(this);
                        
                        //set the game font
                        this.font = getResources().getFont(GameFont.Keys.Default);
                        this.font = getFont().deriveFont(GAME_FONT_SIZE);
                    }

                    //update main game logic
                    getManager().update(this);
                }
            }

            //if the mouse is released reset all mouse events
            if (getMouse().isMouseReleased())
                getMouse().reset();
        }
    }
    
    /**
     * Create a new manager object
     * @throws Exception 
     */
    private void createManager() throws Exception
    {
        manager = new Manager(this);
    }
    
    /**
     * Get the seed
     * @return The seed used in the random generator
     */
    public long getSeed()
    {
        return this.seed;
    }
    
    /**
     * Reset keyboard and mouse input.<br>
     * Stop any existing sound from playing.<br>
     * Reset our game manager object
     * @throws Exception if there is an error resetting the game manager
     */
    public void reset() throws Exception
    {
        //reset mouse and keyboard input
        resetInput();
        
        //if our resources object exists, stop any existing sound from playing
        if (getResources() != null)
            this.resources.stopAllSound();
        
        if (getManager() != null)
        {
            manager.dispose();
            manager = null;
        }
    }
    
    /**
     * Reset the keyboard and mouse inputs
     */
    private void resetInput()
    {
        //reset mouse and keyboard input
        getMouse().reset();
        getKeyboard().reset();
    }
    
    /**
     * If the font isn't set, set the default font
     * @param graphics Object used to render content
     */
    private void createDefaultFont(final Graphics graphics)
    {
        this.font = graphics.getFont().deriveFont(Font.BOLD, GAME_FONT_SIZE);
    }
    
    /**
     * Draw our game to the Graphics object whether resources are still loading or the game is intact
     * @param graphics Object used to render content
     * @throws Exception 
     */
    @Override
    public void render(final Graphics graphics) throws Exception
    {
        //set default font, if not set
        if (getFont() == null)
            createDefaultFont(graphics);
        
        if (getMenu() != null)
        {
            //if the menu is finished and the window has focus
            if (getMenu().hasFinished() && getMenu().hasFocus())
            {
                //before we start game we need to load the resources
                if (getResources().isLoading())
                {
                    //set default font
                    graphics.setFont(getFont());
                    
                    //draw loading screen
                    getResources().render(graphics, getScreen());
                }
            }
            
            //draw game elements
            if (getManager() != null)
            {
                //set default font
                graphics.setFont(getFont());
                
                //render main game components
                getManager().render(graphics);
            }
            
            //draw menu on top of the game if visible
            renderMenu(graphics);
        }
    }
    
    /**
     * Draw the Game Menu
     * 
     * @param graphics Graphics object where Images/Objects will be drawn to
     * @throws Exception 
     */
    private void renderMenu(Graphics graphics) throws Exception
    {
        //if menu is setup draw menu
        if (getMenu().isSetup() && !getMenu().hasFinished())
            getMenu().render(graphics);

        //if menu is finished and we don't want to hide the mouse cursor then draw it, or if the menu is not finished draw it
        if (getMenu().hasFinished() && !Shared.HIDE_MOUSE || !getMenu().hasFinished())
        {
            //draw the mouse
            getMenu().renderMouse(graphics, getMouse());
        }
    }
    
    /**
     * Get our object used to make random decisions
     * @return Random
     */
    public Random getRandom()
    {
        return this.random;
    }
    
    /**
     * Get the font
     * @return The default game font
     */
    private Font getFont()
    {
        return this.font;
    }
    
    /**
     * Object that contains all of the game elements
     * @return Manager
     */
    public Manager getManager()
    {
        return this.manager;
    }
    
    public CustomMenu getMenu()
    {
        return this.menu;
    }
    
    public Resources getResources()
    {
        return resources;
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        try
        {
            if (getKeyboard() != null)
                getKeyboard().addKeyReleased(e.getKeyCode());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        try
        {
            if (getKeyboard() != null)
                getKeyboard().addKeyPressed(e.getKeyCode());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        try
        {
            //do nothing here for now
            //keyboard.addKeyTyped(e.getKeyChar());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        try
        {
            if (getMouse() != null)
                getMouse().setMouseClicked(e);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        try
        {
            if (getMouse() != null)
                getMouse().setMousePressed(e);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        try
        {
            if (getMouse() != null)
                getMouse().setMouseReleased(e);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e)
    {
        try
        {
            if (getMouse() != null)
                getMouse().setMouseEntered(e.getPoint());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e)
    {
        try
        {
            if (getMouse() != null)
                getMouse().setMouseExited(e.getPoint());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        try
        {
            if (getMouse() != null)
                getMouse().setMouseMoved(e.getPoint());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        try
        {
            if (getMouse() != null)
                getMouse().setMouseDragged(e.getPoint());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public Mouse getMouse()
    {
        return mouse;
    }
    
    public Keyboard getKeyboard()
    {
        return keyboard;
    }
    
    /**
     * Get the screen
     * @return The rectangle dimensions for the playing window in our game
     */
    public Rectangle getScreen()
    {
        return this.screen;
    }
}