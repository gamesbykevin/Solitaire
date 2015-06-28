package com.gamesbykevin.solitaire.resources;

import com.gamesbykevin.framework.resources.*;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

/**
 * This class will load all resources in the collection and provide a way to access them
 * @author GOD
 */
public class Resources implements IResources
{
    /**
     * The resource configuration directory where resource locations are
     */
    private static final String CONFIGURATION_DIR = "configuration/"; 
    
    /**
     * Where our configuration file that contains the resource locations
     */
    public static final String XML_CONFIG_GAME_AUDIO = CONFIGURATION_DIR + "audio.xml"; 
    public static final String XML_CONFIG_GAME_FONT  = CONFIGURATION_DIR + "font.xml"; 
    public static final String XML_CONFIG_GAME_IMAGE = CONFIGURATION_DIR + "image.xml"; 
    public static final String XML_CONFIG_GAME_TEXT  = CONFIGURATION_DIR + "text.xml"; 
    public static final String XML_CONFIG_MENU       = CONFIGURATION_DIR + "menu.xml"; 
    
    //the number of different sounds
    private static final int AUDIO_PLACE_TOTAL = 11;
    private static final int AUDIO_INVALID_TOTAL = 5;
    
    //are we loading resources
    private boolean loading = true;
    
    //objects that contain resources
    private GameAudio audio;
    private GameImages images;
    private GameFont fonts;
    private GameText textFiles;
    
    public Resources() throws Exception
    {
        //object to contain audio resources
        this.audio = new GameAudio();
        
        //object to contain images resources
        this.images = new GameImages();
        
        //object to contain font resources
        this.fonts = new GameFont();
        
        //object to contain text resources
        this.textFiles = new GameText();
    }
    
    /**
     * Are we loading resources?
     * @return true if yes, false otherwise
     */
    @Override
    public boolean isLoading()
    {
        return loading;
    }
    
    /**
     * Stop all sound
     */
    public void stopAllSound()
    {
        audio.stopAll();
    }
    
    /**
     * Here we will load the resources one by one and then marking the process finished once done
     * @param source Class in root directory of project so we have a relative location so we know how to access resources
     * @throws Exception 
     */
    @Override
    public void update() throws Exception
    {
        if (!audio.isComplete())
        {
            //load 1 resource at a time
            audio.update(Resources.class);

            //exit method so progress can be drawn
            return;
        }
        
        if (!images.isComplete())
        {
            //load 1 resource at a time
            images.update(Resources.class);

            //exit method so progress can be drawn
            return;
        }
        
        if (!fonts.isComplete())
        {
            //load 1 resource at a time
            fonts.update(Resources.class);

            //exit method so progress can be drawn
            return;
        }
        
        if (!textFiles.isComplete())
        {
            //load 1 resource at a time
            textFiles.update(Resources.class);

            //exit method so progress can be drawn
            return;
        }
        
        //verify all existing keys are contained in the xml file
        audio.verifyLocations(GameAudio.Keys.values());
        
        //verify all existing keys are contained in the xml file
        images.verifyLocations(GameImages.Keys.values());
        
        //verify all existing keys are contained in the xml file
        textFiles.verifyLocations(GameText.Keys.values());
        
        //verify all existing keys are contained in the xml file
        fonts.verifyLocations(GameFont.Keys.values());
        
        //we are done loading the resources
        this.loading = false;
    }
    
    /**
     * Checks to see if audio is turned on
     * @return true=yes, false=no
     */
    public boolean isAudioEnabled()
    {
        return audio.isEnabled();
    }
    
    /**
     * Set the audio enabled/disabled. <br>
     * @param boolean Is the audio enabled 
     */
    public void setAudioEnabled(final boolean enabled)
    {
        audio.setEnabled(enabled);
    }
    
    /**
     * Get the specified Image
     * @param key
     * @return Image
     */
    public Image getGameImage(final Object key)
    {
        return images.get(key);
    }
    
    /**
     * Get the text file
     * @param key The unique key of the text file we want
     * @return Text file
     */
    public Text getGameText(final Object key)
    {
        return textFiles.get(key);
    }
    
    /**
     * Play game audio with no loop
     * @param key The unique key of the audio we want to play
     */
    public void playGameAudio(final Object key)
    {
        playGameAudio(key, false);
    }
    
    public void playPlaceCardAudio(final Random random)
    {
        switch (random.nextInt(AUDIO_PLACE_TOTAL))
        {
            case 0:
                playGameAudio(GameAudio.Keys.Place1);
                break;
                
            case 1:
                playGameAudio(GameAudio.Keys.Place2);
                break;
                
            case 2:
                playGameAudio(GameAudio.Keys.Place3);
                break;
                
            case 3:
                playGameAudio(GameAudio.Keys.Place4);
                break;
                
            case 4:
                playGameAudio(GameAudio.Keys.Place5);
                break;
                
            case 5:
                playGameAudio(GameAudio.Keys.Place6);
                break;
                
            case 6:
                playGameAudio(GameAudio.Keys.Place7);
                break;
                
            case 7:
                playGameAudio(GameAudio.Keys.Place8);
                break;
                
            case 8:
                playGameAudio(GameAudio.Keys.Place9);
                break;
                
            case 9:
                playGameAudio(GameAudio.Keys.Place10);
                break;
                
            case 10:
                playGameAudio(GameAudio.Keys.Place11);
                break;
        }
    }
    
    public void playInvalidCardAudio(final Random random)
    {
        switch (random.nextInt(AUDIO_INVALID_TOTAL))
        {
            case 0:
                playGameAudio(GameAudio.Keys.Invalid1);
                break;
                
            case 1:
                playGameAudio(GameAudio.Keys.Invalid2);
                break;
                
            case 2:
                playGameAudio(GameAudio.Keys.Invalid3);
                break;
                
            case 3:
                playGameAudio(GameAudio.Keys.Invalid4);
                break;
                
            case 4:
                playGameAudio(GameAudio.Keys.Invalid5);
                break;
        }
    }
    
    
    /**
     * Play game audio
     * @param key The unique key of the audio we want to play
     * @param loop Does the audio loop
     */
    public void playGameAudio(final Object key, final boolean loop)
    {
        audio.play(key, loop);
    }
    
    /**
     * Stop playing audio
     * @param key The unique key of the audio we want to stop playing
     */
    public void stopGameAudio(final Object key)
    {
        audio.stop(key);
    }
    
    /**
     * Get the game font
     * @param key The unique key of the object we want
     * @return The specified game font
     */
    public Font getFont(final Object key)
    {
        return fonts.get(key);
    }
    
    public void setFont(final Object key, final Font font)
    {
        fonts.set(key, font);
    }
    
    @Override
    public void dispose()
    {
        if (audio != null)
        {
            audio.dispose();
            audio = null;
        }
        
        if (images != null)
        {
            images.dispose();
            images = null;
        }
        
        if (textFiles != null)
        {
            textFiles.dispose();
            textFiles = null;
        }
        
        if (fonts != null)
        {
            fonts.dispose();
            fonts = null;
        }
    }
    
    @Override
    public void render(final Graphics graphics, final Rectangle screen)
    {
        if (!isLoading())
            return;
        
        if (audio != null)
        {
            if (!audio.isComplete())
            {
                audio.render(graphics, screen);
                return;
            }
        }
        
        if (images != null)
        {
            if (!images.isComplete())
            {
                images.render(graphics, screen);
                return;
            }
        }
        
        if (textFiles != null)
        {
            if (!textFiles.isComplete())
            {
                textFiles.render(graphics, screen);
                return;
            }
        }
        
        if (fonts != null)
        {
            if (!fonts.isComplete())
            {
                fonts.render(graphics, screen);
                return;
            }
        }
    }
}