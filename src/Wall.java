import bagel.Image;
import bagel.Input;

import java.util.List;

public class Wall extends Entity{
    private static final Image IMAGE = new Image("res/wall.png");

    public Wall(double startingX, double startingY) {
        super(Wall.IMAGE, startingX, startingY);
    }

    @Override
    public void update(Input input, List<Entity> entities) {
        super.draw();
    }
}
