package duke.storage;

import duke.command.ExecuteShortcutCommand;
import duke.entities.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
