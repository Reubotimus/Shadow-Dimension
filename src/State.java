/**
 * Represents the state of an entity, handling the times the entity is invincible and
 * able to attack
 */
public class State {
    private boolean isAttacking = false;
    private boolean isInvincible = false;
    private long timeLastAttack = 0L;
    private long timeLastInvincible = 0L;
    private final int attackDuration;
    private final int invincibilityDuration;
    private final int attackCooldown;

    /**
     * creates a new state for an entity
     * @param attackDuration the amount of time the entity will attack for
     * @param invincibilityDuration the amount of time an entity will be invincible for
     * @param attackCooldown the cooldown time between attacks
     */
    public State(int attackDuration, int invincibilityDuration, int attackCooldown) {
        this.attackDuration = attackDuration;
        this.invincibilityDuration = invincibilityDuration;
        this.attackCooldown = attackCooldown;
    }

    /**
     * changes the state of the entity such that it is attacking
     */
    public void attack() {
        if (System.currentTimeMillis() - this.timeLastAttack >
                this.attackDuration + this.attackCooldown) {
            this.isAttacking = true;
            this.timeLastAttack = System.currentTimeMillis();
        }
    }
    /**
     * changes the state of the entity such that it is invincible
     */
    public void invincible() {
        this.isInvincible = true;
        this.timeLastInvincible = System.currentTimeMillis();
    }

    /**
     * updates the state of the entity checking if the attacking and invincibility period are finished
     */
    public void update() {
        if (this.isInvincible &&
                System.currentTimeMillis() - this.timeLastInvincible >
                this.invincibilityDuration) {
            this.isInvincible = false;
        }
        if (this.isAttacking &&
                System.currentTimeMillis() - this.timeLastAttack >
                        this.attackDuration) {
            this.isAttacking = false;
        }
    }

    public boolean isAttacking() {
        return isAttacking;
    }
    public boolean isInvincible() {
        return isInvincible;
    }
}
