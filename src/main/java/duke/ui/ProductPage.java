package duke.ui;

import duke.commons.core.LogsCenter;
import duke.commons.core.index.Index;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.MasterDetailPane;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class ProductPage extends UiPart<AnchorPane> {
    private static final String FXML = "ProductPage.fxml";
    private final Logger logger = LogsCenter.getLogger(ProductPage.class);

    @FXML
    private Label tableTitle;
    @FXML
    private MasterDetailPane productIngredientPane;

    private ObservableList<Product> productList;



    public ProductPage(ObservableList<Product> productList) {
        super(FXML);
        requireNonNull(productList);
        this.productList = productList;

        showProductList();
    }


    private void showProductList() {
        productIngredientPane.setMasterNode(setUpListTable());
        productIngredientPane.setShowDetailNode(false);
    }

    public void showProductDetail(Index index) {
        productIngredientPane.setMasterNode(setUpListTable());
        productIngredientPane.setShowDetailNode(true);
        productIngredientPane.setDividerPosition(0);
        productIngredientPane.setDetailNode(setUpIngredientTable(index));
    }
    private TableView setUpListTable() {
        TableView<Product> productListTable = new TableView<>();
        productListTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setProperty(productListTable, productList);
        setIndexColumn(productListTable);
        setProductInfoColumns(productListTable);
        return productListTable;
    }

    private void setProperty(TableView table, ObservableList observableList) {
        table.setItems(observableList);
        table.getColumns().clear();
    }
    void setIndexColumn(TableView table) {
        TableColumn<Product, Void> indexColumn = new TableColumn<>("S/N");
        indexColumn.setResizable(true);

        //Solution below adapted from: https://stackoverflow.com/questions/31212400/adding-index-of-records-in-a-javafx-tableview-column
        ///////////////////////////////////////////////////////index column
        {
            indexColumn.setCellFactory(col -> {

                // just a default table cell:
                TableCell<Product, Void> cell = new TableCell<>();

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
        table.getColumns().add(indexColumn);
        indexColumn.setMinWidth(80);
        indexColumn.setMaxWidth(80);

    }

    void setProductInfoColumns(TableView table) {
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setResizable(true);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Retail Price");
        priceColumn.setResizable(true);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));

        TableColumn<Product, Double> costColumn = new TableColumn<>("Ingredient Cost");
        costColumn.setResizable(true);
        costColumn.setCellValueFactory(new PropertyValueFactory<>("ingredientCost"));

        TableColumn<Product, Double> statusColumn = new TableColumn<>("status");
        statusColumn.setResizable(true);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(nameColumn, priceColumn,
                costColumn, statusColumn);
    }

    public TableView setUpIngredientTable(Index index) {
        TableView<Item<Ingredient>> ingredientTable = new TableView<>();
        ingredientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Product product = productList.get(index.getZeroBased());
        ObservableList<Item<Ingredient>> ingredientObservableList = FXCollections.observableList(product.getIngredients());
        ingredientTable.setItems(ingredientObservableList);
        setIndexColumn(ingredientTable);

        TableColumn<Item<Ingredient>, String> ingredientColumn = new TableColumn<>("Ingredient");
        ingredientColumn.setResizable(true);
        ingredientColumn.setCellValueFactory(itemStringCellDataFeatures ->
                new SimpleStringProperty(itemStringCellDataFeatures.getValue().getItem().getName()));

        TableColumn<Item<Ingredient>, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setResizable(true);
        quantityColumn.setCellValueFactory(itemStringCellDataFeatures ->
                new SimpleStringProperty(String.valueOf(itemStringCellDataFeatures.getValue().getQuantity().getNumber())));

        ingredientTable.getColumns().addAll(ingredientColumn, quantityColumn);
        return ingredientTable;
    }


}
