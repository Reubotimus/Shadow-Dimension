import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.List;

public class Fire extends Entity{
    private static final Image demonFire = new Image("res/demon/demonFire.png");
    private static final Image navecFire = new Image("res/navec/navecFire.png");
    private final boolean isDemon;
    private final DrawOptions DRAW_OPTIONS;
    enum Quadrant {NE, NW, SW, SE};
    private final Quadrant quadrant;

    private Rectangle enemyRectangle;
    private boolean destroyed = false;

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



    public void destroy(List<Entity> entities) {
        entities.remove(this);
        this.destroyed = true;
    }
    @Override
    public void draw() {
        if (!this.destroyed) {
            Point topLeft = super.rectangle.topLeft();
            super.displayedImage.drawFromTopLeft(topLeft.x, topLeft.y, this.DRAW_OPTIONS);
        }
    }

    @Override
    public void update(Input input, List<Entity> entities) {
        draw();
    }

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

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public boolean isDemon() {
        return isDemon;
    }
}
