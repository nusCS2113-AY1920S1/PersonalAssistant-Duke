package seedu.hustler.game.shop.items.weapons;

import seedu.hustler.game.shop.items.Purchasable;
import seedu.hustler.game.shop.items.shopItem;

/**
 * Abstract interface of weapon that all weapons the avatar yield inherits from.
 */
public abstract class Weapon extends shopItem {

    private final int DMG_INCR;

    public Weapon(int cost, boolean hasPurchased, int dmgIncr) {
        super(cost, hasPurchased);
        this.DMG_INCR = dmgIncr;
    }

    public int getDMG_INCR() {
        return this.DMG_INCR;
    }

    @Override
    public String toString() {
        return "+" + this.DMG_INCR + " DMG " + super.toString();
    }
}
