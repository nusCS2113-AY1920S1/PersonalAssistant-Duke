package command;

import commands.DoneCommand;
import members.Member;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.ToDo;
import utils.DukeException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DoneCommandTest {

    //@@author: Jason
    @Test
    public void checkNotDone() {
        String description = "This is a test for checkNotDone";
        ToDo temp = new ToDo(description);
        assertFalse(temp.getIsDone());
    }

    //@@author: Jason
    @Test
    public void checkDone() throws DukeException {
        String description = "This is a test for checkDone";
        ToDo temp = new ToDo(description);
        temp.markAsDone();
        assertTrue(temp.getIsDone());
    }

    //@@author: Jason
    @Test
    public void execute_negativeIntegerInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        assertThrows(DukeException.class, () -> new DoneCommand("-1")
                .execute(tasks, new ArrayList<Member>(), null));
    }

    //@@author: Jason
    @Test
    public void execute_stringAfterIntegerInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        assertThrows(DukeException.class, () -> new DoneCommand("1 string")
                .execute(tasks, new ArrayList<Member>(), null));
    }

    //@@author: Jason
    @Test
    public void execute_outOfBoundsInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        tasks.add(new ToDo("test2"));
        assertThrows(DukeException.class, () -> new DoneCommand("3")
                .execute(tasks, new ArrayList<Member>(), null));
    }

    //@@author: Jason
    @Test
    public void execute_stringInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        tasks.add(new ToDo("test2"));
        assertThrows(DukeException.class, () -> new DoneCommand("a")
                .execute(tasks, new ArrayList<Member>(), null));
    }

}
