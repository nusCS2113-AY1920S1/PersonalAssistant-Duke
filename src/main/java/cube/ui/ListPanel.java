/**
 * Handles the content to be shown at the listcell, such as the list of products.
 *
 * @author kuromono
 */

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

    /**
     * Main Constructor for ListPanel.
     *
     * @param foodList     The food list to be shown and initialised.
     * @param sellExecutor Function containing the sell button logic.
     * @param editExecutor Function containing the edit button logic.
     */
    public ListPanel(FoodList foodList, SellExecutor sellExecutor, EditExecutor editExecutor) {
        super(FXML);
        ObservableList<Food> observableList = FXCollections.observableArrayList(foodList.getFoodList());
        productListView.setItems(observableList);
        productListView.setCellFactory(listView -> new ListViewCell());

        this.sellExecutor = sellExecutor;
        this.editExecutor = editExecutor;
    }

    /**
     * Updates the food list that is shown in the list cell.
     *
     * @param foodList The food list to be updated with.
     */
    public void updateProductList(FoodList foodList) {
        ObservableList<Food> observableList = FXCollections.observableArrayList(foodList.getFoodList());
        productListView.setItems(observableList);
    }

    /**
     * Overrides the ListViewCell in ListCell to show a custom format.
     */
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
