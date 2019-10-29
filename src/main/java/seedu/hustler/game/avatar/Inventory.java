package seedu.hustler.game.avatar;

import seedu.hustler.Hustler;
import seedu.hustler.game.shop.items.ShopItem;
import java.util.ArrayList;

public class Inventory {

    private ArrayList<ShopItem> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public Inventory updateInventory() {
        this.items = Hustler.shopList.getPurchasedItems();
        return this;
    }

    public int getSize() { return items.size(); }

    public ShopItem get(int i) { return items.get(i); }

    public ArrayList<ShopItem> getItems() {
        return this.items;
    }
}
