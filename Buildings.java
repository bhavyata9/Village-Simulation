import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Buildings that Peasants can build.
 * 
 * @author EJIANG
 * @version 24/03/2015
 */
public abstract class Buildings extends Actor
{
    protected String id; // Determines red/blue army. "r" for red, "b" for blue
    protected String picID;
    protected int health; // Building will disappear when health reaches 0. (It'll be destroyed.)
    protected int fullHealth; // Original amount of health
    protected boolean canSpawn = true;

    public void hitMe(int damage)
    {
        health -= damage;
        updateImage();
        if(health <= 0)
        {
            World world = getWorld();
            world.removeObject(this);
        }
    }

    // Changes picture according to the Building's health levels.
    protected void updateImage()
    {
        if(health*1.0/(fullHealth*1.0) <= 0.75 && health/(fullHealth*1.0) > 0.50) 
        {
            Greenfoot.playSound("fireBurning.wav");
            setImg("F1");
        }
        if(health*1.0/(fullHealth*1.0) <= 0.50 && health/(fullHealth*1.0) > 0.25)
        {
            Greenfoot.playSound("fireBurning.wav");
            setImg("F2");
        }
        if(health*1.0/(fullHealth*1.0) <= 0.25 && health/(fullHealth*1.0) > 0.0)
        {
            Greenfoot.playSound("fireBurning.wav");
            setImg("F3");
        }
    }

    protected void setImg(String iden, String pic)
    {
        id = iden;
        picID = pic;
        setImage(id + pic + ".png");
    } 

    protected void setImg(String picNum)
    {
        setImage(id + picID + picNum + ".png");
    }

    // Returns id
    public String getId()
    {
        return id;
    }

    protected void spawn(String type, String id, int num)
    {
        if(canSpawn == true)
        {
            if(type == "s")
            {
                for(int i = 0 ; i < num; i++)
                {
                    if(Greenfoot.getRandomNumber(4) == 0)
                        getWorld().addObject (new Spearman(this.id, this.getRotation()), getX(), getY());
                    else if(Greenfoot.getRandomNumber(4) == 1)
                        getWorld().addObject (new Horseman(this.id, this.getRotation()), getX(), getY());
                    else if(Greenfoot.getRandomNumber(4) == 2)
                        getWorld().addObject (new Archer(this.id, this.getRotation()), getX(), getY());
                    else if(Greenfoot.getRandomNumber(4) == 3)
                    {
                        int r = 0;
                        if(this.id == "r") r = 0;
                        else if (this.id == "b") r = 180;                        
                        getWorld().addObject (new Catapult(this.id, r), getX() + Greenfoot.getRandomNumber(500) - 225, getY() + Greenfoot.getRandomNumber(500) - 225);
                    }
                }
            }
            else if(type == "p")
            {
                for(int i = 0 ; i < num; i++)
                {
                    getWorld().addObject (new Peasant(this.id), getX(), getY());  
                }
            }
            canSpawn = false;
        }
    }
}
