package seedu.hustler.game.avatar;

import seedu.hustler.game.shop.items.ShopItem;
import java.util.Optional;

/**
 * The stats component which determine the strength of the avatar.
 * @see Stats for documentation on the functions.
 */
public class AvatarStats implements Stats {

    /**
     * Stat that deals damage.
     */
    private final int damage;

    /**
     * Stat that blocks damage.
     */
    private final int defence;

    /**
     * Stat that represents stamina.
     */
    private final int stamina;

    /**
     * Stat that represents speed.
     */
    private final int speed;

    /**
     * Constructs the default Stats values.
     */
    public AvatarStats() {
        this.damage = 1;
        this.defence = 1;
        this.stamina = 3;
        this.speed = 1;
    }

    /**
     * Constructs a new stats with the same values as another stat.
     * @param other the other stat.
     */
    public AvatarStats(AvatarStats other) {
        this.damage = other.damage;
        this.defence = other.defence;
        this.stamina = other.stamina;
        this.speed = other.speed;
    }

    /**
     * Construct Stats with the given damage, defence, stamina
     * and speed.
     * @param damage the damage of the avatar.
     * @param defence the defence of the avatar.
     * @param stamina the stamina of the avatar.
     * @param speed the speed of the avatar.
     */
    public AvatarStats(int damage, int defence, int stamina, int speed) {
        this.damage = damage;
        this.defence = defence;
        this.stamina = stamina;
        this.speed = speed;
    }

    @Override
    public AvatarStats upStats(int level) {
        int newDamage = this.damage + (level % 2 == 0 ? 2 : 1);
        int newDefence = this.defence + (level % 3 == 0 ? 2 : 1);
        int newStamina = this.stamina + 2;
        int newSpeed = this.speed + 1;
        return new AvatarStats(newDamage, newDefence, newStamina, newSpeed);
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public int getDefence() {
        return this.defence;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public int getStamina() {
        return this.stamina;
    }

    @Override
    public String getStats(Optional<ShopItem> weapon, Optional<ShopItem> armor) {
        return "Damage: "  + (this.damage + (weapon.map(value -> + value.getDamageIncr()).orElse(0))) + "\n"
            + "Defence: " + (this.defence + (armor.map(value -> + value.getDefenceIncr()).orElse(0))) + "\n"
            + "Stamina: " + (this.stamina + (armor.map(value -> + value.getStaminaIncr()).orElse(0))) + "\n"
            + "Speed: " + this.speed;
    }

    @Override
    public String toTxt() {
        return this.damage + " " + this.defence + " "
            + this.stamina + " " + this.speed;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof AvatarStats
                && this.getDamage() == ((AvatarStats) obj).getDamage()
                && this.getDefence() == ((AvatarStats) obj).getDefence()
                && this.getSpeed() == ((AvatarStats) obj).getSpeed()
                && this.getStamina() == ((AvatarStats) obj).getStamina());
    }
}
