package duke.ui;

import duke.commons.core.LogsCenter;
import duke.model.order.Order;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.logging.Logger;

public class OrderPage extends UiPart<AnchorPane> {
    private static final String FXML = "OrderPage.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderPage.class);

    @FXML
    private VBox orderVBox;

    public OrderPage(ObservableList<Order> orderList) {
        super(FXML);
        //orderList.addListener();


    }

    public void refreshOrderList(List<Order> orders, List<Order> all) {
        orderVBox.getChildren().clear();
        int index = 1;
        for (Order order : orders) {
            orderVBox.getChildren().add(new OrderCard(order, index));
            index++;
        }
    }

}
