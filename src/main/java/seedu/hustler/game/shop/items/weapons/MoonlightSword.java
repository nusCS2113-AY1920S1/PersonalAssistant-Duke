package seedu.hustler.game.shop.items.weapons;

/**
 * The highest tiered Weapon in the shop.
 */
public class MoonlightSword extends Weapon {

    /**
     * The name of the weapon.
     */
    private static final String NAME = "Moonlight Sword";

    /**
     * The damage increment of the Moonlight Sword.
     */
    private static final int DMG_INCR = 10;

    /**
     * The cost of the Moonlight Sword.
     */
    private static final int COST = 20;

    /**
     * Constructs a Moonlight Sword with it's default variables.
     */
    public MoonlightSword(boolean isPurchased) {
        super(COST, isPurchased, DMG_INCR, NAME);
    }

    @Override
    public MoonlightSword setIsPurchased(boolean isPurchased) {
        return new MoonlightSword(isPurchased);
    }

    @Override
    public String toString() {
        return "Moonlight Sword, " + super.toString();
    }
}
