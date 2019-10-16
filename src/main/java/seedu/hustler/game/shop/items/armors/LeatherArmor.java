package seedu.hustler.game.shop.items.armors;

/**
 * The cheapest armor in the shop.
 */
public class LeatherArmor extends Armor {

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
    public LeatherArmor() {
        super(COST, false, DEF_INCR, STA_INCR);
    }

    /**
     * Constructs a new leather armor with a boolean hasPurchased.
     * @param hasPurchased the boolean value if leather armor has been purchased.
     */
    public LeatherArmor(boolean hasPurchased) {
        super(COST, hasPurchased, DEF_INCR, STA_INCR);
    }

    @Override
    public String toString() {
        return "Leather Armor, " + super.toString();
    }
}
