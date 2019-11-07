import CustomExceptions.RoomShareException;
import Enums.Priority;
import Enums.TimeUnit;
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
    private static Date date1;
    private static Date date2;
    private static Date date3;
    private static Date date4;
    static {
        try {
            date1 = format.parse("22/12/2019 18:00");
            date2 = format.parse("22/12/2019 18:00");
            date3 = format.parse("22/12/2019 18:00");
            date4 = format.parse("22/12/2019 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static String description = "task1";
    private static Assignment ts = new Assignment(description, date1);
    private static Assignment ts2 = new Assignment("task2", date2);
    private static Assignment assignment1 = new Assignment ("as1", date3);
    private static Assignment assignment2 = new Assignment("as2", date4);
    private static TaskList tl = new TaskList(new ArrayList<Task>());

    @Test
    void add() {
        tl.add(ts);
        tl.add(ts2);
        tl.add(assignment1);
        tl.add(assignment2);
        try {
            assertEquals("[A][\u2718] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", tl.get(0).toString());
            assertEquals("[A][\u2718] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", tl.get(1).toString());
            assertEquals("[A][\u2718] as1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", tl.get(2).toString());
            assertEquals("[A][\u2718] as2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", tl.get(3).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void done() {
        tl.add(ts);
        tl.add(ts2);
        int[] array = {0, 1};
        try {
            tl.done(array);
            assertEquals("[A][\u2713] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)\n" +
                            "[A][\u2713] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)",
                    tl.get(0).toString()+ "\n" + tl.get(1).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void find() {
        tl.add(ts);
        tl.find("task");
        try {
            assertEquals("[A][\u2718] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", tl.get(0).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setPriority() {
        tl.add(ts);
        String[] array = {"1", "high"};
        try {
            tl.setPriority(array);
            assertEquals(tl.get(0).getPriority(), Priority.high);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void reorder() {
        tl.add(ts);
        tl.add(ts2);
        try {
            tl.reorder(0, 1);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
        try {
            assertEquals("[A][\u2718] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)\n" +
                    "[A][\u2718] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", tl.get(0).toString() + "\n" + tl.get(1).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void replace() {
        tl.add(ts);
        tl.replace(0,ts2);
        try {
            assertEquals("[A][\u2718] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", tl.get(0).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void snooze() {
        tl.add(ts);
        tl.add(ts2);
        tl.add(assignment1);
        try {
            tl.snooze(0, 1, TimeUnit.hours);
            tl.snooze(1,10, TimeUnit.minutes);
            tl.snooze(2, 1, TimeUnit.day);
            assertEquals("[A][\u2718] task1 (everyone) (by: Sun Dec 22 19:00:00 SGT 2019)", tl.get(0).toString());
            assertEquals("[A][\u2718] task2 (everyone) (by: Sun Dec 22 18:10:00 SGT 2019)", tl.get(1).toString());
            assertEquals("[A][\u2718] as1 (everyone) (by: Mon Dec 23 18:00:00 SGT 2019)", tl.get(2).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }
}