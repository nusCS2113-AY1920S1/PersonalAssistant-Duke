package duke.ui;

import duke.entities.Sale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class SalePage extends AnchorPane {
    @FXML
    private VBox saleList;

    public SalePage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/SalePage.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
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