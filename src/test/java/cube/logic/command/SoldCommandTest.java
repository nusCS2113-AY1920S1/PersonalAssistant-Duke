package cube.logic.command;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.sale.Sale;
import cube.model.sale.SalesHistory;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;
import java.util.Iterator;
import java.util.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static cube.testutil.Assert.assertThrowEquals;

public class SoldCommandTest {
	private class FoodStub extends Food{
	}

    @Test
    public void obtainFoodSold_valid() throws CommandException{
        SoldCommand command = new SoldCommand("anyName", 0);
        FoodList list = new FoodList();
        Food food = new Food("anyName");
        list.add(food);
        command.obtainFoodSold(list);
        assertEquals(command.toSold, food);
    }

    @Test
    public void obtainFoodSold_throws_nameNotExist() {
        SoldCommand command = new SoldCommand("anyName", 0);
        assertThrowEquals(CommandException.class, CommandErrorMessage.FOOD_NOT_EXISTS, () -> {command.obtainFoodSold(new FoodList());});
    }

    /** 
     * Dependent on correct implementation of following class:
     *	 ModelManager, StorageManager, FoodList, Food, CommandResult 
     * Storage not tested.
     */
    @Test
    public void execute_correct_model_change() throws CommandException {
    	ModelManager model = new ModelManager();
    	StorageManager storage = new StorageManager();
    	Food food = new Food("anyName");
        food.setStock(100);
        food.setPrice(100);
        food.setCost(50);
        model.getFoodList().add(food);
    	SoldCommand command = new SoldCommand("anyName", 50);
    	CommandResult result = command.execute(model, storage);

    	Food expectedFood = new Food("anyName");
        expectedFood.setStock(50);
        expectedFood.setPrice(100);
        expectedFood.setCost(50);

    	assertEquals(food, expectedFood);
    }

    /** 
     * Dependent on correct implementation of following class:
     *   ModelManager, StorageManager, FoodList, Food, CommandResult 
     * Storage not tested.
     */
    @Test
    public void execute_invalid_quantity() throws CommandException {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        Food food = new Food("anyName");
        food.setStock(100);
        food.setPrice(100);
        food.setCost(50);
        model.getFoodList().add(food);
        SoldCommand largeQuantityCommand = new SoldCommand("anyName", 500);
        SoldCommand negativeQuantityCommand = new SoldCommand("anyName", -1);

        assertThrowEquals(CommandException.class, CommandErrorMessage.INVALID_QUANTITY_SOLD, () -> {largeQuantityCommand.execute(model, storage);});
        assertThrowEquals(CommandException.class, CommandErrorMessage.INVALID_QUANTITY_SOLD, () -> {negativeQuantityCommand.execute(model, storage);});
    }

    /** 
     * Dependent on correct implementation of following class:
     *   ModelManager, StorageManager, FoodList, Food, CommandResult 
     * Storage not tested.
     */
    @Test
    public void execute_correct_sale_record() throws CommandException {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        Date time = new Date();
        Food food = new Food("anyName");
        food.setStock(100);
        food.setPrice(100);
        food.setCost(50);
        model.getFoodList().add(food);
        SoldCommand command = new SoldCommand("anyName", 50, time);
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(String.format(SoldCommand.MESSAGE_SUCCESS, 50, "anyName", 100*50d, 50*50d));
        Sale expectedSale = new Sale("anyName", 50, 100*50, 50*50, time);
        System.out.println(model.getSalesHistory().iterator().next());
        assertEquals(model.getSalesHistory().iterator().next(), expectedSale);
        assertEquals(result, expectedResult);
    }
}
