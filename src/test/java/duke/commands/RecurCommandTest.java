package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.enums.CommandType;
import duke.exceptions.BadInputException;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecurCommandTest {
    Ui ui = new Ui();
    Storage storage = new Storage("");
    TaskList newList = new TaskList();

    @Test
    void execute_recurDeadline_success() throws BadInputException {
        new RecurCommand(CommandType.DEADLINE, "Test DEADLINE", "15/12/2019 1500", 5, 2).execute(newList, ui, storage);
        SimpleDateFormat formatted = new SimpleDateFormat("EEE MMM d yyyy K:mm a");
        String firstDate = formatted.format(newList.getTask(0).getDate().getTime());
        String secondDate = formatted.format(newList.getTask(1).getDate().getTime());
        assertEquals("Sun Dec 15 2019 3:00 PM", firstDate);
        assertEquals("Fri Dec 20 2019 3:00 PM", secondDate);
    }
}