package seedu.hustler.game.shop.items.weapons;

/**
 * The lowest tiered weapon in the shop.
 */
public class Broadsword extends Weapon {

    /**
     * The name of the weapon.
     */
    private static final String NAME = "Broadsword";

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
    public Broadsword(boolean isPurchased) {
        super(COST, isPurchased, DMG_INCR, NAME);
    }

    @Override
    public Broadsword setIsPurchased(boolean isPurchased) {
        return new Broadsword(isPurchased);
    }

    @Override
    public String toString() {
        return "Broadsword, " + super.toString();
    }
}
