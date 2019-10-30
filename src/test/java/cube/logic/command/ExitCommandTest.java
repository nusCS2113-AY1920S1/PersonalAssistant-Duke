package cube.logic.command;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static cube.testutil.Assert.assertThrowEquals;

public class ExitCommandTest {

    @Test
    public void isExitTest() {
        ExitCommand command = new ExitCommand();
    	assertEquals(command.isExit(), true);
    }

    /** 
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult 
     * Storage not tested.
     */
    @Test
    public void execute_exit_successful() throws CommandException {
    	ExitCommand command = new ExitCommand();
    	CommandResult result = command.execute(new ModelManager(), new StorageManager());

    	CommandResult expectedResult = new CommandResult(ExitCommand.MESSAGE_SUCCESS, false, true);
    	assertEquals(result, expectedResult);
    }

}
