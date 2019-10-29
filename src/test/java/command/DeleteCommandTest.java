package command;

import commands.DeleteCommand;
import members.Member;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.ToDo;
import utils.DukeException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DeleteCommandTest {

    //@@author: Jason
    @Test
    public void checkDelete() throws DukeException {

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        tasks.add(new ToDo("test2"));
        tasks.remove(0);
        assertEquals("test2", tasks.get(0).getDescription());
    }

    //@@author: Jason
    @Test
    public void execute_negativeIntegerInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        assertThrows(DukeException.class, () -> new DeleteCommand("-1")
                .execute(tasks, new ArrayList<Member>(), null));
    }

    //@@author: Jason
    @Test
    public void execute_stringAfterIntegerInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        assertThrows(DukeException.class, () -> new DeleteCommand("1 string")
                .execute(tasks, new ArrayList<Member>(), null));
    }

    //@@author: Jason
    @Test
    public void execute_outOfBoundsInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        tasks.add(new ToDo("test2"));
        assertThrows(DukeException.class, () -> new DeleteCommand("3")
                .execute(tasks, new ArrayList<Member>(), null));
    }

    //@@author: Jason
    @Test
    public void execute_stringInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        tasks.add(new ToDo("test2"));
        assertThrows(DukeException.class, () -> new DeleteCommand("a")
                .execute(tasks, new ArrayList<Member>(), null));
    }

}
