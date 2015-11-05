import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class People here.
 * 
 * @author EJIANG, Bhavya Shah, and Justin Ding
 * @version 29/03/2015
 */
public abstract class People extends SmoothMover
{
    protected String id; // Determines red/blue army. "r" for red, "b" for blue
    protected String picID;
    protected int health; // Person will die when health reaches 0
    protected int speed;

    protected Resources targetResource;
    protected ArrayList<Resources> target;

    protected River v;
    protected int vX, vY;
    protected double yInt, vSlope;

    protected boolean inRiver = false;

    public void act() 
    {
    }

    // Hit the person for a certain amount of damage.
    public void hitMe(int damage)
    {
        health -= damage;
        if(health <= 0)
        {
            Greenfoot.playSound("deadPerson.mp3");
            World world = getWorld();
            world.removeObject(this);
        }
    }

    // Sets the person's image in accordance to its side.
    protected void setImg(String iden, String pic)
    {
        id = iden;
        setImage(id + pic + ".png");
    }

    public void detectCollisions(){
        People a = (People)getOneObjectAtOffset(0,(this.getImage().getHeight()/2)+1, People.class);
        People b = (People)getOneObjectAtOffset(0,(this.getImage().getHeight()/2*-1)-1, People.class);
        People c = (People)getOneObjectAtOffset((this.getImage().getWidth()/2)+1,0, People.class);
        People d = (People)getOneObjectAtOffset((this.getImage().getWidth()/2*-1)-1,0, People.class);
        if ( a != null ){
            a.setLocation(a.getX(),a.getY()+1);
            turn(Greenfoot.getRandomNumber(360));
            move(speed);
        }
        else if ( b != null ){
            b.setLocation(b.getX(),b.getY()-1);
            turn(Greenfoot.getRandomNumber(360));
            move(speed);
        }
        else if ( c != null ){
            c.setLocation(c.getX()+1,c.getY());
            turn(Greenfoot.getRandomNumber(360));
            move(speed);
        }
        else if ( d != null ){
            d.setLocation(d.getX()-1,d.getY());
            turn(Greenfoot.getRandomNumber(360));
            move(speed);
        }
    }

    protected void moveToward()
    {
        this.turnTowards(targetResource.getX(),targetResource.getY());
        move(speed);
    }

    protected void checkRiver()
    {
        v = (River)getOneIntersectingObject(River.class);

        vX = v.getX();
        vY = v.getY();

        double vSlopeDeg = v.getRotation()*1.0;
        // finding radian angle for rotation, and using tangent to convert angle into slope
        vSlope = (Math.tan(Math.toRadians(vSlopeDeg)));

        MapWorld t = (MapWorld)getWorld();
        yInt = t.getIntercept();//vY - (vSlope*vX);
        double x, y;
        if(yInt > 640 || yInt < 0)
        {
            y = getY();
            x = (y - yInt)/vSlope;
            if(id == "r")
            {
                if(getY() == y && getX() == x - 30) setLocation(getX(), getY());
            }
            else if(id == "b")
            {
                if(getY() == y && getX() == 960 - x + 30) setLocation(getX(), getY());
            }
        }
        else if(yInt >= 0 && yInt <= 640)
        {
            x = getX();
            y = (vSlope*x) + yInt;            
            if(id == "r")
            {
                if(getX() == x && getY() == y - 30) setLocation(getX(), getY());
            }
            else if (id == "b")
            {
                if(getX() == x && getY() == 640 - y + 30) setLocation(getX(), getY());
            }
        }
    }

    protected void wade()
    {
        if(getOneIntersectingObject(River.class) != null) inRiver = true;
        else inRiver = false;
        
        int oldSpeed = speed;
        int newSpeed = speed - 1;
        if(inRiver == true) speed = newSpeed;
        else speed = oldSpeed;
    }

    public String getId()
    {
        return id;
    }
}