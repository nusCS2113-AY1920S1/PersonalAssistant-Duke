package tasks;

import org.junit.jupiter.api.Test;
import utils.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DoneTest {

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

}
