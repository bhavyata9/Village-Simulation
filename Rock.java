import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Randomly chooses image, rotation, and size of Rock.
 * 
 * @author EJIANG
 * @version 16/03/2015
 */
public class Rock extends Environment
{
    public Rock()
    {
        this.id = "vR";
        setImg();
    }

    //sets image of rock.
    private void setImg()
    {
        this.getSizeRotationID(4); // generate all parameters needed

        this.base = new GreenfootImage(id + num + ".png");
        setImage(base);
        
        // scaling the original image
        GreenfootImage image = getImage();
        image.scale(roundDim(base.getWidth(), size), roundDim(base.getHeight(), size));
        setImage(image);
        
        setRotation(rotation); // rotates image
    }
}
