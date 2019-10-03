package commands;

import eggventory.StockType;
import eggventory.commands.AddCommand;
import eggventory.enums.CommandType;
import eggventory.exceptions.BadInputException;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCommandTest {


    @Test
    public void execute_addTodoTask_success() throws BadInputException {
        StockType newList = new StockType();
        new AddCommand(CommandType.TODO, "Test TODO", "").execute(newList);
        assertEquals("Test TODO", newList.getTask(0).getDescription());
    }

    @Test
    public void execute_addDeadlineTask_success() throws BadInputException {
        StockType newList = new StockType();
        new AddCommand(CommandType.DEADLINE, "Test DEADLINE", "15/12/2019 1500").execute(newList);
        SimpleDateFormat formatted = new SimpleDateFormat("EEE MMM d yyyy K:mm a");
        String startDate = formatted.format(newList.getTask(0).getDate().getTime());
        assertEquals("Test DEADLINE", newList.getTask(0).getDescription());
        assertEquals("Sun Dec 15 2019 3:00 PM", startDate);
    }

    @Test
    public void execute_addEventTask_success() throws BadInputException {
        StockType newList = new StockType();
        new AddCommand(CommandType.EVENT, "Test EVENT", "15/12/2019 1500 to 17/12/2019 1500").execute(newList);
        SimpleDateFormat formatted = new SimpleDateFormat("EEE MMM d yyyy K:mm a");
        String startDate = formatted.format(newList.getTask(0).getDate().getTime());
        String endDate = formatted.format(newList.getTask(0).getEndDate().getTime());
        assertEquals("Test EVENT", newList.getTask(0).getDescription());
        assertEquals("Sun Dec 15 2019 3:00 PM", startDate);
        assertEquals("Tue Dec 17 2019 3:00 PM", endDate);
    }
}
