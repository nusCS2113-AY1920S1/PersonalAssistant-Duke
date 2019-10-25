package command;

import commands.DoneCommand;
import members.Member;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.ToDo;
import utils.DukeException;
import utils.Storage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DoneCommandTest {

    //@@author: Jason
    @Test
    public void checkNotDone() {
        String description = "This is a test for checkNotDone";
        ToDo temp = new ToDo(description);
        assertEquals(false, temp.getIsDone());
    }

    //@@author: Jason
    @Test
    public void checkDone() throws DukeException {
        String description = "This is a test for checkDone";
        ToDo temp = new ToDo(description);
        temp.markAsDone();
        assertEquals(true, temp.getIsDone());
    }

    //@@author: Jason
    @Test
    public void execute_negativeIntegerInput_throwsDukeException() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("test"));
        assertThrows(DukeException.class, () -> new DoneCommand("-1")
                .execute(tasks, new ArrayList<Member>(), null));
    }

}
