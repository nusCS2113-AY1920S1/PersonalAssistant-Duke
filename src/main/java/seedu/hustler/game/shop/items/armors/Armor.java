package seedu.hustler.game.shop.items.armors;

import seedu.hustler.game.shop.items.ShopItem;
import java.util.Optional;

/**
 * The abstract class that all armor that the avatar yields inherits from.
 */
public abstract class Armor implements ShopItem {

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
     * The defence increment of the armor.
     */
    private final int DEF_INCR;

    /**
     * The stamina increment of the armor.
     */
    private final int STA_INCR;

    /**
     * Constructs a new armor with the given cost, hasPurchased, defence increment
     * and stamina increment.
     * @param cost the cost of the armor.
     * @param hasPurchased the boolean value if armor has been purchased.
     * @param defIcr the defence increment of the armor.
     * @param staIcr the stamina increment of the armor.
     */
    public Armor(int cost, boolean hasPurchased, int defIcr, int staIcr) {
        this.cost = cost;
        this.isPurchased = hasPurchased;
        this.type = "Armor";
        this.DEF_INCR = defIcr;
        this.STA_INCR = staIcr;
    }

    /**
     * Obtains the instance of an armor depending on the string given.
     * @param name the name of the armor.
     * @return the armor with the given name, if any.
     */
    public static Optional<Armor> getArmor(String name) {
        if (name.contains("Chainmail")) {
            return Optional.of(new Chainmail());
        } else if (name.contains("Iron")) {
            return Optional.of(new IronArmor());
        } else if (name.contains("Leather")) {
            return Optional.of(new LeatherArmor());
        }
        return Optional.empty();
    }

    /**
     * Gets the defence increment of the armor.
     * @return the defence increment of the armor.
     */
    public int getDefenceIncr() {
        return DEF_INCR;
    }

    /**
     * Gets the stamina increment of the armor.
     * @return the stamina increment of the armor.
     */
    public int getStaminaIncr() {
        return STA_INCR;
    }

    @Override
    public String toString() {
        return "+" + DEF_INCR + " DEF, +" + STA_INCR + " STA ";
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
