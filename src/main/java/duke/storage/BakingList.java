package duke.storage;

import duke.order.Order;

import java.util.List;

public class BakingList {
    private List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
