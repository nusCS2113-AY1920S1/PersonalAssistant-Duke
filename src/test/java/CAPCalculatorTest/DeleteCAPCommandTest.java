package CAPCalculatorTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.capCalculator.CAPCommand;
import gazeeebo.commands.capCalculator.DeleteCAPCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCAPCommandTest {
    private static final String LINEBREAK = "------------------------------\n";
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private ArrayList<Task> list = new ArrayList<>();
    private Stack<String> commandStack = new Stack<>();
    private ArrayList<Task> deletedTask = new ArrayList<>();
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
    void testDeleteCAPCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1",list);
        ui.fullCommand = "delete CS1231";
        DeleteCAPCommand test = new DeleteCAPCommand(ui, CAPList);
        assertEquals("Successfully deleted: CS1231\n", output.toString());
    }

    @Test
    void testDeleteNotInCAPListCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1",list);
        ui.fullCommand = "delete CG1111";
        DeleteCAPCommand test = new DeleteCAPCommand(ui, CAPList);
        assertEquals("CG1111 is not found in the list.\n", output.toString());
    }

    @Test
    void testDeleteIncorrectFormatInCAPListCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1",list);
        ui.fullCommand = "delete CG1111 and CS1231";
        DeleteCAPCommand test = new DeleteCAPCommand(ui, CAPList);
        assertEquals("Please Input in the correct format\n", output.toString());
    }

}
