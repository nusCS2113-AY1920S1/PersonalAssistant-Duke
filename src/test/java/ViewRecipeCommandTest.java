import duke.logic.command.recipecommands.ViewRecipeCommand;
import duke.logic.command.recipecommands.ViewRequiredIngredientCommand;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.filePathRecipesTest;
import static duke.common.RecipeMessages.MESSAGE_RECIPE_TO_BE_VIEWED;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewRecipeCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    @Test
    public void testViewRecipe() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Title: chicken rice\n" +
                "\n" +
                "Preparation Time: 10\n" +
                "\n" +
                "Rating: Good\n" +
                "\n" +
                "Preparation Steps: \n" +
                "1. No preparation steps provided yet.\n" +
                "\n" +
                "Required Ingredients: \n" +
                "1.  rice [1.0 | G | 50% brown rice] \n" +
                "2.  brown rice [1.0 | G | No additional information.] \n" +
                "3.  jasmine rice [11.0 | G | fried with garlic] \n" +
                "\n" +
                "Feedback: \n" +
                "the soya sauce was delicious";
        arrayListExpectedOutput.add(MESSAGE_RECIPE_TO_BE_VIEWED);
        arrayListExpectedOutput.add(expected);
        ViewRecipeCommand viewRecipeCommand = new ViewRecipeCommand("viewrecipe chicken rice");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(viewRecipeCommand.execute(recipeList, ui, recipeStorage));

        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
    }
}
