package duketest.recipecommandstest;

import duke.logic.command.recipecommands.AddRecipeCommand;
import duke.logic.command.recipecommands.EditPrepStepCommand;
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
public class EditPrepStepCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    @Test
    public void testEditPrepStepInsert() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipeTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Got it. I've added this preparation step to the list of prep steps.\n" +
                "       rinse";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        addRecipeCommand.execute(recipeList, ui, recipeStorage);

        EditPrepStepCommand editPrepStepCommand = new EditPrepStepCommand("editprepstep testrecipe ins/ 1 step/ rinse");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editPrepStepCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        deleteRecipeCommand.execute(recipeList, ui, recipeStorage);
    }

    @Test
    public void testEditPrepStepDelete() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipeTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Noted. I've removed this preparation step from the list of prep steps.\n" +
                "       rinse";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        addRecipeCommand.execute(recipeList, ui, recipeStorage);

        EditPrepStepCommand editPrepStepinsert = new EditPrepStepCommand("editprepstep testrecipe ins/ 1 step/ rinse");
        editPrepStepinsert.execute(recipeList, ui, recipeStorage);
        EditPrepStepCommand editPrepStepCommand = new EditPrepStepCommand("editprepstep testrecipe del/ 1");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editPrepStepCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        deleteRecipeCommand.execute(recipeList, ui, recipeStorage);
    }

    @Test
    public void testEditPrepStepAppend() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipeTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Got it. I've added this preparation step to the list of prep steps.\n" +
                "       rinse";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        addRecipeCommand.execute(recipeList, ui, recipeStorage);

        EditPrepStepCommand editPrepStepCommand = new EditPrepStepCommand("editprepstep testrecipe app/step/ rinse");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editPrepStepCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        deleteRecipeCommand.execute(recipeList, ui, recipeStorage);
    }

    @Test
    public void testEditPrepStepClear() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipeTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "The required prep step list for the recipe has been cleared.";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        addRecipeCommand.execute(recipeList, ui, recipeStorage);

        EditPrepStepCommand editPrepStepCommand = new EditPrepStepCommand("editprepstep testrecipe clr/");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editPrepStepCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        deleteRecipeCommand.execute(recipeList, ui, recipeStorage);
    }
}
