package seedu.hustler.game.shop.items.weapons;

/**
 * The second tier weapon.
 */
public class Mace extends Weapon {
    /**
     * The damage increment of the Mace.
     */
    private static final int DMG_INCR = 4;

    /**
     * The cost of the Mace.
     */
    private static final int COST = 7;

    /**
     * Constructs a mace with its default variables.
     */
    public Mace() {
        super(COST, false, DMG_INCR);
    }

    /**
     * Constructs a mace with the given hasPurchased.
     * @param hasPurchased boolean if the mace has been purchased.
     */
    public Mace(boolean hasPurchased) {
        super(COST, hasPurchased, DMG_INCR);
    }

    @Override
    public String toString() {
        return "Mace, " + super.toString();
    }
}

