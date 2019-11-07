package cube.logic.command;

import cube.model.food.Food;
import cube.model.food.FoodList.SortType;
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

public class ListCommandTest {
    private class FoodListStub extends FoodList {
    }
	private class ModelStub extends ModelManager{
        FoodListStub list = new FoodListStub();
        /*
        @Override
        public FoodListStub getFoodList(){
                 return list;
        }
         */
	}
    @Test
    public void construct_sortType_successful() {
        SortType type = SortType.EXPIRY;
        ListCommand command = new ListCommand(type);
    	assertEquals(command.sortType, type);
    }

    /** 
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, Food.getRevenue(), CommandResult 
     * Storage not tested.
     */
    @Test
    public void execute_list_noSort() throws CommandException {
        FoodListStub list = new FoodListStub();
        ModelStub model = new ModelStub();

    	ListCommand command = new ListCommand();
    	CommandResult result = command.execute(model, new StorageManager());
        
    	CommandResult expectedResult = new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, list, Food.getRevenue()), false, false);
    	assertEquals(result, expectedResult);
    }
}