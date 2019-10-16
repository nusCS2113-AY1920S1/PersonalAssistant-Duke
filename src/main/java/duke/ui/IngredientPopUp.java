package duke.ui;

import com.jfoenix.controls.JFXButton;
import duke.model.product.Product;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class IngredientPopUp extends UiPart<Stage> {

    private static final String FXML = "IngredientPopUp.fxml";

    /**
     * Constructs a UiPart with the specified FXML file URL. The FXML file must not specify the {@code
     * fx:controller} attribute.
     *
     * @param fxmlFileUrl
     */
    public IngredientPopUp(URL fxmlFileUrl) {
        super(fxmlFileUrl);
    }

    public static void setUpPopUp(Product product) {
        Stage ingredientPopUp = new Stage();
        ingredientPopUp.initModality(Modality.APPLICATION_MODAL);
        ingredientPopUp.setTitle("Product Detail");

        new ProductDetailsPage(product);


        //Scene scene1= new Scene(pop);
        //ingredientPopUp.setScene(scene1);
        //ingredientPopUp.setScene();
        ingredientPopUp.showAndWait();

    }

}
