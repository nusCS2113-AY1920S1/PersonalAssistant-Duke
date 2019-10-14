package duke.ui;

import duke.model.sale.Sale;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class SalePage extends UiPart<AnchorPane> {
    private static final String FXML = "SalePage.fxml";

    @FXML
    private ListView<Sale> saleListView;

    public SalePage(ObservableList<Sale> saleList) {
        super(FXML);
        saleListView.setItems(saleList);
        saleListView.setCellFactory(listView -> new SaleListViewCell());
    }

    class SaleListViewCell extends ListCell<Sale> {
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

}