import bagel.Image;
import bagel.Input;

import java.util.List;

/**
 * represents the wall entity
 */
public class Wall extends Entity{
    private static final Image IMAGE = new Image("res/wall.png");

    /**
     * creates a wall entity
     * @param startingX the starting x position of the tree
     * @param startingY the starting y position of the tree
     */
    public Wall(double startingX, double startingY) {
        super(Wall.IMAGE, startingX, startingY);
    }

    /**
     * updates the wall by drawing itself
     * @param input the user input
     * @param entities all other entities within level
     */
    @Override
    public void update(Input input, List<Entity> entities) {
        super.draw();
    }
}
