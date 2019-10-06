package duke.ui.recipe;

import duke.entities_decrypted.Ingredient;
import duke.ui.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class IngredientBox extends AnchorPane {
    @FXML
    private ImageView ingredientPic;
    //private ImageView ingredientPic = new ImageView();

    @FXML
    private Label ingredientName;

    private Ingredient ingredient;

    public IngredientBox(Ingredient ingredient) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/recipe/IngredientBox"
                    + ".fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ingredient.init();
        this.ingredient = ingredient;
        this.ingredientName.setText(ingredient.getName());
        //this.ingredientPic.setImage(ingredient.getPicture());
        this.ingredientPic.setFitHeight(50);
        this.ingredientPic.setPreserveRatio(true);
    }


}
