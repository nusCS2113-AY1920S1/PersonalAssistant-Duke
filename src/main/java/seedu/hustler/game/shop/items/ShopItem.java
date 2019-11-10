package seedu.hustler.game.shop.items;

/**
 * Interface that all shop items implements; checks if the User is able to
 * purchase the selected item in the shop.
 */
public interface ShopItem {
    int getCost();
    Boolean isPurchased();
    String getType();
    void setPurchased(Boolean purchased);
    Boolean isSameType(ShopItem other);
    int getDamageIncr();
    int getDefenceIncr();
    int getStaminaIncr();

    /**
     * The abstract function that checks if User is able to purchase
     * the item from the shop.
     * @param points the current points of the avatar.
     * @return true if User can purchase the item; false if otherwise.
     */
    boolean canPurchase(int points);
}