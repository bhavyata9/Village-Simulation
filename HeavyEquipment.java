import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Heavy weaponry. Once put in place, operate independently of Soldiers.
 * 
 * @author EJIANG
 * @version 18/03/2015
 */
public class HeavyEquipment extends SmoothMover
{
    protected int health;
    protected String id;

    // Attack method. (Rams don't technically 'fire', but it's close enough!)
    public void fire(){}

    // Lets it recieve damage. Removes itself if health = 0.
    protected void hitMe(int damage)
    {
        health -= damage;
        if(health <= 0)
        {
            World world = getWorld();
            world.removeObject(this);
        }
    }

    protected void setImg(String iden, String pic)
    {
        id = iden;
        setImage(id + pic + ".png");
    }
}