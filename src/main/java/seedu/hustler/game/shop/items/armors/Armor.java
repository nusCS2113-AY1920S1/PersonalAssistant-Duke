package seedu.hustler.game.shop.items.armors;

import seedu.hustler.game.shop.items.ShopItem;

import java.util.Optional;

/**
 * The abstract class that all armor that the avatar yields inherits from.
 */
public abstract class Armor extends ShopItem {
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
        super(cost, hasPurchased, "Armor");
        this.DEF_INCR = defIcr;
        this.STA_INCR = staIcr;
    }

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
    public int getDEF_INCR() {
        return DEF_INCR;
    }

    /**
     * Gets the stamina increment of the armor.
     * @return the stamina increment of the armor.
     */
    public int getSTA_INCR() {
        return STA_INCR;
    }

    @Override
    public String toString() {
        return "+" + DEF_INCR + " DEF, +" + STA_INCR + " STA ";
    }
}
