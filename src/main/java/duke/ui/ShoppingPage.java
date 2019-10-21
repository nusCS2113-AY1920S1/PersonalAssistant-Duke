package duke.ui;

import duke.model.inventory.Ingredient;
import duke.model.commons.Item;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ShoppingPage extends UiPart<AnchorPane> {
    private static final String FXML = "ShoppingPage.fxml";

    @FXML
    private TableView shoppingListTable;

    private ObservableList<Item<Ingredient>> shoppingList;

    /**
     * Creates a constructor for ShoppingPage and sets the ObservableList of Items to be the 1 in the input
     * Sets up the table view, its columns and data inputted
     * @param shoppingList An observable list containing the ingredients to be displayed in the shopping list
     */
    public ShoppingPage(ObservableList<Item<Ingredient>> shoppingList) {
        super(FXML);
        this.shoppingList = shoppingList;
        setupTable();
    }

    void setupTable() {
        shoppingListTable.setItems(shoppingList);
        shoppingListTable.getColumns().clear();
        setIndexColumn();
        setShoppingInfoColumns();
    }

    void setIndexColumn() {
        TableColumn<Item<Ingredient>, Void> indexColumn = new TableColumn<>("S/N");
        indexColumn.setResizable(true);

        //Solution below adapted from: https://stackoverflow.com/questions/31212400/adding-index-of-records-in-a-javafx-tableview-column
        ///////////////////////////////////////////////////////index column
        {
            indexColumn.setCellFactory(col -> {

                // just a default table cell:
                TableCell<Item<Ingredient>, Void> cell = new TableCell<>();

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

        shoppingListTable.getColumns().add(indexColumn);
        indexColumn.setMinWidth(50);
        indexColumn.setMaxWidth(50);
    }

    void setShoppingInfoColumns() {
        TableColumn<Item<Ingredient>, String> ingredientColumn = new TableColumn<>("Ingredient");
        ingredientColumn.setResizable(true);
        ingredientColumn.setCellValueFactory(itemStringCellDataFeatures ->
                new SimpleStringProperty(itemStringCellDataFeatures.getValue().getItem().getName()));

        TableColumn<Item<Ingredient>, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setResizable(true);
        quantityColumn.setCellValueFactory(itemStringCellDataFeatures ->
                new SimpleStringProperty(String.valueOf(itemStringCellDataFeatures.getValue().getQuantity().getNumber())));

        TableColumn<Item<Ingredient>, String> remarksColumn = new TableColumn<>("Remarks");
        remarksColumn.setResizable(true);
        remarksColumn.setCellValueFactory(itemStringCellDataFeatures ->
                new SimpleStringProperty(itemStringCellDataFeatures.getValue().getItem().getRemarks()));

        TableColumn<Item<Ingredient>, String> costColumn = new TableColumn<>("Unit Cost ($)");
        costColumn.setResizable(true);
        costColumn.setCellValueFactory(itemStringCellDataFeatures ->
                new SimpleStringProperty(String.valueOf(itemStringCellDataFeatures.getValue().getItem().getUnitPrice())));

        TableColumn<Item<Ingredient>, String> totalCostColumn = new TableColumn<>("Cost ($)");
        totalCostColumn.setResizable(true);
        totalCostColumn.setCellValueFactory(itemStringCellDataFeatures ->
                new SimpleStringProperty(String.valueOf(itemStringCellDataFeatures.getValue().getTotalPrice())));

        shoppingListTable.getColumns().addAll(ingredientColumn, quantityColumn, costColumn,
                totalCostColumn, remarksColumn);
    }
}



