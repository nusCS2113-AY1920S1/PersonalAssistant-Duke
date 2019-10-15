package seedu.hustler.game.shop.items;

public abstract class ShopItem implements Purchasable {
    protected int cost;
    protected Boolean isPurchased;
    protected String type;

    public ShopItem(int cost, Boolean hasPurchased, String type) {
        this.cost = cost;
        this.isPurchased = hasPurchased;
        this.type = type;
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

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return isPurchased ? "[Purchased]" : "[" + this.cost + " points needed]";
    }
}
