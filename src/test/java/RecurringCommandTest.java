
import gazeeebo.commands.tasks.RecurringCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Task;
import gazeeebo.UI.Ui;
<<<<<<< HEAD
import gazeeebo.commands.tasks.RecurringCommand;
=======
>>>>>>> e3e66bcb49dadb4b966d587fb444e87e331407a2
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
    void testRecurring() throws ParseException, IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        RecurringCommand testR = new RecurringCommand();
        ArrayList<Task> list = new ArrayList<>();
        Deadline newd = new Deadline("yearly assignment", "2019-01-01 01:01:01");
        list.add(newd);
        ui.fullCommand = "done 1";

        testR.addRecurring(list, 0,list.get(0).toString(),storage);
        assertEquals("\nI've automatically added this yearly task again:\n[D][ND] yearly assignment(by:01 Jan 2020 01:01:01)"
                + "\nNow you have " + list.size() + " tasks in the list.\n",output.toString());
    }
}

