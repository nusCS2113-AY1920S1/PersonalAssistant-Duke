package seedu.hustler.game.shop.items.weapons;

/**
 * The cheapest weapon in the shop.
 */
public class MoonlightSword extends Weapon {

    private static final int DMG_INCR = 10;
    private static final int COST = 20;

    public MoonlightSword() {
        super(COST, false, DMG_INCR);
    }

    public MoonlightSword(boolean hasPurchased) {
        super(COST, hasPurchased, DMG_INCR);
    }

    public int attack(int damage) {
        return DMG_INCR + damage;
    }

    @Override
    public String toString() {
        return "Moonlight Sword, " + super.toString();
    }
}
