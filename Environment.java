import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Environment here.
 * 
 * @author EJIANG
 * @version 16/03/2015
 */
public abstract class Environment extends Actor
{
    protected String id; // picture identification
    protected int num; // sets which picture to use
    protected int size; // sets size of object, as a percentage
    protected int rotation; // sets rotation of object image
    protected GreenfootImage base; // image to transform
    protected GreenfootImage scaled; // scaled image
    protected boolean isOnesTarget = false;

    // generates numbers to determine image, size, and rotation
    protected void getSizeRotationID(int t)
    {
        num = Greenfoot.getRandomNumber(t)+ 1 ;
        size = Greenfoot.getRandomNumber(50)+50; // sets image size to be between 50-100% of original size
        rotation = Greenfoot.getRandomNumber(360); // randomly choose a rotation
    }

    // rounds size percentages to nearest whole number
    protected int roundDim(int dimension, int scaleFactor)
    {
        double percent = scaleFactor/100.0;
        double newSize = dimension*percent;

        int fin = (int) Math.round(newSize);
        return fin;        
    }

    public boolean checkFinder()
    {
        return isOnesTarget;
    }

    public void setFinder()
    {
        isOnesTarget = true;
    }
}