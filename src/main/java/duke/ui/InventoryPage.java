package duke.ui;

import duke.entities.Ingredient;
import duke.entities.inventory.Inventory;
import duke.entities.recipe.Recipe;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class InventoryPage extends AnchorPane {
    @FXML TableView<Inventory> inventoryListTable;

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
        ObservableList<Inventory> inventoryObservableList = FXCollections.observableArrayList();
        for (Inventory inv : inventory) {
            inventoryObservableList.add(inv);
        }
        inventoryListTable.getColumns().clear();

        if (inventory.size() != 0) {

            TableColumn<Inventory, Void> indexColumn = new TableColumn<>("S/N");
            indexColumn.setMinWidth(50);
            indexColumn.setPrefWidth(50);
            indexColumn.setResizable(true);

            indexColumn.setCellFactory(col -> {
                TableCell<Inventory, Void> cell = new TableCell<>();
                cell.textProperty().bind(Bindings.createStringBinding(() -> {
                    if (cell.isEmpty()) {
                        return null ;
                    } else {
                        return Integer.toString(cell.getIndex());
                    }
                }));
                return cell ;
            });

            TableColumn<Inventory, String> ingredientColumn = new TableColumn<>("Ingredient");
            ingredientColumn.setMinWidth(200);
            ingredientColumn.setPrefWidth(500);
            ingredientColumn.setResizable(true);
            ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Inventory, Integer> quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setMinWidth(100);
            quantityColumn.setPrefWidth(240);
            quantityColumn.setResizable(true);
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            inventoryListTable.setItems(inventoryObservableList);
            inventoryListTable.getColumns().addAll(indexColumn, ingredientColumn, quantityColumn);
        }

    }
}

