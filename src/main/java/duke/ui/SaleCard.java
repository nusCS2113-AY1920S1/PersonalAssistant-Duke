package duke.ui;

import duke.logic.parser.commons.TimeParser;
import duke.model.commons.Item;
import duke.model.sale.Sale;
import duke.model.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 * Controller for OrderCard. An OrderCard displays an order, including its creation time, customer, items,
 * delivery date, index, and status.
 */
public class SaleCard extends UiPart<AnchorPane> {
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

    public SaleCard(Sale sale, int displayedIndex) {
        super(FXML);
        id.setText(Long.toString(sale.getId()));
        index.setText(displayedIndex + ".");
        deadline.setText(TimeParser.convertDateToString(sale.getDeliveryDate()));
        name.setText(sale.getCustomer().name);
        contact.setText(sale.getCustomer().contact);
        remarks.setText(sale.getRemarks());
        status.setText(sale.getStatus().toString().toLowerCase());
        status.getStyleClass().clear();
        status.getStyleClass().addAll("status-" + sale.getStatus().toString().toLowerCase());
    }
}