package cube.logic.command;

import cube.model.food.*;
import cube.model.*;
import cube.storage.*;
import cube.logic.command.util.CommandResult;
import cube.logic.command.exception.CommandException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult 
     * Storage not tested.
     */
    @Test
    public void execute_foodAcceptedByModel() throws CommandException {
    	ModelManager model = new ModelManager();
    	StorageManager storage = new StorageManager();
    	Food food = new Food("ok");
    	AddCommand command = new AddCommand(food);
    	CommandResult result = command.execute(model, storage);

    	CommandResult expectedResult = new CommandResult(String.format(AddCommand.MESSAGE_SUCCESS, food, model.getFoodList().size()));
    	FoodList expectedList = new FoodList();
    	expectedList.add(food);
    	
    	assertEquals(model.getFoodList(), expectedList);
    	assertEquals(result, expectedResult);
    }

    @Test
    public void execute_duplicatedFood_throws_NameExist() {

    }
}
