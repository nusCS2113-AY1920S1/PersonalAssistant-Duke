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
    private Label stock;
    @FXML
    private Label expiry;

    @FXML
    private Button edit;
    @FXML
    private Button sell;

    public FoodListCard(Food food, int displayedIndex, SellExecutor sellExecutor, EditExecutor editExecutor) {
        super(FXML);
        this.food = food;
        this.index = displayedIndex;

        name.setText(food.getName());
        id.setText(displayedIndex + ". ");
        try {
            tags.getChildren().add(new Label(food.getType()));
            price.setText("Price: $" + food.getPrice());
            stock.setText("Stock: " + food.getStock());
            expiry.setText("Expiry: " + ParserUtil.parseDateToString(food.getExpiryDate()));
        } catch (NullPointerException e) {
            tags.getChildren().setAll(new Label("Uncategorized"));
            price.setText("Price: $" + "0.00");
            stock.setText("Stock: " + "0");
            expiry.setText("Expiry: " + "Not Specified");
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
