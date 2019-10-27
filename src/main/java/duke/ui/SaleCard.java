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
        id.setText("ID: " + Long.toString(sale.getId()));
        double tempValue = sale.getValue();
        if (sale.isSpend() && tempValue > 0.0) {
            tempValue = -tempValue;
        }
        DecimalFormat df2 = new DecimalFormat("#.##");
        value.setText("$" + df2.format(tempValue));
        saleDate.setText(TimeParser.convertDateToString(sale.getSaleDate()));
        description.setText("Sale description: " + sale.getDescription());
        remarks.setText("Remarks: " + sale.getRemarks());
    }
}