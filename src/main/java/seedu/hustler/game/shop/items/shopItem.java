package seedu.hustler.game.shop.items;

public abstract class shopItem implements Purchasable {
    protected int cost;
    protected boolean isPurchased;

    public shopItem(int cost, boolean hasPurchased) {
        this.cost = cost;
        this.isPurchased = hasPurchased;
    }

    public int getCost() {
        return this.cost;
    }

    public boolean isPurchased() {
        return this.isPurchased;
    }

    public void setPurchased(boolean purchased) {
        this.isPurchased = purchased;
    }

    @Override
    public boolean canPurchase(int points) {
        return points >= this.cost;
    }

    @Override
    public String toString() {
        return "[" + this.cost + " points needed]";
    }
}
