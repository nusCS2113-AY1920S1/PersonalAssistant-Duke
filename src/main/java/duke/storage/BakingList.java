package duke.storage;

import duke.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class BakingList {

    private List<Order> orderList = new ArrayList<>();

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
