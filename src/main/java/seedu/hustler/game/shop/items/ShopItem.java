package seedu.hustler.game.shop.items;

/**
 *  * The class in which every item will inherit from.
 *   */
public abstract class ShopItem implements Purchasable {
    /**
     *      * The cost of the shop item.
     *           */
    protected int cost;
    /**
     *      * Boolean if the shop item has been purchased.
     *           */
    protected Boolean isPurchased;
    /**
     *      * The type in string, of each item.
     *           */
    protected String type;

    /**
     *      * Constructs a ShopItem with the given cost, hasPurchased, and type.
     *           * @param cost the cost of the item.
     *                * @param hasPurchased if the ShopItem was purchased; false if otherwise.
     *                     * @param type the type in string, of the item.
     *                          */
    public ShopItem(int cost, Boolean hasPurchased, String type) {
        this.cost = cost;
        this.isPurchased = hasPurchased;
       this.type = type;
    }

    /**
     *      * Gets the cost of the Shop Item.
     *           * @return the cost of the shop item.
     *                */
    public int getCost() {
        return this.cost;
    }

    /**
     *      * Gets the boolean value of isPurchased.
     *           * @return true if item has been purchased; false if otherwise.
     *                */
    public Boolean isPurchased() {
        return this.isPurchased;
    }

    /**
     *      * Sets the boolean value of isPurchased.
     *           * @param purchased the boolean value if item has been purchased.
     *                */
    public void setPurchased(Boolean purchased) {
        this.isPurchased = purchased;
    }

    /**
     *      * Gets the type of shop item.
     *           * @return the type of the shop item in String.
     *                */
    public String getType() {
        return this.type;
    }

    /**
     *      * Checks if the shop item is same type as the other by comparing their type.
     *           * @param other the other shop item to compare to.
     *                * @return the boolean value if they are equivalent; false if otherwise.
     *                     */
    public boolean isEquals(ShopItem other) {
        return this.type.equals(other.type);
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
