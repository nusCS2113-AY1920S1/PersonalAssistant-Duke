package duke.ui;

import duke.commons.core.LogsCenter;
import duke.commons.core.index.Index;
import duke.model.product.Product;
import duke.ui.commons.TableUtil;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
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
    private final Double FULL_PAGE = 1.0;
    private final Double HALF_PAGE = 0.5;
    private final Logger logger = LogsCenter.getLogger(ProductPage.class);

    @FXML
    private Label tableTitle;
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane listPane;
    @FXML
    private AnchorPane cardPane;

    private ObservableList<Product> productList;

    public ProductPage(ObservableList<Product> productList) {
        super(FXML);
        requireNonNull(productList);
        this.productList = productList;
        showProductList();
    }


    private void showProductList() {
        listPane.getChildren().add(setUpListTable());
        splitPane.setDividerPositions(FULL_PAGE);
    }

    public void showProductDetail(Index index) {
        Product product = productList.get(index.getZeroBased());
        IngredientCard ingredientCard = new IngredientCard(product);
        config(ingredientCard);
        AnchorPane.setRightAnchor(ingredientCard, 0.0);
        cardPane.getChildren().clear();
        cardPane.getChildren().add(ingredientCard);
        splitPane.setDividerPositions(HALF_PAGE);
    }

    private void config(Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
    }
    private TableView setUpListTable() {
        TableView<Product> productListTable = new TableView<>();
        config(productListTable);
        productListTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setProperty(productListTable, productList);
        TableUtil.setUpIndexColumn(productListTable);
        setProductInfoColumns(productListTable);
        return productListTable;
    }

    private void setProperty(TableView table, ObservableList observableList) {
        table.setItems(observableList);
        table.getColumns().clear();
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

    private IngredientCard getIngredientCard(Index index) {
        Product product = productList.get(index.getZeroBased());
        return new IngredientCard(product);
    }


}
