import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Randomly chooses image and rotation of Tree.
 * 
 * @author EJIANG 
 * @version 16/03/2015
 */
public class Tree extends Environment
{
    public Tree ()
    {
        this.id = "vT";
        setImg();
    }

    private void setImg()
    {
        this.getSizeRotationID(5); // generate all parameters needed
        
        this.base = new GreenfootImage(id + num + ".png");
        setImage(base);

        setRotation(rotation); // rotates image        
    }
}
