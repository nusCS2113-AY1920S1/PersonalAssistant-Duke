package ContactCommandTest;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Contact.DeleteContactCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteContactCommandTest {
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private ArrayList<Task> list = new ArrayList<>();
    private Stack<String> commandStack = new Stack<>();
    private ArrayList<Task> deletedTask = new ArrayList<>();

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
    void testDeleteInContactsCommand() {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);
        contact.put("jason", "9625 1722");
        ui.fullCommand = "delete jason";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("Successfully deleted: jason\n", output.toString());
    }

    @Test
    void testDeleteNotInContactsCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<String, String>(map);
        contact.put("janel", "9625 1722");
        ui.fullCommand = "delete jason";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("jason is not found in the list.\n", output.toString());
    }

    @Test
    void testDeleteWrongFormatContactsCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<String, String>(map);
        contact.put("janel", "9625 1722");
        ui.fullCommand = "delete";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("Incorrect format: delete name\n", output.toString());
    }
}
