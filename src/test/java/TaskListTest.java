import CustomExceptions.RoomShareException;
import Enums.Priority;
import Enums.TimeUnit;
import Model_Classes.Assignment;
import Operations.TaskList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskListTest {
    private  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private  Date date1;
    private  Date date2;
    private  Date date3;
    private  Date date4;
     {
        try {
            date1 = format.parse("22/12/2019 18:00");
            date2 = format.parse("22/12/2019 18:00");
            date3 = format.parse("22/12/2019 18:00");
            date4 = format.parse("22/12/2019 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Assignment assignment1 = new Assignment("task1", date1);
    private Assignment assignment2 = new Assignment("task2", date2);
    private Assignment assignment3 = new Assignment ("as1", date3);
    private Assignment assignment4 = new Assignment("as2", date4);
    private TaskList taskList = new TaskList(new ArrayList<>());

    @Test
    void add() {
        taskList.add(assignment1);
        taskList.add(assignment2);
        taskList.add(assignment3);
        taskList.add(assignment4);
        try {
            assertEquals("[A] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", taskList.get(0).toString());
            assertEquals("[A] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", taskList.get(1).toString());
            assertEquals("[A] as1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", taskList.get(2).toString());
            assertEquals("[A] as2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", taskList.get(3).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void done() {
        taskList.add(assignment1);
        taskList.add(assignment2);
        int[] array = {0, 1};
        try {
            taskList.done(array);
            assertEquals("[A] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)\n" +
                            "[A] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)",
                    taskList.get(0).toString()+ "\n" + taskList.get(1).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void find() {
        taskList.add(assignment1);
        taskList.find("task");
        try {
            assertEquals("[A] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", taskList.get(0).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setPriority() {
        taskList.add(assignment1);
        String[] array = {"1", "high"};
        try {
            taskList.setPriority(array);
            assertEquals(taskList.get(0).getPriority(), Priority.high);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void reorder() {
        taskList.add(assignment1);
        taskList.add(assignment2);
        try {
            taskList.reorder(0, 1);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
        try {
            assertEquals("[A] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)\n" +
                    "[A] task1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", taskList.get(0).toString() + "\n" + taskList.get(1).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void replace() {
        taskList.add(assignment1);
        taskList.replace(0, assignment2);
        try {
            assertEquals("[A] task2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", taskList.get(0).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void snooze() {
        taskList.add(assignment1);
        taskList.add(assignment2);
        taskList.add(assignment3);
        try {
            taskList.snooze(0, 1, TimeUnit.hours);
            taskList.snooze(1,10, TimeUnit.minutes);
            taskList.snooze(2, 1, TimeUnit.day);
            assertEquals("[A] task1 (everyone) (by: Sun Dec 22 19:00:00 SGT 2019)", taskList.get(0).toString());
            assertEquals("[A] task2 (everyone) (by: Sun Dec 22 18:10:00 SGT 2019)", taskList.get(1).toString());
            assertEquals("[A] as1 (everyone) (by: Mon Dec 23 18:00:00 SGT 2019)", taskList.get(2).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }
}