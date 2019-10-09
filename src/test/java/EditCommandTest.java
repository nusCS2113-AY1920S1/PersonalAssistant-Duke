import Storage.Storage;
import Tasks.Deadline;
import Tasks.Task;
import UI.Ui;
<<<<<<< HEAD
import commands.EditCommand;
=======
import commands.*;
>>>>>>> 5d7d0dab82706313eceaddaf30796be95d1e509d
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }


    @Test
    void testEditTime() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Deadline testd = new Deadline("tutorial homework", "2019-12-12 10:10:10");
        tasks.add(testd);
<<<<<<< HEAD
        ui.FullCommand = "edit 1 monthly assign /by 2019-12-12 10:10:10";
        try {
            test.execute(tasks,ui,storage, CommandStack, deletedTask);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception.DukeException dukeException) {
            dukeException.printStackTrace();
        }
        assertEquals("Ok, we have edited your Deadline description and time. \n" +
                        "\tFrom: [D][\u2718]tutorial homework(by:12 Dec 2019 10:10:10)\n" +
                        "\tTo:   [D][\u2718]monthly assign (by:12 Dec 2019 10:10:10)",
=======
        ui.FullCommand = "edit 1";
        ByteArrayInputStream in = new ByteArrayInputStream("edit 1".getBytes());
        System.setIn(in);
        ByteArrayInputStream second = new ByteArrayInputStream("time".getBytes());
        System.setIn(second);
        ByteArrayInputStream third = new ByteArrayInputStream("2020-12-12 11:11:11".getBytes());
        System.setIn(third);
        EditTimeCommand test = new EditTimeCommand(tasks, ui, storage, 0);
        assertEquals("Type your time:\n" +
                        "Ok, we have edited your Deadline time." +
                        "\n\tFrom: [D][ND] tutorial homework(by:12 Dec 2019 10:10:10)" +
                        "\n\tTo:   [D][ND] tutorial homework(by:12 Dec 2020 11:11:11)\n",
>>>>>>> 5d7d0dab82706313eceaddaf30796be95d1e509d
                output.toString());
    }

    @Test
    void testEditDescription() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Deadline testd = new Deadline("tutorial homework", "2019-12-12 10:10:10");
        tasks.add(testd);
        ui.FullCommand = "edit 1";
        ByteArrayInputStream in = new ByteArrayInputStream("edit 1".getBytes());
        System.setIn(in);
        ByteArrayInputStream second = new ByteArrayInputStream("description".getBytes());
        System.setIn(second);
        ByteArrayInputStream third = new ByteArrayInputStream("tutorial assignment".getBytes());
        System.setIn(third);
        EditDescriptionCommand test = new EditDescriptionCommand(tasks, ui, storage, 0);
        assertEquals("Type your description:\n" +
                        "Ok, we have edited your Deadline description." +
                        "\n\tFrom: [D][ND] tutorial homework(by:12 Dec 2019 10:10:10)" +
                        "\n\tTo:   [D][ND] tutorial assignment(by:12 Dec 2019 10:10:10)\n",
                output.toString());
    }
    @Test
    void testBoth() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Event testE = new Event("banner rev", "2019-12-12 10:10:10-11:00:00");
        tasks.add(testE);
        ui.FullCommand = "edit 1";
        ByteArrayInputStream in = new ByteArrayInputStream("edit 1".getBytes());
        System.setIn(in);
        ByteArrayInputStream second = new ByteArrayInputStream("both".getBytes());
        System.setIn(second);
        ByteArrayInputStream third = new ByteArrayInputStream("basketball trg /at 2019-12-12 09:00:00-11:00:00".getBytes());
        System.setIn(third);
        EditBothCommand test = new EditBothCommand(tasks, ui, storage, 0);
        assertEquals("Type your description & date:\n" +
                        "Ok, we have edited your Event description and time." +
                        "\n\tFrom: [E][ND] banner rev(at:12 Dec 2019 10:10:10-11:00:00)" +
                        "\n\tTo:   [E][ND] basketball trg (at:12 Dec 2019 09:00:00-11:00:00)\n",
                output.toString());
    }
}