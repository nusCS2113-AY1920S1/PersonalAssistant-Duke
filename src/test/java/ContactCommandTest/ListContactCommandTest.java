package ContactCommandTest;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Contact.ListContactCommand;
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

public class ListContactCommandTest {
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
    void testListContactsCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<String, String>(map);
        String LINE_BREAK = "------------------------------------------\n";
        contact.put("janel", "9625 1722");
        contact.put("jason", "9825 1822");
        ListContactCommand test = new ListContactCommand(contact, LINE_BREAK);
        assertEquals("Name:                         | Number:\n"
                + LINE_BREAK
                + "janel                         | 9625 1722\n"
                + LINE_BREAK
                + "jason                         | 9825 1822\n"
                + LINE_BREAK
                + "\nCEG CONTACTS:\n"
                + "\nNUS CONTACTS:\n", output.toString());
    }
}
