package duke.ui;

import duke.entities.Order;
import duke.parser.TimeParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

/**
 * Controller for OrderCard. An OrderCard displays an order, including its creation time, customer,
 * items, delivery date, index, and status.
 */
public class OrderCard extends AnchorPane {
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

    /**
     * Constructor for OrderCard.
     *
     * @param order       The order to displayed.
     * @param indexNumber The index of the order.
     */
    public OrderCard(Order order, int indexNumber) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/OrderCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        id.setText(Long.toString(order.getId()));
        index.setText("#" + Integer.toString(indexNumber));
        deadline.setText(TimeParser.convertDateToString(order.getDeliveryDate()));
        name.setText(order.getCustomerName());
        contact.setText(order.getCustomerContact());
        remarks.setText(order.getRemarks());
        status.setText(order.getStatus().toString().toLowerCase());
        status.getStyleClass().clear();
        status.getStyleClass().addAll("status-" + order.getStatus().toString().toLowerCase());
        for (String itemName : order.getItems().keySet()) {
            itemFlow.getChildren().add(new OrderItemBox(itemName, order.getItems().get(itemName)));
        }

    }
}
