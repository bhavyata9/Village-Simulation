import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Grass here.
 * 
 * @author EJIANG 
 * @version 16/03/2015
 */
public class Grass extends Environment
{
    public Grass ()
    {
        this.id = "vG";
        setImg();
    }

    private void setImg()
    {
        this.getSizeRotationID(2); // generate all parameters needed

        this.base = new GreenfootImage(id + num + ".png");
        setImage(base);    
    }    
}
