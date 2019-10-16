package seedu.hustler.game.shop.items.weapons;

/**
 * The cheapest weapon in the shop.
 */
public class Broadsword extends Weapon {

    private static final int DMG_INCR = 2;
    private static final int COST = 3;

    public Broadsword() {
        super(COST, false, DMG_INCR);
    }

    public Broadsword(boolean hasPurchased) {
        super(COST, hasPurchased, DMG_INCR);
    }

    public int attack(int damage) {
        return this.DMG_INCR + damage;
    }

    @Override
    public String toString() {
        return "Broadsword, " + super.toString();
    }
}
