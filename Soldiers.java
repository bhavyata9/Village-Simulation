import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Soldiers here.
 * 
 * @author EJIANG
 * @version 31/03/2015
 */
public abstract class Soldiers extends People
{
    protected int offset; // Because Soldiers are dumb, they will only attack things in front of them
    protected int aDamage; // Damage per attack dealt
    protected int reloadTime; // Interval between attacks
    protected int reloadDelay; // Time since last attack
    protected boolean isAttacking; // If the Soldier is attacking at the moment.
    protected String base;
    protected String soundfile;
    protected String opposite;

    protected People p;

    // Hit the person for a certain amount of damage.
    protected void attack()
    {
        People p = (People)getOneIntersectingObject(People.class);//ObjectAtOffset(offset, 3, People.class);
        if(p != null && p.getId() != this.id)
        {
            if(reloadDelay >= reloadTime) 
            {
                isAttacking = true;

                p.hitMe(aDamage);
                //animateAttack();
                reloadDelay = 0;
            }
        }
        animateAttack();
    }

    protected void animateAttack()
    {
        if(isAttacking == true) 
        {
            Greenfoot.playSound(soundfile);
            setImg(id, base + 2);
            isAttacking = false;
        }
        else setImg(id, base + 1);
    }

    protected void setOpposite()
    {
        if(id == "r") opposite = "b";
        else if (id == "b") opposite = "r";
    }

    protected void findTarget()
    {
        p = getNearestPerson(1000, opposite);
        if(p != null)
        {
            turnTowards(p.getX(), p.getY());
            move(speed);
        }
    }
    
    public void act()
    {
        move(speed);
        findTarget();
        attack();
        reloadDelay++;
        checkAndRemove();
    }
}
