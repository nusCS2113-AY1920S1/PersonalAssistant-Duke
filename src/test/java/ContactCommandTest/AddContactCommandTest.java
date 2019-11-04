
package ContactCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.contact.AddContactCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddContactCommandTest {
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
    void testAddContactsCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<String, String>(map);
        ui.fullCommand = "add Test,96251822";
        AddContactCommand test = new AddContactCommand(ui, contact);
        assertEquals("Successfully added: Test,96251822\n", output.toString());
    }
    @Test
    void testIncorrectFromatAddContactsCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<String, String>(map);
        ui.fullCommand = "add Test,96251822 and Jason,123412";
        AddContactCommand test = new AddContactCommand(ui, contact);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
