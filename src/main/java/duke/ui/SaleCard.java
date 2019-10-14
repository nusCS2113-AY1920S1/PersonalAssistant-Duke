package duke.ui;

import duke.logic.parser.commons.TimeParser;
import duke.model.sale.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 * Controller for SaleCard.
 * A SaleCard displays a sale,
 * including its creation time, description,
 * sale date, value, and remarks.
 */
public class SaleCard extends UiPart<AnchorPane> {
    private static final String FXML = "SaleCard.fxml";

    @FXML
    private AnchorPane innerPane;
    @FXML
    private FlowPane itemFlow;
    @FXML
    private Label id;
    @FXML
    private Label value;
    @FXML
    private Label saleDate;
    @FXML
    private Label description;
    @FXML
    private Label remarks;

    public SaleCard(Sale sale, int displayedIndex) {
        super(FXML);
        id.setText(Long.toString(sale.getId()));
        value.setText(Double.toString(sale.getValue()));
        saleDate.setText(TimeParser.convertDateToString(sale.getSaleDate()));
        description.setText(sale.getDescription());
        remarks.setText(sale.getRemarks());
    }
}