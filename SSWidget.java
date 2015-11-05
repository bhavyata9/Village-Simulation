import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * The bar widget can be used as a health bar, or as a hit point bar for their game characters (as an example). Using another class, 
 * the programmer canuse this bar as a timer (i.e: by creating a separate class that inherits this class but has timer-relevant properties like delay). 
 * Otherwise, the programmer can directly instantiate healthbar objects from this class in a world class. Depending on the bar value, the health bar
 * will have a coloured portion that indicates the amount of "health" that the target object has. It will change colour to illustrate the
 * safe and the danger zones. This bar actor can be used to create several bars (objects). 
 * 
 * The bar is black, and the background is white. Other colours can vary.
 * 
 * There are 3 constructor methods, and 2 update methods (which can be called upon by any constructor provided that the code is accurate while
 * doing so), a method that outputs the greatest height among the images (excluding the emoticon), a method that calculates the sum of widths of 
 * all the images (except the emoticon), a mutator that can set of the bar (i.e: can update, the current HP of the character by increasing or 
 * decreasing the coloured portion of the bar), and several accessors to values like the maximum or current values of the bar, the individual 
 * width of each image(except the emoticon) and the percentage of the bar.  
 * 
 * Depending on the type of constructor that is called, there are 3 types bars that can be instantiated. The first and second constructor
 * allows the programmer to input a name for the player, the purpose of the bar, set the height of the bar, the maximum value of the bar,
 * the initial value of the bar and the indicate the danger zone. The first constructor also displays an emoticon depending on which zone
 * the health bar displays. The second constructor allows the programmer to have a health bar that follows
 * the actor. This is especially useful for games in which the healthBar is located immediately above each character. The final constuctor 
 * allows the user to change the colours of the safe and danger zones of the bar and the text colour. 
 * 
 * This program has been an interesting experience for me. It taught me the value of time management by encouraging me to work at my 
 * fullest capacity within the given time. There were many features that I wanted include, but never got the time to successfully 
 * accomplish or to successfully plan them out. Due to the length of this code, I felt it difficult, at first, to debug and continue writing code.
 * For example, my main challenge was trying to understand how I could draw images and have it still compile into a single one. Other than in 
 * Turing, the drawing commands are something that I have never done before, so it was exciting to learn it. I am eager to use other commands like
 * 2DArc in the future. Following that, was trying to understand how the coloured part of the bar would move. This was simple, after much thought,
 * debugging, trial and error and API investigations. In addition, I tried to create an array of colours (r,g,b values) but I could never find out 
 * how the user or the world class should input the numbers of these arrays, even via the Internet. Thus, I left it as 3 integer values. This is 
 * something that I am not proud of and am determined to fix for next time. Lastly, and most importantly, the biggest challenge I had was 
 * trying to determine the cause of disappearing text. Interestingly, when the methods were executed manually (right clicking) it worked fine,
 * but when I involved the World Class something went wrong. Essentially what had happened was the result of staying up too late. I was not 
 * analytical enough, at the time, to realize that I had declared variables but never assigned values to them. After that, I was happy with the 
 * final product even though some features could not be done like (making the bar flash, having "GAME OVER" pop out, or even changing the shape 
 * of the bar itself). Modularity takes a lot of time to master (or to do perfectly) but is both rewarding and useful for future programs.
 * 
 * The special features of this assignment include the emoticon which I handmade (this is one part of the program that should remain fixed so that it
 * looks like a smiley or a sad face. Making this modular was near impossible because of the many values that could distort the image I wanted). The
 * emoticon is exclusive to the first and third constructors only. The coloured text and the coloured zones that the user creates (the second constructor)
 * is something that appeals to me the most because it was something that I spent the most time on (after determining a way for the coloured portion
 * of the bar to move). It was also a new concept that I learned because I never used this command either. 
 * 
 * SSWidget is a Greenfoot Actor.
 * 
 * @author (Srishti Sehgal) 
 * @version (Feb 24 2015)
 */
public class SSWidget extends Actor
{
    // declaring objects
    private Actor otherActor;

    private GreenfootImage myImage;
    private GreenfootImage bar;
    private GreenfootImage text2;
    private GreenfootImage text1;

    private Color safeColour;
    private Color warnColour;
    private Color textColour;
    private Color backgroundColour = null;

    String forWhom = ""; 
    String forWhat = ""; 

    //declaring variables
    private int sizeFont;

    private int length;
    private int barHeight;
    private int height = 0;
    private int sumWidth = 0;

    private int barValue;
    private int dangerValue;
    private int maximumValue;

    private int numOfSol;
    private int numOfPea;
    private int numOfBui;

    //declaring constants
    private final int FACE_WIDTH = 17;
    private final int OFFSET_ONE = 1;
    private final int OFFSET_TWO = 2;
    private final int OFFSET_TEN = 10;
    private final int SUM_OF_IMAGE_GAPS = 22;

    /**
     *Creates a bar object that will not follow the target actor and will display an emoticon that 
     *will change depending on the zone displayed. The colours of the zones and the text are fixed.
     *The user has the option of inputting: the height of the bar, the initial bar value, the bar value 
     *that marks the end of the "safe zone", and the maximum bar value. 
     *
     *@param playerName  Name of the user, player or game character using the bar.
     *@param barPurpose  The purpose of the bar (i.e: healthbar, timer)
     *@param heightY     The height of the bar (preferably between 10-20)
     *@param startHP     The initial value that the bar will display
     *@param dangerHP    The value that will mark the end of the safe zone and will lead into a change colour to represent a low life/value (must be greater than the startHP and less than the maxHP, must be greater than 0)
     *@param maxHP       The maximum value that the user can have (cannot be less than or equal to 0)
     *@param soldiers    Number of soldiers in one village
     *@param peasants    Number of peasants in one village
     *@param buildings   Number of buildings in one village
     **/
    public SSWidget (String playerName, String barPurpose, int heightY, int startHP, int dangerHP, int maxHP, int soldiers, int peasants, int buildings){
        forWhom += playerName;
        forWhat += barPurpose;

        barHeight = heightY;
        barValue = startHP;
        dangerValue = dangerHP;
        maximumValue = maxHP;

        safeColour = Color.GREEN; //these colours are fixed
        warnColour = Color.RED;
        textColour = Color.BLACK;

        sizeFont = 15; //font size is set to 15

        numOfSol = soldiers;
        numOfPea = peasants;
        numOfBui = buildings;

        update();
    }

    /**
     * Ideally, sets the image for the object instantiated from the first and third constructor. The second constructor can call this method as well. 
     * The difference will be in the emoticon image, which is something that the second constructor does not have. The image contains a bar that responds
     * to a decrease or increase in bar value, an emoticon that changes based on the zone the bar displays and two pieces of
     * text (the name of the player and the purpose of the bar).The background of the bar is white and the bar is black. 
     */
    public void update(){
        //creates "canvases" onto which images will be drawn and then will overlap into one image on screen
        bar = new GreenfootImage(maximumValue + OFFSET_TWO,barHeight + OFFSET_ONE);
        text1 = new GreenfootImage(forWhom, sizeFont, textColour, null);
        text2 = new GreenfootImage(forWhat, sizeFont, textColour, null);
        //face = new GreenfootImage(FACE_WIDTH,barHeight + FACE_WIDTH); 

        myImage = new GreenfootImage (sumWidth(),greaterHeight());//+face.getHeight());//overall image
        myImage.setColor(Color.WHITE);
        myImage.fill();

        //drawing the bar
        bar.setColor(Color.BLACK);
        bar.drawRect(0,0,maximumValue + OFFSET_ONE,barHeight);

        //The bar value controls the coloured portion of the bar. Thus, if the bar value changes, the coloured portion
        //of the bar will respond accordingly.
        if (barValue <= maximumValue){
            if (barValue >= dangerValue){
                bar.setColor(safeColour);
                bar.fillRect(1,1,barValue,barHeight - OFFSET_ONE);

            }
            else if (barValue < dangerValue){
                bar.setColor(warnColour);
                bar.fillRect(1,1,barValue,barHeight - OFFSET_ONE);
            }
        }else if (barValue > maximumValue){
            barValue = maximumValue;
            if (barValue >= dangerValue){
                bar.setColor(safeColour);
                bar.fillRect(1,1,barValue,barHeight - OFFSET_ONE);
                //happyFace();
            }
            else if (barValue < dangerValue){
                bar.setColor(warnColour);
                bar.fillRect(1,1,barValue,barHeight - OFFSET_ONE);
                //if (barValue != 0){
                //sadFace();
                //}
            }
        }
        myImage.drawImage(bar,text1.getWidth() + OFFSET_TEN,0); //places bar in center of overall image
        myImage.drawImage(text1,0,0);//puts text1 on left of bar
        myImage.drawImage(text2,sumWidth() - text2.getWidth(),0);//puts text2 on right of bar

        setImage(myImage); //sets image of the health bar object
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
        if (text2.getHeight() > height){
            height = text2.getHeight();//if the text2 height is greater, make it the "maximum height" of the image
        }
        if (bar.getHeight() > height){
            height = bar.getHeight();//if the bar height is greater, make it the "maximum height" of the image
        }
        return height;
    }  

    /**
     * Mutates the value of the bar value variable. Used by outside classes to cause the bar to expand the coloured portion shrink it.
     * This version is to be used for the update method with parameters.
     * @param amount    The amount by which the coloured portion of the bar will change. Putting negative values will cause the bar to decrease, and positive values to increase the coloured bar.
     */
    public void setBarValue(int amount){
        //average health of peasant + average health of soldiers + average health of hq)

        barValue += amount/(numOfSol + numOfPea + numOfBui);//mutates the bar value, Note: this is used for the first/third constructor
        // (thus different update method is called).
        if (barValue < 0){  //if the bar decreases past 0 (into negatives) set to = 0
            barValue = 0;
        }
        if (barValue > maximumValue){
            barValue = maximumValue; //if the bar increases past 0 (into negatives) set to = 0
        }
        update();
    }

    /**
     * Useful for objects that have a target object and are following it. If the object exists and the bar has been made to follow it, it will. If the object
     * ceases to exist, it will remove itself from the world.
     */
    public void act() 
    {
        //if("escape".equals(Greenfoot.getKey())) getWorld().removeObject(this);
    }

    /**
     * Calculates the sum of the width of all the images, namely the text images and the bar image. Used in calculating
     * the width of the overall image (also known as in this program as "myImage").  
     * @return int  The sum of the width of the images (excluding the emoticon).
     */
    public int sumWidth(){
        sumWidth = maximumValue+text1.getWidth() + text2.getWidth() + SUM_OF_IMAGE_GAPS; //sum of all images
        return sumWidth; //used for overall image
    }

    /**
     * Returns the width of the first piece of text, located on the left of the bar.
     * @return int  Width of the left text.
     */
    public int getText1Width() { 
        return text1.getWidth(); 
    }

    /**
     * Returns the width of the second piece of text, located on the right of the bar.
     * @return int  Width of the right text.
     */
    public int getText2Width() { 
        return text2.getWidth(); 
    }

    /**
     * Returns the width of the bar, located in the middle of the overall image.
     * @return int  Width of the bar.
     */
    public int getBarWidth(){
        return bar.getWidth();
    }

    /**
     * Returns the current value that the bar displays, which is illustrated as the width of the coloured portion.
     * @return int  Current value of the bar (or of the target object).
     */
    public int getBarValue(){
        return barValue;
    }

    /**
     * Returns the value of the bar that marks the end of the safe zone. After this value, the bar or the target object is in the danger zone.
     * The bar will change colour after this value.
     * @return int  Last value of the bar before it changes colour and enters the danger zone.
     */
    public int getDangerValue(){
        return dangerValue;
    }

    /**
     * Returns the height of the bar, located in the middle of the overall image.
     * @return int  Height of the bar.
     */
    public int getBarHeight(){
        return barHeight;
    }

    /**
     * Returns the maximum value of the bar. The bar can only display a full bar and report a full value. No value exceeds this value. 
     * The bar is in the safety zone and is at 100% capacity.
     * @return int  Maximum value of the bar that reports full capacity. 
     */
    public int getMaximumValue(){
        return maximumValue;
    }

    /**
     * Returns the percentage of the bar that is being displayed. Divides the current value by the maximum value.  
     * @return float    Percentage of bar.
     */
    public float barPercentage(){
        return 100*barValue/maximumValue; //note that there is no percent sign.
    }
}
