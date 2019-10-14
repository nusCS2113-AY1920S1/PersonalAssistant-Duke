package duke.ui;

import duke.model.inventory.Ingredient;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class InventoryPage extends UiPart<AnchorPane> {
    private static final String FXML = "InventoryPage.fxml";

    @FXML
    private TableView inventoryListTable;
    @FXML
    private TableView shoppingListTable;

    private ObservableList<Ingredient> inventoryList;

    public InventoryPage(ObservableList<Ingredient> inventoryList) {
        super(FXML);
        this.inventoryList = inventoryList;
        setupTable();
    }

    void setupTable() {
        inventoryListTable.setItems(inventoryList);
        inventoryListTable.getColumns().clear();
        setIndexColumn();
        setInventoryInfoColumns();
    }

    void setIndexColumn() {
        TableColumn<Ingredient, Void> indexColumn = new TableColumn<>("S/N");
        indexColumn.setMinWidth(50);
        indexColumn.setPrefWidth(70);
        indexColumn.setResizable(true);

        //Solution below adapted from: https://stackoverflow.com/questions/31212400/adding-index-of-records-in-a-javafx-tableview-column
        ///////////////////////////////////////////////////////index column
        {
            indexColumn.setCellFactory(col -> {

                // just a default table cell:
                TableCell<Ingredient, Void> cell = new TableCell<>();

                cell.textProperty().bind(Bindings.createStringBinding(() -> {
                    if (cell.isEmpty()) {
                        return null;
                    } else {
                        return Integer.toString(cell.getIndex() + 1);
                    }
                }, cell.emptyProperty(), cell.indexProperty()));

                return cell;
            });
        }
        ////////////////////////////////index column created

        inventoryListTable.getColumns().add(indexColumn);
    }

    void setInventoryInfoColumns() {
        TableColumn<Ingredient, String> ingredientColumn = new TableColumn<>("Ingredient");
        ingredientColumn.setResizable(true);
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Ingredient, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setResizable(true);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Ingredient, String> unitColumn = new TableColumn<>("Unit");
        unitColumn.setResizable(true);
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        inventoryListTable.getColumns().addAll(ingredientColumn, quantityColumn, unitColumn);
    }

}

