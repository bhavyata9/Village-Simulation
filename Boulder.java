import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Boulder that is launched by catapults. 
 * If it hits a building, then the building will be damaged.
 * 
 * 
 * To use the boulder, the rotation of the boulder must be set to the rotation of 
 * the catapult so that it is launched in the right direction, and set the horizontal speeed 
 * acordingly. 
 * 
 * @author Bhavya Shah and EJIANG
 * @version  16/03/2015
 */
public class Boulder extends Projectiles
{
    private boolean isRemoved = false;

    /**
     * Creates a new Boulder with default speed
     */
    public Boulder(String side, int rotation)
    {
        this.life = 100;
        this.damage = 5;
        this.speed = 4;

        this.id = side;
        setImg(id, "D0");       
        setRotation(rotation);
        animate(id, "D", 4, rotation);
    }

    /**
     * Damages Buildings.
     */
    public void act() 
    {
        if (getWorld() == null) 
            return; 
        fly();
        updateImage();
        lifeRemaining();
        if (getWorld() == null) 
            return; 
        checkSpeed();
        if (getWorld() == null) 
            return;
        checkHitBuildings();

        if (getWorld() == null) 
            return;

        checkHitRock();
        if (getWorld() == null) 
            return;

        checkHitTree();
        if (getWorld() == null) 
            return;

        checkHitPeople();
        if (getWorld() == null) 
            return;

        checkHitHeavyEquipment();
        if (getWorld() == null) 
            return;

    }

    /**
     * Moves in the direction of hSpeed 
     */
    public void fly()
    {
        move(speed);
    }

    /**
     * Checks if it hits any other objects other than people.
     * If yes, then removes itself from the world. 
     */
    public void checkHitBuildings()
    {
        Buildings b = (Buildings)getOneIntersectingObject(Buildings.class);
        //         Rock r = (Rock)getOneIntersectingObject(Rock.class);
        //         Tree e = (Tree)getOneIntersectingObject(Tree.class);
        //         Boat t = (Boat)getOneIntersectingObject(Boat.class);
        //         HeavyEquipment hE = (HeavyEquipment)getOneIntersectingObject(HeavyEquipment.class);
        //         People p = (People)getOneIntersectingObject(People.class);

        if (b != null && !b.id.equals(this.id))
        {            

            if (!isRemoved)
            {
                b.hitMe(damage);  
                getWorld().removeObject(this);
                isRemoved = true;
                Greenfoot.playSound("boulderHit.mp3");
                return;
            }
        }

        //         if (p != null && !p.id.equals(this.id))
        //         {      
        //             if (!isRemoved)
        //             {
        //                 p.hitMe(damage);           
        //                 getWorld().removeObject(this); 
        //                 isRemoved = true;
        //             }
        //         }
    }  

    public void checkHitRock()
    {
        Rock r = (Rock)getOneIntersectingObject(Rock.class);	
        if(r != null) // Boulder destroyed by Rock
        {   
            if (!isRemoved)
            {
                getWorld().removeObject(this);   
                isRemoved = true;
                return;
            }

        }
    }

    public void checkHitTree()
    {
        Tree t = (Tree)getOneIntersectingObject(Tree.class);	
        if(t != null) // Boulder destroyed by Rock
        {   
            if (!isRemoved)
            {
                getWorld().removeObject(this);   
                speed--;
                isRemoved = true;
                return;
            }

        }
    }

    public void checkHitHeavyEquipment()
    {
        HeavyEquipment hE = (HeavyEquipment)getOneIntersectingObject(HeavyEquipment.class);	
        if(hE != null && !hE.id.equals(this.id)) // Boulder destroyed by Rock
        {   
            if (!isRemoved)
            {
                getWorld().removeObject(this);
                speed--;
                isRemoved = true;
                return;
            }

        }
    }

    public void checkHitPeople()
    {
        People p = (People)getOneIntersectingObject(People.class);
        if(p != null && !p.id.equals(this.id)) // Boulder destroyed by Rock
        {   
            if (!isRemoved)
            {
                p.hitMe(damage);           
                getWorld().removeObject(this); 
                isRemoved = true;
                return;
            }
        }
    }

    public void checkSpeed() // Removes boulder if speed = 0.
    {
        if(speed == 0)
        {
            getWorld().removeObject(this);
            isRemoved = true;
            return;
        }
    }
}
