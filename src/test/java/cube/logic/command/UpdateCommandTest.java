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
import static cube.testutil.Assert.assertThrowEquals;

public class UpdateCommandTest {
    private class FoodTest extends Food{
    }

    /**
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult.
     * Storage not tested.
     */
    @Test
    public void execute_update_correctly() throws CommandException {
        ModelManager model = new ModelManager();
        final StorageManager storage = new StorageManager();
        FoodList list = model.getFoodList();
        Food oldTest = new Food("test");
        list.add(oldTest);
        Food newTest = new Food("test");
        newTest.setType("test");
        assert newTest.getType().equals("test");
        int[] testChange = {1,0,0,0,0};
        UpdateCommand command = new UpdateCommand(newTest,testChange);
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(
                String.format(UpdateCommand.MESSAGE_SUCCESS, oldTest, newTest));
        FoodList expectedList = new FoodList();
        expectedList.add(newTest);

        assertEquals(model.getFoodList(), expectedList);
    }

    /**
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult.
     */
    @Test
    public void execute_invalidName() {
        ModelManager model = new ModelManager();
        final StorageManager storage = new StorageManager();
        FoodList list = model.getFoodList();
        Food food = new Food("test");
        int[] testChange = {1,0,0,0};
        UpdateCommand command = new UpdateCommand(food, testChange);

        assertThrowEquals(CommandException.class,
                CommandErrorMessage.FOOD_NOT_EXISTS, () -> {
                    command.execute(model, storage);
                });
    }
}
