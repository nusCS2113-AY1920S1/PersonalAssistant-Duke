import CustomExceptions.RoomShareException;
import Enums.Priority;
import Enums.TimeUnit;
import Model_Classes.Assignment;
import Model_Classes.Leave;
import Model_Classes.Meeting;
import Operations.OverdueList;
import Operations.TaskList;
import Operations.TempDeleteList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OverdueListTest {
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;

    {
        try {
            date1 = format.parse("22/12/2019 18:00");
            date2 = format.parse("23/12/2019 18:00");
            date3 = format.parse("24/12/2019 18:00");
            date4 = format.parse("25/12/2019 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Assignment assignment1 = new Assignment("assignment1", date1);
    private Assignment assignment2 = new Assignment("assignment2", date2);
    private Meeting meeting1 = new Meeting("meeting1", date3);
    private Meeting meeting2 = new Meeting("meeting2", date4);

    private Leave leave = new Leave("leave", "kel", date3, date4);
    private OverdueList overdueList = new OverdueList(new ArrayList<>());

    @Test
    void add() {
        overdueList.add(assignment1);
        overdueList.add(assignment2);
        overdueList.add(meeting1);
        overdueList.add(meeting2);
        try {
            assertEquals("[A] assignment1 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", overdueList.get(0).toString());
            assertEquals("[A] assignment2 (everyone) (by: Mon Dec 23 18:00:00 SGT 2019)", overdueList.get(1).toString());
            assertEquals("[M] meeting1 (everyone) (on: Tue Dec 24 18:00:00 SGT 2019)", overdueList.get(2).toString());
            assertEquals("[M] meeting2 (everyone) (on: Wed Dec 25 18:00:00 SGT 2019)", overdueList.get(3).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void reschedule() {
        overdueList.add(assignment1);
        overdueList.add(meeting1);
        overdueList.add(assignment2);
        overdueList.add(meeting2);
        int[] index = {0, 1};
        try {
            overdueList.reschedule(index, new TaskList(new ArrayList<>()));
            assertEquals("[A] assignment2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)\n" +
                    "[M] meeting2 (everyone) (by: Sun Dec 22 18:00:00 SGT 2019)", overdueList.get(0).toString() + "\n" + overdueList.get(1).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void remove() {
        overdueList.add(assignment1);
        overdueList.add(meeting1);
        overdueList.add(assignment2);
        overdueList.add(meeting2);
        int[] index = {0, 1};
        try {
            overdueList.remove(index, new TempDeleteList(new ArrayList<>()));
            assertEquals("[A] assignment2 (everyone) (by: Mon Dec 23 18:00:00 SGT 2019)\n" +
                    "[M] meeting2 (everyone) (on: Wed Dec 25 18:00:00 SGT 2019)", overdueList.get(0).toString() + "\n" + overdueList.get(1).toString());
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

}
