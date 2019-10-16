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
        populateShop();
    }

    /**
     * Prints out the list of ShopItems available in the store.
     */
    public void list() {
        System.out.println("********************* Here are the items in the shop *********************");
        System.out.println();
        for (int i = 0; i < this.shopList.size(); i++) {
            /**
             * Divides the list to format the printing of different classes.
             */
            if (i == 0 || !(shopList.get(i).isEquals(shopList.get(i - 1)))) {
                System.out.println("\t======" + shopList.get(i).getType() + "=====");
            }
            System.out.print((i + 1) + ". ");
            System.out.println(shopList.get(i).toString());
        }
        System.out.println();
        System.out.println("***************************************************************************");
    }

    /**
     * Purchase the current item in the shop in the given index.
     * @param index the index of the item in the list.
     * @return the ShopItem in the given index; null if IndexOfOfBounds exception is caught.
     */
    public ShopItem buy(int index) {
        try {
            if (!shopList.get(index).isPurchased()) {
                if (shopList.get(index).canPurchase(Achievements.totalPoints)) {
                    shopList.get(index).setPurchased(true);
                    Achievements.totalPoints -= shopList.get(index).getCost();
                    System.out.println("\t Item has been purchased!");
                    System.out.println("\tYour leftover points are: " + Achievements.totalPoints);
                    return shopList.get(index);
                } else {
                    System.out.println("\tNot enough points. Please accumulate more points!");
                    return shopList.get(index);
                }
            } else {
                System.out.println("\tItem has already been purchased! Please check your inventory.");
                return shopList.get(index);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\tThere is no such index! Please input a valid number");
            return null;
        }
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
     * Obtain all the isPurchased value of every ShopItem to update in the txtfile.
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
     * Adds in every existing ShopItem in shop.
     * @return the newly populated ShopList.
     */
    private ShopList populateShop() {
        shopList.add(new Broadsword());
        shopList.add(new Mace());
        shopList.add(new MoonlightSword());
        shopList.add(new LeatherArmor());
        shopList.add(new IronArmor());
        shopList.add(new Chainmail());
        return this;
    }
}