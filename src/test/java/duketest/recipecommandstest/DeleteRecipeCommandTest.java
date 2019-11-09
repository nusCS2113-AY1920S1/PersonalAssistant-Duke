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

public class DeleteRecipeCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    @Test
    public void testDeleteRecipeCommand() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Noted. I've removed this recipe:\n" +
                "       testrecipe\n" +
                "Now you have 4 recipe(s) in the list.";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        ArrayList<String> arrayListDummy = new ArrayList<>(addRecipeCommand.execute(recipeList, ui, recipeStorage));

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(deleteRecipeCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
    }
}
