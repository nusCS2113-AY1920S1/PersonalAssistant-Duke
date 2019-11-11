package seedu.hustler.game.shop.items.weapons;

import seedu.hustler.game.shop.items.ShopItem;
import java.util.Optional;

/**
 * Abstract class of weapon that all weapons the avatar yields inherits from.
 * @see ShopItem for the documentation of the functions.
 */
public abstract class Weapon implements ShopItem {

    /**
     * The type in string, of the weapon.
     */
    protected static final String TYPE = "Weapon";

    /**
     * The name of the weapon.
     */
    private final String name;

    /**
     * The cost of the shop item.
     */
    protected int cost;
    /**
     * Boolean if the shop item has been purchased.
     */

    protected Boolean isPurchased;

    /**
     * The damage increment of the weapon.
     */
    private final int dmgIncr;

    /**
     * Constructs a weapon with the cost, hasPurchased, and the damage increment.
     * @param cost the cost of the weapon.
     * @param hasPurchased the boolean value if the weapon has been purchased; false if otherwise.
     * @param dmgIncr the int of the damage increment.
     */
    public Weapon(int cost, boolean hasPurchased, int dmgIncr, String name) {
        this.cost = cost;
        this.isPurchased = hasPurchased;
        this.dmgIncr = dmgIncr;
        this.name = name;
    }

    /**
     * Obtains the instance of an weapon depending on the string given.
     * @param name the name of the weapon.
     * @return the weapon with the given name, if any.
     */
    public static Optional<ShopItem> getWeapon(String name) {
        if (name.contains("Broadsword")) {
            return Optional.of(new Broadsword(false));
        } else if (name.contains("Mace")) {
            return Optional.of(new Mace(false));
        } else if (name.contains("Moonlight")) {
            return Optional.of(new MoonlightSword(false));
        }
        return Optional.empty();
    }

    /**
     * Gets the damage increment of the weapon.
     * @return the damage of the weapon.
     */
    public int getDamageIncr() {
        return this.dmgIncr;
    }

    /**
     * Gets the defence increment of the weapon.
     * @implNote All types of weapons currently do not implement damage increment.
     *     and hence this function should not be used.
     * @return the damage increment of the armor.
     */
    public int getDefenceIncr() {
        return 0;
    }

    /**
     * Gets the defence increment of the weapon.
     * @implNote All types of weapons currently do not implement damage increment.
     *     and hence this function should not be used.
     * @return the damage increment of the armor.
     */
    public int getStaminaIncr() {
        return 0;
    }

    @Override
    public String toString() {
        return "+" + this.dmgIncr + " DMG ";
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
        return TYPE;
    }

    @Override
    public Boolean isSameType(ShopItem other) {
        return TYPE.equals(other.getType());
    }

    @Override
    public boolean canPurchase(int points) {
        return this.cost <= points;
    }
}
