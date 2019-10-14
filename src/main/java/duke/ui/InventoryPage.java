package duke.ui;

import duke.model.commons.Ingredient;
import duke.model.commons.Item;
import duke.model.order.Quantity;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class InventoryPage extends UiPart<AnchorPane> {
    private static final String FXML = "InventoryPage.fxml";

    @FXML
    private TableView inventoryListTable;
    @FXML
    private TableView shoppingListTable;

    private ObservableList<Item<Ingredient>> inventoryList;

    public InventoryPage(ObservableList<Item<Ingredient>> inventoryList) {
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

    //Solution adapted from: http://fxapps.blogspot.com/2012/09/showing-object-properties-in-tableview.html
    void setInventoryInfoColumns() {
        TableColumn<Item<Ingredient>, String> ingredientColumn = new TableColumn<>("Ingredient");
        ingredientColumn.setResizable(true);
        ingredientColumn.setCellValueFactory(itemStringCellDataFeatures -> new SimpleStringProperty(itemStringCellDataFeatures.getValue().getItem().getName()));

        TableColumn<Item<Ingredient>, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setResizable(true);
        quantityColumn.setCellValueFactory(itemStringCellDataFeatures -> new SimpleStringProperty(itemStringCellDataFeatures.getValue().getQuantity().getNumberAsString()));

        TableColumn<Item<Ingredient>, String> unitColumn = new TableColumn<>("Unit");
        unitColumn.setResizable(true);
        unitColumn.setCellValueFactory(itemStringCellDataFeatures -> new SimpleStringProperty(itemStringCellDataFeatures.getValue().getItem().getUnit()));

        inventoryListTable.getColumns().addAll(ingredientColumn, quantityColumn, unitColumn);
    }
}



