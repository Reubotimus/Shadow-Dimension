import bagel.*;

import java.util.List;

public class HealthBar extends Entity{
    private final int MAX_HEALTH;
    private int health;
    private final Font font;
    private double x;
    private double y;

    public HealthBar(int MAX_HEALTH, int fontSize, double x, double y) {
        super();
        this.MAX_HEALTH = MAX_HEALTH;
        this.health = MAX_HEALTH;
        this.font = new Font("res/frostbite.ttf", fontSize);
        this.x = x;
        this.y = y;
    }

    public void removeHealth(int amount) {
        this.health -= amount;
    }

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

    @Override
    public void update(Input input, List<Entity> entities) {
        draw();
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public int getHealth() {
        return health;
    }
}
