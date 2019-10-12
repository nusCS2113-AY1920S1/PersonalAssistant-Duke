package model.task;

import duchess.exceptions.DuchessException;
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
    public void constructor_invalidString_exceptionThrown() {
        List.of(
                "invalid string",
                "invalid string /at 20/12/2019 1243",
                "invalid string /by 20/12/2019 12:43",
                "invalid string /by 20/12/2019 1293",
                "invalid string /by 31/11/2019 1243"
        ).forEach(str -> {
            assertThrows(DuchessException.class, () -> {
                List<String> invalidList = getList(str);
                new Deadline(invalidList);
            });
        });
    }

    @Test
    public void toString_formatted_correctly() throws DuchessException {
        List<String> list = getList("do something /by 20/12/2019 1243");
        assertEquals("[D][✘] do something (by: 20/12/2019 1243)", new Deadline(list).toString());
    }

    @Test
    public void snooze_within_year_snoozes() throws DuchessException {
        List<String> list = getList("do something /by 20/12/2019 1212");
        Task task = new Deadline(list);
        task.snooze();
        assertEquals(task.toString(), "[D][✘] do something (by: 27/12/2019 1212)");
    }

    @Test
    public void snooze_over_years_snoozes() throws DuchessException {
        List<String> list = getList("do something /by 27/12/2019 1212");
        Task task = new Deadline(list);
        task.snooze();
        assertEquals(task.toString(), "[D][✘] do something (by: 03/01/2020 1212)");
    }

    @Test
    public void setDeadline_setsDeadline() throws DuchessException {
        List<String> list = getList("do something /by 26/12/2019 1212");
        Deadline deadline = new Deadline(list);
        assertEquals(deadline.getDeadline(), "2019-12-26T12:12");
        deadline.setDeadline("2019-12-27T12:12");
        assertEquals(deadline.getDeadline(), "2019-12-27T12:12");
    }
}
