import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Projectiles here.
 * 
 * @author Bhavya Shah and EJIANG
 * @version 17/03/2015
 */
public abstract class Projectiles extends SmoothMover
{
    protected int speed; // How fast it flies
    protected int damage; // How much damage it does

    protected String id; // Identifies Red/Blue army.
    protected String picID; // Identifies picture to use

    protected int life; // Lifetime of the projectile.

    public Projectiles () {}

    /**
     * Create new thing initialised with given speed.
     */
    public Projectiles(String side, String picID, int rotation)
    {
        setImg(side, picID);       
        setRotation(rotation);
    }

    public abstract void fly();

    protected void setImg(String iden, String pic)
    {
        id = iden;
        setImage(id + pic + ".png");
    }

    // Counts down time remaining until self-removal
    protected void lifeRemaining()
    {
        if(life > 0)
        {
            life--;
        }
        else 
        {
            getWorld().removeObject(this); 
        }
    }
}