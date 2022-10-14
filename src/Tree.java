import bagel.Image;
import bagel.Input;

import java.util.List;

/**
 * represents the tree class
 */
public class Tree extends Entity{
    private static final Image IMAGE = new Image("res/tree.png");

    /**
     * creates a new tree
     * @param startingX the starting x position of the tree
     * @param startingY the starting  y position of the tree
     */
    public Tree(double startingX, double startingY) {
        super(Tree.IMAGE, startingX, startingY);
    }

    /**
     * updates the tree by drawing itself
     * @param input the user input
     * @param entities all other entities within level
     */
    @Override
    public void update(Input input, List<Entity> entities) {
        super.draw();
    }
}
