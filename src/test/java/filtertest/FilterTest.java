package filtertest;

import commands.Command;
import commands.FilterCommand;
import commons.UserInteraction;
import stubclasses.StorageStub;
import tasks.Assignment;
import tasks.Deadline;
import tasks.Event;
import tasks.TaskList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTest {
    private static Assignment eventWithoutKeyword;
    private static Assignment deadlineWithoutKeyword;
    private static Assignment eventWithKeyword;
    private static Assignment deadlineWithKeyword;
    private static String keyword;
    private StorageStub storage = new StorageStub();
    private UserInteraction ui = new UserInteraction();
    private static TaskList events = new TaskList();
    private static TaskList deadlines = new TaskList();

    /**
     * This method initializes the variables required.
     */
    @BeforeAll
    public static void setAllVariables() {
        eventWithoutKeyword = new Event("CS2101 EventTest","12/10/2019","1200","1201");
        deadlineWithoutKeyword = new Deadline("CS2101 DeadlineTest","13/10/2019","1202");
        eventWithKeyword = new Event("CS2101 EventWithKey","12/10/2019","1300","1301");
        deadlineWithKeyword = new Deadline("CS2101 DeadlineWithKey","13/10/2019","1200");
    }

    @Test
        public void execute_keywordNotInList() {
        events = new TaskList();
        deadlines = new TaskList();
        keyword = "key";
        deadlines.addTask(deadlineWithoutKeyword);
        events.addTask(eventWithoutKeyword);
        Command command = new FilterCommand(keyword);
        String w1 = "There are no task(s) matching your keyword.";
        String w2;
        try {
            w2 = command.execute(events, deadlines, ui, storage);
        } catch (Exception e) {
            w2 = e.getMessage();
        }
        assertEquals(w1, w2);
    }

    @Test
    public void execute_keywordInList() {
        events = new TaskList();
        deadlines = new TaskList();
        keyword = "key";
        deadlines.addTask(deadlineWithKeyword);
        events.addTask(eventWithKeyword);
        Command command = new FilterCommand(keyword);
        String w1 = "Here are the following events/deadline with the keyword key\n"
                + "1. [E]CS2101 EventWithKey at 12/10/2019 1300 to 1301\n"
                + "2. [D]CS2101 DeadlineWithKey by 13/10/2019 1200\n";
        String w2;
        try {
            w2 = command.execute(events, deadlines, ui, storage);
        } catch (Exception e) {
            w2 = e.getMessage();
        }
        assertEquals(w1, w2);
    }

    @Test
    public void execute_keywordSubstringNotInList() {
        events = new TaskList();
        deadlines = new TaskList();
        keyword = "keyword";
        deadlines.addTask(deadlineWithKeyword);
        events.addTask(eventWithKeyword);
        Command command = new FilterCommand(keyword);
        String w1 = "There are no task(s) matching your keyword.";
        String w2;
        try {
            w2 = command.execute(events, deadlines, ui, storage);
        } catch (Exception e) {
            w2 = e.getMessage();
        }
        assertEquals(w1, w2);
    }

    @Test
    public void execute_emptyKeyword_throwsDukeInvalidFormatException() {
        events = new TaskList();
        deadlines = new TaskList();
        keyword = new String();
        deadlines.addTask(deadlineWithKeyword);
        events.addTask(eventWithKeyword);
        Command command = new FilterCommand(keyword);
        String w1 = " OOPS!!! Please enter filter command as follows\n"
                + "show/filter keyword\n";
        String w2;
        try {
            w2 = command.execute(events, deadlines, ui, storage);
        } catch (Exception e) {
            w2 = e.getMessage();
        }
        assertEquals(w1, w2);
    }

}
