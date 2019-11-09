package duke.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for order item.
 * An order item includes product name and quantity.
 */
public class OrderItemBox extends UiPart<AnchorPane> {
    private static final String FXML = "OrderItemBox.fxml";

    @FXML
    private Label itemName;
    @FXML
    private Label itemQuantity;

    /**
     * Creates a OrderItemBox.
     * @param name of product
     * @param quantity of product.
     */
    public OrderItemBox(String name, double quantity) {
        super(FXML);

        itemName.setText(name);
        itemQuantity.setText(Double.toString(quantity));
    }
}
