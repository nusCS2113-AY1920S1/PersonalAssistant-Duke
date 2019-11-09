package seedu.hustler.game.shop.items.armors;

import seedu.hustler.game.shop.items.ShopItem;

/**
 * The highest tiered armor in the shop.
 */
public class Chainmail extends Armor {

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
    public Chainmail() {
        super(COST, false, DEF_INCR, STA_INCR);
    }

    @Override
    public String toString() {
        return "Chainmail, " + super.toString();
    }
}