import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * This is the introductory world class. In this class, music will play and users can input
 * certain numbers in the textfields, which will allow them to start the simulation their way.
 * 
 * @author EJIANG and Srishti Sehgal
 * @version 30/03/2015
 */
public class TitleWorld extends World
{
    GreenfootSound bgm = new GreenfootSound("Big Sky.mp3"); //setting background music

    private Color c = new Color(255, 255, 255, 100);

    private Button button = new  Button();

    private InputBoxes i = new InputBoxes("≃ No. of Trees", 0, c); // field for approx. number of Resources
    private InputBoxes b = new InputBoxes("≃ No. of Resources", 0, c); // field for approx. number of Trees
    private InputBoxes nameR = new InputBoxes("Name of Village A", 0, c); // field for name of first village
    private InputBoxes nameB = new InputBoxes("Name of Village B", 0, c); // field for name of second village

    /**
     * Constructor for objects of class TitleWorld.
     * Adds the button, which will carry the user to the next screen.
     * Adds the textfields, which allow the user to input certain values.
     */
    public TitleWorld()
    {    
        super(960, 640, 1);

        //spawns textfields
        addObject(i, 130, 81);
        addObject(b, 130, 120);
        addObject(nameR, 350, 81);
        addObject(nameB, 350, 120);

        //the "go" button a.k.a. the "To war!" button
        addObject(button, 546, 100);

        //user instructions
        Enter enter = new Enter();
        addObject(enter, 101, 35);

    }

    /**
     * Starts music playback.
     */
    public void started()
    {
        bgm.playLoop();
    }

    /**
     * Stops music playback.
     */
    public void stopped()
    {
        bgm.stop();
    }

    /**
     * Returns textbox object
     *@return   InputBoxes  The first textbox from the top of the screen.
     */
    public InputBoxes getI(){return i;}

    /**
     * Returns textbox object
     *@return   InputBoxes  The second textbox from the top of the screen.
     */
    public InputBoxes getB(){return b;}

    /**
     * Returns textbox object
     *@return   InputBoxes  The first textbox from the top of the screen, in the second column.
     */
    public InputBoxes getNameR(){return nameR;}

    /**
     * Returns textbox object
     *@return   InputBoxes  The second textbox from the top of the screen, in the second column.
     */
    public InputBoxes getNameB(){return nameB;}

    /**
     * Returns button object
     *@return   Button  The second textbox from the top of the screen.
     */
    public Button getButton(){return button;}
}
