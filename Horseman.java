import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Horseman here.
 * 
 * @author EJIANG
 * @version 31/03/2015
 */
public class Horseman extends Soldiers
{
    public Horseman(String side, int rotation)
    {
        super();
        this.health = 6;
        this.offset = 25;
        this.speed = 3;

        this.reloadTime = 45; // Time between attacks
        this.aDamage = 4; // Damage per attack

        this.id = side;
        this.picID = "HM1";
        this.base = "HM";

        setOpposite();

        this.soundfile = "horseSound.mp3";

        setImg(side, picID);
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
