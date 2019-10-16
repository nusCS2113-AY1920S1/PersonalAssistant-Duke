package duke.ui;

import duke.logic.parser.commons.TimeParser;
import duke.model.commons.Item;
import duke.model.order.Order;
import duke.model.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;

/**
 * Controller for OrderCard. An OrderCard displays an order, including its creation time, customer, items,
 * delivery date, index, and status.
 */
public class OrderCard extends UiPart<AnchorPane> {
    private static final String FXML = "OrderCard.fxml";

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
    @FXML
    private Label total;

    /**
     * Creates a card displaying the {@code order}.
     *
     * @param order          to display
     * @param displayedIndex the index of the order to show on the card
     */
    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        id.setText(Long.toString(order.getId()));
        index.setText(displayedIndex + ".");
        deadline.setText(TimeParser.convertDateToString(order.getDeliveryDate()));
        name.setText(order.getCustomer().name);
        contact.setText(order.getCustomer().contact);
        remarks.setText(order.getRemarks());

        status.setText(order.getStatus().toString().toLowerCase());
        status.getStyleClass().clear();
        status.getStyleClass().addAll("status-" + order.getStatus().toString().toLowerCase());

        for (Item<Product> item : order.getItems()) {
            itemFlow.getChildren().add(
                    new OrderItemBox(item.getItem().getProductName(), item.getQuantity().getNumber())
            );
        }

        changeIndexLabelColor(order.isIsIngredientEnough(), order.getStatus());
        order.isIngredientEnoughProperty().addListener(((observable, oldValue, newValue) -> {
            changeIndexLabelColor(newValue, order.getStatus());
        }));

        total.setText(Double.toString(order.getTotal()));

    }

    private void changeIndexLabelColor(boolean isIngredientEnough, Order.Status status) {
        if (!isIngredientEnough && status.equals(Order.Status.ACTIVE)) {
            index.setTextFill(Paint.valueOf("red"));
        } else {
            index.setTextFill(Paint.valueOf("white"));
        }
    }
}
