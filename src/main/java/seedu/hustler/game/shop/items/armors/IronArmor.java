package seedu.hustler.game.shop.items.armors;

/**
 * The second tiered armor in the shop.
 */
public class IronArmor extends Armor {

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
    public IronArmor() {
        super(COST, false, DEF_INCR, STA_INCR);
    }

    /**
     * Constructs a new iron armor with the boolean value of hasPurchased.
     * @param hasPurchased the boolean value if the iron armor has been purchased.
     */
    public IronArmor(boolean hasPurchased) {
        super(COST, hasPurchased, DEF_INCR, STA_INCR);
    }

    @Override
    public String toString() {
        return "Iron Armor, " + super.toString();
    }
}
