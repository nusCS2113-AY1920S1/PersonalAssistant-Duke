package duketest.recipecommandstest;

import duke.logic.command.recipecommands.AddRecipeCommand;
import duke.logic.command.recipecommands.EditRequiredIngredientCommand;
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

//@@author wjlingg
public class EditRequiredIngredientCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;

    @Test
    public void testEditRequiredIngredientFullInsert() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Got it. I've added this ingredient to the list of required ingredients.\n" +
                "       dumplings";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        ArrayList<String> arrayListDummy1 = new ArrayList<>(addRecipeCommand.execute(recipeList, ui, recipeStorage));

        EditRequiredIngredientCommand editRequiredIngredientCommand = new EditRequiredIngredientCommand("editreqingredient testrecipe ins/ 1 n/ dumplings q/ 10 u/g");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editRequiredIngredientCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        ArrayList<String> arrayListDummy2 = new ArrayList<>(deleteRecipeCommand.execute(recipeList, ui, recipeStorage));
    }

    @Test
    public void testEditRequiredIngredientPartialInsert() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Got it. I've added this ingredient to the list of required ingredients.\n" +
                "       dumplings";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        ArrayList<String> arrayListDummy1 = new ArrayList<>(addRecipeCommand.execute(recipeList, ui, recipeStorage));

        EditRequiredIngredientCommand editRequiredIngredientinsert = new EditRequiredIngredientCommand("editreqingredient testrecipe ins/ 1 n/ dumplings q/ 10 u/g a/ fried");
        ArrayList<String> arrayListDummy2 = new ArrayList<>(editRequiredIngredientinsert.execute(recipeList, ui, recipeStorage));

        EditRequiredIngredientCommand editRequiredIngredientCommand = new EditRequiredIngredientCommand("editreqingredient testrecipe ins/ 1 n/ dumplings q/ 5 u/a/");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editRequiredIngredientCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        ArrayList<String> arrayListDummy3 = new ArrayList<>(deleteRecipeCommand.execute(recipeList, ui, recipeStorage));
    }

    @Test
    public void testEditRequiredIngredientDelete() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Noted. I've removed this ingredient from the list of required ingredients.\n" +
                "       dumplings";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        ArrayList<String> arrayListDummy1 = new ArrayList<>(addRecipeCommand.execute(recipeList, ui, recipeStorage));

        EditRequiredIngredientCommand editRequiredIngredientinsert = new EditRequiredIngredientCommand("editreqingredient testrecipe ins/ 1 n/ dumplings q/ 10 u/g a/ fried");
        ArrayList<String> arrayListDummy2 = new ArrayList<>(editRequiredIngredientinsert.execute(recipeList, ui, recipeStorage));
        EditRequiredIngredientCommand editRequiredIngredientCommand = new EditRequiredIngredientCommand("editreqingredient testrecipe del/ 1");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editRequiredIngredientCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        ArrayList<String> arrayListDummy3 = new ArrayList<>(deleteRecipeCommand.execute(recipeList, ui, recipeStorage));
    }

    @Test
    public void testEditRequiredIngredientFullAppend() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Got it. I've added this ingredient to the list of required ingredients.\n" +
                "       dumplings";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        ArrayList<String> arrayListDummy1 = new ArrayList<>(addRecipeCommand.execute(recipeList, ui, recipeStorage));

        EditRequiredIngredientCommand editRequiredIngredientCommand = new EditRequiredIngredientCommand("editreqingredient testrecipe app/n/ dumplings q/ 10 u/g a/ fried");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editRequiredIngredientCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        ArrayList<String> arrayListDummy2 = new ArrayList<>(deleteRecipeCommand.execute(recipeList, ui, recipeStorage));
    }

    @Test
    public void testEditRequiredIngredientPartialAppend() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Got it. I've added this ingredient to the list of required ingredients.\n" +
                "       dumplings";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        ArrayList<String> arrayListDummy1 = new ArrayList<>(addRecipeCommand.execute(recipeList, ui, recipeStorage));


        EditRequiredIngredientCommand editRequiredIngredientinsert = new EditRequiredIngredientCommand("editreqingredient testrecipe app/n/ dumplings q/ 10 u/g a/ fried");
        ArrayList<String> arrayListDummy2 = new ArrayList<>(editRequiredIngredientinsert.execute(recipeList, ui, recipeStorage));

        EditRequiredIngredientCommand editRequiredIngredientCommand = new EditRequiredIngredientCommand("editreqingredient testrecipe app/n/ dumplings q/ 30 u/");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editRequiredIngredientCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        ArrayList<String> arrayListDummy3 = new ArrayList<>(deleteRecipeCommand.execute(recipeList, ui, recipeStorage));
    }

    @Test
    public void testEditRequiredIngredientClear() throws ParseException {
        ui = new Ui(mainWindow);
        recipeStorage = new RecipeStorage(filePathRecipesTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "The required ingredient list for the recipe has been cleared.";
        arrayListExpectedOutput.add(expected);

        AddRecipeCommand addRecipeCommand = new AddRecipeCommand("addrecipe testrecipe");
        ArrayList<String> arrayListDummy1 = new ArrayList<>(addRecipeCommand.execute(recipeList, ui, recipeStorage));

        EditRequiredIngredientCommand editRequiredIngredientCommand = new EditRequiredIngredientCommand("editreqingredient testrecipe clr/");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(editRequiredIngredientCommand.execute(recipeList, ui, recipeStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("deleterecipe testrecipe");
        ArrayList<String> arrayListDummy2 = new ArrayList<>(deleteRecipeCommand.execute(recipeList, ui, recipeStorage));
    }
}
