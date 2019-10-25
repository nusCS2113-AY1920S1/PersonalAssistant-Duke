import gazeeebo.commands.contact.*;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.TriviaManager.TriviaManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactsCommandTest {
    Ui ui = new Ui();
    Storage storage = new Storage();
    TriviaManager triviaManager = new TriviaManager(storage);
    ArrayList<Task> list = new ArrayList<>();
    Stack<String> commandStack = new Stack<>();
    ArrayList<Task> deletedTask = new ArrayList<>();

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    public ContactsCommandTest() throws IOException {
    }

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
    void testwelcome() throws IOException {
        ContactCommand testc = new ContactCommand();
        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
        System.setIn(in);
        testc.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
        assertEquals("Welcome to your contacts page! What would you like to do?\n\n"
                        + "__________________________________________________________\n"
                        + "1. Add contacts: add\n"
                        + "2. Find contacts: find name\n"
                        + "3. Delete a contact: delete name\n"
                        + "4. See your contacts list: list\n"
                        + "5. Exit contact page: esc\n"
                        + "__________________________________________________________\n\n"
                + "Going back to Main Menu\n"
                , output.toString()
        );
    }

    @Test
    void testAddContactsCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<String, String>(map);
        ByteArrayInputStream in = new ByteArrayInputStream("Test,9625 1822".getBytes());
        System.setIn(in);
        AddContactCommand test = new AddContactCommand(ui, contact);
        assertEquals("Input in this format: Name,Number\n"
                + "Successfully added: Test,9625 1822\n", output.toString());
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
    @Test
    void testFindContactsCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<String, String>(map);
        String LINE_BREAK = "------------------------------------------\n";
        contact.put("janel", "9625 1722");
        contact.put("jason", "9825 1822");
        ui.fullCommand = "find jason";
        FindContactCommand test = new FindContactCommand(ui, contact, LINE_BREAK);
        assertEquals("Name:                         | Number:\n"
                + LINE_BREAK
                + "jason                         | 9825 1822\n"
                + LINE_BREAK, output.toString());
    }

    @Test
    void testUnableFindContactCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<String, String>(map);
        String LINE_BREAK = "------------------------------------------\n";
        contact.put("janel", "9625 1722");
        contact.put("jason", "9825 1822");
        ui.fullCommand = "find Elis";
        FindContactCommand test = new FindContactCommand(ui, contact, LINE_BREAK);
        assertEquals("Elis is not found in the list.", output.toString());
    }
}
