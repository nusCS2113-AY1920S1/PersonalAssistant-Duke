//@@author JasonLeeWeiHern

package captest;

import gazeeebo.UI.Ui;
import gazeeebo.parser.CapCommandParser;
import gazeeebo.commands.capcalculator.FindCapCommand;
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

public class FindCapCommandParserTest {
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
    void testFindByModuleCodeCapCommand() {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCap2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCap2);
        caplist.put("1", list);
        caplist.put("2", list2);
        ui.fullCommand = "find CG1112";
        FindCapCommand test = new FindCapCommand(ui, caplist, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "2   | CG1112      | 6  | A\n"
                + LINEBREAK, output.toString());
    }

    @Test
    void testFindNotInTheCapListCommand() {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCap2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCap2);
        caplist.put("1", list);
        caplist.put("2", list2);
        ui.fullCommand = "find CS2101";
        FindCapCommand test = new FindCapCommand(ui, caplist, LINEBREAK);
        assertEquals("CS2101 is not found in the list.\n", output.toString());
    }

    @Test
    void testFindIncorrectFormatInTheCapListCommand() {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCap2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCap2);
        caplist.put("1", list);
        caplist.put("2", list2);
        ui.fullCommand = "find cs2101 cs2203";
        FindCapCommand test = new FindCapCommand(ui, caplist, LINEBREAK);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
