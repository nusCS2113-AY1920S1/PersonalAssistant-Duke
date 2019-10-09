package duke.ui;

import duke.model.ingredient.Ingredient;
import duke.model.product.Product;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;

public class ProductPage extends UiPart<AnchorPane> {
    private static final String FXML = "RecipePage.fxml";

    @FXML
    private Label tableTitle;
    @FXML
    private TableView productListTable;
    @FXML
    private TableView showProductTable;


    public ProductPage() {
        super(FXML);

        Product product = new Product("");
        product.init();
        List<Product> rpl = new ArrayList<>();
        rpl.add(product);
    }


/*


    //RecipeList
    void refreshRecipeListPage(List<Product> rpl) {
        recipeListPane.setVisible(true);
        recipeShowPane.setVisible(false);
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        for (Product product : rpl) {
            productObservableList.add(product);
        }

        recipeTable.getColumns().clear();
        if (rpl.size() != 0) {

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

            TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setMinWidth(150);
            nameColumn.setPrefWidth(500);
            nameColumn.setResizable(true);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Product, Integer> timeColumn = new TableColumn<>("Time");
            timeColumn.setMinWidth(50);
            timeColumn.setPrefWidth(100);
            timeColumn.setResizable(true);
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

            TableColumn<Product, Double> costColumn = new TableColumn<>("Cost");
            costColumn.setMinWidth(50);
            costColumn.setPrefWidth(70);
            costColumn.setResizable(true);
            costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

            TableColumn<Product, Integer> difficultyLevelColumn = new TableColumn<>("Difficulty Level");
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
