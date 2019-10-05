package duke.ui;

import duke.commons.core.LogsCenter;
import duke.model.order.Order;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.logging.Logger;

public class OrderPage extends UiPart<AnchorPane> {
    private static final String FXML = "OrderPage.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderPage.class);

    @FXML
    private ListView<Order> orderListView;

    public OrderPage(ObservableList<Order> orderList) {
        super(FXML);
        orderList.addListener(new ListChangeListener<Order>() {
            @Override
            public void onChanged(Change<? extends Order> c) {
                System.out.println("changed");
            }
        });
//        OrderList oo = new OrderList();
//        oo.add(new Order(
//                new Customer("1","1"),
//                TimeParser.convertStringToDate("now"),
//                Order.Status.ACTIVE,
//                "NA",
//                new HashMap<Product, Integer>()));
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    class OrderListViewCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, 1).getRoot());
            }
        }
    }

//    public void refreshOrderList(List<Order> orders, List<Order> all) {
//        orderVBox.getChildren().clear();
//        int index = 1;
//        for (Order order : orders) {
//            orderVBox.getChildren().add(new OrderCard(order, index));
//            index++;
//        }
//    }

}
