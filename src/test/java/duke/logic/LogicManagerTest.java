package duke.logic;

import duke.commons.core.Message;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.InventoryMessageUtils;
import duke.logic.message.ProductMessageUtils;
import duke.logic.message.ShoppingMessageUtils;
import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import duke.model.ModelManager;
import duke.storage.JsonBakingHomeStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static duke.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogicManagerTest {
    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonBakingHomeStorage bakingHomeStorage = new JsonBakingHomeStorage(temporaryFolder.resolve(
            "bakingHome.json"));
        logic = LogicManager.getInstance(model, bakingHomeStorage);
    }

    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getBakingHome());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    @Test
    public void execute_invalidCommand_throwParseException() {
        assertParseException("jdkjdkjdkjdk", Message.MESSAGE_UNKNOWN_COMMAND);
        assertParseException("---%%%", Message.MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void execute_validCommand_success() throws CommandException {
        assertCommandSuccess("inv", InventoryMessageUtils.MESSAGE_SUCCESS_SHOW_INVENTORY, model);
        assertCommandSuccess("shop", ShoppingMessageUtils.MESSAGE_SUCCESS_SHOW_SHOPPING, model);
        assertCommandSuccess("product", ProductMessageUtils.MESSAGE_SUCCESS_SHOW_PRODUCT, model);
        assertCommandSuccess("  product   ", ProductMessageUtils.MESSAGE_SUCCESS_SHOW_PRODUCT, model);

        assertCommandSuccess("inv clear", InventoryMessageUtils.MESSAGE_SUCCESS_CLEAR_INVENTORY, model);
        assertCommandSuccess("product sort -by name -scope active -re",
            String.format(ProductMessageUtils.MESSAGE_SORT_PRODUCT_SUCCESS, "NAME"), model);
        assertCommandSuccess("product add -name _name",
            String.format(ProductMessageUtils.MESSAGE_ADD_PRODUCT_SUCCESS, "_name"), model);
        assertCommandSuccess("product search -include a",
            String.format(ProductMessageUtils.MESSAGE_SEARCH_PRODUCT_SHOWN, "a"), model);
    }
}
