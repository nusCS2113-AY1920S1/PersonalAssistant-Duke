package duke.testutil;


import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.BakingHome;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.Product;

import java.util.ArrayList;
import java.util.List;

import static duke.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult actualResult = command.execute(actualModel);
            assertEquals(expectedCommandResult, actualResult);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    public static void assertCommandFailure(Command command, Model actualModel,
                                            String expectedMessage) {
        BakingHome expectedBakingHome = new BakingHome((actualModel.getBakingHome()));
        List<Item<Ingredient>> expectedFilteredInventoryList =
            new ArrayList<>(actualModel.getFilteredInventoryList());
        // Can add more lists here if needed
        List<Product> expectedFilteredProductList = new ArrayList<>(actualModel.getFilteredProductList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        //BakingHomeModel should not be changed
        assertEquals(expectedBakingHome, actualModel.getBakingHome());
        assertEquals(expectedFilteredProductList, actualModel.getFilteredProductList());
        assertEquals(expectedFilteredInventoryList, actualModel.getFilteredInventoryList());

    }
}
