package seedu.hustler.game.shop.items.weapons;

/**
 * The cheapest weapon in the shop which gives the least damage.
 */
public class Broadsword extends Weapon {
    /**
     * The damage increment of Broadsword.
     */
    private static final int DMG_INCR = 2;

    /**
     * The cost of the Broadsword.
     */
    private static final int COST = 3;

    /**
     * Constructs a Broadsword with it's default variables.
     */
    public Broadsword() {
        super(COST, false, DMG_INCR);
    }

    /**
     * Constructs a new Broadsword with the given hasPurchased.
     * @param hasPurchased boolean if the Broadsword has been purchased.
     */
    public Broadsword(boolean hasPurchased) {
        super(COST, hasPurchased, DMG_INCR);
    }

    @Override
    public String toString() {
        return "Broadsword, " + super.toString();
    }
}
