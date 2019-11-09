package seedu.hustler.game.shop.items.weapons;

import seedu.hustler.game.shop.items.ShopItem;

import java.util.Optional;

/**
 * Abstract class of weapon that all weapons the avatar yields inherits from.
 */
public abstract class Weapon implements ShopItem {

    /**
     * The cost of the shop item.
     */
    protected int cost;
    /**
     * Boolean if the shop item has been purchased.
     */

    protected Boolean isPurchased;

    /**
     * The type in string, of each item.
     */
    protected String type;

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
        this.cost = cost;
        this.isPurchased = hasPurchased;
        this.type = "Weapon";
        this.DMG_INCR = dmgIncr;
    }

    /**
     * Obtains the instance of an weapon depending on the string given.
     * @param name the name of the weapon.
     * @return the weapon with the given name, if any.
     */
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
    public int getDamageIncr() {
        return this.DMG_INCR;
    }

    @Override
    public String toString() {
        return "+" + this.DMG_INCR + " DMG ";
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public Boolean isPurchased() {
        return this.isPurchased;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setPurchased(Boolean purchased) {
        this.isPurchased = purchased;
    }

    @Override
    public Boolean isSameType(ShopItem other) {
        return this.type.equals(other.getType());
    }

    @Override
    public boolean canPurchase(int points) {
        return this.cost <= points;
    }
}
