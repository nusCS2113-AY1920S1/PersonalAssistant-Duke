package seedu.hustler.game.shop.items.weapons;

/**
 * The second tiered weapon in the shop.
 */
public class Mace extends Weapon {

    /**
     * The name of the weapon.
     */
    private static final String NAME = "Mace";

    /**
     * The damage increment of the Mace.
     */
    private static final int DMG_INCR = 4;

    /**
     * The cost of the Mace.
     */
    private static final int COST = 7;

    /**
     * Constructs a Mace with it's default variables.
     */
    public Mace(boolean isPurchased) {
        super(COST, isPurchased, DMG_INCR, NAME);
    }

    @Override
    public Mace setIsPurchased(boolean isPurchased) {
        return new Mace(isPurchased);
    }

    @Override
    public String toString() {
        return "Mace, " + super.toString();
    }
}

