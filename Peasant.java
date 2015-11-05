import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Peasants here.
 * 
 * @author EJIANG and Justin Ding
 * @version 29/03/2015
 */
public class Peasant extends People
{    
    private String base = "P";
    private String img;

    private boolean onTask = false;
    private int numOfHouses;
    private String buildID; // Type of Building being built

    private int[] randomX = new int[2];
    private int[] randomY = new int[2];
    private int buildX;
    private int buildY;
    private boolean placeFound = false;

    private int randomNum;
    private int randomPosition;

    private Resources r; // Target Resource
    private Tree t; // Target Tree
    private int targetX;
    private int targetY;

    //HQ INFO
    private Headquarters h;
    private boolean sideHQFound = false;
    private int headquartersX;
    private int headquartersY;
    private int levelOfHQ;

    //CLEANING VARIABLES
    private int actCount = 0; // this one is used for building, too.
    private int cleaningActCount;
    private boolean finished = true;
    private int clearedCount = 0;
    private boolean needClear = false;
    private int numOfClear = 0;
    private boolean clearing = false;
    private boolean buildingClear = false;
    private boolean hQClear = false;
    private int clearRange = 100;

    private boolean riverNear = false;

    //JOB INFO
    private boolean noJob = true;
    private boolean builder = false;
    private boolean lumber = false;
    private boolean finder = false;
    private boolean buildingHouse = false;
    private boolean buildingBarracks = false;
    private boolean holdingWood = false;
    private boolean holdingGold = false;
    private boolean holdingCoal = false;
    private boolean resourceTargetUsed = false;
    private boolean gettingWood = false;
    private boolean buildingDock = false;
    private boolean reasourceNear = false;

    private boolean closestResourceFound = false;
    private boolean gotResource = false;
    private boolean goAfterTree = false;
    //     private boolean toRiver = false;

    private ArrayList<Headquarters> targetMain;
    private ArrayList<Environment> cleanUp;
    private ArrayList<Environment> hQCleanUp;
    private ArrayList<Buildings> used;
    private ArrayList<Resources> resource;

    public Peasant()
    {
        speed = 1;
    }

    public Peasant(String side)
    {
        super();
        this.id = side;
        this.health = 3;
        this. speed = 1;
        setImg(id,"P");
    }

    /**
     * Move towards determined building spot.
     */
    private void moveToBuild()
    {
        this.turnTowards(buildX,buildY);
        this.move(speed);
    }

    /**
     * Search for the friendly Headquarters (same id as this Peasant)
     * Use co-ordinates of HQ as the center point and build around with it
     */
    private void findSideHQ()
    {
        targetMain = (ArrayList)getWorld().getObjects(Headquarters.class);
        for(Headquarters i: targetMain)
        {
            if(i.id == this.id)
            {
                headquartersX = i.getX();
                headquartersY = i.getY();
                h = i;
            }
            else
            {
                move(1);
            }
        }
    }

    /**
     * Finds the nearest Resource, and sets it as the target Resource.
     * Identifies Resource type and increases inventory accordingly when it is reached.
     */
    private void findResources()
    {
        // Find the nearest resource
        double dRiver = Math.hypot(h.getX() - vX, h.getY() - vY);

        r = (Resources)getNearestResource(dRiver);
        r.setFinder();
        targetX = r.getX();
        targetY = r.getY();

        turnTowards(targetX, targetY);

        if(getX() == targetX && getY() == targetY)
        {
            updateInv();
        }
    }

    private void findTree()
    {
        t = (Tree)getNearestTree();
        targetX = t.getX();
        targetY = t.getY();

        turnTowards(targetX, targetY);
    }

    /**
     * Updates inventory values inside HQ.
     * Used when collecting Resources.
     */
    private void updateInv()
    {
        if(getIntersectingObjects(Resources.class).size() > 0)//isCollecting = true && isTouching(Resources.class) == true)
        {
            if(r.getName() == "w") h.updateInventory(1, 0, 0); //+= r.collectMe();
            if(r.getName() == "g") h.updateInventory(0, 1, 0); //+= r.collectMe();
            if(r.getName() == "c") h.updateInventory(0, 0, 1); //+= r.collectMe();
        }
        //else move(speed);
    }

    /**
     * Randomly find a location within a fixed "radius" of the Headquarters.
     * This is because it must account for the actual size of the image, rather than what it visually appears to be.
     */
    private void findPlaceToBuild()
    {
        // Random X on the east side of the HQ (20 pixels away from the 'center')
        randomX[0] = randomNum(100)+headquartersX+27;
        // Random X on the west side of the HQ(20 pixels away from the 'center')
        randomX[1] = randomNum(100)+headquartersX-127;
        // Random Y on the north side of the HQ(20 pixels away from the 'center')
        randomY[0] = randomNum(100)+headquartersY+21;
        // Random Y on the south side of the HQ(20 pixels away from the 'center')
        randomY[1] = randomNum(100)+headquartersY-119;

        // Randomly chooses one of four choices for the building spot
        randomPosition = randomNum(4);
        if(randomPosition == 0)
        {
            buildX = randomX[0];
            buildY = randomY[0];
        }
        else if(randomPosition == 1)
        {
            buildX = randomX[0];
            buildY = randomY[1];
        }
        else if(randomPosition == 2)
        {
            buildX= randomX[1];
            buildY = randomY[0];
        }
        else if(randomPosition == 3)
        {
            buildX= randomX[1];
            buildY = randomY[1];
        }
    }

    // Sets Building type to be built.
    private void setBuildID(String type)
    {
        buildID = type;
    }

    private int randomNum(int max)
    {
        randomNum = Greenfoot.getRandomNumber(max);
        return randomNum;
    }

    private void checkSpace(int range)
    {
        cleanUp = (ArrayList)getObjectsInRange(range, Environment.class);
        used = (ArrayList)getObjectsInRange(range, Buildings.class);
        resource = (ArrayList)getObjectsInRange(range, Resources.class);
    }

    // Removes trees, and replaces them with Wood. Walk away after that. 
    private void chopDown()
    {
        Tree t = (Tree)getOneIntersectingObject(Tree.class);
        if(t != null)
        {
            Wood w = new Wood();
            getWorld().addObject(w, t.getX(), t.getY());
            getWorld().removeObject(t);
        }
    }

    private void cleanArea()
    {
        //cleanUp = (ArrayList)getObjectsInRange(range,Environment.class);
        if(needClear == true)
        {
            Environment i = cleanUp.get(0);
            if(i.id == "vRiver" || i.id == "vGold" || i.id == "vWood" || i.id == "vCoal") 
            {
                cleanUp.remove(i);
            }
            if(cleanUp.size() > 0)
            {
                i = cleanUp.get(0);
                //hQCleanUp.remove(i);
                //h.setArrayList(hQCleanUp);
                //i.setFinder();
                if(i != null)
                {
                    this.turnTowards(i.getX(),i.getY());
                    move(speed);
                }
                else
                {
                    cleanUp.remove(i);
                }
                if(this.getX() == i.getX() && this.getY() == i.getY())
                {
                    cleaningActCount++;
                    if(cleaningActCount % 50 == 0)
                    {
                        //h.setHQArrayList(clearRange);
                        //hQCleanUp = h.returnArrayList();
                        chopDown();
                        getWorld().removeObject(i);
                        cleanUp.remove(i);
                        //clearedCount++;
                        //numOfClear++;
                        cleaningActCount = 0;
                    }
                }
                if(clearedCount == cleanUp.size())
                {
                    resetClearList();
                }

            }
        }
    }

    private void resetClearList()
    {
        cleanUp.clear();
        clearedCount = 0;
        numOfClear = 0;
        finished = true;
        needClear = false;
    }

    private void backToHQ()
    {
        this.turnTowards(headquartersX,headquartersY);
        move(speed);
    }

    private void build(String type)
    {
        if(type == "b") // Build Barracks
        {
            Barracks k = new Barracks(this.id);
            getWorld().addObject(k, this.getX(), this.getY());
            buildingBarracks = false;
        }
        else if(type == "h") // Build House
        {
            House a = new House(this.id);
            getWorld().addObject(a,this.getX(),this.getY());
            buildingHouse = false;
        }
        move(speed);
        actCount = 0;
        onTask = false;
        placeFound = false;
        riverNear = false;
    }

    public void act() 
    {
        detectCollisions(); // Make sure no one runs over each other.
        if(sideHQFound == false)
        {
            findSideHQ();
            sideHQFound = true;
            //h.setHQArrayList(clearRange);
            //hQCleanUp = h.returnArrayList();
        }
        if(noJob == true)
        {
            backToHQ();
            if(this.getX() == headquartersX && this.getY() == headquartersY)
            {
                if(h.buildTask() == false)
                {
                    this.builder = true;
                    this.noJob = false;
                    h.setBuildTask(true);
                }
                else if(h.lumberTask() == false)
                {
                    this.lumber = true;
                    this.noJob = false;
                    h.setLumberTask(true);
                }
                else
                {
                    this.finder = true;
                    this.noJob = false;
                    if(h.checkFinder() == false)
                    {
                        h.confirmFinder();
                    }
                }
            }
        }

        if( h.numOfWood() >= 2 && h.numOfGold() >= 1 && h.numOfCoal() >= 3 && builder == true && onTask == false && sideHQFound == true || h.getDistress() == true)
        {
            backToHQ();
            if(this.getX() == headquartersX && this.getY() == headquartersY)
            {
                needClear = false;
                clearing = false;
                findPlaceToBuild();
                h.useWood(2);
                h.useGold(1);
                h.useCoal(3);
                buildingBarracks = true;
                placeFound = true;
                onTask = true;
            }
        }
        else if( h.numOfWood() >= 2 && builder == true && onTask == false && sideHQFound == true && h.returnHouse() < 5)
        {
            backToHQ();
            if(this.getX() == headquartersX && this.getY() == headquartersY)
            {
                needClear = false;
                clearing = false;
                findPlaceToBuild();
                h.useWood(2);
                buildingHouse = true;
                placeFound = true;
                onTask = true;
            }
        }
        else if(lumber == true && onTask == false && sideHQFound == true)
        {
            backToHQ();
            if(this.getX() == headquartersX && this.getY() == headquartersY)
            {
                findTree();
                goAfterTree = true;
                onTask = true;
            }
        }
        else if(finder == true && onTask == false && sideHQFound == true && resourceTargetUsed == false)
        {
            backToHQ();
            if(this.getX() == headquartersX && this.getY() == headquartersY)
            {
                findResources();
                closestResourceFound = true;
                onTask = true;
            }
        }

        if(placeFound == true && finished == true )
        {
            moveToBuild();
        }

        if(this.getX() == buildX && this.getY() == buildY && finished == true)
        {
             if(buildingHouse == true)
            {
                actCount++;
                if(actCount % 50 == 0)
                {
                    build("h");
                }
            }
            else if(buildingBarracks == true)
            {
                actCount++;
                if(actCount % 50 == 0)
                {
                    build("b");
                }
            }   
        }

        if(goAfterTree == true)
        {
            turnTowards(targetX, targetY);
            move(speed);
            if(this.getX() == targetX && this.getY() == targetY)
            {
                actCount++;
                if(actCount == 50)
                {
                    chopDown();
                    goAfterTree = false;
                    findResources();
                    closestResourceFound = true;
                    gettingWood = true;
                }
            }
        }

        if(closestResourceFound == true)
        {
            turnTowards(targetX, targetY);
            move(speed);//moveToward();
            if(this.getX() == targetX && this.getY() == targetY)
            {
                actCount++;
                if(actCount % 50 == 0)
                {
                    if(r.getName() == "w")
                    {
                        img = base + 1;
                        setImg(id,img);
                        holdingWood = true;
                        // set speed lower
                    }
                    else if(r.getName() == "g")
                    {
                        img = base + 2;
                        setImg(id,img);
                        holdingGold = true;
                        // set speed lower
                    }
                    else if(r.getName() == "c")
                    {
                        img = base + 3;
                        setImg(id,img);
                        holdingCoal = true;
                        // set speed lower
                    }
                    r.collectMe();
                    resourceTargetUsed = true;
                    if(r.isExhausted == true)
                    {
                        getWorld().removeObject(r);
                        resourceTargetUsed = false;
                        closestResourceFound = false;
                        gettingWood = false;
                    }
                    closestResourceFound = false;
                    gotResource = true;
                    actCount = 0;
                }
            }
        }

        if(gotResource == true)
        {
            backToHQ();
            if(this.getX() == headquartersX && this.getY() == headquartersY)
            {
                if(holdingWood == true)
                {
                    h.updateInventory(1,0,0);
                    holdingWood = false;
                    // set speed normal(1);
                }
                else if(holdingGold == true)
                {
                    h.updateInventory(0,1,0);
                    holdingGold = false;
                    // set speed normal(1);
                }
                else if(holdingCoal == true)
                {
                    h.updateInventory(0,0,1);
                    holdingCoal = false;
                    // set speed normal(1);
                }
                setImg(id, base);
                if(resourceTargetUsed == true)
                {
                    closestResourceFound = true;
                }
                gotResource = false;
                onTask = false;
            }
        }        
    }
}