import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;
import java.util.ArrayList;
/**
 * A variation of an actor that maintains a precise location (using doubles for the co-ordinates
 * instead of ints).  This allows small precise movements (e.g. movements of 1 pixel or less)
 * that do not lose precision.
 * 
 * @author Poul Henriksen
 * @author Michael Kolling
 * @author Neil Brown
 * 
 * @version 3.0
 * 
 * Edited and animation added by EJIANG 18/03/2015
 * Targeting code by Justin Ding and EJIANG 22/03/2015
 */
public abstract class SmoothMover extends Actor
{
    private Vector movement;
    private double exactX;
    private double exactY;

    // ANIMATION VARIABLES
    private String fileName; // Name of file
    private int currentImage = 0;
    private GreenfootImage[] images;
    private int totalImages; // Total number of images in sequence
    private int aTime; // Time delay between images, if applicable

    //TARGETING VARIABLES
    protected Resources closestResource;
    protected ArrayList<Resources> nearResources;
    protected ArrayList<River> v;

    protected Tree closestTree;
    protected ArrayList<Tree> nearTree;

    public SmoothMover()
    {
        this(new Vector());
    }

    /**
     * Create new thing initialised with given speed.
     */
    public SmoothMover(Vector movement)
    {
        this.movement = movement;
    }

    public void move()
    {
        exactX = exactX + movement.getX();
        exactY = exactY + movement.getY();
    }

    /**
     * Move forward by the specified distance.
     * (Overrides the method in Actor).
     */
    @Override
    public void move(int distance)
    {
        move((double)distance);
    }

    /**
     * Move forward by the specified exact distance.
     */
    public void move(double distance)
    {
        double radians = Math.toRadians(getRotation());
        double dx = Math.cos(radians) * distance;
        double dy = Math.sin(radians) * distance;
        setLocation(exactX + dx, exactY + dy);
    }

    /**
     * Set the location using exact coordinates.
     */
    public void setLocation(double x, double y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation((int) (x + 0.5), (int) (y + 0.5));
    }

    /**
     * Set the location using integer coordinates.
     * (Overrides the method in Actor.)
     */
    @Override
    public void setLocation(int x, int y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation(x, y);
    }

    /**
     * Return the exact x-coordinate (as a double).
     */
    public double getExactX() 
    {
        return exactX;
    }

    /**
     * Return the exact y-coordinate (as a double).
     */
    public double getExactY() 
    {
        return exactY;
    }

    /**
     * Increase the speed with the given vector.
     */
    public void addForce(Vector force) 
    {
        movement.add(force);
    }

    /**
     * Accelerate the speed of this mover by the given factor. (Factors < 1 will
     * decelerate.)
     */
    public void accelerate(double factor)
    {
        movement.scale(factor);
        if (movement.getLength() < 0.15) {
            movement.setNeutral();
        }
    }

    /**
     * Return the speed of this actor.
     */
    public double getSpeed()
    {
        return movement.getLength();
    }

    /**
     * Stop movement of this actor.
     */
    public void stop()
    {
        movement.setNeutral();
    }

    /**
     * Return the current speed.
     */
    public Vector getMovement() 
    {
        return movement;
    }

    // Removes things if they get to the end of the world.
    protected void checkAndRemove()
    {
        if(exactX < 0 ||exactX > 960)
        {
            getWorld().removeObject(this);
        }
        if(exactY < 0 || exactY > 640)
        {
            getWorld().removeObject(this);
        }
    }

    public void animate(String side, String basename, int noOfImages, int rotation)
    {
        fileName = side + basename;
        totalImages = noOfImages;

        images = new GreenfootImage[totalImages];
        for(int i = 0; i < (totalImages); i++) {
            images[i] = new GreenfootImage(fileName + i + ".png");
        }
        setImage(images[currentImage]);
        setRotation(rotation);
    }

    // Animate by running through series of pictures.
    public void updateImage()
    {
        currentImage = (currentImage + 1) % images.length;
        setImage(images[currentImage]);
    }

    // Find distance from this actor to another
    public double getDistance(Actor actor)
    {
        return Math.hypot(actor.getX() - exactX, actor.getY() - exactY);
    }

    //Find the nearest Resource. 
    public Resources getNearestResource(double dV)
    {
        nearResources = (ArrayList)getWorld().getObjects(Resources.class);
        v = (ArrayList)getWorld().getObjects(River.class);
        if(nearResources.size() > 0)
        {
            closestResource = nearResources.get(0);

            double shortestDistance = getDistance(nearResources.get(0));
            double distance;

            for(int i = 0; i < nearResources.size(); i++)
            {
                distance = getDistance(nearResources.get(i));
                if(distance < shortestDistance && nearResources.get(i).checkFinder() == false && distance < dV)
                {
                    closestResource = nearResources.get(i);
                    shortestDistance = distance;
                }
            }
        }
        return closestResource;
    }

    // Find nearest Person of a specific side. Useful for lost and attacking Soldiers.
    public People getNearestPerson(int range, String side)
    {
        List <People> nearPeople = getObjectsInRange(range, People.class);

        People nearestPerson = null;

        double nearestDistance = range;
        double distance;

        for(int i = 0; i < nearPeople.size(); i++)
        {
            if(nearPeople.get(i).id == side)
            {
                distance = getDistance(nearPeople.get(i));
                if(distance < nearestDistance)
                {
                    nearestPerson = nearPeople.get(i);
                    nearestDistance = distance;
                }
            }
        }
        return nearestPerson;
    }

    // Find the nearest Building. Useful for attacking Soldiers and lost Peasants.
    public Buildings getNearestBuilding(int range, String side)
    {
        List <Buildings> nearBuildings = getObjectsInRange(range, Buildings.class);

        Buildings nearestBuilding = null;

        double nearestDistance = range;
        double distance;
        for(int i = 0; i < nearBuildings.size(); i++) 
        {
            if(nearBuildings.get(i).getId() == side)
            {
                distance = getDistance(nearBuildings.get(i));
                if(distance < nearestDistance)
                {
                    nearestBuilding = nearBuildings.get(i);
                    nearestDistance = distance;
                }
            }
        }
        return nearestBuilding;
    }

    public Tree getNearestTree()
    {
        nearTree = (ArrayList)getWorld().getObjects(Tree.class);
        if(nearTree.size() > 0)
        {
            closestTree = nearTree.get(0);

            double shortestDistance = getDistance(nearTree.get(0));
            double distance;

            for(int i = 0; i < nearTree.size(); i++)
            {
                distance = getDistance(nearTree.get(i));
                if(distance < shortestDistance && nearTree.get(i).checkFinder() == false)
                {
                    closestTree = nearTree.get(i);
                    shortestDistance = distance;
                }
            }
        }
        return closestTree;
    }
}
// import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
// import java.util.List;
// import java.util.ArrayList;
// /**
//  * A variation of an actor that maintains a precise location (using doubles for the co-ordinates
//  * instead of ints).  This allows small precise movements (e.g. movements of 1 pixel or less)
//  * that do not lose precision.
//  * 
//  * @author Poul Henriksen
//  * @author Michael Kolling
//  * @author Neil Brown
//  * 
//  * @version 3.0
//  * 
//  * Edited and animation added by EJIANG 18/03/2015
//  * Targeting code by Justin Ding and EJIANG 22/03/2015
//  */
// public abstract class SmoothMover extends Actor
// {
//     private Vector movement;
//     private double exactX;
//     private double exactY;
// 
//     // ANIMATION VARIABLES
//     private String fileName; // Name of file
//     private int currentImage = 0;
//     private GreenfootImage[] images;
//     private int totalImages; // Total number of images in sequence
//     private int aTime; // Time delay between images, if applicable
// 
//     //TARGETING VARIABLES
//     protected Resources closestResource;
//     protected ArrayList<Resources> nearResources;
// 
//     public SmoothMover()
//     {
//         this(new Vector());
//     }
// 
//     /**
//      * Create new thing initialised with given speed.
//      */
//     public SmoothMover(Vector movement)
//     {
//         this.movement = movement;
//     }
// 
//     public void move()
//     {
//         exactX = exactX + movement.getX();
//         exactY = exactY + movement.getY();
//     }
// 
//     /**
//      * Move forward by the specified distance.
//      * (Overrides the method in Actor).
//      */
//     @Override
//     public void move(int distance)
//     {
//         move((double)distance);
//     }
// 
//     /**
//      * Move forward by the specified exact distance.
//      */
//     public void move(double distance)
//     {
//         double radians = Math.toRadians(getRotation());
//         double dx = Math.cos(radians) * distance;
//         double dy = Math.sin(radians) * distance;
//         setLocation(exactX + dx, exactY + dy);
//     }
// 
//     /**
//      * Set the location using exact coordinates.
//      */
//     public void setLocation(double x, double y) 
//     {
//         exactX = x;
//         exactY = y;
//         super.setLocation((int) (x + 0.5), (int) (y + 0.5));
//     }
// 
//     /**
//      * Set the location using integer coordinates.
//      * (Overrides the method in Actor.)
//      */
//     @Override
//     public void setLocation(int x, int y) 
//     {
//         exactX = x;
//         exactY = y;
//         super.setLocation(x, y);
//     }
// 
//     /**
//      * Return the exact x-coordinate (as a double).
//      */
//     public double getExactX() 
//     {
//         return exactX;
//     }
// 
//     /**
//      * Return the exact y-coordinate (as a double).
//      */
//     public double getExactY() 
//     {
//         return exactY;
//     }
// 
//     /**
//      * Increase the speed with the given vector.
//      */
//     public void addForce(Vector force) 
//     {
//         movement.add(force);
//     }
// 
//     /**
//      * Accelerate the speed of this mover by the given factor. (Factors < 1 will
//      * decelerate.)
//      */
//     public void accelerate(double factor)
//     {
//         movement.scale(factor);
//         if (movement.getLength() < 0.15) {
//             movement.setNeutral();
//         }
//     }
// 
//     /**
//      * Return the speed of this actor.
//      */
//     public double getSpeed()
//     {
//         return movement.getLength();
//     }
// 
//     /**
//      * Stop movement of this actor.
//      */
//     public void stop()
//     {
//         movement.setNeutral();
//     }
// 
//     /**
//      * Return the current speed.
//      */
//     public Vector getMovement() 
//     {
//         return movement;
//     }
// 
//     // Removes things if they get to the end of the world.
//     protected void checkAndRemove()
//     {
//         if(exactX < 0 ||exactX > 960)
//         {
//             getWorld().removeObject(this);
//         }
//         if(exactY < 0 || exactY > 640)
//         {
//             getWorld().removeObject(this);
//         }
//     }
// 
//     public void animate(String side, String basename, int noOfImages, int rotation)
//     {
//         fileName = side + basename;
//         totalImages = noOfImages;
// 
//         images = new GreenfootImage[totalImages];
//         for(int i = 0; i < (totalImages); i++) {
//             images[i] = new GreenfootImage(fileName + i + ".png");
//         }
//         setImage(images[currentImage]);
//         setRotation(rotation);
//     }
// 
//     // Animate by running through series of pictures.
//     public void updateImage()
//     {
//         currentImage = (currentImage + 1) % images.length;
//         setImage(images[currentImage]);
//     }
// 
//     // Find distance from this actor to another
//     public double getDistance(Actor actor)
//     {
//         return Math.hypot(actor.getX() - exactX, actor.getY() - exactY);
//     }
// 
//     //     public Resources getNearestResource(int range)
//     //     {
//     //         List <Resources> nearResources = getObjectsInRange(range, Resources.class);
//     // 
//     //         Resources nearestResource = null;
//     // 
//     //         double nearestDistance = 1000;
//     //         double distance;
//     // 
//     //         for(Resources r : nearResources)//int i = 0; i < nearResources.size(); i++) 
//     //         {
//     //             distance = getDistance(r);
//     //             if(distance < nearestDistance)
//     //             {
//     //                 nearestResource = r;//nearResources.get(i);
//     //                 nearestDistance = distance;
//     //             }
//     //         }
//     //         return nearestResource;
//     //     }
// 
//     //Find the nearest Resource. 
//     public Resources getNearestResource()
//     {
//         nearResources = (ArrayList)getWorld().getObjects(Resources.class);
//         if(nearResources.size() > 0)
//         {
//             closestResource = nearResources.get(0);
// 
//             double shortestDistance = getDistance(nearResources.get(0));
//             double distance;
// 
//             for(int i = 0; i < nearResources.size(); i++)
//             {
//                 distance = getDistance(nearResources.get(i));
//                 if(distance < shortestDistance)
//                 {
//                     closestResource = nearResources.get(i);
//                     shortestDistance = distance;
//                 }
//             }
//         }
//         return closestResource;
//     }
// 
//     // Find nearest Person of a specific side. Useful for lost and attacking Soldiers.
//     public People getNearestPerson(int range, String side)
//     {
//         List <People> nearPeople = getObjectsInRange(range, People.class);
// 
//         People nearestPerson = null;
// 
//         double nearestDistance = range;
//         double distance;
// 
//         for(int i = 0; i < nearPeople.size(); i++)
//         {
//             if(nearPeople.get(i).id == side)
//             {
//                 distance = getDistance(nearPeople.get(i));
//                 if(distance < nearestDistance)
//                 {
//                     nearestPerson = nearPeople.get(i);
//                     nearestDistance = distance;
//                 }
//             }
//         }
//         return nearestPerson;
//     }
// 
//     // Find the nearest Building. Useful for attacking Soldiers and lost Peasants.
//     public Buildings getNearestBuilding(int range)
//     {
//         List <Buildings> nearBuildings = getObjectsInRange(range, Buildings.class);
// 
//         Buildings nearestBuilding = null;
// 
//         double nearestDistance = range;
//         double distance;
//         for(int i = 0; i < nearBuildings.size(); i++) 
//         {
//             distance = getDistance(nearBuildings.get(i));
//             if(distance < nearestDistance)
//             {
//                 nearestBuilding = nearBuildings.get(i);
//                 nearestDistance = distance;
//             }
//         }
//         return nearestBuilding;
//     }
// }