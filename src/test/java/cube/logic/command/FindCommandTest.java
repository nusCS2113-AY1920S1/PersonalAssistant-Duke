package cube.logic.command;

import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;

import static cube.testutil.Assert.assertThrowEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FindCommandTest {

    @Test
    public void construct_findType_successfully() {
        FindCommand.FindBy testEnumValue = FindCommand.FindBy.INDEX;
        String testType = "INDEX";
        int testIndex = 1;
        FindCommand command = new FindCommand(testIndex,testType);
        assertEquals(command.getParam(), testEnumValue);
    }

    /**
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult.
     * Storage not tested.
     */
    @Test
    public void execute_valid_index() throws CommandException {
        ModelManager model = new ModelManager();
        FoodList list = model.getFoodList();
        Food toFind = new Food("test");
        list.add(toFind);
        FindCommand command = new FindCommand(1, "INDEX");
        CommandResult result = command.execute(model, new StorageManager());

        CommandResult expectedResult = new CommandResult(
                String.format(FindCommand.MESSAGE_SUCCESS, toFind));
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_invalid_index() {
        FindCommand findTooLargeIndex = new FindCommand(1, "INDEX");
        assertThrowEquals(CommandException.class,
                CommandErrorMessage.INVALID_INDEX, () -> {
                    findTooLargeIndex.execute(new ModelManager(), new StorageManager());
                });
        FindCommand findNegativeIndex = new FindCommand(-1, "INDEX");
        assertThrowEquals(CommandException.class,
                CommandErrorMessage.INVALID_INDEX, () -> {
                    findNegativeIndex.execute(new ModelManager(), new StorageManager());
                });
    }

    @Test
    public void execute_valid_name() throws CommandException {
        ModelManager model = new ModelManager();
        FoodList list = model.getFoodList();
        Food toFind = new Food("test");
        list.add(toFind);
        FindCommand command = new FindCommand("test", "NAME");
        CommandResult result = command.execute(model, new StorageManager());

        FoodList expectedList = new FoodList();
        expectedList.add(toFind);
        CommandResult expectedResult = new CommandResult(
                String.format(FindCommand.MESSAGE_SUCCESS_M, 1, expectedList));
        assertEquals(model.getFoodList(), expectedList);
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_invalid_name() {
        FindCommand findInvalidName = new FindCommand("test", "NAME");
        assertThrowEquals(CommandException.class,
                CommandErrorMessage.FOOD_NOT_EXISTS, () -> {
                    findInvalidName.execute(new ModelManager(), new StorageManager());
                });
    }

    @Test
    public void execute_valid_type() throws CommandException {
        ModelManager model = new ModelManager();
        FoodList list = model.getFoodList();
        Food toFind = new Food("test");
        toFind.setType("test");
        list.add(toFind);
        FindCommand command = new FindCommand("test", "TYPE");
        CommandResult result = command.execute(model, new StorageManager());

        FoodList expectedList = new FoodList();
        expectedList.add(toFind);
        CommandResult expectedResult = new CommandResult(
                String.format(FindCommand.MESSAGE_SUCCESS_M, 1, expectedList));
        assertEquals(model.getFoodList(), expectedList);
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_invalid_type() {
        FindCommand findInvalidType = new FindCommand("test", "TYPE");
        assertThrowEquals(CommandException.class,
                CommandErrorMessage.INVALID_TYPE, () -> {
                    findInvalidType.execute(new ModelManager(), new StorageManager());
                });
    }
}