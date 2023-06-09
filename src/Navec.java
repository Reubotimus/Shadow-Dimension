import bagel.Image;
import bagel.Input;

import java.util.List;
import java.util.Random;

/**
 * Represents Navec entity in game
 */
public class Navec extends Enemy {
    private static final Image invincibleLeftIm = new Image("res/navec/navecInvincibleLeft.png");
    private static final Image invincibleRightIm = new Image("res/navec/navecInvincibleRight.png");
    private static final Image leftIm = new Image("res/navec/navecLeft.png");
    private static final Image rightIm = new Image("res/navec/navecRight.png");
    private static final Random rand = new Random();

    /**
     * creates a new Navec
     * @param startingX the starting x position of navec
     * @param startingY the starting y position of nave
     */
    public Navec(double startingX, double startingY) {
        super(Navec.leftIm, startingX, startingY, 150,
                new State(0, 3000, 0),
                true,
                new HealthBar(2 * 40, 15, startingX, startingY - 6),
                Navec.invincibleLeftIm, Navec.invincibleRightIm, Navec.leftIm, Navec.rightIm);
        this.direction = Direction.values()[rand.nextInt(Direction.values().length)];
        super.speed = 0.2 + (0.5 * rand.nextDouble());
        super.attackRadius = 200;
    }

    /**
     * prints the damage inflicted by an entity onto navec
     * @param entity the entity that inflicted damage on navec
     */
    @Override
    protected void printDamage(Entity entity) {
        System.out.println("Fae inflicts " +
                entity.damage +
                " damage points on Navec. Navec’s current health: " +
                super.healthBar.getHealth() + "/" +
                super.healthBar.getMAX_HEALTH());
    }

}
