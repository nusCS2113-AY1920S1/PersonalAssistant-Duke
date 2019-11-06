package seedu.hustler.game.shop.items;

/**
 * Interface that all shop items implements; checks if the User is able to
 * purchase the selected item in the shop.
 */
public interface Purchasable {
    public int getCost();
    public Boolean isPurchased();
    public String getType();
    public void setPurchased(Boolean purchased);
    public Boolean isSameType(Purchasable other);
    /**
     * The abstract function that checks if User is able to purchase
     * the item from the shop.
     * @param points the current points of the avatar.
     * @return true if User can purchase the item; false if otherwise.
     */
    boolean canPurchase(int points);
}