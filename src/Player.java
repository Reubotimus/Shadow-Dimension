import bagel.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * player class representing player game entity
 */
public class Player extends Entity {
    private static final Image LEFT_IDLE_IM = new Image("res/fae/faeLeft.png");
    private static final Image RIGHT_IDLE_IM = new Image("res/fae/faeRight.png");
    private static final Image LEFT_ATTACK_IM = new Image("res/fae/faeAttackLeft.png");
    private static final Image RIGHT_ATTACK_IM = new Image("res/fae/faeAttackRight.png");
    State state = new State(1000, 3000, 2000);
    private boolean movingLeft = true;


    private final HealthBar healthBar = new HealthBar(100, 30, 20, 25);


    public Player(double startingX, double startingY) {
        super(Player.LEFT_ATTACK_IM, startingX, startingY);
        super.speed = 2;
        super.damage = 20;
    }

    /**
     * returns a list of booleans describing what sides the player is colliding with other objects
     * the order of
     */
    boolean[] detectCollisions(ArrayList<Entity> entities) {
        boolean[] collisionArray = new boolean[4];
        final int padding = (int) super.speed + 1;
        Entity currentSprite;
        int i = 0, n = entities.size();
        while(i < n) {
            currentSprite = entities.get(i);
            if (currentSprite instanceof Sinkhole
                    && this.getRectangle().intersects(currentSprite.getRectangle())) {
                this.healthBar.removeHealth(currentSprite.damage);
                entities.remove(i);
                n--;
                System.out.println("Sinkhole inflicts " +
                        currentSprite.damage +
                        " damage points on Fae. Fae’s current health: " + 
                        this.healthBar.getHealth() + "/" + 
                        this.healthBar.getMAX_HEALTH());
                continue;
            }
            i++;

            if (!this.getRectangle().intersects(currentSprite.getRectangle())) continue;

            if (currentSprite instanceof Fire && !this.state.isInvincible()) {
                this.healthBar.removeHealth(currentSprite.getDamage());
                this.state.invincible();
                if (((Fire) currentSprite).isDemon()) {
                    System.out.println("Demon inflicts " +
                            currentSprite.damage +
                            " damage points on Fae. Fae’s current health: " +
                            this.healthBar.getHealth() + "/" +
                            this.healthBar.getMAX_HEALTH());
                } else {
                    System.out.println("Navec inflicts " +
                            currentSprite.damage +
                            " damage points on Fae. Fae’s current health: " +
                            this.healthBar.getHealth() + "/" +
                            this.healthBar.getMAX_HEALTH());
                }

                continue;
            }

            if (currentSprite instanceof Wall || currentSprite instanceof Tree) {
                // checks collision on the left
                if (currentSprite.getRectangle().right() > this.getRectangle().left() - super.speed - padding
                        && currentSprite.getRectangle().right() < this.getRectangle().left() + super.speed + padding) {
                    collisionArray[0] = true;
                    continue;
                }
                // checks for collision on the right
                if (currentSprite.getRectangle().left() > this.getRectangle().right() - super.speed - padding
                        && currentSprite.getRectangle().left() < this.getRectangle().right() + super.speed + padding) {
                    collisionArray[1] = true;
                    continue;
                }
                // checks for collision on the top
                if (currentSprite.getRectangle().bottom() > this.getRectangle().top() - super.speed - padding
                        && currentSprite.getRectangle().bottom() < this.getRectangle().top() + super.speed + padding) {
                    collisionArray[2] = true;
                    continue;
                }
                // checks for collision on the bottom
                if (currentSprite.getRectangle().top() > this.getRectangle().bottom() - super.speed - padding
                        && currentSprite.getRectangle().top() < this.getRectangle().bottom() + super.speed + padding) {
                    collisionArray[3] = true;
                }
            }
        }
        return collisionArray;
    }


    /**
     * detects all collisions and moves player according to input
     * @param input keyboard input for the frame
     * @param entities all other game entities
     */
    public void update(Input input, List<Entity> entities) {
        state.update();
        double x = 0;
        double y = 0;

        boolean[] collisionArray = detectCollisions((ArrayList<Entity>) entities);
        boolean collidingLeft = collisionArray[0];
        boolean collidingRight = collisionArray[1];
        boolean collidingUp = collisionArray[2];
        boolean collidingDown = collisionArray[3];

        if (this.rectangle.left() > Entity.topLeft.x && !collidingLeft && input.isDown(Keys.LEFT)) {
            this.movingLeft = true;
            x -= super.speed;
        } if (this.rectangle.left() < Entity.bottomRight.x && !collidingRight && input.isDown(Keys.RIGHT)) {
            this.movingLeft = false;
            x += super.speed;
        } if (this.rectangle.top() > Entity.topLeft.y && !collidingUp && input.isDown(Keys.UP)) {
            y -= super.speed;
        } if (this.rectangle.top() < Entity.bottomRight.y && !collidingDown && input.isDown(Keys.DOWN)) {
            y += super.speed;
        } if (input.isDown(Keys.A) ) {
            state.attack();
        }
        translate(x, y);

        if (state.isAttacking() && this.movingLeft) {
            super.displayedImage = Player.LEFT_ATTACK_IM;
        } else if (state.isAttacking() && !this.movingLeft) {
            super.displayedImage = Player.RIGHT_ATTACK_IM;
        } else if (!state.isAttacking() && this.movingLeft) {
            super.displayedImage = Player.LEFT_IDLE_IM;
        } else if (!state.isAttacking() && !this.movingLeft) {
            super.displayedImage = Player.RIGHT_IDLE_IM;
        }

        super.draw();
        this.healthBar.update(input, entities);
    }

    /**
     * @return the healthbar of the player, represents the players health
     */
    public HealthBar getHealthBar() {
        return healthBar;
    }

}