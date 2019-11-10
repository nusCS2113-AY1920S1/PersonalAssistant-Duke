package duketest.recipecommandstest;

import duke.logic.command.recipecommands.AddRecipeCommand;
import duke.logic.command.recipecommands.DeleteRecipeCommand;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static duke.common.Messages.filePathRecipeTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author wjlingg
public class AddRecipeCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    @Test
    public void testAddRecipeCommand() {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipeTest);
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
        deleteRecipeCommand.execute(recipeList, ui, recipeStorage);
    }
}
