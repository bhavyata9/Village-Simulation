import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *  
 * @author EJIANG
 * @version 16/03/2015
 */
public class River extends Environment
{
    public River ()
    {
        this.id = "vRiver";
        setImg();
    } 

    private void setImg()
    {
        this.rotation = Greenfoot.getRandomNumber(360); //sets rotation. Not using other method b/c no need.
        this.base = new GreenfootImage(id + ".png");
        setImage(base);

        setRotation(rotation); // rotates image
    }
}
