//@@author JasonLeeWeiHern

package capCalculatorTest;

import gazeeebo.UI.Ui;
import gazeeebo.parser.CapCommandParser;
import gazeeebo.commands.capCalculator.FindCapCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindCapCommandParserTest {
    private static final String LINEBREAK = "------------------------------\n";
    private Ui ui = new Ui();
    private HashMap<String, ArrayList<CapCommandParser>> map = new HashMap<>();
    private Map<String, ArrayList<CapCommandParser>> CAPList = new TreeMap<>(map);

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
    void testFindByModuleCodeCAPCommand() {
        CapCommandParser newCAP = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCAP2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1", list);
        CAPList.put("2", list2);
        ui.fullCommand = "find CG1112";
        FindCapCommand test = new FindCapCommand(ui, CAPList, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "2   | CG1112      | 6  | A\n"
                + LINEBREAK, output.toString());
    }

    @Test
    void testFindNotInTheCapListCommand() {
        CapCommandParser newCAP = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCAP2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1", list);
        CAPList.put("2", list2);
        ui.fullCommand = "find CS2101";
        FindCapCommand test = new FindCapCommand(ui, CAPList, LINEBREAK);
        assertEquals("CS2101 is not found in the list.\n", output.toString());
    }

    @Test
    void testFindIncorrectFormatInTheCapListCommand() {
        CapCommandParser newCAP = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CapCommandParser> list2 = new ArrayList<>();
        CapCommandParser newCAP2 = new CapCommandParser("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1", list);
        CAPList.put("2", list2);
        ui.fullCommand = "find cs2101 cs2203";
        FindCapCommand test = new FindCapCommand(ui, CAPList, LINEBREAK);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
