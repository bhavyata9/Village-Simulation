import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Resources here.
 * 
 * @author EJIANG 
 * @version 22/03/2015
 */
public abstract class Resources extends Environment
{
    protected boolean isExhausted = false;
    protected int visitsRemaining; // How many more times it can be collected before exhaustion.
       
    protected abstract String getName();

    public boolean cannotCollect()
    {
        return isExhausted;
    }
    
    public int collectMe() // returns amount to add to inventory
    {
        visitsRemaining--;
        if(visitsRemaining == 0) isExhausted = true;
        
        return 1;
    }
}
