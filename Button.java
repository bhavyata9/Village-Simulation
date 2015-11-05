import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The button transports the user to the next world, where the simulation actually takes place.
 * It checks to make sure that user has inputted integers or Strings in the textfields and that 
 * the button is clicked.
 * 
 * @author (Srishti Sehgal) 
 * @version 29/03/2015
 */
public class Button extends Actor
{   private int numberI = 0;
    private boolean jobDoneI = false; //field for resources
    private int numberB = 0;
    private boolean jobDoneB = false; //field for trees
    private String nameFirst = "";
    private boolean jobDoneNameFirst = false; //field for name of the first village
    private String nameSecond = "";
    private boolean jobDoneNameSecond = false; //field for name of the second village

    /**
     * Act - do whatever the Button wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.mouseClicked(this)){
            TitleWorld t = (TitleWorld) getWorld();
            try {
                numberI = Integer.parseInt(t.getI().getText());
                jobDoneI = true;
            }
            catch(Exception e){
                if(numberI == 0) {
                    t.getI().update("Please Try Again");
                    jobDoneI = false;
                }
            }

            //The following parses the string to an integer. If it is not
            //possible, a message appears in the textbox and the user must try again. 
            try {
                numberB = Integer.parseInt(t.getB().getText());
                jobDoneB = true;
            }
            catch(Exception e){
                if(numberB == 0) {
                    t.getB().update("Please Try Again");
                    jobDoneB = false;
                }
            }

            //names of the villages, keep them as Strings
            nameFirst = t.getNameR().getText();
            jobDoneNameFirst = true;

            nameSecond = t.getNameB().getText();
            jobDoneNameSecond = true;

            if(jobDoneI && jobDoneB){
                t.stopped();
                Greenfoot.setWorld(new MapWorld(numberI, numberB, nameFirst, nameSecond));
            }
        }
    }    

    /**
     * Conversion of string to integer, for the first textbox on the screen
     *@return boolean Tells whether the string in the text has been successfully converted to an integer
     */
    public boolean getJobDoneI(){
        return jobDoneI;
    }

    /**
     * Conversion of string to integer, for the second textbox on the screen
     *@return boolean Tells whether the string in the text has been successfully converted to an integer
     */
    public boolean getJobDoneB(){
        return jobDoneB;
    }

    /**
     * After conversion, this is the number the user inputted.
     *@return int number in first textbox
     */
    public int getNumberI(){
        return numberI;
    }

    /**
     * After conversion, this is the number the user inputted.
     *@return int number in second textBox
     */
    public int getNumberB(){
        return numberB;
    }
    
    /**
     * Conversion of string to integer, for the first textbox on the screen
     *@return boolean Tells whether the string in the text has been successfully converted to an integer
     */
    public boolean getjobDoneNameFirst(){
        return jobDoneNameFirst;
    }

    /**
     * Conversion of string to integer, for the second textbox on the screen
     *@return boolean Tells whether the string in the text has been successfully converted to an integer
     */
    public boolean getjobDoneNameSecond(){
        return jobDoneNameSecond;
    }

    /**
     * After conversion, this is the number the user inputted.
     *@return int number in first textbox
     */
    public String getNameFirst(){
        return nameFirst;
    }

    /**
     * After conversion, this is the number the user inputted.
     *@return int number in second textBox
     */
    public String getNameSecond(){
        return nameSecond;
    }
}