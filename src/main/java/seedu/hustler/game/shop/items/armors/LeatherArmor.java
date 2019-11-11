package seedu.hustler.game.shop.items.armors;

/**
 * The cheapest armor in the shop.
 */
public class LeatherArmor extends Armor {

    /**
     * The name of the armor.
     */
    private static final String NAME = "Leather Armor";

    /**
     * The defence increment of the leather armor.
     */
    private static final int DEF_INCR = 1;

    /**
     * The stamina increment of the leather armor.
     */
    private static final int STA_INCR = 3;

    /**
     * The cost of the leather armor.
     */
    private static final int COST = 5;

    /**
     * Constructs a new leather armor with its default variables.
     */
    public LeatherArmor(boolean isPurchased) {
        super(COST, isPurchased, DEF_INCR, STA_INCR, NAME);
    }

    @Override
    public LeatherArmor setIsPurchased(boolean isPurchased) {
        return new LeatherArmor(isPurchased);
    }
}
