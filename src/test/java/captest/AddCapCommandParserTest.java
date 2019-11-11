//@@author JasonLeeWeiHern

package captest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.capcalculator.AddCapCommand;
import gazeeebo.parser.CapCommandParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCapCommandParserTest {
    private Ui ui = new Ui();
    private HashMap<String, ArrayList<CapCommandParser>> map = new HashMap<>();
    private Map<String, ArrayList<CapCommandParser>> caplist = new TreeMap<>(map);

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
    void testAddCapCommand() {
        ui.fullCommand = "add 1,CS1231,4,A";
        AddCapCommand test = new AddCapCommand(ui, caplist);
        assertEquals("Successfully added: CS1231\n", output.toString());
    }

    @Test
    void testIncorrectFormatAddCapCommand() {
        ui.fullCommand = "add 1,CS1231,4,A and 2,EE2026,4,B";
        AddCapCommand test = new AddCapCommand(ui, caplist);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
