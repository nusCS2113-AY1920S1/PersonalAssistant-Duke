package duke.ui;

import duke.model.sale.Sale;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.text.DecimalFormat;

public class SalePage extends UiPart<AnchorPane> {
    private static final String FXML = "SalePage.fxml";
    @FXML
    private Label revenue;
    @FXML
    private Label cost;
    @FXML
    private Label profit;
    @FXML
    private ListView<Sale> saleListView;

    public SalePage(ObservableList<Sale> saleList) {
        super(FXML);
        saleListView.setItems(saleList);
        saleListView.setCellFactory(listView -> new SaleListViewCell());

        updateStatistics(saleList);
        saleList.addListener((ListChangeListener<Sale>) change -> {
            updateStatistics(saleList);
        });
    }

    static class SaleListViewCell extends ListCell<Sale> {
        @Override
        protected void updateItem(Sale sale, boolean empty) {
            super.updateItem(sale, empty);
            updateSelected(false);
            if (empty || sale == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SaleCard(sale, getIndex() + 1).getRoot());
            }
        }
    }

    private void updateStatistics(ObservableList<Sale> saleList) {
        double tempRevenue = 0.00;
        double tempCost = 0.00;

        for (Sale sale : saleList) {
            if (sale.isSpend()) {
                tempCost += sale.getValue();
            } else {
                tempRevenue += sale.getValue();
            }
        }
        DecimalFormat df2 = new DecimalFormat("#.##");
        revenue.setText("Revenue: $" + df2.format(tempRevenue));
        cost.setText("Cost: $" + df2.format(tempRevenue));
        profit.setText("Profit: $" + df2.format(tempRevenue - tempCost));
    }
}