import bagel.*;
import bagel.util.*;

import java.util.List;

/**
 * represents all entities within the game
 */
public abstract class Entity {
    // The image displayed on screen
    protected Image displayedImage;

    /* The rectangle is the bounds in which the sprite
    interacts with other sprites. The top left of the rectangle defining the position of the sprite*/
    protected Rectangle rectangle;
    protected static int timescale = 0;
    protected double speed;
    protected int damage = 0;

    protected static Point topLeft;

    protected static Point bottomRight;




    /**
     * constructs sprite with a starting image and location
     */
    public Entity(Image startingImage, double startingX, double startingY) {
        this.displayedImage = startingImage;
        this.rectangle = this.displayedImage.getBoundingBox();
        this.rectangle.moveTo(new Point(startingX, startingY));
    }

    public Entity(){}

    /**
     * draws sprite image at location on screen
     */
    public void draw() {
        Point topLeft = this.rectangle.topLeft();
        this.displayedImage.drawFromTopLeft(topLeft.x, topLeft.y);
    }

    /**
     * moves sprite to new x, y location
     */
    public void translate(double x, double y) {
        final Point topLeft = this.rectangle.topLeft();
        this.rectangle.moveTo(new Point(topLeft.x + x, topLeft.y + y));
    }

    /**
     * entity will be able to interact with user input and / or other entites
     * @param input the user input
     * @param entities all other entities within level
     */
    public abstract void update(Input input, List<Entity> entities);

    /**
     * @return the rectangle representing the bounds of the entity
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * @return the damage that the entity deals
     */
    public int getDamage() {
        return damage;
    }


    /**
     * @return the timescale of entities
     */
    public static int getTimescale() {
        return timescale;
    }

    /**
     * sets the new top left bound for all entities
     * @param topLeft the bound for all entities
     */
    public static void setTopLeft(Point topLeft) {
        Entity.topLeft = topLeft;
    }
    /**
     * sets the new bottom right bound for all entities
     * @param bottomRight the bound for all entities
     */
    public static void setBottomRight(Point bottomRight) {
        Entity.bottomRight = bottomRight;
    }

    /**
     * increases the timescale for all entities
     */
    public static void increaseTimescale() {
        if (Entity.timescale != 3) {
            Entity.timescale++;
        }
        System.out.println("Sped up, Speed: " + Entity.timescale);
    }

    /**
     * decreases timescale for all entities
     */
    public static void decreaseTimescale() {
        if (Entity.timescale != -3) {
            Entity.timescale--;
        }
        System.out.println("Slowed down, Speed: " + Entity.timescale);
    }
}


