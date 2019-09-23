package duke.ui.recipe;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Label;

public class Recipe extends AnchorPane {

    @FXML
    private Label RecipeName;
    @FXML
    private Label difficulty;
    @FXML
    private ScrollPane ingredients;
    @FXML
    private JFXListView<String> steps;

    @FXML
    public void initialize() {
        RecipeName.setText("RECIPE_NAME");
    }

}
