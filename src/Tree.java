import bagel.Image;
import bagel.Input;

import java.util.List;

public class Tree extends Entity{
    private static final Image IMAGE = new Image("res/tree.png");

    public Tree(double startingX, double startingY) {
        super(Tree.IMAGE, startingX, startingY);
    }

    @Override
    public void update(Input input, List<Entity> entities) {
        super.draw();
    }
}
