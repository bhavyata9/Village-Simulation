import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Collections;
/**
 * Write a description of class Archer here.
 * 
 * @author EJIANG
 * @version 31/03/2015
 */
public class Archer extends Soldiers
{   
    //private People p;
    private Buildings b;
    private boolean hasTarget = false;
    private boolean isAttackingPeople;

    public Archer(String side, int rotation)
    {
        super();
        this.health = 3;
        this.id = side;
        this.speed = 1;

        setOpposite();

        this.reloadTime = 75; // Delay between Arrows fired.

        this.picID = "A";

        setImg(side, picID);
        setRotation(rotation);
    }

    public void act()
    {
        //if(checkInRange() == true) speed = 0;
        //move(speed);
        findTarget();
        attack();
        reloadDelay++;
        checkAndRemove();
    }

    public void attack()
    {
        if (reloadDelay >= reloadTime) 
        {
            Arrow a = new Arrow(this.id, getRotation());
            getWorld().addObject(a, getX(), getY());

            reloadDelay = 0;
        }
    }

    public void findTarget()
    {
        if(hasTarget == false)
        {
            int x = Greenfoot.getRandomNumber(5);
            if(x == 0)
            {
                isAttackingPeople = true;
            }
            else
            {
                isAttackingPeople = false;
            }
            hasTarget = true;
        }
        if(isAttackingPeople == false)
        {
            b = getNearestBuilding(1000, opposite);
            if(b != null)
            {
                turnTowards(b.getX(), b.getY());
                move(speed);
            }
            if(b == null) 
            {
                hasTarget = false;
                speed = 1;
                move(speed);//speed = 1;
            }
        }
        else if(isAttackingPeople == true)
        {
            p = getNearestPerson(1000, opposite);
            if(p != null)
            {
                turnTowards(p.getX(), p.getY());
                move(speed);
            }
            if(p == null)
            {
                hasTarget = false;
                speed = 1;
                move(speed);// 1;
            }
        }
    }

    public boolean checkInRange()
    {
        if(isAttackingPeople == false)
        {
            if(getObjectsInRange(200, Headquarters.class).size() > 0) return true;
            //if(getOneObjectAtOffset(100, 0, Buildings.class) != null) return true;//setLocation(getX(), getY());
        }
        else if(isAttackingPeople == true)
        {
            if(getObjectsInRange(200, People.class).size() > 0) return true;
            //if(getOneObjectAtOffset(100, 0, People.class) != null) return true;//setLocation(getX(), getY());
        }   
        return false;//getOneObjectAtOffset( 0, getImage().getHeight()/2, Thing.class);
    }
}
