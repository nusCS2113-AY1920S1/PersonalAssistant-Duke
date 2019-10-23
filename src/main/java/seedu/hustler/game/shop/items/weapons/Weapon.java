package seedu.hustler.game.shop.items.weapons;

import seedu.hustler.game.shop.items.ShopItem;

import java.util.Optional;

/**
 * Abstract class of weapon that all weapons the avatar yields inherits from.
 */
public abstract class Weapon extends ShopItem {
    /**
     * The damage increment of the weapon.
     */
    private final int DMG_INCR;

    /**
     * Constructs a weapon with the cost, hasPurchased, and the damage increment.
     * @param cost the cost of the weapon.
     * @param hasPurchased the boolean value if the weapon has been purchased; false if otherwise.
     * @param dmgIncr the int of the damage increment.
     */
    public Weapon(int cost, boolean hasPurchased, int dmgIncr) {
        super(cost, hasPurchased, "Weapon");
        this.DMG_INCR = dmgIncr;
    }

    public static Optional<Weapon> getWeapon(String name) {
        if (name.contains("Broadsword")) {
            return Optional.of(new Broadsword());
        } else if (name.contains("Mace")) {
            return Optional.of(new Mace());
        } else if (name.contains("Moonlight")) {
            return Optional.of(new MoonlightSword());
        }
        return Optional.empty();
    }

    /**
     * Gets the damage increment of the weapon.
     * @return the damage of the weapon.
     */
    public int getDamage() {
        return this.DMG_INCR;
    }

    @Override
    public String toString() {
        return "+" + this.DMG_INCR + " DMG ";
    }
}
