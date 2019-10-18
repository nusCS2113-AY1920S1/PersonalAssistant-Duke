package duke.ui;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.Product;
import duke.ui.commons.TableUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class IngredientCard extends AnchorPane {

    private Product product;

    @FXML
    private Label productName;

    @FXML
    private AnchorPane tablePane;

    /**
     * Creates an Ingredient Card with the given product, to show the ingredients in the product
     * @param product
     */
    public IngredientCard(Product product) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/IngredientCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.product = product;
        setUpCard();
       // config(this);
    }

    private void config(Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
    }

    private void setUpCard() {
        productName.setText(product.getProductName());
        tablePane.getChildren().add(getIngredientTable());
    }

    private TableView<Item<Ingredient>> getIngredientTable() {

        javafx.scene.control.TableView<Item<Ingredient>> ingredientTable = new javafx.scene.control.TableView<>();
        config(ingredientTable);
        ingredientTable.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<Item<Ingredient>> ingredientObservableList = FXCollections.observableList(product.getIngredients());
        ingredientTable.setItems(ingredientObservableList);
        TableUtil.setUpIndexColumn(ingredientTable);

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
