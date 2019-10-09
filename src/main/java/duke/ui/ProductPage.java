package duke.ui;

import duke.commons.core.LogsCenter;
import duke.model.ingredient.Ingredient;
import duke.model.order.Order;
import duke.model.product.Product;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;
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



/*

    //RecipeList
    void refreshRecipeListPage(List<comProduct> rpl) {
        recipeListPane.setVisible(true);
        recipeShowPane.setVisible(false);
        ObservableList<comProduct> productObservableList = FXCollections.observableArrayList();
        for (comProduct product : rpl) {
            productObservableList.add(product);
        }

        recipeTable.getColumns().clear();
        if (rpl.size() != 0) {

            TableColumn<comProduct, Void> indexColumn = new TableColumn<>("S/N");
            indexColumn.setMinWidth(50);
            indexColumn.setPrefWidth(70);
            indexColumn.setResizable(true);


            //Solution below adapted from: https://stackoverflow.com/questions/31212400/adding-index-of-records-in-a-javafx-tableview-column
            ///////////////////////////////////////////////////////index column
            {
                indexColumn.setCellFactory(col -> {

                    // just a default table cell:
                    TableCell<comProduct, Void> cell = new TableCell<>();

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

            TableColumn<comProduct, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setMinWidth(150);
            nameColumn.setPrefWidth(500);
            nameColumn.setResizable(true);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<comProduct, Integer> timeColumn = new TableColumn<>("Time");
            timeColumn.setMinWidth(50);
            timeColumn.setPrefWidth(100);
            timeColumn.setResizable(true);
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

            TableColumn<comProduct, Double> costColumn = new TableColumn<>("Cost");
            costColumn.setMinWidth(50);
            costColumn.setPrefWidth(70);
            costColumn.setResizable(true);
            costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

            TableColumn<comProduct, Integer> difficultyLevelColumn = new TableColumn<>("Difficulty Level");
            difficultyLevelColumn.setMinWidth(70);
            difficultyLevelColumn.setPrefWidth(300);
            difficultyLevelColumn.setResizable(true);
            difficultyLevelColumn.setCellValueFactory(new PropertyValueFactory<>("difficultyLevel"));

            recipeTable.setItems(productObservableList);
            recipeTable.getColumns().addAll(indexColumn, nameColumn, timeColumn, costColumn,
                    difficultyLevelColumn);
        }
    }
*/

}
