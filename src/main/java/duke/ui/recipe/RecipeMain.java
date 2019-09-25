package duke.ui.recipe;

import com.jfoenix.controls.JFXListView;
import duke.recipe.Ingredient;
import duke.recipe.Recipe;
import duke.recipe.Step;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RecipeMain extends AnchorPane {

    @FXML
    private AnchorPane recipePane;

    @FXML
    private Label recipeName;
    @FXML
    private Label difficulty_level;
    @FXML
    private VBox ingredients;
    @FXML
    private VBox steps;

    private Recipe recipe = new Recipe("");

    //public RecipeMain(Recipe recipe) {
    //    this.recipe = recipe;
    //}

    @FXML
    public void initialize() {
     //   recipe = new Recipe();
    }

    public RecipeMain() {
        //recipe = new Recipe("");
    }

    public String getName() {
        return recipe.getName();
    }

    public void showRecipePane() {
        Recipe recipe = new Recipe("");
        recipe.init();
        recipePane.setVisible(true);
        recipeName.setText(recipe.getName());
        difficulty_level.setText(Integer.toString(recipe.getDiffLevel()));

        ingredients.getChildren().clear();
        for (Ingredient ingt : recipe.getIngredients()) {
            Label newLabel = new Label();
            newLabel.setText(ingt.getName());
            ingredients.getChildren().add(newLabel);
        }

        steps.getChildren().clear();
        int index = 1;
        for (Step step: recipe.getSteps()) {
            Label newLabel = new Label();
            newLabel.setText("step " + index + step.getDescription());
            steps.getChildren().add(newLabel);
        }
    }

}
