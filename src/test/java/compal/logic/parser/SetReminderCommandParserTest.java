package compal.logic.parser;

import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Catherinetan99
public class SetReminderCommandParserTest {
    private SetReminderCommandParser parser = new SetReminderCommandParser();

    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @BeforeEach
    void setUp() {
        taskListMain.setArrList(taskArrListMain);
        taskListDup.setArrList(taskArrListDup);

        Event event1 = new Event("Event 1", Task.Priority.medium, "26/12/2019", "26/12/2019", "1400", "1500");
        taskListMain.addTask(event1);
        taskListDup.addTask(event1);

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "05/12/2019", "1500");
        taskListMain.addTask(deadline1);
        taskListDup.addTask(deadline1);
    }

    @Test
    void parser_missingIdToken_exceptionThrown() {
        assertParseFailure(parser, "", "ArgumentError: Missing /id");
    }

    @Test
    void parser_missingStatusToken_exceptionThrown() {
        assertParseFailure(parser, "/id 3", "ArgumentError: Missing /status");
    }

    @Test
    void parser_missingIdInput_exceptionThrown() {
        assertParseFailure(parser, "/id", "Error: Missing input!");
    }

    @Test
    void parser_missingStatusInput_exceptionThrown() {
        assertParseFailure(parser, "/id 3 /status", "Error: Missing input!");
    }

    @Test
    void parser_setHasReminderTrue_success() throws CommandException, ParserException {
        String testInput = "/id 0 /status Y";

        String taskReminder = "Noted. I have changed the reminder status of this task.\n";
        taskReminder = taskReminder.concat(taskListDup.getTaskById(0).toString());

        assertEquals(taskReminder, parser.parseCommand(testInput).commandExecute(taskListMain).feedbackToUser);
    }

    @Test
    void parser_setHasReminderFalse_success() throws ParserException, CommandException {
        String testInput = "/id 1 /status N";

        String taskReminder = "Noted. I have changed the reminder status of this task.\n";
        taskReminder = taskReminder.concat(taskListDup.getTaskById(1).toString());

        assertEquals(taskReminder, parser.parseCommand(testInput).commandExecute(taskListMain).feedbackToUser);
    }
}
