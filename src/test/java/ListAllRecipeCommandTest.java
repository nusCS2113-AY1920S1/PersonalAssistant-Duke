import duke.logic.command.recipecommands.ListAllRecipeCommand;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static duke.common.Messages.filePathRecipesTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import static duke.common.RecipeMessages.MESSAGE_HERE_ARE_THE_RECIPES;

public class ListAllRecipeCommandTest {

    private Ui ui;
        private MainWindow mainWindow;
        private RecipeStorage recipeStorage;
        private RecipeList recipeList;

        @Test
        public void testListAllRecipeCommand() {
            ui = new Ui(mainWindow);
            recipeStorage = new RecipeStorage(filePathRecipesTest);
            recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        arrayListExpectedOutput.add(MESSAGE_HERE_ARE_THE_RECIPES);
        arrayListExpectedOutput.add("1. chicken rice");
        arrayListExpectedOutput.add("2. duck rice");
        arrayListExpectedOutput.add("3. rice");
        arrayListExpectedOutput.add("4. sphagetti");

        ListAllRecipeCommand listAllRecipeCommand = new ListAllRecipeCommand("listallrecipes");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(listAllRecipeCommand.execute(recipeList, ui, recipeStorage));

        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
    }
}
