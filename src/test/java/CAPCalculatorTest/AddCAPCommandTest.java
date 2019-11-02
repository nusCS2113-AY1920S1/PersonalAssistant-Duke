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
        ui.fullCommand = "add 1,CS1231,4,A";
        AddCAPCommand test = new AddCAPCommand(ui, CAPList);
        assertEquals("Successfully added: CS1231\n", output.toString());
    }

    @Test
    void testIncorrectFormatAddCAPCommand () throws IOException {
        ui.fullCommand = "add 1,CS1231,4,A and 2,EE2026,4,B";
        AddCAPCommand test = new AddCAPCommand(ui, CAPList);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
