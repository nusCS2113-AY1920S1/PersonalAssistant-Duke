//@@author JasonLeeWeiHern

package captest;

import gazeeebo.ui.Ui;
import gazeeebo.parser.CapCommandParser;
import gazeeebo.commands.capcalculator.ListCapCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCapCommandParserTest {
    private static final String LINEBREAK = "------------------------------\n";
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
    void testListAllCapCommand() throws IOException {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCap2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCap2);
        caplist.put("1", list);
        caplist.put("2", list2);
        ui.fullCommand = "list all";
        ListCapCommand test = new ListCapCommand(ui, caplist, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "1   | CS1231      | 4  | A\n" + LINEBREAK
                + "2   | CG1112      | 6  | A\n"
                + LINEBREAK
                + "Total CAP: 5.0\n", output.toString());
    }

    @Test
    void testListSemFoundCapCommand() throws IOException {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCap2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCap2);
        caplist.put("1", list);
        caplist.put("2", list2);
        ui.fullCommand = "list 1";
        ListCapCommand test = new ListCapCommand(ui, caplist, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "1   | CS1231      | 4  | A\n"
                + LINEBREAK
                + "Sem 1 CAP: 5.0\n", output.toString());
    }

    @Test
    void testListSemFoundEmptyCapCommand() throws IOException {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCap2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCap2);
        caplist.put("1", list);
        caplist.put("2", list2);
        ui.fullCommand = "list 3";
        ListCapCommand test = new ListCapCommand(ui, caplist, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "No modules in this semester!\n", output.toString());
    }

    @Test
    void testListSemNotFoundCapCommand() throws IOException {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCap2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCap2);
        caplist.put("1", list);
        caplist.put("2", list2);
        ui.fullCommand = "list 1 and 2";
        ListCapCommand test = new ListCapCommand(ui, caplist, LINEBREAK);
        assertEquals("Please Input in the correct format\n", output.toString());
    }

    @Test
    void testListNonsenseCapCommand() throws IOException {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCap2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCap2);
        caplist.put("1", list);
        caplist.put("2", list2);
        ui.fullCommand = "list nonsense";
        ListCapCommand test = new ListCapCommand(ui, caplist, LINEBREAK);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
