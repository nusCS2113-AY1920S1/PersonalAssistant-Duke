package compal.logic.parser;

import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Catherinetan99
public class SetReminderCommandParserTest {
    private SetReminderCommandParser parser = new SetReminderCommandParser();
    private ArrayList<Task> taskArrList = new ArrayList<>();
    private TaskList taskList = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("Event 1", Task.Priority.medium, "26/12/2019", "26/12/2019", "1400", "1500");
        taskArrList.add(event1);
        taskList.setArrList(taskArrList);
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

        StringBuilder taskReminder = new StringBuilder("Noted. I have changed the reminder status of this task.\n");
        Event event1 = new Event("Event 1", Task.Priority.medium, "26/12/2019", "26/12/2019", "1400", "1500");
        taskReminder.append(event1.toString());
        String reminders = taskReminder.toString();

        assertEquals(reminders, parser.parseCommand(testInput).commandExecute(taskList).feedbackToUser);
    }

    @Test
    void parser_setHasReminderFalse_success() throws ParserException, CommandException {
        String testInput = "/id 0 /status N";

        StringBuilder taskReminder = new StringBuilder("Noted. I have changed the reminder status of this task.\n");
        Event event1 = new Event("Event 1", Task.Priority.medium, "26/12/2019", "26/12/2019", "1400", "1500");
        taskReminder.append(event1.toString());
        String reminders = taskReminder.toString();

        assertEquals(reminders, parser.parseCommand(testInput).commandExecute(taskList).feedbackToUser);
    }
}
