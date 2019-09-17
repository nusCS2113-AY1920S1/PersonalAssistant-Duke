import duke.DukeException;
import duke.Storage;
import duke.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {
    //    public static Ui ui;
    //    public static Storage storage;

    //    private void testSetup() throws DukeException {
    //        ui = new Ui();
    //        storage = new Storage("./data/test_data.txt");
    //    }

    @Test
    public void test() throws DukeException {
        dummyTest();

        //Setting up configurations
        //testSetup();

        //Tests for todo
        new TodoTest().eat();
        new TodoTest().jog();
        new TodoTest().todo();

        //Test for deadline
        new DeadlineTest().test();
        new DeadlineTest().examBy_Date();

        //Test for event
        new EventTest().test();
        new EventTest().birthdayAt_myBday();
        //new EventTest().clashEvent();

        //Test for reminders
        new ReminderTest().test();

        //Test for freetime
        new FindFreeTimesTest().testTaskDateBeforeCurrent();

        //Test for do after task
        new DoAfterTaskTest().test();

        //Test for within period task
        new WithinPeriodTask().test();

        //Test for viewschedule
        new ViewSchedulesTest().test();
    }

    public void dummyTest() {
        assertEquals(2,2);
    }

}
