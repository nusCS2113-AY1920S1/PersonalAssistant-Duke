package duketest.inventorycommandstest;

import duke.logic.command.inventorycommands.AddToInventoryCommand;
import duke.logic.command.inventorycommands.ListInventoryCommand;
import duke.logic.command.inventorycommands.UseRecipeCommand;
import duke.logic.command.inventorycommands.DeleteFromInventoryCommand;
import duke.logic.command.recipecommands.AddRecipeCommand;
import duke.logic.command.recipecommands.DeleteRecipeCommand;
import duke.logic.command.recipecommands.EditRequiredIngredientCommand;
import duke.model.list.inventorylist.InventoryList;
import duke.model.list.recipelist.RecipeList;
import duke.storage.InventoryStorage;
import duke.storage.RecipeStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UseRecipeCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private InventoryStorage inventoryStorage;
    private InventoryList inventoryList;
    private RecipeStorage recipeStorage;
    private RecipeList recipeList;


    @Test
    public void testUserRecipe() {
        ui = new Ui(mainWindow);
        inventoryStorage = new InventoryStorage(filePathInventoryTest);
        inventoryList = new InventoryList(inventoryStorage.load());
        recipeStorage = new RecipeStorage(filePathTempTest);
        recipeList = new RecipeList(recipeStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Your inventory list has been updated.";
        arrayListExpectedOutput.add(expected);

        UseRecipeCommand useRecipeCommand = new UseRecipeCommand("userecipe cake");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(useRecipeCommand.execute(inventoryList, recipeList, inventoryStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
    }
}
