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

    public OverviewDisplay() {
        super(FXML);
        productQty.setText("0");
        profit.setText("0");
        revenue.setText("0");
    }

    public OverviewDisplay(int qty, double totalProfit, double totalRevenue) {
        super(FXML);
        productQty.setText(String.valueOf(qty));
        profit.setText(String.valueOf(totalProfit));
        revenue.setText(String.valueOf(totalRevenue));
    }

    public void updateOverview(int qty, double totalProfit, double totalRevenue) {
        productQty.setText(String.valueOf(qty));
        profit.setText(String.valueOf(totalProfit));
        revenue.setText(String.valueOf(totalRevenue));
    }
}
