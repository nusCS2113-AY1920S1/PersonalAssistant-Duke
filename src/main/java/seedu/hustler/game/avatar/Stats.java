package seedu.hustler.game.avatar;

import seedu.hustler.game.shop.items.armors.Armor;
import seedu.hustler.game.shop.items.weapons.Weapon;
import java.util.Optional;

/**
 * The stats component which determine the strength of the avatar.
 */
public class Stats implements Convertible {

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
    public Stats() {
        this.damage = 1;
        this.defence = 1;
        this.stamina = 3;
        this.speed = 1;
    }

    /**
     * Construct Stats with the given damage, defence, stamina
     * and speed.
     * @param damage the damage of the avatar.
     * @param defence the defence of the avatar.
     * @param stamina the stamina of the avatar.
     * @param speed the speed of the avatar.
     */
    public Stats(int damage, int defence, int stamina, int speed) {
        this.damage = damage;
        this.defence = defence;
        this.stamina = stamina;
        this.speed = speed;
    }

    /**
     * Increases the stats based on the current level.
     * @param level the current level of the avatar.
     * @return the updated stats after increment.
     */
    public Stats upStats(int level) {
        int newDamage = this.damage + (level % 2 == 0 ? 2 : 1);
        int newDefence = this.defence + (level % 3 == 0 ? 2 : 1);
        int newStamina = this.stamina + 2;
        int newSpeed = this.speed + 1;
        return new Stats(newDamage, newDefence, newStamina, newSpeed);
    }

    /**
     * Gets the damage.
     * @return the damage stat value.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Gets the defence.
     * @reutrn the defence stat value.
     */
    public int getDefence() {
        return this.defence;
    }

    /**
     * Gets the speed.
     * @return the speed stat value.
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Gets the stamina.
     * @return the stamina stat value.
     */
    public int getStamina() {
        return this.stamina;
    }

    /**
     * Obtain the stats to print out.
     * @param weapon the weapon equipped, if any.
     * @param armor the armor equipped, if any.
     * @return the String consisting of the stats.
     */
    public String getStats(Optional<Weapon> weapon, Optional<Armor> armor) {
        return "\tDamage: "  + (this.damage + (weapon.map(value -> + value.getDamageIncr()).orElse(0))) + "\n"
            + "\tDefence: " + (this.defence + (armor.map(value -> + value.getDefenceIncr()).orElse(0))) + "\n"
            + "\tStamina: " + (this.stamina + (armor.map(value -> + value.getStaminaIncr()).orElse(0))) + "\n"
            + "\tSpeed: " + this.speed;
    }

    @Override
    public String toTxt() {
        return this.damage + " " + this.defence + " "
            + this.stamina + " " + this.speed;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof Stats
                && this.getDamage() == ((Stats) obj).getDamage()
                && this.getDefence() == ((Stats) obj).getDefence()
                && this.getSpeed() == ((Stats) obj).getSpeed()
                && this.getStamina() == ((Stats) obj).getStamina());
    }
}
