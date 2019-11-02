package cube.ui;

import cube.model.food.Food;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FoodListCard extends UiManager<HBox> {
    private static final String FXML = "FoodListCard.fxml";

    private Food food;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label type;
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

    public FoodListCard(Food food, int displayedIndex) {
        super(FXML);


        this.food = food;
        name.setText(food.getName());
        id.setText(displayedIndex + ". ");
        type.setText("Type: " + food.getType());
        price.setText("Price: $" + food.getPrice());
        stock.setText("Stock: " + food.getStock());
        expiry.setText("Expiry Date: " + food.getExpiryDate().toString());
    }
}
