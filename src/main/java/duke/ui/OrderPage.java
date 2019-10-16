package duke.ui;

import duke.commons.core.LogsCenter;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Order;
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

    public OrderPage(ObservableList<Order> orderList, ObservableList<Item<Ingredient>> inventoryList) {
        super(FXML);
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    class OrderListViewCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);
            updateSelected(false);
            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

}
