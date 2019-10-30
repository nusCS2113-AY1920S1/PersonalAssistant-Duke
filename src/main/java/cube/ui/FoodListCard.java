package cube.ui;

import cube.model.food.Food;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;

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

    public FoodListCard(Food food, int displayedIndex) {
        super(FXML);
        this.food = food;
        name.setText(food.getName());
        id.setText(displayedIndex + ". ");
        type.setText(food.getType());
    }
}
