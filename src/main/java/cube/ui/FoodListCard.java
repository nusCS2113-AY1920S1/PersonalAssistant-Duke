package cube.ui;

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

    private final EditExecutor editExecutor;
    private final DeleteExecutor deleteExecutor;

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
    private Button delete;

    public FoodListCard(Food food, int displayedIndex, EditExecutor editExecutor, DeleteExecutor deleteExecutor) {
        super(FXML);
        this.food = food;
        this.index = displayedIndex;

        name.setText(food.getName());
        id.setText(displayedIndex + ". ");
        tags.getChildren().add(new Label(food.getType()));
        price.setText("Price: $" + food.getPrice());
        stock.setText("Stock: " + food.getStock());
        expiry.setText("Expiry: " + food.getExpiryDate().toString());

        this.editExecutor = editExecutor;
        this.deleteExecutor = deleteExecutor;
    }

    @FXML
    private void handleEdit() {
        editExecutor.execute(index);
    }

    @FXML
    private void handleDelete() {
        deleteExecutor.execute(index);
    }

    /**
     * Represents a function that can execute edit commands.
     */
    @FunctionalInterface
    public interface EditExecutor {
        /**
         * Executes the command and returns the result.
         */
        void execute(int index);
    }

    /**
     * Represents a function that can execute delete commands.
     */
    @FunctionalInterface
    public interface DeleteExecutor {
        /**
         * Executes the command and returns the result.
         */
        void execute(int index);
    }
}
