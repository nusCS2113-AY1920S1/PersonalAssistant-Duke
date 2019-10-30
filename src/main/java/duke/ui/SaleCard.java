package duke.ui;

import duke.logic.parser.commons.TimeParser;
import duke.model.sale.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.text.DecimalFormat;

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
    private Label index;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label value;
    @FXML
    private Label remarks;

    public SaleCard(Sale sale, int displayedIndex) {
        super(FXML);
        index.setText(displayedIndex + ".");
        date.setText(TimeParser.convertDateToString(sale.getSaleDate()));
        id.setText("ID: " + Long.toString(sale.getId()));
        description.setText("Description: " + sale.getDescription());
        double tempValue = sale.getValue();
        if (sale.isSpend() && tempValue > 0.0) {
            tempValue = -tempValue;
        }
        DecimalFormat df2 = new DecimalFormat("#.##");
        value.setText("Amount: $" + df2.format(tempValue));
        remarks.setText("Remarks: " + sale.getRemarks());
    }
}