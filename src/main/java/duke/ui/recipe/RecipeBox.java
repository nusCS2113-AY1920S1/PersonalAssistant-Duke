package duke.ui.recipe;

import duke.recipe.Recipe;
import duke.ui.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class RecipeBox extends AnchorPane {
    @FXML
    private Label index;
    @FXML
    private Label name;
    @FXML
    private Label time;
    @FXML
    private Label cost;
    @FXML
    private HBox diffLevel;

    public RecipeBox(Recipe recipe, int indexNumber) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/recipe/RecipeBox"
                    + ".fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        index.setText(indexNumber + ". ");
        name.setText(recipe.getName());
        time.setText(Integer.toString(recipe.getTime()));
        cost.setText(Double.toString(recipe.getCost()));
        for (int i = 0; i < recipe.getDiffLevel(); i++)
        {
            diffLevel.getChildren().add(new Button("Button " + (int)(i + 1)));
        }
    }
}
