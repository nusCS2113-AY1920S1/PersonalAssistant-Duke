package seedu.hustler.game.shop.items.weapons;

public class Mace extends Weapon {

    private static final int DMG_INCR = 4;
    private static final int COST = 7;

    public Mace() {
        super(COST, false, DMG_INCR);
    }

    public Mace(boolean hasPurchased) {
        super(COST, hasPurchased, DMG_INCR);
    }

    public int attack(int damage) {
        return this.DMG_INCR + damage;
    }

    @Override
    public String toString() {
        return "Mace, " + super.toString();
    }
}

