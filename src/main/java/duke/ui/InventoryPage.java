package duke.ui;

import duke.entities.Ingredient;
import duke.entities.inventory.Inventory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import javax.swing.table.TableColumn;
import java.io.IOException;
import java.util.List;

public class InventoryPage extends AnchorPane {

    public InventoryPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/InventoryPage.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshInventoryList(List<Inventory> inventory) {

    }
}

