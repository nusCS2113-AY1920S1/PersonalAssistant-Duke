import CustomExceptions.RoomShareException;
import Enums.Priority;
import Model_Classes.Assignment;
import Model_Classes.Task;
import Operations.TaskList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskListTest {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static Date date;

    static {
        try {
            date = format.parse("22/12/2019 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static String description = "task1";
    private static Assignment ts = new Assignment(description, date);
    private static Assignment ts2 = new Assignment("task2", date);
    private static Assignment assignment1 = new Assignment ("as1", date);
    private static Assignment assignment2 = new Assignment("as2", date);
    private static TaskList tl = new TaskList(new ArrayList<Task>());

    @Test
    void add() {
        tl.add(ts);
        assertEquals("[A][\u2718] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", ts.toString());
    }

    @Test
    void find() {
        tl.find("task");
        assertEquals("[A][\u2718] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", ts.toString());
    }

    @Test
    void reorder() {
        tl.add(ts);
        tl.add(ts2);
        tl.reorder(1, 2);
        assertEquals("[A][\u2718] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)\n" +
                "[A][\u2718] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", ts2.toString() + "\n" + ts.toString());
    }

    @Test
    void done() {
        tl.add(assignment1);
        tl.add(assignment2);
        int[] array = {1, 2};
        try {
            tl.done(array);
            assertEquals("[A][\u2713] as1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)\n" +
                    "[A][\u2713] as2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)",
                    assignment1.toString()+ "\n" + assignment2.toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void replace() {
        tl.add(assignment1);
        tl.replace(1,ts);
        assertEquals("[A][\u2718] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", ts.toString());
    }

    @Test
    void setPriority() {
        tl.add(ts);
        String[] array = {"1", "high"};
        try {
            tl.setPriority(array);
            assertEquals(tl.get(1).getPriority(), Priority.high);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }
}