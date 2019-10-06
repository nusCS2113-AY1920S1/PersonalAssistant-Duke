package duke.ui;

import duke.logic.parser.commons.TimeParser;
import duke.model.commons.Product;
import duke.model.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 * Controller for OrderCard. An OrderCard displays an order, including its creation time, customer, items,
 * delivery date, index, and status.
 */
public class OrderCard extends UiPart<AnchorPane> {
    static final String FXML = "OrderCard.fxml";

    @FXML
    private AnchorPane innerPane;
    @FXML
    private FlowPane itemFlow;
    @FXML
    private Label id;
    @FXML
    private Label index;
    @FXML
    private Label deadline;
    @FXML
    private Label name;
    @FXML
    private Label contact;
    @FXML
    private Label remarks;
    @FXML
    private Label status;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        id.setText(Long.toString(order.getId()));
        index.setText("#" + displayedIndex);
        deadline.setText(TimeParser.convertDateToString(order.getDeliveryDate()));
        name.setText(order.getCustomer().name);
        contact.setText(order.getCustomer().contact);
        remarks.setText(order.getRemarks());
        status.setText(order.getStatus().toString().toLowerCase());
        status.getStyleClass().clear();
        status.getStyleClass().addAll("status-" + order.getStatus().toString().toLowerCase());
        for (Product item : order.getItems().keySet()) {
            itemFlow.getChildren().add(new OrderItemBox(item.name, order.getItems().get(item)));
        }

    }
}
