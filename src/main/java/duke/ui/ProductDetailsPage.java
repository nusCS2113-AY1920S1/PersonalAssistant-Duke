package duke.ui;

import duke.model.product.IngredientItemList;
import duke.model.product.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ProductDetailsPage extends UiPart<AnchorPane> {

    public static final String FXML = "ProductDetailsPage.fxml";

    @FXML
    private Label productName;

    @FXML
    private AnchorPane infoPane;

    @FXML
    private AnchorPane ingredientListPane;

    @FXML
    private TableView infoTable;

    @FXML
    private TableView ingredientTable;

    private Product product = new Product("name here", "5.5", "4.4");

    public ProductDetailsPage(Product product) {
        super(FXML);
        requireNonNull(product);
        this.product = product;

        setUp();
    }

    private void setUp() {
        setUpInfoTable();
        setUpIngredientList();
    }

    private TableView setUpInfoTable() {
        List<Product> list = new ArrayList<>();
        list.add(product);
        ObservableList<Product> ObList = FXCollections.observableList(list);
       // TableView<Product> infoTable= new TableView<>();

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Retail Price");
        priceColumn.setResizable(true);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));

        TableColumn<Product, Double> costColumn = new TableColumn<>("Ingredient Cost");
        costColumn.setResizable(true);
        costColumn.setCellValueFactory(new PropertyValueFactory<>("ingredientCost"));

        TableColumn<Product, Double> statusColumn = new TableColumn<>("Status");
        statusColumn.setResizable(true);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        Label label = new Label();
        infoPane.getChildren().addAll(label);
        infoTable.getColumns();
        System.out.println("here");
        infoTable.getColumns().addAll(priceColumn,costColumn,statusColumn);

        return infoTable;
    }

    private TableView setUpIngredientList() {
        ArrayList<IngredientNameAndPortion> nameAndPortionList = new ArrayList<>();
        IngredientItemList items = product.getIngredients();
        for(int i = 0; i < items.size(); i++) {
            String name = items.get(i).getItem().getName();
            Double portion = items.get(i).getQuantity().getNumber();
            nameAndPortionList.add(new IngredientNameAndPortion(name, portion));
        }
        ObservableList<IngredientNameAndPortion> ingredientInfoList= FXCollections.observableList(nameAndPortionList);

      //  TableView<IngredientNameAndPortion> ingredientTable = new TableView<>();

        TableColumn<IngredientNameAndPortion, String> nameColumn = new TableColumn<>("Ingredient Name");
        nameColumn.setResizable(true);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<IngredientNameAndPortion, Double> portionColumn = new TableColumn<>("Portion");
        portionColumn.setResizable(true);
        portionColumn.setCellValueFactory(new PropertyValueFactory<>("portion"));

        ingredientTable.getColumns().addAll(nameColumn, portionColumn);

        return ingredientTable;
    }

    private class IngredientNameAndPortion extends ArrayList {
        public String name;
        public Double portion;

        public IngredientNameAndPortion(String name, Double portion) {
            this.name = name;
            this.portion = portion;
        }
    }


}
