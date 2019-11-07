package capCalculatorTest;

import gazeeebo.UI.Ui;
import gazeeebo.parsers.CAPCommandParser;
import gazeeebo.commands.capCalculator.FindCAPCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindCAPCommandParserTest {
    private static final String LINEBREAK = "------------------------------\n";
    private Ui ui = new Ui();
    private HashMap<String, ArrayList<CAPCommandParser>> map = new HashMap<>();
    private Map<String, ArrayList<CAPCommandParser>> CAPList = new TreeMap<>(map);

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
        CAPCommandParser newCAP = new CAPCommandParser("CS1231", 4, "A");
        ArrayList<CAPCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommandParser> list2 = new ArrayList<>();
        CAPCommandParser newCAP2 = new CAPCommandParser("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1", list);
        CAPList.put("2", list2);
        ui.fullCommand = "find CG1112";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "2   | CG1112      | 6  | A\n"
                + LINEBREAK, output.toString());
    }

    @Test
    void testFindNotInTheCapListCommand() {
        CAPCommandParser newCAP = new CAPCommandParser("CS1231", 4, "A");
        ArrayList<CAPCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommandParser> list2 = new ArrayList<>();
        CAPCommandParser newCAP2 = new CAPCommandParser("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1", list);
        CAPList.put("2", list2);
        ui.fullCommand = "find CS2101";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("CS2101 is not found in the list.\n", output.toString());
    }

    @Test
    void testFindIncorrectFormatInTheCapListCommand() {
        CAPCommandParser newCAP = new CAPCommandParser("CS1231", 4, "A");
        ArrayList<CAPCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommandParser> list2 = new ArrayList<>();
        CAPCommandParser newCAP2 = new CAPCommandParser("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1", list);
        CAPList.put("2", list2);
        ui.fullCommand = "find cs2101 cs2203";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
