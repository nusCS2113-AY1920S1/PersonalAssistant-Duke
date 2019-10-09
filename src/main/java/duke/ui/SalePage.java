package duke.ui;

import duke.entities_decrypted.Sale;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class SalePage extends UiPart<AnchorPane> {
    private static final String FXML = "SalePage.fxml";

    @FXML
    private VBox saleList;

    public SalePage() {
        super(FXML);
    }

    public void refreshSaleList(List<Sale> saleList, List<Sale> all) {
        /*
        saleList.getChildren().clear();
        int i = 1;
        for (Sale sale : saleList) {
            saleList.getChildren().add(new SaleCard(sale, i));
            ++i;
        }
        */
    }

}