package seedu.hustler.game.shop.items.armors;

public class LeatherArmor extends Armor {

    private static final int DEF_INCR = 1;
    private static final int STA_INCR = 3;
    private static final int COST = 5;

    public LeatherArmor() {
        super(COST, false, DEF_INCR, STA_INCR);
    }

    public LeatherArmor(boolean hasPurchased) {
        super(COST, hasPurchased, DEF_INCR, STA_INCR);
    }

    @Override
    public String toString() {
        return "Leather Armor, " + super.toString();
    }
}
