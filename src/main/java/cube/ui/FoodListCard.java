/**
 * Handles the content of a single list cell to be shown in the ListPanel.
 *
 * @author kuromono
 */

package cube.ui;

import cube.logic.parser.ParserUtil;
import cube.model.food.Food;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class FoodListCard extends UiManager<HBox> {
    private static final String FXML = "FoodListCard.fxml";

    private Food food;
    private int index;

    private final SellExecutor sellExecutor;
    private final EditExecutor editExecutor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private FlowPane tags;
    @FXML
    private Label id;
    @FXML
    private Label price;
    @FXML
    private Label cost;
    @FXML
    private Label stock;
    @FXML
    private Label expiry;

    @FXML
    private Button edit;
    @FXML
    private Button sell;

    /**
     * Main constructor for FoodListCard.
     * Initialise the values to be shown.
     *
     * @param food           Food item to be displayed.
     * @param displayedIndex Index to be displayed.
     * @param sellExecutor   Function that contains the logic for sell button.
     * @param editExecutor   Function that contains the logic for edit button.
     */
    public FoodListCard(Food food, int displayedIndex, SellExecutor sellExecutor, EditExecutor editExecutor) {
        super(FXML);
        this.food = food;
        this.index = displayedIndex;

        name.setText(food.getName());
        id.setText(displayedIndex + ". ");

        if (food.getType() != "") {
            tags.getChildren().add(new Label(food.getType()));
        } else {
            tags.getChildren().setAll(new Label("Uncategorized"));
        }

        price.setText("Price: $" + food.getPrice());
        cost.setText("Cost: $" + food.getCost());
        stock.setText("Stock: " + food.getStock());
        expiry.setText("Expiry: " + "Not Specified");

        if (food.getExpiryDate() != null) {
            expiry.setText("Expiry: " + ParserUtil.parseDateToString(food.getExpiryDate()));
        }

        this.sellExecutor = sellExecutor;
        this.editExecutor = editExecutor;
    }

    @FXML
    private void handleEdit() {
        editExecutor.execute(index);
    }

    @FXML
    private void handleSell() {
        sellExecutor.execute(index);
    }

    /**
     * Represents a function that can execute delete commands.
     */
    @FunctionalInterface
    public interface EditExecutor {
        /**
         * Executes the command and returns the result.
         */
        void execute(int index);
    }

    /**
     * Represents a function that can execute edit commands.
     */
    @FunctionalInterface
    public interface SellExecutor {
        /**
         * Executes the command and returns the result.
         */
        void execute(int index);
    }

}
