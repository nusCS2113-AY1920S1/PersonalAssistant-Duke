package seedu.hustler.game.shop.items.armors;

/**
 * The second tiered armor in the shop.
 */
public class IronArmor extends Armor {

    /**
     * The name of the armor.
     */
    private static final String NAME = "Iron Armor";

    /**
     * The defence increment of the iron armor.
     */
    private static final int DEF_INCR = 3;

    /**
     * The stamina increment of the iron armor.
     */
    private static final int STA_INCR = 3;

    /**
     * The cost of the iron armor.
     */
    private static final int COST = 10;

    /**
     * Constructs a new iron armor with its default variables.
     */
    public IronArmor(boolean isPurchased) {
        super(COST, isPurchased, DEF_INCR, STA_INCR, NAME);
    }

    @Override
    public IronArmor setIsPurchased(boolean isPurchased) {
        return new IronArmor(isPurchased);
    }
}
