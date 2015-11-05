import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spearman here.
 * 
 * @author EJIANG
 * @version 31/03/2015
 */
public class Spearman extends Soldiers
{   
    public Spearman(String side, int rotation)
    {
        super();
        this.health = 3;
        this.offset = 16;
        this.speed = 1;

        this.reloadTime = 35; // Delay between attacks.
        this.aDamage = 2; // Damage dealt per attack.

        this.id = side;
        this.picID = "S1";
        this.base = "S";

        setOpposite();

        this.soundfile = "spearSound.mp3";

        setImg(id, picID);
        setRotation(rotation);
    }

    //     public void act()
    //     {
    //         move(speed);
    //         findTarget();
    //         attack();
    //         reloadDelay++;
    //     }
}
