package seedu.hustler.game.shop;

import seedu.hustler.game.shop.items.ShopItem;
import seedu.hustler.game.shop.items.weapons.Broadsword;
import seedu.hustler.game.shop.items.weapons.Mace;
import seedu.hustler.game.shop.items.weapons.MoonlightSword;
import seedu.hustler.game.shop.items.armors.Chainmail;
import seedu.hustler.game.shop.items.armors.IronArmor;
import seedu.hustler.game.shop.items.armors.LeatherArmor;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The list of items that can be purchased in the shop.
 */
public class ShopList {

    /**
     * The ArrayList of ShopItem to be purchased.
     */
    private ArrayList<ShopItem> shopList;

    /**
     * Constructs a ShopList and populate with every existing shopItem.
     */
    public ShopList() {
        this.shopList = new ArrayList<>();
    }

    /**
     * Constructs a ShopList with the items in the given ShopList.
     */
    public ShopList(ShopList other) {
        this.shopList = new ArrayList<>();
        this.shopList.addAll(other.shopList);
    }

    /**
     * Gets the shop item list of the shop.
     * @return the array list containing all of the shop items.
     */
    public ArrayList<ShopItem> getShopList() {
        return this.shopList;
    }

    /**
     * Purchase the current item in the shop in the given index
     * with the given current points obtained by the user.
     * @param index the index of the item in the list.
     * @return the ShopItem that is purchased, if any.
     */
    public Optional<ShopItem> buy(int index, int totalPoints) {
        if (!shopList.get(index).isPurchased()) {
            if (shopList.get(index).canPurchase(totalPoints)) {
                updateIsPurchased(index, true);
                return Optional.ofNullable(shopList.get(index));
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the current size of the ArrayList in shopList.
     * @return the size of the shopList.
     */
    public int size() {
        return this.shopList.size();
    }

    /**
     * Gets the shop item with the given index.
     * @return the shop item in the shop list.
     */
    public ShopItem getItem(int index) {
        return this.shopList.get(index);
    }

    /**
     * Updates the Boolean value of isPurchased of the ShopItem to be true.
     * @param index the index of the ShopItem.
     */
    public void updateIsPurchased(int index, boolean isPurchased) {
        this.shopList.set(index, shopList.get(index).setIsPurchased(isPurchased));
    }

    /**
     * Writes all the isPurchased value of every ShopItem to update in the txtfile.
     * @return the String to be written in shop.txt.
     */
    public String itemsStatus() {
        String toWrite = "";
        for (int i = 0; i < shopList.size(); i++) {
            toWrite += shopList.get(i).isPurchased().toString() + (i != shopList.size() - 1 ? "\n" : "");
        }
        return toWrite;
    }

    /**
     * Gets the array list of purchased shop items.
     * @return the array list of the shop items that has been purchased.
     */
    public ArrayList<ShopItem> getPurchasedItems() {
        ArrayList<ShopItem> itemsPurchased = new ArrayList<>();
        for (ShopItem item : shopList) {
            if (item.isPurchased()) {
                itemsPurchased.add(item);
            }
        }
        return itemsPurchased;
    }

    /**
     * Adds a shop item in the current array list in the shop list.
     * @param item the item to be added into the shop list.
     * @return the new instance of the shoplist with the item updated.
     */
    public ShopList addItem(ShopItem item) {
        ShopList newShop = new ShopList(this);
        newShop.shopList.add(item);
        return newShop;
    }

    /**
     * Adds in every existing ShopItem in shop.
     * @return the newly populated ShopList.
     */
    public ShopList populateShop() {
        ShopList newShopList = new ShopList();
        newShopList.shopList.add(new Broadsword(false));
        newShopList.shopList.add(new Mace(false));
        newShopList.shopList.add(new MoonlightSword(false));
        newShopList.shopList.add(new LeatherArmor(false));
        newShopList.shopList.add(new IronArmor(false));
        newShopList.shopList.add(new Chainmail(false));
        return newShopList;
    }
}