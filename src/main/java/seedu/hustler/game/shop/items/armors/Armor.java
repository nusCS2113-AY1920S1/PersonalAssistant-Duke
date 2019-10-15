package seedu.hustler.game.shop.items.armors;

import seedu.hustler.game.shop.items.ShopItem;

public abstract class Armor extends ShopItem {

    private final int DEF_INCR;
    private final int STA_INCR;

    public Armor(int cost, boolean hasPurchased, int defIcr, int staIcr) {
        super(cost, hasPurchased, "Armor");
        this.DEF_INCR = defIcr;
        this.STA_INCR = staIcr;
    }

    public int getDEF_INCR() {
        return DEF_INCR;
    }

    public int getSTA_INCR() {
        return STA_INCR;
    }

    @Override
    public String toString() {
        return "+" + DEF_INCR + " DEF, +" + STA_INCR + " STA " + super.toString();
    }
}
