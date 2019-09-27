package duke.ui;

import com.jfoenix.controls.JFXButton;
import duke.entities.Order;
import duke.logic.Duke;

///////////////////////////
import duke.entities.recipe.Ingredient;
import duke.entities.recipe.Recipe;
import duke.entities.recipe.Step;
import duke.storage.recipe.RecipeList;
import duke.ui.recipe.RecipeBox;
import duke.ui.recipe.RecipeMain;
import duke.ui.recipe.IngredientBox;
//>>>>>>> Recipe
///////////////////////////
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MainWindow extends AnchorPane {

    private Duke duke;
    private Ui ui;

    //Popup box
    @FXML
    private HBox popUp;
    @FXML
    private Label popUpLabel;
    @FXML
    JFXButton popUpButton;
    @FXML
    private TextField userInput;

    //Main page
    @FXML
    private Label currentPage;
    @FXML
    private AnchorPane pagePane;

    //Sidebar
    @FXML
    private JFXButton recipeButton;
    @FXML
    private JFXButton orderButton;
    @FXML
    private JFXButton inventoryButton;
    @FXML
    private JFXButton salesButton;

    private OrderPage orderPage;
    private RecipePage recipePage;

    @FXML
    public RecipeMain recipeMain;

    @FXML
    private VBox recipeList;


    public void initialize() {
        Ui ui = new Ui(this);
        duke = new Duke(ui);
        popUp.setVisible(false);
    }


    //Change to refresh
    void refreshRecipeList(RecipeList rpl) {
        recipeList.getChildren().clear();
        int index = 1;
        for (Recipe recipe : rpl) {
            recipeList.getChildren().add(new RecipeBox(recipe, index));
            index++;
        }
    }

    @FXML
    private void handleUserInput() {
        popUp.setVisible(false);
        String input = userInput.getText();
        duke.executeInput(input);
        userInput.clear();
    }

    @FXML
    private void handleOk() {
        popUp.setVisible(false);
    }

    @FXML
    private void handleShowRecipe() {
        System.out.println("!!!");
        showRecipePage();
    }

    @FXML
    private void handleShowOrder() {
        showOrderPage();
    }

    @FXML
    private void handleShowInventory() {

    }

    @FXML
    private void handleShowSales() {

    }

    void initializePages() {
        orderPage = new OrderPage();
        AnchorPane.setLeftAnchor(orderPage, 0.0);
        AnchorPane.setRightAnchor(orderPage, 0.0);
        AnchorPane.setTopAnchor(orderPage, 0.0);
        AnchorPane.setBottomAnchor(orderPage, 4.0);

        recipePage = new RecipePage();
        AnchorPane.setLeftAnchor(recipePage, 0.0);
        AnchorPane.setRightAnchor(recipePage, 0.0);
        AnchorPane.setTopAnchor(recipePage, 0.0);
        AnchorPane.setBottomAnchor(recipePage, 4.0);
    }

    void showMessage(String message) {
        popUpLabel.setText(message);
        popUpLabel.setTextFill(Color.valueOf("#000000"));
        popUpButton.getStyleClass().clear();
        popUpButton.getStyleClass().add("message-popup");
        popUp.getStyleClass().clear();
        popUp.getStyleClass().add("message-popup");
        popUp.setVisible(true);
    }

    void showErrorPopUp(String message) {
        popUpLabel.setText(message);
        popUpLabel.setTextFill(Color.valueOf("#ffffff"));
        popUpButton.getStyleClass().clear();
        popUpButton.getStyleClass().add("error-popup");
        popUp.getStyleClass().clear();
        popUp.getStyleClass().add("error-popup");
        popUp.setVisible(true);
    }

    void refreshOrderList(List<Order> orders, List<Order> all) {
        this.orderPage.refreshOrderList(orders, all);
    }


    void showOrderPage() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(orderPage);

        recipeButton.setButtonType(JFXButton.ButtonType.FLAT);
        orderButton.setButtonType(JFXButton.ButtonType.RAISED);
        inventoryButton.setButtonType(JFXButton.ButtonType.FLAT);
        salesButton.setButtonType(JFXButton.ButtonType.FLAT);

        currentPage.setText("Orders");
    }

    void showRecipePage() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(recipePage);

        recipeButton.setButtonType(JFXButton.ButtonType.RAISED);
        orderButton.setButtonType(JFXButton.ButtonType.FLAT);
        inventoryButton.setButtonType(JFXButton.ButtonType.FLAT);
        salesButton.setButtonType(JFXButton.ButtonType.FLAT);

        currentPage.setText("Recipes");
    }


    void disableInput() {
        userInput.setDisable(true);
    }


    @FXML
    private AnchorPane recipePane;

    @FXML
    private Label recipeName;
    @FXML
    private HBox diffLevel;
    @FXML
    private HBox ingredients;
    @FXML
    private VBox steps;

    @FXML
    private ImageView sstar;

    private Recipe recipe = new Recipe("");

    //public RecipeMain(Recipe recipe) {
    //    this.recipe = recipe;
    //}



    public String getName() {
        return recipe.getName();
    }

    public void showRecipePane() {
        Recipe recipe = new Recipe("");
        recipe.init();
        recipePane.setVisible(true);
        recipeName.setText(recipe.getName());

        String currentDir = System.getProperty("user.dir");
        try {
            Image image1 = new Image(new FileInputStream(currentDir + "\\src\\main\\resources\\images\\star.png"));
            diffLevel.getChildren().clear();
            for (int i = 0; i < recipe.getDiffLevel(); i++) {
                ImageView star = new ImageView();
                star.setImage(image1);
                star.setFitHeight(28);
                star.setPreserveRatio(true);
                diffLevel.getChildren().add(star);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        ingredients.getChildren().clear();
        for (Ingredient ingredient : recipe.getIngredients()) {
            IngredientBox ingredientBox = new IngredientBox(ingredient);
            ingredient.init();
            ingredients.getChildren().add(ingredientBox);
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
