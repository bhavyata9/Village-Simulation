import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Dock here.
 * 
 * @author EJIANG
 * @version 30/03/2015
 */
public class Dock extends Buildings
{
    private int actCount = 0;
    private Boat b;
    private int order; // Was this the first or second Dock built?
    private ArrayList<Dock> k;
    //private boolean canDock;

    public Dock(String side, int rotation, int num)
    {
        super();
        this.id = side;
        setImg(id, "D");
        this.health = 10;
        this.fullHealth = this.health;
        this.order = num;

        if(order == 1) setRotation(rotation + 90); // Makes it so it is 90 degrees to the River it's built on.
        else if(order == 2) setRotation(rotation - 90);
    }

    public void act()
    {
        spawnBoats();
        actCount++;

        if(actCount == 750)
        {
            canSpawn = true;
            actCount = 0;      
        }
    }

    private void spawnBoats()
    {
        if(canSpawn == true)
        {
            b = new Boat(this.id, order);
            getWorld().addObject (b, getX(), getY());
        }
        canSpawn = false;
    }

    //     private void launchBoat()
    //     {
    //         Boat b = (Boat)getOneIntersectingObject(Boat.class);
    //         if(b != null && b.getLaunched() == false)
    //         {
    //             b.launch();
    //         }
    //     }
    //     private void checkBoats()
    //     {
    //         Boat b = (Boat)getOneObjectAtOffset(30, 30, Boat.class);
    //         if(b != null) b.setLocation(this.getX(), this.getY());
    //     }
}
