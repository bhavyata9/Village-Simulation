import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Barracks here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Barracks extends Buildings
{
    private Headquarters h;
    public Barracks(){}

    public Barracks(String side)
    {
        super();
        this.id = side;
        setImg(id, "B");
        this.health = 20;
        this.fullHealth = this.health;
        //findSideHQ();
    }

    private void findSideHQ()
    {
        List <Headquarters> t = getWorld().getObjects(Headquarters.class);
        for(Headquarters i: t)
        {
            if(i.id == this.id)
            {
                h = i;
            }
        }
    }

    private void hireSoldiers(int b)
    {
        h.updateInventory(0, (b*(-1)), (b*(-3)));
        //         h.useCoal(b*3);
        //         h.useGold(b);
    }

    public void act()
    {
        findSideHQ();
        spawn("s", id, 1);

        int bC = getWorld().getObjects(Barracks.class).size();

        if(h.numOfCoal()/(bC*3) >= 1 && h.numOfGold()/(bC) >= 1)
        {
            canSpawn = true;
            hireSoldiers(bC);
        }
    }
}
