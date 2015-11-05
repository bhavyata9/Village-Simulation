import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.ArrayList;
/**
 * Creates a status bar of both villages at the bottom of the screen. This will give updated
 * statistics about each village such as: 
 * 
 * @author Srishti Sehgal, Justin Ding, EJIANG, and Bhavya Shah
 * @version 31 March 2015)
 */
public class StatBars extends Actor
{
    // declaring objects
    private GreenfootImage myImageVillageA;//the overall image for this village
    private GreenfootImage boxA;//the box, in which stuff will be put
    private GreenfootImage text1;//name of the village (might be removed)
    private SSWidget healthBarA;//overall health of the city

    private Headquarters hq;
    private boolean headquartersFound = false;
    private String id;
    private ArrayList<Headquarters> sideHQ;
    // private boolean sideHQFound = false;

    private GreenfootImage hqSoldiers;//number of soldiers
    private GreenfootImage hqPeasants;//number of peasants
    private GreenfootImage hqWood;//amount of wood
    private GreenfootImage hqGold;//amount of gold
    private GreenfootImage hqCoal;//amount of coal

    private GreenfootImage woodLabel; 
    private GreenfootImage coalLabel;
    private GreenfootImage goldLabel;
    private GreenfootImage soldiersLabel; 
    private GreenfootImage peasantsLabel; 

    //declaring variables
    private int sizeFont;
    private int length;
    private int barHeight;
    private int height = 0;
    private int sumWidth = 0;
    private int startingHP;
    private int maximumHP;
    private int dangerHP;
    private int healthHeight;
    private Color textColour = Color.BLACK;
    String villageA = ""; 
    String purposeA = "";

    //declaring constants
    private final int OFFSET_TWO = 2;

    private boolean isHealthBar = true;;

    /**
     *Creates a bar object that will not follow the target actor and will display an emoticon that 
     *will change depending on the zone displayed. The colours of the zones and the text are fixed.
     *The user has the option of inputting: the height of the bar, the initial bar value, the bar value 
     *that marks the end of the "safe zone", and the maximum bar value. 
     *@param id          The team of the simulation, red or blue.
     *@param villageA    Name of the user, player or game character using the bar.
     *@param heightY     The height of the bar (preferably between 10-20)
     *@param widthX      The width of the bar(set in World Class-Map)
     *@param purpose     Used for the healthBar, tells users the reason of the healthBar.
     *@param initHP      The initial amount of health of the village
     *@param danger      The dangerzone of health for the village
     *@param maximum     The maximum amount of health for the village
     *@param HBHeight    Used for the healthBar, fixes the height of the bar
     */
    public StatBars (String side, String villageName1, int heightY, int widthX, String purpose, int initHP, int danger, int maximum, int HBheight)
    {    
        id = side;
        villageA += villageName1;
        purposeA += purpose;

        barHeight = heightY;
        length = widthX;
        startingHP = initHP;
        dangerHP = danger;
        maximumHP = maximum;
        healthHeight = HBheight;

        sizeFont = 15; //font size is set to 15
        //updateA();
    }

    /**
     * Ideally, sets the image for the object instantiated from the constructor. 
     * The difference will be in the emoticon image, which is something that the second constructor does not have. The image contains a bar that responds
     * to a decrease or increase in bar value and two pieces of text (the name of the village and the purpose of the bar).The background 
     * of the bar is magenta and the outline is black. 
     */
    public void updateA(){
        //creates "canvases" onto which images will be drawn and then will overlap into one image on screen
        boxA = new GreenfootImage(length, barHeight + OFFSET_TWO); //outline
        text1 = new GreenfootImage(villageA, sizeFont, textColour, null); //name of the village appears in the top-most region of the StatBar

        //updates statistics for the following: Wood, Coal, Gold, Number of Soldiers, Number of Peasants
        hqWood = new GreenfootImage(Integer.toString(hq.numOfWood()), sizeFont, textColour, null);
        hqCoal = new GreenfootImage(Integer.toString(hq.numOfCoal()), sizeFont, textColour, null);
        hqGold = new GreenfootImage(Integer.toString(hq.numOfGold()), sizeFont, textColour, null);
        hqSoldiers = new GreenfootImage(Integer.toString(hq.returnSoldier()), sizeFont, textColour, null);
        hqPeasants = new GreenfootImage(Integer.toString(hq.returnPeasant()), sizeFont, textColour, null);

        healthBarA.update();

        //labels for the above statistics 
        woodLabel = new GreenfootImage("Wood", sizeFont, textColour, null);
        coalLabel = new GreenfootImage("Coal", sizeFont, textColour, null);
        goldLabel = new GreenfootImage("Gold", sizeFont, textColour, null);
        soldiersLabel = new GreenfootImage("Soldiers", sizeFont, textColour, null);
        peasantsLabel = new GreenfootImage("Peasants", sizeFont, textColour, null);

        //overall image
        myImageVillageA = new GreenfootImage (length,greaterHeight());//overall image
        Color c = new Color(255, 255, 255, 100);
        myImageVillageA.setColor(c);
        myImageVillageA.fill();

        //drawing the outline
        boxA.setColor(Color.BLACK);
        boxA.drawRect(0,0,length-2,barHeight);

        myImageVillageA.drawImage(boxA,0,0); //places bar in center of overall image
        myImageVillageA.drawImage(text1,length/2,0);//puts text1 on left of bar
        myImageVillageA.drawImage(hqWood,60,barHeight-20);//puts the statistics along the bottom portion of the StatBar
        myImageVillageA.drawImage(hqCoal,135,barHeight-20);
        myImageVillageA.drawImage(hqGold,210,barHeight-20);
        myImageVillageA.drawImage(hqSoldiers,285,barHeight-20);
        myImageVillageA.drawImage(hqPeasants,360,barHeight-20);

        //draws the labels for their respective statistics directly above them
        myImageVillageA.drawImage(woodLabel,50,barHeight-40);
        myImageVillageA.drawImage(coalLabel,125,barHeight-40);
        myImageVillageA.drawImage(goldLabel,200,barHeight-40);
        myImageVillageA.drawImage(soldiersLabel,275,barHeight-40);
        myImageVillageA.drawImage(peasantsLabel,350,barHeight-40);

        setImage(myImageVillageA); //sets image of the StatBar object
    }

    /**
     * Creates healthBar object and adds it to world at the same time as this object,
     * the StatBar, is added to the world.
     */
    public void createBar()
    {
        healthBarA = new SSWidget(villageA, purposeA, healthHeight, startingHP, dangerHP, maximumHP, hq.numOfWood(), hq.numOfWood(), hq.numOfWood());
        getWorld().addObject(healthBarA, getX(), getY()); //also adds the HealthBar Object to the world
    }

    /**
     * Calculates the greatest height among the images, namely the text images and the bar image. The highest one will be used in calculating
     * the height of the overall image (also known as in this program as "myImage").  
     * @return int  The largest height among the images (excluding the emoticon).
     */
    public int greaterHeight(){
        if (text1.getHeight() > height){
            height = text1.getHeight(); //if the text1 height is greater, make it the "maximum height" of the image
        }
        if (boxA.getHeight() > height){
            height = boxA.getHeight();//if the bar height is greater, make it the "maximum height" of the image
        }
        return height;
    }

    /**
     * Every act, the StatBar attempts to search for hq. It updates itself every act 
     * using the statistics from the correct hq. It also spawns the healthBar (but only once)
     */
    public void act() 
    {     
        if(headquartersFound == false)
        {
            findSideHQ();
            headquartersFound = true;
        }
        if (isHealthBar)
        {
            createBar();
            isHealthBar = false;
        }
        updateA();        
    }

    /**
     * Remove the healthBar object, called by MapWorld
     */
    public void remove()
    {
        getWorld().removeObject(this);
        healthBarA.getWorld().removeObject(healthBarA);
        isHealthBar = true;
    }

    /**
     * Attempts to find the correct side (based on the headquarters), so that statistics for the
     * correct village can be displayed.
     */
    private void findSideHQ()
    {
        sideHQ = (ArrayList)getWorld().getObjects(Headquarters.class);

        if((getWorld().getObjects(Headquarters.class)).isEmpty() == false)
        {
            for(Headquarters i: sideHQ)
            {
                if(i.getId() == this.id)
                {
                    hq = i;
                }
            }
        }
    }

    /**
     * Calculates the sum of the width of all the images, namely the text images and the bar image. Used in calculating
     * the width of the overall image (also known as in this program as "myImage").  
     * @return int  The sum of the width of the images.
     */
    public int sumWidth(){
        sumWidth = length + text1.getWidth(); //sum of all images
        return sumWidth; //used for overall image
    }
}