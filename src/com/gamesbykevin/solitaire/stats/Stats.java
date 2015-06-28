package com.gamesbykevin.solitaire.stats;

import com.gamesbykevin.framework.awt.CustomImage;
import com.gamesbykevin.framework.base.Animation;
import com.gamesbykevin.framework.util.Timer;
import com.gamesbykevin.framework.util.Timers;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * This class will contain the game stats.<br>
 * Timer, score, status message etc.....
 * @author GOD
 */
public final class Stats extends CustomImage
{
    //game timer
    private Timer timer = new Timer();
    
    //status message(s)
    private String message1 = "";
    private String message2 = "";
    private String message3 = "";
    
    //the score of our game
    private int score = 0;
    
    //location of each
    private Point locationTimer;
    private Point locationMessage1;
    private Point locationMessage2;
    private Point locationMessage3;
    private Point locationScore;
    
    //flag change to render a new image
    private boolean change = true;
    
    //default keys used for background animation
    private static final String DEFAULT_KEY = "Default";
    
    /**
     * Create a new stats object
     * @param width pixel width
     * @param height pixel height
     */
    public Stats(final int width, final int height, final Image image) throws Exception
    {
        super(width, height);
        
        //set the image that contains the background
        super.setImage(image);
        
        //create and map background
        super.createSpriteSheet();
        super.getSpriteSheet().add(new Animation(840, 1330, 100, 100, 0), DEFAULT_KEY);
        super.getSpriteSheet().setCurrent(DEFAULT_KEY);
        
        //where we will draw each attribute
        int y = 20;
        
        this.locationTimer = new Point(20, y);
        this.locationMessage3 = new Point(20, y += 15);
        this.locationMessage1 = new Point(20, y += 15);
        this.locationMessage2 = new Point(20, y += 15);
        this.locationScore = new Point(20, 90);
        
        //flag and render image
        flagChange();
        render();
    }
    
    /**
     * Get the score
     * @return The game score
     */
    public int getScore()
    {
        return this.score;
    }
    
    /**
     * Set the score
     * @param score The score we want to display
     */
    public void setScore(final int score)
    {
        this.score = score;
        
        //flag a change
        flagChange();
    }
    
    /**
     * Set message
     * @param message The custom message we want to display
     */
    public void setMessage3(final String message3)
    {
        this.message3 = message3;
        
        //flag a change
        flagChange();
    }
    
    /**
     * Get message
     * @return The custom message we want to display
     */
    public String getMessage3()
    {
        return this.message3;
    }
    
    /**
     * Set message
     * @param message The custom message we want to display
     */
    public void setMessage1(final String message1)
    {
        this.message1 = message1;
        
        //flag a change
        flagChange();
    }
    
    /**
     * Get message
     * @return The custom message we want to display
     */
    public String getMessage1()
    {
        return this.message1;
    }
    
    /**
     * Set message
     * @param message The custom message we want to display
     */
    public void setMessage2(final String message2)
    {
        this.message2 = message2;
        
        //flag a change
        flagChange();
    }
    
    /**
     * Get message
     * @return The custom message we want to display
     */
    public String getMessage2()
    {
        return this.message2;
    }
    
    /**
     * Get the timer
     * @return The timer used to track game time
     */
    public Timer getTimer()
    {
        return this.timer;
    }
    
    /**
     * Flag change if we want to render a new image
     */
    public void flagChange()
    {
        this.change = true;
    }
    
    public void drawStats(final Graphics graphics) throws Exception
    {
        //render image (if necessary)
        render();
        
        //draw the entire stats object
        super.draw(graphics, getBufferedImage(), null);
        
    }
    
    /**
     * Render a new image to be drawn
     */
    @Override
    public void render() throws Exception
    {
        if (change)
        {
            //unflag change
            change = false;
            
            //clear the image
            super.clear();
            
            //store oringinal location
            final double x = getX();
            final double y = getY();
            
            //reset to origin
            super.setX(0);
            super.setY(0);
            
            //draw background
            super.draw(getGraphics2D());
            
            //set original location
            super.setX(x);
            super.setY(y);
            
            //draw the timer
            getGraphics2D().drawString("Time: " + getTimer().getDescPassed(Timers.FORMAT_8), locationTimer.x, locationTimer.y);

            //draw the status message
            getGraphics2D().drawString(getMessage1(), locationMessage1.x, locationMessage1.y);

            //draw the status message
            getGraphics2D().drawString(getMessage2(), locationMessage2.x, locationMessage2.y);
            
            //draw the status message
            getGraphics2D().drawString(getMessage3(), locationMessage3.x, locationMessage3.y);
            
            //draw the score
            getGraphics2D().drawString("Score: " + getScore(), locationScore.x, locationScore.y);
        }
    }
}