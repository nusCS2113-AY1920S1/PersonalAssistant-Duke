package duketest.recipecommandstest;

import duke.logic.command.recipecommands.AddRecipeCommand;
import duke.logic.command.recipecommands.EditFeedbackCommand;
import duke.logic.command.recipecommands.DeleteRecipeCommand;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.filePathRecipeTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author wjlingg
public class EditFeedbackCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    @Test
    public void testEditFeedbackCommand() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipeTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "The feedback of 'testrecipe' has been edited to: too salty";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        addRecipeCommand.execute(recipeList, ui, recipeStorage);

        EditFeedbackCommand editFeedbackCommand = new EditFeedbackCommand("editfeedback testrecipe f/ too salty");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editFeedbackCommand.execute(recipeList, ui, recipeStorage));

        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        deleteRecipeCommand.execute(recipeList, ui, recipeStorage);
    }
}
