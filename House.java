import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class House here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class House extends Buildings
{
    public House(){}
    
    public House(String side)
    {
        super();
        this.id = side;
        setImg(id, "H");
        this.health = 10;
        this.fullHealth = this.health;
    }
    
    public void act()
    {
        spawn("p", id, 1);
    }
}
