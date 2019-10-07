import controllers.TaskFactory;
import exceptions.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.tasks.Deadline;
import models.tasks.Event;
import models.tasks.ToDos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DukeTest {
    @Test
    void testTodoCreation() {
        try {
            assertEquals(new ToDos("write book","2").getDescription(),
                new TaskFactory().createTask("todo write book /in 2").getDescription());
        } catch (DukeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDeadlineCreation() throws DukeException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date = formatter.parse("10/11/2019 1930");
        assertEquals(new Deadline("return book", "10 November 2019 07.30 pm", date).getDescription(),
            new TaskFactory().createTask("deadline return book /by 10/11/2019 1930").getDescription());
    }

    @Test
    void testEventCreation() throws DukeException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date = formatter.parse("22/09/2019 1600");
        assertEquals(new Event("project meeting", "22 September 2019 04.00 pm", date).getDescription(),
            new TaskFactory().createTask("event project meeting /at 22/09/2019 1600").getDescription());
    }

    @Test
    void testDeadlineOutput() throws DukeException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date = formatter.parse("30/10/2019 1400");
        assertEquals("return book (by: 30 October 2019 02.00 PM)",
            new Deadline("return book", "30 October 2019 02.00 PM", date).getDescription());
    }

    @Test
    void testEventOutput() throws DukeException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date = formatter.parse("16/10/2019 0800");
        assertEquals("project meeting (at: 16 October 2019 08.00 AM)",
            new Event("project meeting", "16 October 2019 08.00 AM", date).getDescription());
    }

    @Test
    void alwaysTrue() {
        assertTrue(true);
    }
}
