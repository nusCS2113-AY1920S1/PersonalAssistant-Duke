//@@author JasonLeeWeiHern

package captest;

import gazeeebo.ui.Ui;
import gazeeebo.parser.CapCommandParser;
import gazeeebo.commands.capcalculator.DeleteCapCommand;
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

public class DeleteCapCommandParserTest {
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
    void testDeleteCapCommand() {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        caplist.put("1", list);
        ui.fullCommand = "delete CS1231";
        DeleteCapCommand test = new DeleteCapCommand(ui, caplist);
        assertEquals("Successfully deleted: CS1231\n", output.toString());
    }

    @Test
    void testDeleteNotInCapListCommand() {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        caplist.put("1", list);
        ui.fullCommand = "delete CG1111";
        DeleteCapCommand test = new DeleteCapCommand(ui, caplist);
        assertEquals("CG1111 is not found in the list.\n", output.toString());
    }

    @Test
    void testDeleteIncorrectFormatInCapListCommand() {
        CapCommandParser newCap = new CapCommandParser("CS1231", 4, "A");
        ArrayList<CapCommandParser> list = new ArrayList<>();
        list.add(newCap);
        caplist.put("1", list);
        ui.fullCommand = "delete CG1111 and CS1231";
        DeleteCapCommand test = new DeleteCapCommand(ui, caplist);
        assertEquals("Please Input in the correct format\n", output.toString());
    }

}
