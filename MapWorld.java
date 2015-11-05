import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
/**
 * All things spawn randomly.
 * Until user entry can be accepted through Bars, Object quantities will be randomly generated, and spawned at random locations.
 * Rotation is also randomly generated.
 * 
 * As well, the number of environmental objects will be kept rather constant. This is to mimic the renewability of Resources in real life.
 * 
 * @author EJIANG, Srishti, and Justin
 * @version 30/03/2015
 */
public class MapWorld extends World
{
    GreenfootSound bgm = new GreenfootSound("Scattershot.mp3"); //setting background music

    private int treeCount;
    private int rockCount;
    private int grassCount;
    private int resourceCount;

    private final int TREE_SPAWN_RATE = 50;
    private final int ROCK_SPAWN_RATE = 500;
    private final int GRASS_SPAWN_RATE = 10;
    private final int RESOURCE_SPAWN_RATE = 100;

    private int tSpawnCounter;
    private int rkSpawnCounter;
    private int gSpawnCounter;
    private int rcSpawnCounter;

    private ResetButton reset = new ResetButton();
    private StatBars sbR;
    private StatBars sbB;
    private Headquarters r;
    private Headquarters b;
    private boolean statBarCreated = false;

    private boolean onScreen = false;

    private River v;
    private int vX;
    private int vY;

    private double vSlope;
    private double yInt;
    private double c;

    // NAMES OF FIGHTING ARMIES, ENTERED RESOURCE VALUES. OBTAINED FROM USERS VIA BUTTON
    private String rVillage;
    private String bVillage;

    private int actCount;
    private String winSide;
    private Headquarters winner;
    private Font textFont;
    private List<Headquarters> hQ;

    //     private int blueSoldiers;
    //     private int redSoldiers;
    //     private int bluePeasants;
    //     private int redPeasants;

    private boolean warOver = false;

    GreenfootImage image = new GreenfootImage(getWidth(), getHeight());
    GreenfootImage winnerT = new GreenfootImage(500, 500);

    GreenfootImage back = new GreenfootImage(getWidth(), getHeight()); // size of the object

    //private int xT, xB, yInt, yR; //top and bottom x intercepts, left and right y intercepts
    //private int xStart, xEnd, yStart, yEnd; //x and y start/end points for the river

    //     private int [] rX;
    //     private int [] rY;

    public MapWorld(int t, int r, String rV, String bV)
    {    
        super(960, 640, 1);

        treeCount = genCount(t, 5);
        rockCount = genCount(10, 20);
        grassCount = genCount(200, 100);
        resourceCount = genCount(r, 5);

        rVillage = rV;
        bVillage = bV;

        tSpawnCounter = TREE_SPAWN_RATE;
        rkSpawnCounter = ROCK_SPAWN_RATE;
        gSpawnCounter = GRASS_SPAWN_RATE;
        rcSpawnCounter = RESOURCE_SPAWN_RATE;

        prepare();
    }

    // Starts music playback
    public void started()
    {
        bgm.playLoop();
    }

    // And stops it.
    public void stopped()
    {
        bgm.stop();
    }

    //initalizes Environmental objects
    public void prepare()
    {
        spawn(grassCount, 3);

        v = new River();
        addObject (v, genX(960, 450), genY(640, 300));

        spawn(treeCount, 1);
        spawn(rockCount, 2);

        spawn(resourceCount, 4);

        spawnHQ();

        sbR = new StatBars("r", rVillage, 150, 475, "Village Health", 20, 10, 100, 20);
        sbB = new StatBars("b", bVillage, 150, 475, "Village Health", 20, 10, 100, 20);
    }

    public void act()
    {
        if(warOver == false)
        {
            actCount++;

            tSpawnCounter--;
            rkSpawnCounter--;
            gSpawnCounter--;
            rcSpawnCounter--;
            if(tSpawnCounter == 0)
            {
                if(getObjects(Tree.class).size() < treeCount)
                {
                    int d = (treeCount - getObjects(Tree.class).size());
                    spawn(d, 1);
                }        
                tSpawnCounter = TREE_SPAWN_RATE;
            }
            if(rkSpawnCounter == 0)
            {
                if(getObjects(Rock.class).size() < rockCount)
                {
                    int d = (rockCount - getObjects(Rock.class).size());
                    spawn(d, 2);
                }
                rkSpawnCounter = ROCK_SPAWN_RATE;
            }
            if(gSpawnCounter == 0)
            {
                if(getObjects(Grass.class).size() < grassCount)
                {
                    int d = (grassCount - getObjects(Grass.class).size());
                    spawn(d, 3);
                }
                gSpawnCounter = GRASS_SPAWN_RATE;
            }
            if (rcSpawnCounter == 0)
            {
                if(getObjects(Resources.class).size() < resourceCount)
                {
                    int d = (resourceCount - getObjects(Resources.class).size());
                    spawn(d, 4);
                }
                rcSpawnCounter = RESOURCE_SPAWN_RATE;
            }

            if("space".equals(Greenfoot.getKey()))// && onScreen == false)
            {
                onScreen = !onScreen;
                if (onScreen)
                {
                    addObject(sbR, 236, 565);
                    addObject(sbB, 723, 565); 
                }
                else
                {
                    sbR.remove();
                    sbB.remove();
                }
            }

            // CONDITIONS TO END WAR
            if(actCount % 25 == 0)
            {
                hQ = this.getObjects(Headquarters.class);
                if(hQ.size() == 1)
                {
                    winner = hQ.get(0);
                    winSide = winner.id;
                    if (winSide == "r")
                    {
                        winSide = rVillage;
                    }
                    else
                    {
                        winSide = bVillage;
                    }
                    delete(winSide);
                    warOver = true;
                }

                //                 blueSoldiers = sbB.getSoldiers();
                //                 redSoldiers = sbR.getSoldiers();
                //                 bluePeasants = sbB.getPeasants();
                //                 redPeasants = sbR.getPeasants();
                //                 if (blueSoldiers == 0 && bluePeasants == 0)
                //                 {
                //                     winSide = getVillageNameOne();
                //                     delete(winSide);
                //                 }
                //                 else if (redSoldiers == 0 && redPeasants == 0)
                //                 {
                //                     winSide = getVillageNameTwo();
                //                     delete(winSide);
                //                 }           

            }
        }
    }

    //spawns Environmental objects. c is number to be spawned, the number identifies which object to spawn.
    private void spawn(int c, int type)
    {
        for(int i = 0; i < c; i++)
        {
            if(type == 1) addObject (new Tree(), genX(960, 10), genY(640, 10));
            else if(type == 2) addObject (new Rock(), genX(960, 10), genY(640, 10));
            else if(type == 3) addObject (new Grass(), genX(960, 10), genY(640, 10));

            else if(type == 4)
            {
                //for now, it's going to randomly choose which type of resource to spawn
                if(Greenfoot.getRandomNumber(2)+1 == 1) addObject (new Gold(), genX(960, 10), genY(640, 10));
                else addObject (new Coal(), genX(960, 10), genY(640, 10));
            }
        }
    }

    //returns random x coordinate, within a border
    private int genX(int max, int border)
    {
        int x = Greenfoot.getRandomNumber(max - (2*border)) + border;
        return x;
    }

    //returns random y coordinate, within a border
    private int genY(int max, int border)
    {
        int y = Greenfoot.getRandomNumber(max - (2*border)) + border;
        return y;
    }

    //returns random integeter in specified range, starting at start
    private int genCount(int range, int start)
    {
        int z = Greenfoot.getRandomNumber(range)+ start;
        return z;
    }

    //spawns two headquarters, on opposite side of the River.
    private void spawnHQ()
    {
        vX = v.getX();
        vY = v.getY();

        double vSlopeDeg = v.getRotation()*1.0;

        // finding radian angle for rotation, and using tangent to convert angle into slope
        vSlope = (Math.tan(Math.toRadians(vSlopeDeg)));

        //finding y intercept of line with the edges of the world.
        yInt = vY - (vSlope*vX);
        if(yInt > 640 || yInt < 0)
        {
            int rx, ry, bx, by;

            ry = genY(640, 100);
            c = ry - yInt;
            if(c < 0) 
            {
                ry = genY(640, 100);
                c = ry - yInt;
            }
            rx = genX((int)((ry - yInt)/vSlope), 100);

            r = new Headquarters("r");
            addObject(r, rx, ry);

            by = genY(640, 100);
            c = 960 - ((by - yInt)/vSlope);
            if(c < 0)
            {
                by = genY(640, 100);
                c = 960 - ((by - yInt)/vSlope);
            }
            bx = genX((int)(960 - ((by - yInt)/vSlope)), 100) + (int)((by - yInt)/vSlope);

            b = new Headquarters("b");
            addObject(b, bx, by);

            c = 0;
        }
        else if(yInt >= 0 && yInt <= 640)
        {
            int rx, ry, bx, by;

            rx = genX(960, 100);
            ry = genY((int)((vSlope*rx) + yInt), 100);

            r = new Headquarters("r");
            addObject(r, rx, ry);

            bx = genX(960, 100);
            c = 640 - (int)((vSlope*bx) + yInt);
            if(c < 0)
            {
                bx = genX(960, 100);
                c = 640 - (int)((vSlope*bx) + yInt);
            }
            by = genY((640 - (int)((vSlope*bx) + yInt)), 100) + (int)((vSlope*bx) + yInt);

            b = new Headquarters("b");
            addObject(b, bx, by);

            c = 0;
        }
    }

    //     public void dockCoordinates()
    //     {
    //         int rx = 0;
    //         int ry = 0;
    //         int by = 0;
    //         int bx = 0;
    // 
    //         if(yInt > 640 || yInt < 0)
    //         {
    //             ry = vY - 50;
    //             rx = (int)((ry - yInt)/vSlope);
    // 
    //             by = vY + 50;
    //             bx = (int)((by - yInt)/vSlope);           
    //         }
    //         else if(yInt >= 0 && yInt <= 640)
    //         {
    //             rx = vX + 50;
    //             ry = (int)((vSlope*rx) + yInt);
    // 
    //             bx = vX - 50;
    //             by = (int)((vSlope*bx) + yInt);
    //         }
    //         v.setDockLocations(rx, ry, bx, by);
    //     }

    // Returns distance between any two actors in the world.
    public double getDistance(Actor a, Actor b)
    {
        double distance;
        double xLength = a.getX() - b.getX();
        double yIntength = a.getY() - b.getY();
        distance = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yIntength, 2));
        return (double)distance;
    }

    /**
     * Tells whether the StatBar/HealthBar is on the screen or not
     *@return boolean StatBar on the screen
     */
    public boolean getOnScreen()
    {
        return onScreen;
    }

    /**
     *The y -intercept for the slope of River's central line
     *@return double y-intercept
     */
    public double getIntercept()
    {
        return yInt;
    }

    /**
     * This is the value from the button, which comes from the textfields in the TitleWorld
     * that the user inputs. 
     * 
     *@return int   number of resources 
     */
    public int getResources(){
        return resourceCount;//s;
    }

    /**
     * This is the value from the button, which comes from the textfields in the TitleWorld
     * that the user inputs. 
     * 
     *@return int   number of trees 
     */
    public int getTrees(){
        return treeCount;//trees;
    }

    /**
     * This is the string from the button, which comes from the textfields in the TitleWorld
     * that the user inputs. 
     * 
     *@return String   number of name of the first village 
     */
    public String getNameVillageOne(){
        return rVillage;
    }

    /**
     * This is the value from the button, which comes from the textfields in the TitleWorld
     * that the user inputs. 
     * 
     *@return String   number of the second village 
     */
    public String getNameVillageTwo(){
        return bVillage;
    }

    /**
     * Displays winner, removes all object from world and sets
     * the background colour to black.
     */
    public void delete(String winner)
    {
        textFont = new Font ("Chiller", Font.BOLD, 75);
        image.setFont(textFont);

        List objects = getObjects(null);
        removeObjects(objects);
        back.setColor(Color.BLACK); // color of your choice
        back.fill();
        back.setTransparency(100); // alpha value of choice
        image.drawImage(back, 0, 0);
        image.setColor(Color.WHITE);
        image.drawString("The winner is " + winner,100,200);
        setBackground(image);
        addObject(reset, 480, 320);
    }

    //     private void searchPeople()
    //     {
    //         find = (ArrayList)this.getObjects(People.class);
    //         for(People h : find)
    //         {
    //             if(h.id == "b")
    //             {
    //                 subBlue.add(h);
    //             }
    //             else
    //             {
    //                 subRed.add(h);
    //             }
    //         }
    //         blue = subBlue;
    //         red = subRed;
    //         subBlue.clear();
    //         subRed.clear();
    //     }
}

