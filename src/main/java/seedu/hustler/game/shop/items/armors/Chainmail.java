package seedu.hustler.game.shop.items.armors;

public class Chainmail extends Armor {

    private static final int DEF_INCR = 7;
    private static final int STA_INCR = 6;
    private static final int COST = 15;

    public Chainmail() {
        super(COST, false, DEF_INCR, STA_INCR);
    }

    public Chainmail(boolean hasPurchased) {
        super(COST, hasPurchased, DEF_INCR, STA_INCR);
    }

    @Override
    public String toString() {
        return "Chainmail, " + super.toString();
    }
}