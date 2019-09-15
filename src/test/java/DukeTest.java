import duke.DukeException;
import duke.Storage;
import duke.Ui;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {
    public static Ui ui;
    public static Storage storage;

    private void testSetup() throws DukeException {
        new File("./data").mkdir();
        File file = new File("./data/test_data.txt");
        try {
            file.createNewFile();
        } catch (IOException error) {
            System.out.println("ERROR, FAILED TO CREATE");
        }
        ui = new Ui();
        storage = new Storage("./data/test_data.txt");
    }

    @Test
    public void test() throws DukeException {
        dummyTest();

        //Setting up configurations
        testSetup();

        //Tests for todo
        new TodoTest().test("todo eat");
        new TodoTest().jog("todo jog");
        new TodoTest().todo("todo todo");

        //Test for deadline
        new DeadlineTest().test("deadline test /by 0000");
        new DeadlineTest().examBy_Date("deadline exam /by 01/01/2019");

        //Test for event
        new EventTest().test("event test /at 0000");
        new EventTest().birthdayAt_myBday("event bday /at 06/06/2019");

        //Test for reminders
        new ReminderTest().test();

    }

    public void dummyTest() {
        assertEquals(2,2);
    }

}
