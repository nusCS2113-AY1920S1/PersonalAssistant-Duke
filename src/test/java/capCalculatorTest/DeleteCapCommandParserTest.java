//@@author JasonLeeWeiHern
package capCalculatorTest;

import gazeeebo.UI.Ui;
import gazeeebo.parser.CapCommandParser;
import gazeeebo.commands.capCalculator.DeleteCapCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCapCommandParserTest {
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
    void testDeleteCAPCommand() {
        CapCommandParser newCAP = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1", list);
        ui.fullCommand = "delete CS1231";
        DeleteCapCommand test = new DeleteCapCommand(ui, CAPList);
        assertEquals("Successfully deleted: CS1231\n", output.toString());
    }

    @Test
    void testDeleteNotInCAPListCommand() {
        CapCommandParser newCAP = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1", list);
        ui.fullCommand = "delete CG1111";
        DeleteCapCommand test = new DeleteCapCommand(ui, CAPList);
        assertEquals("CG1111 is not found in the list.\n", output.toString());
    }

    @Test
    void testDeleteIncorrectFormatInCAPListCommand() {
        CapCommandParser newCAP = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1", list);
        ui.fullCommand = "delete CG1111 and CS1231";
        DeleteCapCommand test = new DeleteCapCommand(ui, CAPList);
        assertEquals("Please Input in the correct format\n", output.toString());
    }

}
