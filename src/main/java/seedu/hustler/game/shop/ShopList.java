package seedu.hustler.game.shop;

import seedu.hustler.game.achievement.Achievements;
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
    private final ArrayList<ShopItem> shopList;

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
    public Optional<ShopItem> buy(int index) {
        if (!shopList.get(index).isPurchased()) {
            if (shopList.get(index).canPurchase(Achievements.totalPoints)) {
                shopList.get(index).setPurchased(true);
                Achievements.totalPoints -= shopList.get(index).getCost();
            } else {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(shopList.get(index));
    }

    /**
     * Gets the current size of the ArrayList in shopList.
     * @return the size of the shopList.
     */
    public int size() {
        return this.shopList.size();
    }

    /**
     * Updates the Boolean value of isPurchased of the ShopItem to be true.
     * @param index the index of the ShopItem.
     * @param bool the boolean value of isPurchased.
     */
    public void updateStatus(int index, Boolean bool) {
        this.shopList.get(index).setPurchased(bool);
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
        shopList.add(new Broadsword());
        shopList.add(new Mace());
        shopList.add(new MoonlightSword());
        shopList.add(new LeatherArmor());
        shopList.add(new IronArmor());
        shopList.add(new Chainmail());
        return this;
    }
}