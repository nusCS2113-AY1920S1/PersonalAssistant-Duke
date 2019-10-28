package compal.logic.parser;

import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@@author Catherinetan99
public class ViewReminderCommandParserTest {
    private ViewReminderCommandParser parser = new ViewReminderCommandParser();
    private ArrayList<Task> taskArrList = new ArrayList<>();
    private TaskList taskList = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("Event 1", Task.Priority.medium, "26/10/2019", "26/10/2019", "1400", "1500");
        taskArrList.add(event1);

        Event event2 = new Event("Event 2", Task.Priority.high, "05/12/2019", "05/12/2019", "0900", "1600");
        event2.setHasReminder(true);
        taskArrList.add(event2);

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "01/11/2019", "1500");
        taskArrList.add(deadline1);

        Deadline deadline2 = new Deadline("Deadline 2", Task.Priority.low, "29/10/2019", "1400");
        deadline2.markAsDone();
        taskArrList.add(deadline2);

        taskList.setArrList(taskArrList);
    }

    @Test
    void parser_viewReminderParser_success() throws CommandException {
        StringBuilder taskReminder = new StringBuilder("Here are your tasks:\n");

        Event event1 = new Event("Event 1", Task.Priority.medium, "26/10/2019", "26/10/2019", "1400", "1500");
        String event1TaskString = event1.toString() + "\n";
        taskReminder.append(event1TaskString);

        Event event2 = new Event("Event 2", Task.Priority.high, "05/12/2019", "05/12/2019", "0900", "1600");
        String event2TaskString = event2.toString() + "\n";
        taskReminder.append(event2TaskString);

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "01/11/2019", "1500");
        String deadline1TaskString = deadline1.toString() + "\n";
        taskReminder.append(deadline1TaskString);

        String reminders = taskReminder.toString();

        try {
            assertEquals(reminders, parser.parseCommand("").commandExecute(taskList).feedbackToUser);
        } catch (ParserException e) {
            e.printStackTrace();
        }

        //assertParseSuccess(parser, "",
        //new CommandResult(reminders, false), taskList);
    }
}
