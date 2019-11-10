package capCalculatorTest;

import gazeeebo.UI.Ui;
import gazeeebo.parser.CAPCommandParser;
import gazeeebo.commands.capCalculator.DeleteCAPCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCAPCommandParserTest {
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
    void testDeleteCAPCommand() {
        CAPCommandParser newCAP = new CAPCommandParser("CS1231", 4, "A");
        ArrayList<CAPCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1", list);
        ui.fullCommand = "delete CS1231";
        DeleteCAPCommand test = new DeleteCAPCommand(ui, CAPList);
        assertEquals("Successfully deleted: CS1231\n", output.toString());
    }

    @Test
    void testDeleteNotInCAPListCommand() {
        CAPCommandParser newCAP = new CAPCommandParser("CS1231", 4, "A");
        ArrayList<CAPCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1", list);
        ui.fullCommand = "delete CG1111";
        DeleteCAPCommand test = new DeleteCAPCommand(ui, CAPList);
        assertEquals("CG1111 is not found in the list.\n", output.toString());
    }

    @Test
    void testDeleteIncorrectFormatInCAPListCommand() {
        CAPCommandParser newCAP = new CAPCommandParser("CS1231", 4, "A");
        ArrayList<CAPCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1", list);
        ui.fullCommand = "delete CG1111 and CS1231";
        DeleteCAPCommand test = new DeleteCAPCommand(ui, CAPList);
        assertEquals("Please Input in the correct format\n", output.toString());
    }

}
