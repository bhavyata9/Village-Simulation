import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Headquarters here.
 * 
 * @author EJIANG and Justin Ding 
 * @version 31/03/2015
 */
public class Headquarters extends Buildings
{
    //INVENTORY
    private int woodCount = 2;
    private int goldCount = 0;
    private int coalCount = 0;

    //HEADCOUNTS
    private int peasantCount;
    private int subPeasant;
    private int soldierCount;
    private int subSoldier;
    private int buildingCount;
    private int subBuilding;
    private int houseCount;
    private int subHouse;
    private boolean dockExist = false;

    //JOB INFO
    private boolean buildTask;
    private boolean lumberTask;
    private boolean findTask;
    private boolean noFinder;

    private int actCount;
    private boolean inDistress = false;

    //ArrayLists
    private ArrayList<Buildings> buildingList;
    private ArrayList<House> houseList;
    private ArrayList<Peasant> peasantList;
    private ArrayList<Soldiers> soldiersList;

    public Headquarters(){}

    /**
     * create an headquarters object with a side asign to it
     * 
     * @param side the side of the headquarter will be "r" or "b"
     */
    public Headquarters(String side)
    {
        super();
        this.id = side;
        setImg(id, "HQ");
        this.health = 40;
        this.fullHealth = this.health;
    }

    /**
     * spawn one peasant in the beginning of the game.
     * Then every 50 act it will update the building Count,
     * soldier Count, peasant Count and house Count for this side of the village.
     */
    public void act()
    {
        spawn("p", id, 2);
        actCount++;
        if(actCount % 50 == 0)
        {
            subBuilding = 0;
            subSoldier = 0;
            subPeasant = 0;
            subHouse = 0;
            searchBuilding();
            searchPeasant();
            searchSoldiers();
            actCount = 0;
        }
        buildingCount = subBuilding;
        soldierCount = subSoldier;
        peasantCount = subPeasant;
        houseCount = subHouse;
        
        if(buildingCount > 4 && peasantCount < 2) inDistress = true;
    }

    /** 
     * Updates inventory values by adding the specified value to their respective categories.
     */
    public void updateInventory(int w, int g, int c)
    {
        woodCount += w;
        goldCount += g;
        coalCount += c;
    }

    public void useWood(int num)
    {
        if(num > 0 && woodCount >= num)
        {
            woodCount -= num;
        }
    }

    public int numOfWood()
    {
        return woodCount;
    }

    public void useGold(int num)
    {
        if(num > 0 && goldCount >= num)
        {
            goldCount -= num;
        }
    }

    public int numOfGold()
    {
        return goldCount;
    }

    public void useCoal(int num)
    {
        if(num > 0 && coalCount >= num)
        {
            coalCount -= num;
        }
    }

    public int numOfCoal()
    {
        return coalCount;
    }


    public boolean buildTask()
    {
        return buildTask;
    }

    public void setBuildTask(boolean status)
    {
        buildTask = status;
    }

    public boolean lumberTask()
    {
        return lumberTask;
    }

    public void setLumberTask(boolean status)
    {
        lumberTask = status;
    }

    public boolean findTask()
    {
        return findTask;
    }

    public void setFindTask(boolean status)
    {
        findTask = status;
    }

    public int returnBuilding()
    {
        return buildingCount;
    }

    public int returnSoldier()
    {
        return soldierCount;
    }

    public int returnPeasant()
    {
        return peasantCount;
    }

    public int returnHouse()
    {
        return houseCount;
    }

    public boolean checkDock()
    {
        return dockExist;
    }

    public boolean checkFinder()
    {
        return noFinder;
    }

    public void confirmFinder()
    {
        noFinder = true;
    }
    
    public boolean getDistress()
    {
        return inDistress;
    }

    private void searchBuilding()
    {
        buildingList = (ArrayList)getWorld().getObjects(Buildings.class);
        houseList = (ArrayList)getWorld().getObjects(House.class);

        for(Buildings d : buildingList)
        {
            if(d.id == this.id)
            {
                subBuilding++;
            }
            if(d.id == this.id && d.picID == this.id +"D")
            {
                dockExist = true;
            }
            else
            {
                dockExist = false;
            }
        }
        
        for(House l : houseList)
        {
            if(l.id == this.id && l.picID == this.id + "H")
            {
                subHouse++;
            }
        }
    }

    private void searchPeasant()
    {
        peasantList = (ArrayList)getWorld().getObjects(Peasant.class);
        for(Peasant e : peasantList)
        {
            if(e.id == this.id)
            {
                subPeasant++;
            }
        }
    }

    private void searchSoldiers()
    {
        soldiersList = (ArrayList)getWorld().getObjects(Soldiers.class);
        for(Soldiers f : soldiersList)
        {
            if(f.id == this.id)
            {
                subSoldier++;
            }
        }
    }
}
// import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
// import java.util.ArrayList;
// /**
//  * Write a description of class Headquarters here.
//  * 
//  * @author EJIANG and Justin Ding 
//  * @version 29/03/2015
//  */
// public class Headquarters extends Buildings
// {
//     //INVENTORY
//     private int woodCount = 2;
//     private int goldCount = 0;
//     private int coalCount = 0;
// 
//     //HEADCOUNTS
//     private int peasantCount;
//     private int soldierCount;
//     private int buildingCount;
//     private int houseCount;
//     private boolean dockExist = false;
// 
//     //JOB INFO
//     private boolean buildTask;
//     private boolean cleanTask;
//     private boolean findTask;
//     private boolean noFinder;
// 
//     private int actCount;
// 
//     private ArrayList<Environment> clearList;
//     private ArrayList<Buildings> buildingList;
//     private ArrayList<Peasant> peasantList;
//     private ArrayList<Soldiers> soldiersList;
// 
//     public Headquarters(){}
// 
//     public Headquarters(String side)
//     {
//         super();
//         this.id = side;
//         setImg(id, "HQ");
//         this.health = 40;
//         this.fullHealth = this.health;
//     }
// 
//     public void setUpList(int range)
//     {
//         clearList = (ArrayList)getObjectsInRange(range, Environment.class);
//     }
// 
//     public void act()
//     {
//         spawn("p", id, 1);
//         actCount++;
// 
//         if(actCount % 50 == 0)
//         {
//             buildingCount = 0;
//             soldierCount = 0;
//             peasantCount = 0;
//             buildingCount = 0;
//             houseCount = 0;
//             searchBuilding();
//             searchPeasant();
//             searchSoldiers();
//             actCount = 0;
//         }
//     }
// 
//     /** 
//      * Updates inventory values by adding the specified value to their respective categories.
//      */
//     public void updateInventory(int w, int g, int c)
//     {
//         woodCount += w;
//         goldCount += g;
//         coalCount += c;
//     }
// 
//     public void useWood(int num)
//     {
//         if(num > 0 && woodCount >= num)
//         {
//             woodCount -= num;
//         }
//     }
// 
//     public int numOfWood()
//     {
//         return woodCount;
//     }
// 
//     public void useGold(int num)
//     {
//         if(num > 0 && goldCount >= num)
//         {
//             goldCount -= num;
//         }
//     }
// 
//     public int numOfGold()
//     {
//         return goldCount;
//     }
// 
//     public void useCoal(int num)
//     {
//         if(num > 0 && coalCount >= num)
//         {
//             coalCount -= num;
//         }
//     }
// 
//     public int numOfCoal()
//     {
//         return coalCount;
//     }
// 
//     public ArrayList returnArrayList()
//     {
//         return clearList;
//     }
// 
//     public void setArrayList(ArrayList setor)
//     {
//         clearList = setor;
//     }
// 
//     public void setHQArrayList(int range)
//     {
//         clearList = (ArrayList)getObjectsInRange(range,Environment.class);
//     }
// 
//     public boolean buildTask()
//     {
//         return buildTask;
//     }
// 
//     public void setBuildTask(boolean status)
//     {
//         buildTask = status;
//     }
// 
//     public boolean cleanTask()
//     {
//         return cleanTask;
//     }
// 
//     public void setCleanTask(boolean status)
//     {
//         cleanTask = status;
//     }
// 
//     public boolean findTask()
//     {
//         return findTask;
//     }
// 
//     public void setFindTask(boolean status)
//     {
//         findTask = status;
//     }
// 
//     public int returnBuilding()
//     {
//         return buildingCount;
//     }
// 
//     public int returnSoldier()
//     {
//         return soldierCount;
//     }
// 
//     public int returnPeasant()
//     {
//         return peasantCount;
//     }
// 
//     public int returnHouse()
//     {
//         return houseCount;
//     }
// 
//     public boolean checkDock()
//     {
//         return dockExist;
//     }
// 
//     public boolean checkFinder()
//     {
//         return noFinder;
//     }
// 
//     public void confirmFinder()
//     {
//         noFinder = true;
//     }
// 
//     private void searchBuilding()
//     {
//         buildingList = (ArrayList)getWorld().getObjects(Buildings.class);
//         for(Buildings d : buildingList)
//         {
//             if(d.id == this.id)
//             {
//                 buildingCount++;
//             }
//             if(d.id == this.id && d.picID == this.id + "H")
//             {
//                 houseCount++;
//             }
//             if(d.id == this.id && d.picID == this.id +"D")
//             {
//                 dockExist = true;
//             }
//         }
//     }
// 
//     private void searchPeasant()
//     {
//         peasantList = (ArrayList)getWorld().getObjects(Peasant.class);
//         for(Peasant e : peasantList)
//         {
//             if(e.id == this.id)
//             {
//                 peasantCount++;
//             }
//         }
//     }
// 
//     private void searchSoldiers()
//     {
//         soldiersList = (ArrayList)getWorld().getObjects(Soldiers.class);
//         for(Soldiers f : soldiersList)
//         {
//             if(f.id == this.id)
//             {
//                 soldierCount++;
//             }
//         }
//     }
// }
