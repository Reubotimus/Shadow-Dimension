import bagel.Image;
import bagel.Input;

import java.util.List;

public class Sinkhole extends Entity{
    private static final Image IMAGE = new Image("res/sinkhole.png");
    public Sinkhole(double startingX, double startingY) {
        super(Sinkhole.IMAGE, startingX, startingY);
        super.damage = 30;
    }

    @Override
    public void update(Input input, List<Entity> entities) {
        super.draw();
    }
}
