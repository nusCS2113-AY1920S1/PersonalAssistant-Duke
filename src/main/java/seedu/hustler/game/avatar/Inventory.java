package seedu.hustler.game.avatar;

import seedu.hustler.Hustler;
import seedu.hustler.game.shop.items.Purchasable;
import seedu.hustler.game.shop.items.ShopItem;
import java.util.ArrayList;

/**
 * The inventory storage of User. Items in this storage can be equipped
 * by their avatar.
 */
public class Inventory {

    /**
     * The arraylist of the items that the user has purchased.
     */
    private ArrayList<Purchasable> items;

    /**
     * Constructs a new inventory with an empty array list of items.
     */
    public Inventory() {
        items = new ArrayList<>();
    }

    /**
     * Gets the size of the items.
     * @return the size of the array list in the inventory.
     */
    public int getSize() { return items.size(); }


    /**
     * Gets the shop item with the given index.
     * @param index the index of the item in the array list.
     * @return the shop item with the given index.
     */
    public Purchasable get(int index) { return items.get(index); }

    /**
     * Gets the array list of the shop item in the inventory.
     * @return the array list of the items.
     */
    public ArrayList<Purchasable> getItems() {
        return this.items;
    }

    public Inventory updateInventory() {
        this.items = Hustler.shopList.getPurchasedItems();
        return this;
    }
}
