import Storage.Storage;
import Tasks.Deadline;
import Tasks.Task;
import UI.Ui;
import commands.EditCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EdiCommandTest {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    //saving the original System.out
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        //tell java to print to my own stream
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }


    @Test
    public void testexecute() throws ParseException, IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        String input = ui.FullCommand;
        EditCommand test = new EditCommand();
        ArrayList<Task> tasks = storage.ReadFile();

        Deadline testd = new Deadline("tutorial homework", "2019-12-12 10:10:10");
        tasks.add(testd);
        ui.FullCommand = "edit 9 monthly assign /by 2019-12-12 10:10:10";
        test.execute(tasks,ui,storage);
        assertEquals("Ok, have edited your Deadline description and time.\n\tFrom: [D][?]monthly assig(by:12 Dec 2019 10:10:10)\n\tTo:   [D][?]monthly assig (by:12 Dec 2019 10:10:10)",
                output.toString());

    }

}
