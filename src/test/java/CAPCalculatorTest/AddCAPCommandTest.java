package CAPCalculatorTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.capCalculator.AddCAPCommand;
import gazeeebo.commands.capCalculator.CAPCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCAPCommandTest {
    private static final String LINEBREAK = "------------------------------\n";
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private ArrayList<Task> list = new ArrayList<>();
    private Stack<String> commandStack = new Stack<>();
    private ArrayList<Task> deletedTask = new ArrayList<>();
    private HashMap<String, ArrayList<CAPCommand>> map = new HashMap<>();
    private Map<String, ArrayList<CAPCommand>> CAPList = new TreeMap<>(map);

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
    void testAddCAPCommand () throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("1,CS1231,4,A".getBytes());
        System.setIn(in);
        AddCAPCommand test = new AddCAPCommand(ui, CAPList);
        assertEquals("Input in this format: semNumber,Module_Code,total_MC,CAP\n"
                + "Successfully added: CS1231\n", output.toString());
    }
}
