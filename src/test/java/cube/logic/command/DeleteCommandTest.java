package cube.logic.command;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.DeleteCommand.DeleteBy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static cube.testutil.Assert.assertThrowEquals;

public class DeleteCommandTest {
	private class FoodStub extends Food{
	}


    /** 
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult 
     * Storage not tested.
     */
    @Test
    public void execute_valid_index() throws CommandException{
        ModelManager model = new ModelManager();
        FoodList list = model.getFoodList();
        Food toDelete = new Food("anyName");
        list.add(toDelete);
        DeleteCommand command = new DeleteCommand(1, "INDEX");
        CommandResult result = command.execute(model, new StorageManager());

        FoodList expectedList = new FoodList();
        CommandResult expectedResult = new CommandResult(String.format(DeleteCommand.MESSAGE_SUCCESS_SINGLE, toDelete, 0));
        assertEquals(model.getFoodList(), expectedList);
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_invalid_index() {
        DeleteCommand deleteTooLargeIndex = new DeleteCommand(1, "INDEX");
        assertThrowEquals(CommandException.class, CommandErrorMessage.INVALID_INDEX, () -> {deleteTooLargeIndex.execute(new ModelManager(), new StorageManager());});
        DeleteCommand deleteNegativeIndex = new DeleteCommand(-1, "INDEX");
        assertThrowEquals(CommandException.class, CommandErrorMessage.INVALID_INDEX, () -> {deleteNegativeIndex.execute(new ModelManager(), new StorageManager());});
    }

    /** 
     * Dependent on correct implementation of following class:
     *   ModelManager, StorageManager, FoodList, Food, CommandResult 
     * Storage not tested.
     */
    @Test 
    public void execute_valid_name() throws CommandException {
        ModelManager model = new ModelManager();
        FoodList list = model.getFoodList();
        Food toDelete = new Food("anyName");
        list.add(toDelete);
        DeleteCommand command = new DeleteCommand("anyName", "NAME");
        CommandResult result = command.execute(model, new StorageManager());

        FoodList expectedList = new FoodList();
        CommandResult expectedResult = new CommandResult(String.format(DeleteCommand.MESSAGE_SUCCESS_SINGLE, toDelete, 0));
        assertEquals(model.getFoodList(), expectedList);
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_invalid_name() {
        DeleteCommand deleteTooLargeIndex = new DeleteCommand("anyName", "NAME");
        assertThrowEquals(CommandException.class, CommandErrorMessage.FOOD_NOT_EXISTS, () -> {deleteTooLargeIndex.execute(new ModelManager(), new StorageManager());});
    }

    /** 
     * Dependent on correct implementation of following class:
     *   ModelManager, StorageManager, FoodList, Food, CommandResult 
     * Storage not tested.
     */
    @Test 
    public void execute_valid_type() throws CommandException {
        ModelManager model = new ModelManager();
        FoodList list = model.getFoodList();
        Food toDelete = new Food("anyName");
        toDelete.setType("anyType");
        list.add(toDelete);
        DeleteCommand command = new DeleteCommand("anyType", "TYPE");
        CommandResult result = command.execute(model, new StorageManager());

        FoodList expectedList = new FoodList();
        CommandResult expectedResult = new CommandResult(String.format(DeleteCommand.MESSAGE_SUCCESS_MULTIPLE, "anyType", 1, 0));
        assertEquals(model.getFoodList(), expectedList);
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_invalid_type() {
        DeleteCommand deleteTooLargeIndex = new DeleteCommand("anyType", "TYPE");
        assertThrowEquals(CommandException.class, CommandErrorMessage.INVALID_TYPE, () -> {deleteTooLargeIndex.execute(new ModelManager(), new StorageManager());});
    }
}
