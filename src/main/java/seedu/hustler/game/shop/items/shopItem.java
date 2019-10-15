package seedu.hustler.game.shop.items;

public abstract class shopItem implements Purchasable {
    protected int cost;
    protected Boolean isPurchased;

    public shopItem(int cost, Boolean hasPurchased) {
        this.cost = cost;
        this.isPurchased = hasPurchased;
    }

    public int getCost() {
        return this.cost;
    }

    public Boolean isPurchased() {
        return this.isPurchased;
    }

    public void setPurchased(Boolean purchased) {
        this.isPurchased = purchased;
    }

    @Override
    public boolean canPurchase(int points) {
        return points >= this.cost;
    }

    @Override
    public String toString() {
        return isPurchased ? "[Purchased]" : "[" + this.cost + " points needed]";
    }
}
