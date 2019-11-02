package cube.ui;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.ui.FoodListCard.DeleteExecutor;
import cube.ui.FoodListCard.EditExecutor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ListPanel extends UiManager<ListView> {
    private static final String FXML = "ListPanel.fxml";

    private final EditExecutor editExecutor;
    private final DeleteExecutor deleteExecutor;

    @FXML
    private ListView<Food> productListView;

    public ListPanel(FoodList foodList, EditExecutor editExecutor, DeleteExecutor deleteExecutor) {
        super(FXML);
        ObservableList<Food> observableList = FXCollections.observableArrayList(foodList.getFoodList());
        productListView.setItems(observableList);
        productListView.setCellFactory(listView -> new ListViewCell());

        this.editExecutor = editExecutor;
        this.deleteExecutor = deleteExecutor;
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
                setGraphic(new FoodListCard(food, getIndex() + 1, editExecutor, deleteExecutor).getRoot());
            }
        }
    }

}
