package seedu.hustler.game.shop.items.weapons;

import seedu.hustler.game.shop.items.ShopItem;

/**
 * Abstract interface of weapon that all weapons the avatar yield inherits from.
 */
public abstract class Weapon extends ShopItem {

    private final int DMG_INCR;

    public Weapon(int cost, boolean hasPurchased, int dmgIncr) {
        super(cost, hasPurchased, "Weapon");
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
