import logic.Duke;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {

    private Duke duke = new Duke();

    @Test
    public void test() {
        duke.initialize();
        addTask_todo_addSuccess();
        addTask_deadline_addSuccess();
        addTask_event_addSuccess();
        addTask_event_invalidFormat();
        deleteTask_todo_deleteSuccess();
    }

    public void addTask_todo_addSuccess() {
        String input = "todo 123";
        String expected = "----------------------------------------------\n" +
                "Got it. I've added this task:\n" +
                "  \uD83D\uDCCB ❌ 123\n" +
                "Now you have 1 tasks in the list.\n" +
                "----------------------------------------------\n";
        assertEquals(expected, duke.getResponse(input));

    }

    public void addTask_deadline_addSuccess() {
        String input = "deadline 456 /by 10-10-1999 1800";
        String expected = "----------------------------------------------\n" +
                "Got it. I've added this task:\n" +
                "  ⏰ ❌ 456 (by: 10-10-1999 1800)\n" +
                "Now you have 2 tasks in the list.\n" +
                "----------------------------------------------\n";
        assertEquals(expected, duke.getResponse(input));

    }

    public void addTask_event_addSuccess() {
        String input = "event 789 /at 10-10-1999 1800 to 10-10-1999 1800";
        String expected = "----------------------------------------------\n" +
                "Got it. I've added this task:\n" +
                "  \uD83D\uDCC5 ❌ 789 (10-10-1999 1800 -- 10-10-1999 1800)\n" +
                "Now you have 3 tasks in the list.\n" +
                "----------------------------------------------\n";
        assertEquals(expected, duke.getResponse(input));
    }

    public void addTask_event_invalidFormat() {
        String input = "event 789 /at 10-10-1999 1800";
        String expected = "----------------------------------------------\n" +
                "☹ OOPS!!! Please enter event date\n" +
                "----------------------------------------------\n";
        assertEquals(expected, duke.getResponse(input));
    }

    public void deleteTask_todo_deleteSuccess() {
        String input = "delete 1";
        String expected =
                "----------------------------------------------\n" +
                        "Noted. I've removed this task: \n" +
                        " \uD83D\uDCCB ❌ 123 \n" +
                        "Now you have 2 tasks in the list.\n";

        assertEquals(expected, duke.getResponse(input));
    }


}
