//@@author JasonLeeWeiHern
import gazeeebo.commands.tasks.RecurringCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.UI.Ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RecurringCommandTest  {
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private RecurringCommand testR = new RecurringCommand();
    private ArrayList<Task> list = new ArrayList<>();

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }
    @Test
    void testWeeklyDeadlineRecurring() throws IOException {
        Deadline newd = new Deadline("weekly assignment", "2019-01-01 01:01:01");
        list.add(newd);
        ui.fullCommand = "done 1";
        testR.addRecurring(list, 0,list.get(0).toString(),storage);
        assertEquals("\nI've automatically added this weekly task again:\n[D][ND] weekly assignment(by:08 Jan 2019 01:01:01)"
                + "\nNow you have " + list.size() + " tasks in the list.\n",output.toString());
    }
    @Test
    void testWeeklyEventRecurring() throws IOException {
        Event newE = new Event("weekly Career Talk", "2019-10-10 10:10:10-11:11:11");
        list.add(newE);
        ui.fullCommand = "done 1";
        testR.addRecurring(list, 0, list.get(0).toString(), storage);
        assertEquals("\nI've automatically added this weekly task again:\n[E][ND]weekly Career Talk(at:17 Oct 2019 10:10:10-11:11:11)"
                + "\nNow you have " + list.size() + " tasks in the list.\n",output.toString());
    }
    @Test
    void testMonthlyDeadlineRecurring() throws IOException {
        Deadline newd = new Deadline("monthly assignment", "2019-01-01 01:01:01");
        list.add(newd);
        ui.fullCommand = "done 1";
        testR.addRecurring(list, 0,list.get(0).toString(),storage);
        assertEquals("\nI've automatically added this monthly task again:\n[D][ND] monthly assignment(by:01 Feb 2019 01:01:01)"
                + "\nNow you have " + list.size() + " tasks in the list.\n",output.toString());
    }
    @Test
    void testMonthlyEventRecurring() throws IOException {
        Event newE = new Event("monthly Career Talk", "2019-10-10 10:10:10-11:11:11");
        list.add(newE);
        ui.fullCommand = "done 1";
        testR.addRecurring(list, 0, list.get(0).toString(), storage);
        assertEquals("\nI've automatically added this monthly task again:\n[E][ND]monthly Career Talk(at:10 Nov 2019 10:10:10-11:11:11)"
                + "\nNow you have " + list.size() + " tasks in the list.\n",output.toString());
    }
    @Test
    void testYearlyEventRecurring() throws IOException {
        Event newE = new Event("yearly Career Talk", "2019-10-10 10:10:10-11:11:11");
        list.add(newE);
        ui.fullCommand = "done 1";
        testR.addRecurring(list, 0, list.get(0).toString(), storage);
        assertEquals("\nI've automatically added this yearly task again:\n[E][ND]yearly Career Talk(at:10 Oct 2020 10:10:10-11:11:11)"
                + "\nNow you have " + list.size() + " tasks in the list.\n",output.toString());
    }

    @Test
    void testYearlyDeadlineRecurring() throws IOException {
        Deadline newd = new Deadline("yearly assignment", "2019-01-01 01:01:01");
        list.add(newd);
        ui.fullCommand = "done 1";
        testR.addRecurring(list, 0,list.get(0).toString(),storage);
        assertEquals("\nI've automatically added this yearly task again:\n[D][ND] yearly assignment(by:01 Jan 2020 01:01:01)"
                + "\nNow you have " + list.size() + " tasks in the list.\n",output.toString());
    }
}

