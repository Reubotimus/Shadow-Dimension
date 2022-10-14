import bagel.*;
import bagel.util.*;

import java.util.List;
import java.util.Random;

public abstract class Enemy extends Entity {
    protected double attackRadius;
    protected State state = new State(16, 3000, 0);
    protected boolean isAggressive;
    protected HealthBar healthBar;
    enum Direction {
        NORTH, EAST, SOUTH, WEST
    }
    protected Direction direction;
    private final Image invincibleLeftIm;
    private final Image invincibleRightIm;
    private final Image leftIm;
    private final Image rightIm;
    private Fire fire = null;
    public Enemy(Image startingImage, double startingX, double startingY, double attackRadius, State state, boolean isAggressive, HealthBar healthBar,
            Image invincibleLeftIm,
            Image invincibleRightIm,
            Image leftIm,
            Image rightIm) {
        super(startingImage, startingX, startingY);
        this.attackRadius = attackRadius;
        this.state = state;
        this.isAggressive = isAggressive;
        this.healthBar = healthBar;
        this.invincibleLeftIm = invincibleLeftIm;
        this.invincibleRightIm = invincibleRightIm;
        this.leftIm = leftIm;
        this.rightIm = rightIm;
        this.direction = Direction.values()[new Random().nextInt(Direction.values().length)];
        if (this.direction == Direction.EAST) {
            super.displayedImage = this.rightIm;
        }

    }

    @Override
    public void translate(double x, double y) {
        super.translate(x, y);
        if (this.fire != null) {
            this.fire.changeQuadrant(super.rectangle, this.fire.getQuadrant());
        }
    }

    public void shootFire(Player player, List<Entity> entities) {
        assert player.getRectangle().centre().distanceTo(super.rectangle.centre()) < this.attackRadius;
        Point point = player.rectangle.centre().asVector().sub(this.rectangle.centre().asVector()).normalised().asPoint();
        Fire.Quadrant quadrant;
        if (point.x > 0 && point.y >= 0) {
            quadrant =  Fire.Quadrant.SE;
        } else if (point.x <= 0 && point.y >= 0) {
            quadrant =  Fire.Quadrant.SW;
        } else if (point.x <= 0 && point.y < 0) {
            quadrant =  Fire.Quadrant.NW;
        } else {
            quadrant =  Fire.Quadrant.NE;
        }
        if (this.fire == null) {
            boolean isDemon = this instanceof Demon;
            Fire newFire = new Fire(super.rectangle, isDemon, quadrant);
            this.state.attack();
            entities.add(newFire);
            this.fire = newFire;
        } else {
            this.fire.changeQuadrant(super.rectangle, quadrant);
        }

    }

    protected abstract void printDamage(Entity entity);

    private void move(List<Entity> entities) {
        for (Entity entity : entities) {
            if ((super.rectangle.left() < Entity.topLeft.x ||
                        super.rectangle.right() > Entity.bottomRight.x ||
                        super.rectangle.top() < Entity.topLeft.y ||
                        super.rectangle.bottom() > Entity.bottomRight.y) ||
                    ((entity instanceof Sinkhole || entity instanceof Tree) &&
                            super.rectangle.intersects(entity.rectangle))) {

                switch (this.direction) {
                    case NORTH -> {
                        this.direction = Enemy.Direction.SOUTH;
                        if (this.state.isInvincible() &&
                                (super.displayedImage == this.invincibleLeftIm || super.displayedImage == this.leftIm)) {
                            super.displayedImage = this.invincibleLeftIm;
                        } else if (this.state.isInvincible()) {
                            super.displayedImage = this.invincibleRightIm;
                        } else if (super.displayedImage == this.invincibleLeftIm || super.displayedImage == this.leftIm) {
                            super.displayedImage = this.leftIm;
                        } else {
                            super.displayedImage = this.rightIm;
                        }
                    }
                    case EAST -> {
                        this.direction = Enemy.Direction.WEST;
                        if (this.state.isInvincible()) {
                            super.displayedImage = this.invincibleLeftIm;
                        } else {
                            super.displayedImage = this.leftIm;
                        }
                    }
                    case SOUTH -> {
                        this.direction = Enemy.Direction.NORTH;
                        if (this.state.isInvincible() &&
                                (super.displayedImage == this.invincibleLeftIm || super.displayedImage == this.leftIm)) {
                            super.displayedImage = this.invincibleLeftIm;
                        } else if (this.state.isInvincible()) {
                            super.displayedImage = this.invincibleRightIm;
                        } else if (super.displayedImage == this.invincibleLeftIm || super.displayedImage == this.leftIm) {
                            super.displayedImage = this.leftIm;
                        } else {
                            super.displayedImage = this.rightIm;
                        }
                    }
                    case WEST -> {
                        this.direction = Enemy.Direction.EAST;
                        if (this.state.isInvincible()) {
                            super.displayedImage = this.invincibleRightIm;
                        } else {
                            super.displayedImage = this.rightIm;
                        }
                    }
                }
            }
        }
        switch (this.direction) {
            case NORTH -> {
                super.translate(0, -(Math.pow(1.5, Entity.getTimescale())) * super.speed);
            }
            case EAST -> {
                super.translate((Math.pow(1.5, Entity.getTimescale())) * super.speed, 0);
            }
            case SOUTH -> {
                super.translate(0, (Math.pow(1.5, Entity.getTimescale())) * super.speed);
            }
            case WEST -> {
                super.translate(-(Math.pow(1.5, Entity.getTimescale())) * super.speed, 0);
            }
        }
    }

    private void interactWithPlayer(List<Entity> entities) {
        if (entities.get(0) instanceof Player player) {
            if (super.rectangle.centre().distanceTo(player.rectangle.centre()) < this.attackRadius) {

                shootFire(player, entities);

                if (player.state.isAttacking() && !this.state.isInvincible() &&
                        super.rectangle.intersects(player.rectangle)) {
                    this.healthBar.removeHealth(player.getDamage());
                    this.state.invincible();
                    if (this.healthBar.getHealth() == 0) {
                        entities.remove(this);
                        entities.remove(this.fire);
                    }
                    switch (this.direction) {
                        case EAST -> super.displayedImage = this.invincibleRightIm;
                        case WEST -> super.displayedImage = this.invincibleLeftIm;
                        case NORTH, SOUTH -> {
                            if (super.displayedImage == this.leftIm) {
                                super.displayedImage = this.invincibleLeftIm;
                            }
                            if (super.displayedImage == this.rightIm) {
                                super.displayedImage = this.invincibleRightIm;
                            }
                        }
                    }
                    printDamage(player);
                }
            } else if (this.fire != null) {
                this.fire.destroy(entities);
                this.fire = null;
            }
        }
    }

    @Override
    public void update(Input input, List<Entity> entities) {
        this.state.update();
        this.interactWithPlayer(entities);
        move(entities);
        this.healthBar.setPosition(super.rectangle.left(), super.rectangle.top() - 6);
        this.healthBar.update(input, entities);
        super.draw();
    }
}
