package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;

public class ReminderCommandTest {

    @Test
    public void execute_reminder() throws CommandException, ParseException {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        ReminderCommand command = new ReminderCommand(30, 7);

        Food food = new Food("testName");
        food.setStock(1);

        // Fixes
        FoodList foodList = new FoodList();
        foodList.add(food);
        storage.storeFoodList(foodList);
        model.setFoodList(foodList);

        final CommandResult result = command.execute(model, storage);

        String expectedFeedbackToUser = "Here are the upcoming expiry dates:\n"
                + "\n"
                + "Here are the food products that are low in stock:\n"
                + String.format("%1$s : %2$s left\n", "testName", 1);

        CommandResult expectedResult = new CommandResult(expectedFeedbackToUser);

        System.out.println(result.getFeedbackToUser());
        System.out.println(expectedResult.getFeedbackToUser());

        assertEquals(result, expectedResult);
    }
}
