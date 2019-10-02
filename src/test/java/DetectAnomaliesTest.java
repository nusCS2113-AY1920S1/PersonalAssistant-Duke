import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.Event;
import duchess.model.task.Task;
import duchess.model.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DetectAnomaliesTest {

    List<String> getList(String input) {
        return List.of(input.split(" "));
    }

    @Test
    void clash_returnsTrue() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Event(getList("meeting /at 12/12/2020 1800 to 12/12/2020 1900"));
        taskList.add(task);
        for (String str : List.of(
                "party /at 12/12/2020 1830 to 12/12/2020 1930",
                "party /at 12/12/2020 1730 to 12/12/2020 1830",
                "party /at 12/12/2020 1730 to 12/12/2020 1930",
                "party /at 12/12/2020 1800 to 12/12/2020 1830",
                "party /at 12/12/2020 1900 to 12/12/2020 1930"
        )) {
            assertTrue(taskList.isClashing(new Event(getList(str))));
        }
    }

    @Test
    void no_clash_returnsFalse() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Event(getList("Event meeting /at 12/12/2020 1800 to 12/12/2020 1900"));
        for (String str : List.of(
                "party /at 12/12/2020 1930 to 12/12/2020 2000",
                "party /at 12/12/2020 1630 to 12/12/2020 1730"
        )) {
            assertFalse(taskList.isClashing(new Event(getList(str))));
        }
    }

}
