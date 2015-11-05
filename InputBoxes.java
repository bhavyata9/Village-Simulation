import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 *This is textBox. It will function as a way for users to 
 *input string values for certain parameters like number of soldiers, 
 *village names, etc...
 * 
 * So far, on the world screen, a box with the words "Empy field" appears. There is no
 * flashing line to indicate that the user can change the text field, but you still can.
 * The box shrinks and expands with the text inputted into it (at this moment in time)
 * You can retrieve the string entered and then later on convert to integer for other classes
 * with parsing. 
 * 
 * @author Srishti, Bhavya, Justin, EJIANG
 * @version 31/03/2015
 */
public class InputBoxes extends Actor
{
    private GreenfootImage input;
    private GreenfootImage box;
    private GreenfootImage myImage;
    private GreenfootImage cursor;
    private String text;
    private int height = 0;
    private final int OFFSET_TWO = 2;
    private final int OFFSET_ONE = 1;
    private Color textColour;
    private Color fillColour;
    private Boolean canType = false;

    private int number;
    private int blinkingCursor;
    private int blinkSpeed = 2;
    private Color blinkInitialColor = null;
    private Color blinkColor;
    private static final long BLINK_DELAY = 30;
    private boolean blinkOn; // Variable to control blink state
    private int placement;

    private int numberI = 0;
    private boolean jobDoneI = false;
    private int numberB = 0;
    private boolean jobDoneB = false;
    private int actCount;

    /**
     * Creates a text box
     * @param instr           -  The text in the textbox
     * @param inTextColour    -  Combined RGB value for the text colour(colour of the String)
     * @param c               -  java.awt Color for background
     */
    public InputBoxes(String instr, int inTextColour, Color c) 
    {
        text = instr;
        blinkSpeed = 20;
        textColour = new Color (inTextColour);
        fillColour = c;

        input = new GreenfootImage (instr, 25, textColour, null);
        box = new GreenfootImage(200, input.getHeight() + OFFSET_TWO);
        box.setColor(fillColour);
        box.fillRect(1,1,200 - OFFSET_TWO,input.getHeight());

        myImage = new GreenfootImage (200,greaterHeight() + OFFSET_ONE);
        myImage.drawImage(box,0,0); 
        myImage.drawImage(input,0,0);

        setImage(myImage);
    } 

    /**
     * Updates text. It will be black on a white background, which a font size of 25. It has a fixed length of 200.
     *
     * @param  t the new string
     */
    public void update(String t)
    {
        text = t;
        input = new GreenfootImage (t, 25, textColour, null);
        //placement = input.getWidth();
        box = new GreenfootImage(200, input.getHeight() + OFFSET_TWO);
        box.setColor(fillColour);
        box.fillRect(1, 1, 200 - OFFSET_TWO,input.getHeight());

        myImage = new GreenfootImage (200,greaterHeight() + OFFSET_ONE);
        myImage.drawImage(box, 0, 0); 
        myImage.drawImage(input, 0, 0);

        cursor = new GreenfootImage(2, input.getHeight());

        cursor.setColor(blinkColor);
        cursor.fillRect(0, 0, 1, input.getHeight());
        myImage.drawImage(cursor, input.getWidth() ,0);

        setImage(myImage);
    }

    /**
     * Calculates the greatest height among the images, namely the text images and the bar image. The highest one will be used in calculating
     * the height of the overall image (also known as in this program as "myImage").  
     * @return int  The largest height among the images
     */
    public int greaterHeight(){
        if (input.getHeight() > height){
            height = input.getHeight(); //if the text1 height is greater, make it the "maximum height" of the image
        }
        if (box.getHeight() > height){
            height = box.getHeight();//if the text2 height is greater, make it the "maximum height" of the image
        }
        return height;
    }

    /**
     * Update the textField
     */
    public void act()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        actCount++;
        if (Greenfoot.mouseClicked(this))
        {
            canType = true;
        }

        // Blink the cursor
        if (canType == true && actCount % BLINK_DELAY == 0) {
            blinkOn = !blinkOn;
            if (blinkOn) {
                this.blinkColor = Color.BLUE;
                this.update(this.getText());
            } else {
                this.blinkColor = Color.RED;
                this.update(this.getText());
            }
        }

        //when text is inputed into the field
        if (canType)
        {
            String k = Greenfoot.getKey();
            if (k != null)
            {
                if ( k == "enter")
                {
                    canType = false;
                    this.blinkColor = new Color (0,0,0,0); //makes transparent
                    this.update(this.getText() + "");
                }
                else
                {
                    if ( k == "space" && this.getText().length() >= 0)
                    {
                        k = " ";
                        this.update(this.getText()+ k);
                    } 
                    else if ( k == "shift" && this.getText().length() >= 0)
                    {
                        k = "";
                        this.update(this.getText());
                    } 
                    if (k == "right" && this.getText().length() >= 0)
                    {
                        k = "";
                        this.update(this.getText());
                    }
                    if (k == "left" && this.getText().length() >= 0)
                    {
                        k = "";
                        this.update(this.getText());
                    }
                    if (k == "up" && this.getText().length() >= 0)
                    {
                        k = "";
                        this.update(this.getText());
                    }
                    if (k == "down" && this.getText().length() >= 0)
                    {
                        k = "";
                        this.update(this.getText());
                    }
                    else if (k != "backspace")
                    {
                        this.update(this.getText() + k);
                    } 
                    else if (this.getText().length() == 1 && k == "backspace")
                    {
                        this.update("");
                    }
                    else if ( !(this.getText().isEmpty()) && k == "backspace") 
                    {
                        this.update(this.getText().substring(0, this.getText().length() - 1));
                    }
                }
            }   
        }
    }

    //     /**
    //      * @param x Amount by which cursor will shift. Left if x is negative, right if x is positive.
    //      * Returns position of the blinking cursor at a specific location, relative to the text entered
    //      * @return int
    //      */
    //     public int placement(int x){
    //         return placement = input.getWidth() + x;
    //     }

    /**
     * @return String currently displayed string
     */
    public String getText() //number of resources, approx # of ppl to start
    {
        return text;
    }

    /**
     * @return int speed of blinking cursor
     */
    public int getBlinkSpeed(){
        return blinkSpeed;
    }
}