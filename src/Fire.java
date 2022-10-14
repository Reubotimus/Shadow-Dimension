import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.List;

/**
 * Fire entity, is shot by enemies
 */
public class Fire extends Entity{
    private static final Image demonFire = new Image("res/demon/demonFire.png");
    private static final Image navecFire = new Image("res/navec/navecFire.png");
    private final boolean isDemon;
    private final DrawOptions DRAW_OPTIONS;
    enum Quadrant {NE, NW, SW, SE};
    private final Quadrant quadrant;

    private Rectangle enemyRectangle;
    private boolean destroyed = false;

    /**
     * gets the x value based on the enemies rectangle and the quadrant that the player is in
     * @param rect the rectangle of the enemy that shot the fire
     * @param quadrant the quadrant that the player is in relative to the enemy, the fire points in this direction
     * @return the x value that the fire will start at
     */
    private static double getX(Rectangle rect, Quadrant quadrant) {
        if (quadrant == Quadrant.SE) {
            return rect.right();
        } else if (quadrant == Quadrant.SW) {
            return rect.left();
        } else if (quadrant == Quadrant.NW) {
            return rect.left();
        } else {
            return rect.right();
        }
    }

    /**
     * gets the y value based on the enemies rectangle and the quadrant that the player is in
     * @param rect the rectangle of the enemy that shot the fire
     * @param quadrant the quadrant that the player is in relative to the enemy, the fire points in this direction
     * @return the y value that the fire will start at
     */
    private static double getY(Rectangle rect, Quadrant quadrant) {
        if (quadrant == Quadrant.SE) {
            return rect.bottom();
        } else if (quadrant == Quadrant.SW) {
            return rect.bottom();
        } else if (quadrant == Quadrant.NW) {
            return rect.top();
        } else {
            return rect.top();
        }
    }

    /**
     * creates a new fire
     * @param rectangle the rectangle of the Enemy that shot the fire
     * @param isDemon represents if the fire is from a Demon or Navec
     * @param quadrant the quadrant that the player is in relative to the enemy, the fire points in this direction
     */
    public Fire(Rectangle rectangle, boolean isDemon, Quadrant quadrant) {
        super(Fire.demonFire, getX(rectangle, quadrant), getY(rectangle, quadrant));
        this.isDemon = isDemon;
        this.quadrant = quadrant;
        super.damage = 10;

        if (!isDemon) {
            super.displayedImage = Fire.navecFire;
            super.damage *= 2;
        }

        if (quadrant == Quadrant.NE) {
            super.translate(0, -super.displayedImage.getHeight());
            this.DRAW_OPTIONS = new DrawOptions().setRotation(-(3 * Math.PI) / 2);
        } else if (quadrant == Quadrant.NW) {
            super.translate(-super.displayedImage.getWidth(), -super.displayedImage.getHeight());
            this.DRAW_OPTIONS = new DrawOptions();
        } else if (quadrant == Quadrant.SW) {
            super.translate(-super.displayedImage.getWidth(), 0);
            this.DRAW_OPTIONS = new DrawOptions().setRotation(-Math.PI / 2);
        } else {
            this.DRAW_OPTIONS = new DrawOptions().setRotation(Math.PI);
        }
    }


    /**
     * draws the fire with the rotation factored in
     */
    @Override
    public void draw() {
        if (!this.destroyed) {
            Point topLeft = super.rectangle.topLeft();
            super.displayedImage.drawFromTopLeft(topLeft.x, topLeft.y, this.DRAW_OPTIONS);
        }
    }

    /**
     * draws the fire
     * @param input the user input
     * @param entities all other entities within level
     */
    @Override
    public void update(Input input, List<Entity> entities) {
        draw();
    }

    /**
     * sets the position and rotation of the fire based on the enemy that shot the fire and the quadrant the player is in
     * @param rectangle the rectangle of the enemy that shot the fire
     * @param quadrant the quadrant that the player is in relative to the enemy, the fire points in this direction
     */
    public void changeQuadrant(Rectangle rectangle, Quadrant quadrant) {
        super.rectangle.moveTo(new Point(getX(rectangle, quadrant), getY(rectangle, quadrant)));
        if (quadrant == Quadrant.NE) {
            super.translate(0, -super.displayedImage.getHeight());
            this.DRAW_OPTIONS.setRotation(-(3 * Math.PI) / 2);
        } else if (quadrant == Quadrant.NW) {
            super.translate(-super.displayedImage.getWidth(), -super.displayedImage.getHeight());
            this.DRAW_OPTIONS.setRotation(0);
        } else if (quadrant == Quadrant.SW) {
            super.translate(-super.displayedImage.getWidth(), 0);
            this.DRAW_OPTIONS.setRotation(-Math.PI / 2);
        } else {
            this.DRAW_OPTIONS.setRotation(Math.PI);
        }
    }

    /**
     * @return the quadrant the player is in relative to the enemy and that this fire is pointing towards
     */
    public Quadrant getQuadrant() {
        return quadrant;
    }

    /**
     * @return if the fire is shot from a demon or a navec
     */
    public boolean isDemon() {
        return isDemon;
    }
}
