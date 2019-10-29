package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.FindFreeSlotCommandParser;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static compal.logic.command.CommandTestUtil.assertCommandFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Catherinetan99
public class FindFreeSlotCommandTest {
    private FindFreeSlotCommandParser parser = new FindFreeSlotCommandParser();
    private ArrayList<Task> taskArrList = new ArrayList<>();
    private TaskList taskList = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        taskList.setArrList(taskArrList);
        Event event1 = new Event("Event 1", Task.Priority.medium, "05/01/2020", "05/01/2020", "0000", "0900");
        taskList.addTask(event1);

        Event event2 = new Event("Event 2", Task.Priority.high, "05/01/2020", "05/01/2020", "1000", "1100");
        taskList.addTask(event2);

        Event event3 = new Event("Event 3", Task.Priority.low, "05/01/2020", "05/01/2020", "1300", "1700");
        taskList.addTask(event3);
    }

    @Test
    void execute_invalidDurationInput_exceptionThrown() throws ParseException {
        String stringDate = "05/01/2020";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
        FindFreeSlotCommand testFindFreeSlot = new FindFreeSlotCommand(date, 1234, 1234);
        assertCommandFailure(testFindFreeSlot, taskList, FindFreeSlotCommand.MESSAGE_LIMIT_EXCEEDED);
    }

    @Test
    void execute_findFreeSlotCommand_success() throws ParseException, ParserException, CommandException {
        String stringDate = "05/01/2020";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);

        FindFreeSlotCommand testFindFreeSlot = new FindFreeSlotCommand(date, 1, 0);
        CommandResult testCommandResult = testFindFreeSlot.commandExecute(taskList);
        String test;
        test = testCommandResult.feedbackToUser;

        StringBuilder freeSlot = new StringBuilder("Here are the available time slots for 05/01/2020:\n");
        freeSlot.append("1. 0900 to 1000\n");
        freeSlot.append("2. 1100 to 1300\n");
        freeSlot.append("3. 1700 to 2400\n");
        String expected = freeSlot.toString();
        assertEquals(expected, test);
    }

    @Test
    void execute_findFreeSlotCommand_noSlotsFound() throws ParseException, ParserException, CommandException {
        String stringDate = "05/01/2020";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);

        FindFreeSlotCommand testFindFreeSlot = new FindFreeSlotCommand(date, 8, 30);
        CommandResult testCommandResult = testFindFreeSlot.commandExecute(taskList);
        String test = testCommandResult.feedbackToUser;

        String expected = "You have no available slots on 05/01/2020 ! :(";
        assertEquals(expected, test);
    }

    @Test
    void execute_findFreeSlotCommand_noTasksOnInputDate() throws ParseException, ParserException, CommandException {
        String stringDate = "06/12/2019";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);

        FindFreeSlotCommand testFindFreeSlot = new FindFreeSlotCommand(date, 1, 30);
        CommandResult testCommandResult = testFindFreeSlot.commandExecute(taskList);
        String test = testCommandResult.feedbackToUser;

        String expected = "You are free for the entire day! You have no tasks on 06/12/2019!";
        assertEquals(expected, test);
    }
}
