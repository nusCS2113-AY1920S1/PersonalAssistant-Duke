package seedu.hustler.game.shop.items.armors;

import seedu.hustler.game.shop.items.ShopItem;
import java.util.Optional;

/**
 * The abstract class that all armor that the avatar yields inherits from.
 * @see ShopItem for the documentation of the functions.
 */
public abstract class Armor implements ShopItem {

    /**
     * The type in string, of the armor.
     */
    private static final String TYPE = "Armor";

    /**
     * The name of the armor.
     */
    private final String name;

    /**
     * The cost of the armor.
     */
    private final int cost;

    /**
     * Boolean if the armor has been purchased.
     */
    private final Boolean isPurchased;

    /**
     * The defence increment of the armor.
     */
    private final int defIncr;

    /**
     * The stamina increment of the armor.
     */
    private final int staIncr;

    /**
     * Constructs a new armor with the given cost, hasPurchased, defence increment
     * and stamina increment.
     * @param cost the cost of the armor.
     * @param hasPurchased the boolean value if armor has been purchased.
     * @param defIcr the defence increment of the armor.
     * @param staIcr the stamina increment of the armor.
     */
    public Armor(int cost, boolean hasPurchased, int defIcr, int staIcr, String name) {
        this.cost = cost;
        this.isPurchased = hasPurchased;
        this.defIncr = defIcr;
        this.staIncr = staIcr;
        this.name = name;
    }

    /**
     * Obtains the instance of an armor depending on the string given.
     * @param name the name of the armor.
     * @return the armor with the given name, if any.
     */
    public static Optional<ShopItem> getArmor(String name) {
        if (name.contains("Chainmail")) {
            return Optional.of(new Chainmail(false));
        } else if (name.contains("Iron")) {
            return Optional.of(new IronArmor(false));
        } else if (name.contains("Leather")) {
            return Optional.of(new LeatherArmor(false));
        }
        return Optional.empty();
    }

    /**
     * Gets the damage increment of the armor.
     * @implNote All types of weapons currently do not implement damage increment.
     *     and hence this function should not be used.
     * @return the damage increment of the armor.
     */
    public int getDamageIncr() {
        return 0;
    }

    /**
     * Gets the defence increment of the armor.
     * @return the defence increment of the armor.
     */
    public int getDefenceIncr() {
        return defIncr;
    }

    /**
     * Gets the stamina increment of the armor.
     * @return the stamina increment of the armor.
     */
    public int getStaminaIncr() {
        return staIncr;
    }

    @Override
    public String toString() {
        return this.name + ", " + "+" + defIncr + " DEF, +" + staIncr + " STA ";
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
