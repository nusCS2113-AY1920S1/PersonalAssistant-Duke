import CustomExceptions.RoomShareException;
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
    private static Date date, to;
    static {
        try {
            date = format.parse("22/12/2019 18:00");
            to = format.parse("24/12/2019 18:00");
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

    private static String[] GetStringArray(ArrayList<Task> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];
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
        al.add(ts);
        al.add(ts1);
        al.add(ts2);
        ts1.setDone(true);
        String[] str = GetStringArray(altest);
        String[] str1 = GetStringArray(al);

        assertEquals(str.toString(), str1.toString());
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
