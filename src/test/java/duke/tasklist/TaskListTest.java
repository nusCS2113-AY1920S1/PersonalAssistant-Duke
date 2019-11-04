package duke.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import duke.exception.DukeException;
import duke.extensions.Recurrence;
import duke.task.Task;
import org.junit.jupiter.api.Test;

class TaskListTest {
    private TaskList createTaskList() throws DukeException {
        TaskList t = new TaskList();

        //Recurrence parameters
        Recurrence recurrenceWeekly = new Recurrence(Optional.of("weekly"));
        Recurrence recurrenceDaily = new Recurrence(Optional.of("daily"));
        Recurrence recurrenceNone = new Recurrence(Optional.empty());

        //Filter parameters
        Optional<String> cs = Optional.of("cs");
        Optional<String> empty = Optional.empty();

        //Date parameters
        Optional<LocalDateTime> dateTime1 = Optional.of(LocalDateTime.of(2017, Month.OCTOBER, 29,
                0, 0));
        Optional<LocalDateTime> dateTime2 = Optional.of(LocalDateTime.of(2018, Month.OCTOBER, 29,
                0, 0));
        Optional<LocalDateTime> dateTime3 = Optional.of(LocalDateTime.of(2017, Month.FEBRUARY, 10,
                0, 0));

        //Description parameters
        String description1 = "cs2113 is the best :')";
        String description2 = "cg2271 is the best :')";
        String description3 = "st2334 is the best :')";
        t.add(new Task(empty, dateTime1, recurrenceDaily, description1, 4,1));
        t.add(new Task(cs, dateTime3, recurrenceDaily, description3, 4,1));
        t.add(new Task(empty, dateTime2, recurrenceNone, description2, 5,1));
        t.add(new Task(cs, dateTime1, recurrenceWeekly, description3, 4,1));

        return t;
    }

    @Test
    public void get_indexOutOfBoundsTooHigh_failure() throws DukeException {
        TaskList t = createTaskList();
        Exception exception = assertThrows(DukeException.class, () ->
                t.get(4));
        assertEquals("Please enter valid task index!", exception.getMessage());
    }

    @Test
    public void get_indexOutOfBoundsBelowZero_failure() throws DukeException {
        TaskList t = createTaskList();
        Exception exception = assertThrows(DukeException.class, () ->
                t.get(-1));
        assertEquals("Please enter a positive index!", exception.getMessage());
    }

    @Test
    public void filteredGet_indexOutOfBoundsBelowZero_failure() throws DukeException {
        TaskList t = createTaskList();
        Optional<String> cs = Optional.of("cs");
        Exception exception = assertThrows(DukeException.class, () ->
                t.get(cs,-1));
        assertEquals("Please enter a positive index!", exception.getMessage());
    }
    @Test
    public void filteredGet_indexOutOfBoundsTooHigh_failure() throws DukeException {
        TaskList t = createTaskList();
        Optional<String> cs = Optional.of("cs");
        Exception exception = assertThrows(DukeException.class, () ->
                t.get(cs,3));
        assertEquals("Please enter a valid task index!", exception.getMessage());
    }
}
