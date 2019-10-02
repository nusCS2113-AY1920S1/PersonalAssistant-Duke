import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.Deadline;
import duchess.model.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineTest {
    List<String> getList(String input) {
        return List.of(input.split(" "));
    }

    @Test
    void constructor_invalidString_exceptionThrown() {
        List.of(
                "invalid string",
                "invalid string /at 20/12/2019 1243",
                "invalid string /by 20/12/2019 12:43",
                "invalid string /by 20/12/2019 1293",
                "invalid string /by 31/11/2019 1243"
        ).forEach(str -> {
            assertThrows(DukeException.class, () -> {
                List<String> invalidList = getList(str);
                new Deadline(invalidList);
            });
        });
    }

    @Test
    void toString_formatted_correctly() throws DukeException {
        List<String> list = getList("do something /by 20/12/2019 1243");
        assertEquals("[D][✘] do something (by: 20/12/2019 1243)", new Deadline(list).toString());
    }

    @Test
    void snooze_works_correctly() throws DukeException {
        List<String> list = getList("do something /by 20/12/2019 1212");
        Task task = new Deadline(list);
        task.snooze();
        assertEquals(task.toString(), "[D][✘] do something (by: 27/12/2019 1212)");
    }

    @Test
    void snooze_over_years_works_correctly() throws DukeException {
        List<String> list = getList("do something /by 27/12/2019 1212");
        Task task = new Deadline(list);
        task.snooze();
        assertEquals(task.toString(), "[D][✘] do something (by: 03/01/2020 1212)");
    }
}
