package seedu.hustler.game.shop;

import com.sun.net.httpserver.Filter;
import seedu.hustler.game.achievement.Achievements;
import seedu.hustler.game.shop.items.shopItem;
import seedu.hustler.game.shop.items.weapons.*;
import seedu.hustler.game.shop.items.armors.*;

import java.util.ArrayList;
import java.util.Optional;

public class ShopList {

    private ArrayList<shopItem> shopList;

    public ShopList() {
        this.shopList = new ArrayList<>();
        populateShop();
    }

    public void list() {
        for (int i = 0; i < this.shopList.size(); i++) {
            System.out.print((i + 1) + ". ");
            System.out.println(shopList.get(i).toString());
        }
    }

    public void buy(int index) {
        try {
            if (!shopList.get(index).isPurchased()) {
                if (shopList.get(index).canPurchase(Achievements.totalPoints)) {
                    shopList.get(index).setPurchased(true);
                    Achievements.totalPoints -= shopList.get(index).getCost();
                    System.out.println("\t Item has been purchased!");
                    System.out.println("\tYour leftover points are: " + Achievements.totalPoints);
                } else {
                    System.out.println("\tNot enough points. Please accumulate more points!");
                }
            } else {
                System.out.println("\tItem has already been purchased! Please check your inventory.");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\tThere is no such index! Please input a valid number");
        }
    }

    public int size() {
        return this.shopList.size();
    }

    public void updateStatus(int index, Boolean bool) {
        this.shopList.get(index).setPurchased(bool);
    }

    public String itemsStatus() {
        String toWrite = "";
        for (int i = 0; i < shopList.size(); i++) {
            toWrite += shopList.get(i).isPurchased().toString() + (i != shopList.size() - 1 ? "\n" : "");
        }
        return toWrite;
    }

    private void populateShop() {
        shopList.add(new Broadsword());
        shopList.add(new Mace());
        shopList.add(new MoonlightSword());
        shopList.add(new LeatherArmor());
        shopList.add(new IronArmor());
        shopList.add(new Chainmail());
    }
}