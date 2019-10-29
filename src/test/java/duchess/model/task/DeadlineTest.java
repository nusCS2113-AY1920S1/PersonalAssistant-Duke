package duchess.model.task;

import duchess.exceptions.DuchessException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void toString_formatted_correctly() {
        assertEquals("[D][✘] do something (by: 20/12/2019 1243)",
                new Deadline("do something", LocalDateTime.parse("2019-12-20T12:43")).toString());
    }

    @Test
    public void snooze_within_year_snoozes() throws DuchessException {
        Task task = new Deadline("do something", LocalDateTime.parse("2019-12-20T12:12"));
        task.snooze();
        assertEquals(task.toString(), "[D][✘] do something (by: 27/12/2019 1212)");
    }

    @Test
    public void snooze_over_years_snoozes() throws DuchessException {
        Task task = new Deadline("do something", LocalDateTime.parse("2019-12-27T12:12"));
        task.snooze();
        assertEquals(task.toString(), "[D][✘] do something (by: 03/01/2020 1212)");
    }

    @Test
    public void setDeadline_setsDeadline() {
        Deadline deadline = new Deadline("do something", LocalDateTime.parse("2019-12-26T12:12"));
        assertEquals(deadline.getDeadline(), "2019-12-26T12:12");
        deadline.setDeadline("2019-12-27T12:12");
        assertEquals(deadline.getDeadline(), "2019-12-27T12:12");
    }
}
