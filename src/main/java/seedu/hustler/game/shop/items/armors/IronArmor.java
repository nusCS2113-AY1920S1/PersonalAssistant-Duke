package seedu.hustler.game.shop.items.armors;

public class IronArmor extends Armor {

    private static final int DEF_INCR = 3;
    private static final int STA_INCR = 3;
    private static final int COST = 10;

    public IronArmor() {
        super(COST, false, DEF_INCR, STA_INCR);
    }

    public IronArmor(boolean hasPurchased) {
        super(COST, hasPurchased, DEF_INCR, STA_INCR);
    }

    @Override
    public String toString() {
        return "Iron Armor, " + super.toString();
    }
}
