package duketest.recipecommandstest;

import duke.logic.command.recipecommands.AddRecipeCommand;
import duke.logic.command.recipecommands.DeleteRecipeCommand;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.filePathRecipesTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddRecipeCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    @Test
    public void testAddRecipeCommand() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Got it. I've added this recipe:\n" +
                "       testrecipe\n" +
                "Now you have 5 recipe(s) in the list.";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(addRecipeCommand.execute(recipeList, ui, recipeStorage));

        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        ArrayList<String> arrayListDummy = new ArrayList<>(deleteRecipeCommand.execute(recipeList, ui, recipeStorage));
    }
}
