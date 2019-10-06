import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import UI.Ui;
import commands.EditCommand;
import commands.EventCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EditCommandTest {
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
    void testexecute() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        EditCommand test = new EditCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Deadline testd = new Deadline("tutorial homework", "2019-12-12 10:10:10");
        tasks.add(testd);
        ui.FullCommand = "edit 1 monthly assign /by 2019-12-12 10:10:10";
        test.execute(tasks,ui,storage);
        assertEquals("Ok, we have edited your Deadline description and time. \n" +
                        "\tFrom: [D][ND] tutorial homework(by:12 Dec 2019 10:10:10)\n" +
                        "\tTo:   [D][ND] monthly assign (by:12 Dec 2019 10:10:10)",
                output.toString());
    }

}