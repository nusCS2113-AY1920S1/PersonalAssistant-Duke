package seedu.hustler.game.shop.items;

public abstract class shopItem implements Purchasable {
    protected int cost;
    protected boolean hasPurchased;

    public shopItem(int cost, boolean hasPurchased) {
        this.cost = cost;
        this.hasPurchased = hasPurchased;
    }

    @Override
    public boolean canPurchase(int points) {
        return points >= this.cost;
    }

    @Override
    public String toString() {
        return "[" + this.cost + " points needed";
    }
}
