/**
 * Handles content to be shown at the Overview area, such as an overview of no. of products, annual profit & revenue.
 *
 * @author kuromono
 */

package cube.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class OverviewDisplay extends UiManager<StackPane> {
    private static final String FXML = "OverviewDisplay.fxml";

    @FXML
    private Label productQty;
    @FXML
    private Label profit;
    @FXML
    private Label revenue;

    /**
     * Default constructor of OverviewDisplay.
     * Initialises the values to be shown in the overview.
     *
     * @param qty          number of food items.
     * @param totalProfit  total profit.
     * @param totalRevenue total revenue.
     */
    public OverviewDisplay(int qty, double totalProfit, double totalRevenue) {
        super(FXML);
        productQty.setText(String.valueOf(qty));
        profit.setText(String.valueOf(totalProfit));
        revenue.setText(String.valueOf(totalRevenue));
    }

    /**
     * Updates the Overview Display.
     *
     * @param qty          number of food items.
     * @param totalProfit  total profit.
     * @param totalRevenue total revenue.
     */
    public void updateOverview(int qty, double totalProfit, double totalRevenue) {
        productQty.setText(String.valueOf(qty));
        profit.setText(String.valueOf(totalProfit));
        revenue.setText(String.valueOf(totalRevenue));
    }
}
