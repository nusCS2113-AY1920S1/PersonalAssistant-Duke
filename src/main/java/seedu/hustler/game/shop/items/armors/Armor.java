package seedu.hustler.game.shop.items.armors;

import seedu.hustler.game.shop.items.ShopItem;

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
        return "+" + DEF_INCR + " DEF, +" + STA_INCR + " STA " + super.toString();
    }
}
