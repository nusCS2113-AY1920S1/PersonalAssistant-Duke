package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import utils.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventTest {
    @Test
    public void checkDescription() throws DukeException {
        String description = "This is a test Event";
        String at = "10/12/2019 1130";
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date tempDate = ft.parse(at);
            Event temp = new Event(description, tempDate);
            assertEquals(description, temp.getDescription());
        } catch (ParseException e) {
            throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy");
        }

    }

    @Test
    public void checkAt() throws DukeException {
        String description = "This is a test Event";
        String at = "10/12/2019 1130";
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date tempDate = ft.parse(at);
            Event temp = new Event(description, tempDate);
            assertEquals(tempDate, temp.getAt());
        } catch (ParseException e) {
            throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy");
        }
    }

}
