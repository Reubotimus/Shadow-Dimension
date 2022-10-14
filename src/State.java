public class State {
    private boolean isAttacking = false;
    private boolean isInvincible = false;
    private long timeLastAttack = 0L;
    private long timeLastInvincible = 0L;
    private final int attackDuration;
    private final int invincibilityDuration;
    private final int attackCooldown;

    public State(int attackDuration, int invincibilityDuration, int attackCooldown) {
        this.attackDuration = attackDuration;
        this.invincibilityDuration = invincibilityDuration;
        this.attackCooldown = attackCooldown;
    }

    public void attack() {
        if (System.currentTimeMillis() - this.timeLastAttack >
                this.attackDuration + this.attackCooldown) {
            this.isAttacking = true;
            this.timeLastAttack = System.currentTimeMillis();
        }
    }

    public void invincible() {
        this.isInvincible = true;
        this.timeLastInvincible = System.currentTimeMillis();
    }

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
