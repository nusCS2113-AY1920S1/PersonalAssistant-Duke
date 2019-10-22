package tasks;

import model.tasks.Deadline;
import org.junit.jupiter.api.Test;
import utils.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void checkDescription() throws DukeException {
        String description = "test method for deadline";
        String at = "15/12/2019 1130";
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date tempDate = ft.parse(at);
            Deadline temp = new Deadline(description, tempDate);
            assertEquals(description, temp.getDescription());
        } catch (ParseException e) {
            throw new DukeException("Invalid command format");
        }

    }

    @Test
    public void checkAt() throws DukeException {
        String description = "This is a test method for deadline";
        String at = "10/12/2019 1130";
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date tempDate = ft.parse(at);
            Deadline temp = new Deadline(description, tempDate);
            assertEquals(tempDate, temp.getTime());
        } catch (ParseException e) {
            throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy");
        }
    }

}
