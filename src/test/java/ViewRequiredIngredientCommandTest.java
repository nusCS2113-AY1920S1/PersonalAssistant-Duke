import duke.logic.command.recipecommands.ViewRequiredIngredientCommand;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static duke.common.RecipeMessages.MESSAGE_RECIPE_TO_BE_VIEWED;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.filePathRecipesTest;

public class ViewRequiredIngredientCommandTest {
    private Ui ui;
    private MainWindow mainWindow;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    @Test
    public void testViewRequiredIngredient() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected1 = "Recipe Title: chicken rice\n" +
                "Required Ingredients: \n" +
                "1.  rice [1.0 | G | 50% brown rice] \n" +
                "2.  brown rice [1.0 | G | No additional information.] \n" +
                "3.  jasmine rice [11.0 | G | fried with garlic] \n";
        String expected2 = "Recipe Title: duck rice\n" +
                "Required Ingredients: \n" +
                "1.  jasmine rice [10.0 | G | No additional information.] \n" +
                "2.  brown rice [1.0 | G | No additional information.] \n";

        arrayListExpectedOutput.add(MESSAGE_RECIPE_TO_BE_VIEWED);
        arrayListExpectedOutput.add(expected1);
        arrayListExpectedOutput.add(expected2);
        arrayListExpectedOutput.add("\nCombined list of ingredient with the respective amount: ");
        arrayListExpectedOutput.add("1. jasmine rice | 21.0");
        arrayListExpectedOutput.add("2. rice | 1.0");
        arrayListExpectedOutput.add("3. brown rice | 2.0");

        ViewRequiredIngredientCommand viewRequiredIngredientCommand = new ViewRequiredIngredientCommand("viewreqingredient chicken rice, duck rice");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(viewRequiredIngredientCommand.execute(recipeList, ui, recipeStorage));

        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
    }
}
