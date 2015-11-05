import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Boat here.
 * 
 * @author EJIANG
 * @version 30/03/2015
 */
public class Boat extends SmoothMover
{
    private River r;
    private String id;
    private int order; // Was this the first or second Boat launched?
    private int rRotation;
    private Dock d;
    private boolean isDocked = false;
    private boolean canShip = true;
    private int actCount = 0;
    private int waitTime = 200;
    //private int dx;
    //private int dy;

    public Boat(String side, int o)
    {
        super();
        this.id = side;
        this.order = o;
    }

    public void act() 
    {
        if(isDocked == false)
        {
            //waitTime--;
            //if(waitTime == 0)
            //{
            getDirection();
            move(2);
            dock();
            checkAndRemove();
            //}
        }
    }

    // VERY IMPORTANT!!!! GREENFOOT'S DIRECTION CONVENTION IS BACKWARDS!
    // RIGHT = +x
    // DOWN = +y
    // CW = +angle
    private void getDirection()
    { 
        r = (River)getOneIntersectingObject(River.class);
        rRotation = r.getRotation();

        if(order == 1)setRotation(rRotation);
        else if(order == 2)setRotation(rRotation + 180);
    }

    //     public void dock()
    //     {
    //         setLocation(getX(), getY());
    //         isDocked = true;
    //     }

    public void launch()
    {
        isDocked = false;
        //launched = true;
    }

    public void dock()
    {
        Dock d = (Dock)getOneObjectAtOffset(0, 0, Dock.class);
        if(d != null && d.getId()!= this.id)
        {
            setLocation(d.getX(), d.getY());
        }
        
        actCount++;
        if(actCount == 650) getWorld().removeObject(this);
    }

    public boolean getDocked()
    {
        return isDocked;
    }
}
