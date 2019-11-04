package contactCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.contact.FindContactCommand;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindContactsCommandTest {
    private Ui ui = new Ui();

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
    void testFindContactsCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<>(map);
        String LINE_BREAK = "------------------------------------------\n";
        contact.put("janel", "9625 1722");
        contact.put("jason", "9825 1822");
        ui.fullCommand = "find jason";
        FindContactCommand test = new FindContactCommand(ui, contact, LINE_BREAK);
        assertEquals("Name:                         | Number:\n"
                + LINE_BREAK
                + "jason                         | 9825 1822\n"
                + LINE_BREAK, output.toString());
    }

    @Test
    void testUnableFindContactCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<>(map);
        String LINE_BREAK = "------------------------------------------\n";
        contact.put("janel", "9625 1722");
        contact.put("jason", "9825 1822");
        ui.fullCommand = "find jay";
        FindContactCommand test = new FindContactCommand(ui, contact, LINE_BREAK);
        assertEquals("jay is not found in the list.\n", output.toString());
    }

    @Test
    void testIncorrectFormatFindContactCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<>(map);
        String LINE_BREAK = "------------------------------------------\n";
        contact.put("janel", "9625 1722");
        contact.put("jason", "9825 1822");
        ui.fullCommand = "find janel and jason";
        FindContactCommand test = new FindContactCommand(ui, contact, LINE_BREAK);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
