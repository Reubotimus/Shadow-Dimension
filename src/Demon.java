import bagel.Image;
import bagel.Input;

import java.util.List;
import java.util.Random;

public class Demon extends Enemy{
    private static final Image invincibleLeftIm = new Image("res/demon/demonInvincibleLeft.png");
    private static final Image invincibleRightIm = new Image("res/demon/demonInvincibleRight.png");
    private static final Image leftIm = new Image("res/demon/demonLeft.png");
    private static final Image rightIm = new Image("res/demon/demonRight.png");
    private static final Random rand = new Random();

    public Demon(double startingX, double startingY) {
        super(Demon.leftIm, startingX, startingY, 200,
                new State(0, 3000, 0),
                rand.nextBoolean(),
                new HealthBar(40, 15, startingX, startingY - 6),
                Demon.invincibleLeftIm, Demon.invincibleRightIm, Demon.leftIm, Demon.rightIm);
        super.speed = 0.2 + (0.5 * rand.nextDouble());
    }

}
