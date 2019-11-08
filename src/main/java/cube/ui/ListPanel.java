package cube.ui;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.ui.FoodListCard.EditExecutor;
import cube.ui.FoodListCard.SellExecutor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ListPanel extends UiManager<ListView> {
    private static final String FXML = "ListPanel.fxml";

    private final SellExecutor sellExecutor;
    private final EditExecutor editExecutor;

    @FXML
    private ListView<Food> productListView;

    public ListPanel(FoodList foodList, SellExecutor sellExecutor, EditExecutor editExecutor) {
        super(FXML);
        ObservableList<Food> observableList = FXCollections.observableArrayList(foodList.getFoodList());
        productListView.setItems(observableList);
        productListView.setCellFactory(listView -> new ListViewCell());

        this.sellExecutor = sellExecutor;
        this.editExecutor = editExecutor;
    }

    public void updateProductList(FoodList foodList) {
        ObservableList<Food> observableList = FXCollections.observableArrayList(foodList.getFoodList());
        productListView.setItems(observableList);
    }

    class ListViewCell extends ListCell<Food> {
        @Override
        protected void updateItem(Food food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodListCard(food, getIndex() + 1, sellExecutor, editExecutor).getRoot());
            }
        }
    }

}
