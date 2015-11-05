import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Fires Boulders when the fire method is called.
 * Removes itself when it has been sufficiently damaged.
 * 
 * @author Bhavya Shah and EJIANG
 * @version 30/03/2015
 */
public class Catapult extends HeavyEquipment
{
    private static final int reloadTime = 50; // Delay between Boulders fired.
    private int reloadDelay; // Time since last Boulder fired.
    private boolean isFiring; // If the Catapult is firing at the moment.
    private String base;
    private String opposite;

    private Buildings b;

    private boolean hasTarget = false;

    public Catapult(){}

    public Catapult(String side, int rotation)
    {
        super();
        this.id = side;
        this.base = "C";
        setImg(id, "C0");       
        setRotation(rotation);
        animate(id, "C", 3, rotation);

        if(id == "r") opposite = "b";
        else if (id == "b") opposite = "r";
    }

    /**
     * Act - do whatever the Catapult wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {         
        findTarget();
        fire();
        reloadDelay++;
        animateAttack();
    } 

    /**
     * Fires the boulder
     */

    public void fire ()
    {
        if (reloadDelay >= reloadTime)
        {   // The sound is played here because of the delay.
            Greenfoot.playSound("catapultSound.mp3");
            isFiring = true;
            Boulder b = new Boulder(this.id, getRotation());
            getWorld().addObject(b, getX(), getY());

            reloadDelay = 0;
        }
    }

    private void animateAttack()
    {
        if(isFiring == true) 
        {
            setImg(id, base + 2); //updateImage();//setImg(id, "C0");
            isFiring = false;
        }
        else setImg(id, base + 0);
    }

    public void findTarget()
    {
        b = getNearestBuilding(1000, opposite);
        if(b != null)
        {
            turnTowards(b.getX(), b.getY());
            //move(speed);
        }
        if(b == null) 
        {
            hasTarget = false;
            //speed = 1;
            //move(speed);//speed = 1;
        }
    }
}
