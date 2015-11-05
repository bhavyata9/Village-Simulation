import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Arrow that is fired by Archers.
 * 
 * If it hits a person before its lifetime is finished, then the person will die. 
 * 
 * To use the arrow, the rotation of the arrow must be set to the rotation of 
 * the person so that it goes in the right direction.
 * 
 * @author Bhavya Shah and EJIANG
 * @version 18/03/2015
 */
public class Arrow extends Projectiles
{   
    private boolean isRemoved = false;

    public Arrow(String side, int rotation)
    {
        this.life = 75;
        this.damage = 1; // Damage dealt per Arrow
        this.speed = 5; // speed of Arrow

        this.id = side;
        setImg(side, "AR");       
        setRotation(rotation);
        Greenfoot.playSound("arrowSound.mp3");
    }

    /**
     * Damages People.
     */
    public void act() 
    {
        if (getWorld() == null) 
            return; 
        fly();
        lifeRemaining();
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
     * Sets movement direction, speed, and image.
     */
    public void fly()
    {        
        move(speed);
    }

    /**
     * Checks if it hits any people from the opposite side or other objects.
     * If so, removes itself from the world. 
     */
    // Should damage Buildings and HeavyEquipment, too.
    public void checkHitPeople()
    {
        People p = (People)getOneIntersectingObject(People.class);
        //         Rock r = (Rock)getOneIntersectingObject(Rock.class);
        //         Tree e = (Tree)getOneIntersectingObject(Tree.class);//         
        //         Buildings b = (Buildings)getOneIntersectingObject(Buildings.class);
        //         HeavyEquipment hE = (HeavyEquipment)getOneIntersectingObject(HeavyEquipment.class);

        if (p != null && !p.id.equals(this.id))
        {            
            p.hitMe(damage);           
            getWorld().removeObject(this); 
            isRemoved = true;
            return;
        }
        //         if(r != null || e != null) // Arrows blocked by Rocks, Trees, and Boatation
        //         {                     
        //             getWorld().removeObject(this);
        //             isRemoved = true;
        //         }
        //         if(b != null && !b.id.equals(this.id)) // Can damage Buildings
        //         {
        //             b.hitMe(damage);
        //             getWorld().removeObject(this);
        //             isRemoved = true;
        //         }
        //         if(hE != null && !hE.id.equals(this.id)) // Can also damage HeavyEquipment
        //         {
        //             hE.hitMe(damage);
        //             getWorld().removeObject(this);   
        //             isRemoved = true;
        //         }
    }

    public void checkHitBuildings()
    {
        Buildings b = (Buildings)getOneIntersectingObject(Buildings.class);
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
    }

    public void checkHitHeavyEquipment()
    {
        HeavyEquipment hE = (HeavyEquipment)getOneIntersectingObject(HeavyEquipment.class);	
        if(hE != null && !hE.id.equals(this.id) ) // Boulder destroyed by Rock
        {   
            if (!isRemoved)
            {
                hE.hitMe(damage);
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
                isRemoved = true;
                return;
            }

        }
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
}

