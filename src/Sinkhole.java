import bagel.Image;
import bagel.Input;

import java.util.List;

/**
 * represents sinkhole entity
 */
public class Sinkhole extends Entity{
    private static final Image IMAGE = new Image("res/sinkhole.png");

    /**
     * creates a new sinkhole
     * @param startingX the starting x position of the sinkhole
     * @param startingY the starting y position of the sinkhole
     */
    public Sinkhole(double startingX, double startingY) {
        super(Sinkhole.IMAGE, startingX, startingY);
        super.damage = 30;
    }

    /**
     * updates the sinkhole by drawing it
     * @param input the user input
     * @param entities all other entities within level
     */
    @Override
    public void update(Input input, List<Entity> entities) {
        super.draw();
    }
}
