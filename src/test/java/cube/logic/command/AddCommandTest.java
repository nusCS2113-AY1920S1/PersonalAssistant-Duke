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

public class AddCommandTest {
	private class FoodStub extends Food{
	}

    @Test
    public void constructor_valid_person() {
    	FoodStub food = new FoodStub();
    	new AddCommand(food);
    }

    /** 
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult.
     * Storage not tested.
     */
    @Test
    public void execute_foodAcceptedByModel() throws CommandException {
    	ModelManager model = new ModelManager();
    	StorageManager storage = new StorageManager();
    	Food food = new Food("ok");
    	AddCommand command = new AddCommand(food);
    	CommandResult result = command.execute(model, storage);

    	CommandResult expectedResult = new CommandResult(String.format(
    			AddCommand.MESSAGE_SUCCESS, food, model.getFoodList().size()), false, false);
    	FoodList expectedList = new FoodList();
    	expectedList.add(food);
    	
    	assertEquals(model.getFoodList(), expectedList);
    	assertEquals(result, expectedResult);
    }

    /** 
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult.
     */
    @Test
    public void execute_duplicatedFood_throws_NameExist() {
    	ModelManager model = new ModelManager();
    	StorageManager storage = new StorageManager();
    	FoodList list = model.getFoodList();
    	Food food = new Food("ok");
    	list.add(food);
    	AddCommand command = new AddCommand(food);

    	assertThrowEquals(CommandException.class,
				CommandErrorMessage.FOOD_ALREADY_EXISTS, () -> {
    		command.execute(model, storage);
    	});
    }
}
