import java.time.Month;
import java.util.*;

import duke.task.Event;
import duke.task.Item;
import duke.task.TaskList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FindDateTest {
    @Test
    public void FindDateTest() {
        String date = "23/09/2019";
        ArrayList<Item> list = new ArrayList<>();
        String descr = "read book";
        String time = "23/09/2019 1830";
        Event ev = new Event(descr,false,time);

        String[] temp = date.split("/");

        String dd = TaskList.numOrdinal(Integer.parseInt(temp[0]));
        assertEquals("23rd",dd);

        Month mm = Month.of(Integer.parseInt(temp[1]));
        String month = "" + mm;
        assertEquals("SEPTEMBER", month);

        String yy = temp[2];
        assertEquals("2019", yy);

        String check = dd + " of " + mm + " " + yy;
        for (Item i : list) {
            String desc = i.toString();
            assertEquals("[E][✗] read book (at: 23rd of September 2019, 6.30pm)", desc);
        }
        System.out.println("Passed FindDateTest");
    }
}

