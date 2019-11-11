package seedu.hustler.game.shop.items;

/**
 * Interface that all shop items implements; checks if the User is able to
 * purchase the selected item in the shop.
 */
public interface ShopItem {

    /**
     * Gets the cost of the shop item.
     * @return the integer value of the shop item.
     */
    int getCost();

    /**
     * Checks if the shop item is purchased.
     * @return true if shop item is purchased; false if otherwise.
     */
    Boolean isPurchased();

    /**
     * Gets the type, as a weapon or an armor, of the shop item.
     * @return the type, in string of the shop item.
     */
    String getType();

    /**
     * Sets the shop item purchased status.
     * @param isPurchased the status that the shop item will be.
     * @return the new instance of the shop item with the updated purchased status.
     */
    ShopItem setIsPurchased(boolean isPurchased);

    /**
     * Checks if a shop item is of the same type.
     * @param other the other shop item to be compared to.
     * @return true if the other shop item is of the same type; false if otherwise.
     */
    Boolean isSameType(ShopItem other);

    /**
     * Gets the damage increment of the shop item.
     * @return the integer value of the damage increment.
     */
    int getDamageIncr();

    /**
     * Gets the defence increment of the shop item.
     * @return the integer value of the defence increment.
     */
    int getDefenceIncr();

    /**
     * Gets the stamina increment of the shop item.
     * @return the integer value of the stamina increment.
     */
    int getStaminaIncr();

    /**
     * The abstract function that checks if User is able to purchase
     * the item from the shop.
     * @param points the current points of the avatar.
     * @return true if User can purchase the item; false if otherwise.
     */
    boolean canPurchase(int points);
}