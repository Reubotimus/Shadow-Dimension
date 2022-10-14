import bagel.*;

import java.util.List;

/**
 * used to represent and print the health of an entity
 */
public class HealthBar extends Entity{
    private final int MAX_HEALTH;
    private int health;
    private final Font font;
    private double x;
    private double y;

    /**
     * creates a new healthbar
     * @param MAX_HEALTH the maximum and starting health of the entity
     * @param fontSize the font size that the health is displayed with
     * @param x the x position of the healthbar
     * @param y the y position of the healthbar
     */
    public HealthBar(int MAX_HEALTH, int fontSize, double x, double y) {
        super();
        this.MAX_HEALTH = MAX_HEALTH;
        this.health = MAX_HEALTH;
        this.font = new Font("res/frostbite.ttf", fontSize);
        this.x = x;
        this.y = y;
    }

    /**
     * removes health from the entity
     * @param amount the amount that is removed
     */
    public void removeHealth(int amount) {
        this.health -= amount;
    }

    /**
     * draws the health as a percentage with different colours based on its value
     */
    @Override
    public void draw() {
        // displays health bar
        int percentage = (int)Math.round((this.health * 100) / (double)this.MAX_HEALTH);
        DrawOptions options = new DrawOptions();
        if (percentage < 35) {
            options.setBlendColour(1, 0, 0);
            this.font.drawString(String.valueOf(percentage) + "%", this.x, this.y, options);
            return;
        }
        if (percentage < 65) {
            options.setBlendColour(0.9, 0.6, 0);
            this.font.drawString(String.valueOf(percentage) + "%", this.x, this.y, options);
            return;
        }
        options.setBlendColour(0, 0.8, 0.2);
        this.font.drawString(String.valueOf(percentage) + "%", this.x, this.y, options);
        return;
    }

    /**
     * updates healthbar by drawing it
     * @param input the user input
     * @param entities all other entities within level
     */
    @Override
    public void update(Input input, List<Entity> entities) {
        draw();
    }

    /**
     * sets the healthbar's position based on absolute values
     * @param x the new x position
     * @param y the new y position
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the maximum health of the entity
     */
    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    /**
     * @return the current health of the entity
     */
    public int getHealth() {
        return health;
    }
}
