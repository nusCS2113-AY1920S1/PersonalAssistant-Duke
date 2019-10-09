package duke.ui;

import duke.commons.core.LogsCenter;
import duke.model.product.Product;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class ProductPage extends UiPart<AnchorPane> {
    private static final String FXML = "ProductPage.fxml";
    private final Logger logger = LogsCenter.getLogger(ProductPage.class);

    @FXML
    private Label tableTitle;
    @FXML
    private TableView productListTable;

    private ObservableList<Product> productList;

    public ProductPage(ObservableList<Product> productList) {
        super(FXML);
        requireNonNull(productList);
        this.productList = productList;

        setupTable();
    }

    void setupTable() {
        productListTable.setItems(productList);
        productListTable.getColumns().clear();
        setIndexColumn();
        setProductInfoColumns();
    }

    void setIndexColumn() {
        TableColumn<Product, Void> indexColumn = new TableColumn<>("S/N");
        indexColumn.setMinWidth(50);
        indexColumn.setPrefWidth(70);
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
        productListTable.getColumns().add(indexColumn);
    }

    void setProductInfoColumns() {
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setResizable(true);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Retail Price");
        priceColumn.setResizable(true);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Double> costColumn = new TableColumn<>("Ingredient Cost");
        costColumn.setResizable(true);
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        productListTable.getColumns().addAll(nameColumn, priceColumn,
                costColumn);
    }

}
