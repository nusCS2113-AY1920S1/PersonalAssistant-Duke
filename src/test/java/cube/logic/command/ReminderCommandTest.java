package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.parser.ParserUtil;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;
import java.util.Date;

public class ReminderCommandTest {

    @Test
    public void execute_reminder() throws CommandException, ParseException {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        ReminderCommand command = new ReminderCommand(3, 7);
        final CommandResult result = command.execute(model, storage);

        Food food = new Food("testName");
        food.setStock(0);
        food.setExpiryDate(new Date());

        String expectedFeedbackToUser = "Here are the upcoming expiry dates:\n"
                + String.format("%1$s due in %2$s\n",
                        "testName", ParserUtil.parseDateToString(new Date())) + "\n"
                + "Here are the food products that are low in stock:\n"
                + String.format("%1$s : %2$s left\n", "testName", 0);

        CommandResult expectedResult = new CommandResult(expectedFeedbackToUser);

        assertEquals(result, expectedResult);
    }
}
