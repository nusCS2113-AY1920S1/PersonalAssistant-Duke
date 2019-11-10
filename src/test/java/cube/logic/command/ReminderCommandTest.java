package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.parser.exception.ParserException;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReminderCommandTest {
    private class FoodStub extends Food {
    }


    @Test
    public void execute_reminder() throws CommandException, ParseException {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        ReminderCommand command = new ReminderCommand(3, 7);
        CommandResult result = command.execute(model, storage);

        Food food = new Food("testName");
        food.setStock(0);
        food.setExpiryDate(new Date());

        FoodList expectedStockReminder = new FoodList();
        FoodList expectedExpiryReminder = new FoodList();
        expectedStockReminder.add(food);
        expectedExpiryReminder.add(food);

        // to make list of food items in expiryDate and stock reminder or not
        // look into message success
        // what else is expected?

        CommandResult expectedResult = new CommandResult(String.format(ReminderCommand.MESSAGE_SUCCESS));

        assertEquals(result, expectedResult);
    }
}
