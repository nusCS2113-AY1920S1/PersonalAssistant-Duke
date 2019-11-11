package seedu.hustler.game.shop.items.armors;

/**
 * The highest tiered armor in the shop.
 */
public class Chainmail extends Armor {

    /**
     * The name of the armor.
     */
    private static final String NAME = "Chainmail";

    /**
     * The defence increment of the Chainmail.
     */
    private static final int DEF_INCR = 7;

    /**
     * The stamina increment of the Chainmail.
     */
    private static final int STA_INCR = 6;

    /**
     * The cost of the Chainmail.
     */
    private static final int COST = 15;

    /**
     * Constructs a new Chainmail with its default variables.
     */
    public Chainmail(boolean isPurchased) {
        super(COST, isPurchased, DEF_INCR, STA_INCR, NAME);
    }

    @Override
    public Chainmail setIsPurchased(boolean isPurchased) {
        return new Chainmail(isPurchased);
    }
}