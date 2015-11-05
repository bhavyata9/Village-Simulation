import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

/**
 * The reset button will allow the user to go back to the titleworld, or the world 
 * where s/he can re-input values to start the simulation over again.
 * 
 * @author (Srishti Sehgal) 
 * @version (March 31, 2015)
 */
public class ResetButton extends Actor
{
    /**
     * When clicked, remove all objects and change world to TitleWorld
     */
    public void act() 
    {
        if(Greenfoot.mouseClicked(this)){
            MapWorld m = (MapWorld)getWorld();
            Greenfoot.stop();
            List objects = getWorld().getObjects(null);
            getWorld().removeObjects(objects);
            m.stopped();
            TitleWorld t = new TitleWorld();
            Greenfoot.setWorld(t);
            Greenfoot.start();
            t.started();
        }
    }
}    
