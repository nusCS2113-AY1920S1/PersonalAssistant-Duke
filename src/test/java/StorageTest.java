import CustomExceptions.RoomShareException;
import Enums.TimeUnit;
import Model_Classes.Assignment;
import Model_Classes.Leave;
import Model_Classes.Meeting;
import Model_Classes.Task;
import Operations.Storage;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static Date date, to, dateTest, dateTest2, dateTest3, dateTest4;
    static {
        try {
            date = format.parse("22/12/2019 18:00");
            to = format.parse("24/12/2019 18:00");
            dateTest = format.parse("12/12/2019 18:00");
            dateTest2 = format.parse("12/12/2019 10:00");
            dateTest3 = format.parse("12/12/2019 21:00");
            dateTest4 = format.parse("22/12/2019 13:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Task> al = new ArrayList<>();
//    private static TaskList tl1 = new TaskList(new ArrayList<Task>());
    private Assignment ts = new Assignment("assign", date);
    private Meeting ts1 = new Meeting("meet", date);
    private Leave ts2 = new Leave("leave", "user", date, to);
    private Storage storage = new Storage();
    private Meeting meetingTest = new Meeting("test1", dateTest, 2, TimeUnit.hours);
    private Meeting meetingTest2 = new Meeting("test2", dateTest2);
    private Assignment assignmentTest = new Assignment("subtasks", dateTest3);
    private Assignment assignmentTest2 = new Assignment("test6", dateTest4);

    private static String[] GetStringArray(ArrayList<Task> arr)
    {

        // declaration and initialise String Array
        String[] str = new String[arr.size()];
        int i =0;
        // ArrayList to Array Conversion
        for (Task s : arr) {

            // Assign each value to String array
            str[i] = s.toString();
            i++;
        }
        return str;
    }
    @Test
    void loadFile() throws RoomShareException {
        String fileName = "test.txt";
        ArrayList<Task> altest = storage.loadFile(fileName);
        assignmentTest2.setAssignee("harry");
        al.add(meetingTest);
        al.add(meetingTest2);
        al.add(assignmentTest);
        al.add(assignmentTest2);
        ts1.setDone(true);
        String[] str = GetStringArray(altest);
        String[] str1 = GetStringArray(al);

        assertEquals(str[0] + str[1] + str[2] + str[3],
                str1[0] + str1[1] + str1[2] + str[3]);
    }

    @Test
    void convertForStorage() throws RoomShareException {
        assertEquals("22/12/2019 18:00", storage.convertForStorage(ts1));
    }

    @Test
    void convertForStorageLeave() throws RoomShareException {
        assertEquals("22/12/2019 18:00-24/12/2019 18:00", storage.convertForStorageLeave(ts2));
    }
}
