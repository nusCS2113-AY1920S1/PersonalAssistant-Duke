package duke.storage;

import duke.command.ExecuteShortcutCommand;
import duke.entities.Ingredient;
import duke.entities.Order;
import duke.entities.inventory.Inventory;

import java.util.*;

public class BakingList {

    private List<Order> orderList = new ArrayList<>();

    private Map<String, ExecuteShortcutCommand> shortcuts = new HashMap<>();

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Map<String, ExecuteShortcutCommand> getShortcuts() {
        return shortcuts;
    }

    public void setShortcuts(Map<String, ExecuteShortcutCommand> shortcuts) {
        this.shortcuts = shortcuts;
    }

    public List<Inventory> inventoryList = new ArrayList<>();

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
}
