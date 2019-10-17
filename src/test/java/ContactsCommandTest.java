import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Contact.AddContactCommand;
import gazeeebo.commands.Contact.ContactsCommand;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.commands.Contact.DeleteContactCommand;
import gazeeebo.commands.Contact.ListContactCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactsCommandTest {
    Ui ui = new Ui();
    Storage storage = new Storage();
    TriviaManager triviaManager = new TriviaManager();
    ArrayList<Task> list = new ArrayList<>();
    Stack<String> commandStack = new Stack<>();
    ArrayList<Task> deletedTask = new ArrayList<>();

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
    void testwelcome() throws IOException {
        ContactsCommand testc = new ContactsCommand();
        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
        System.setIn(in);
        testc.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
        assertEquals("CONTACTS PAGE\n\n"
                + "Name:                         | Number:\n------------------------------------------\n"
                + "RenHao                        | 8712 2345\n------------------------------------------\n"
                + "janel                         | 9123 4567\n------------------------------------------\n"
                + "jason                         | 9123 4567\n------------------------------------------\n"
                + "jess                          | 9123 4567\n------------------------------------------\n"
                + "yueyu                         | 9876 5432\n------------------------------------------\n"
                + "\nNUS CONTACTS:\n"
                + "NUS Campus.S(Bukit Timah)     | 6516 3636\n------------------------------------------\n"
                + "NUS Campus.S(KR)              | 6874 1616\n------------------------------------------\n"
                + "NUS Campus.S(Outram)          | 6222 5568\n------------------------------------------\n"
                + "NUS Lifeline                  | 6516 7777\n------------------------------------------\n"
                + "NUS OCS BTC                   | 6516 3636\n------------------------------------------\n"
                + "NUS OCS KRC                   | 6874 1616\n------------------------------------------\n"
                + "NUS OCS UTOWN                 | 6601 2004\n------------------------------------------\n"
                + "NUS OSHE                      | 6778 6304\n------------------------------------------\n",
                output.toString()
        );
    }

    @Test
    void testAddContactsCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);
        ByteArrayInputStream in = new ByteArrayInputStream("Test,9625 1822".getBytes());
        System.setIn(in);
        AddContactCommand test = new AddContactCommand(ui, contact);
        assertEquals("Input in this format: Name,Number\n"
                + "Okay we have successfully added a new contact - Test,9625 1822\n", output.toString());
    }

    @Test
    void testDeleteInContactsCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);
        contact.put("jason", "9625 1722");
        ui.FullCommand = "delete jason";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("jason has been removed.\n", output.toString());
    }

    @Test
    void testDeleteNotInContactsCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);
        contact.put("janel", "9625 1722");
        ui.FullCommand = "delete jason";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("jason is not in the list.\n", output.toString());
    }

    @Test
    void testDeleteWrongFormatContactsCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);
        contact.put("janel", "9625 1722");
        ui.FullCommand = "delete";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("You need to indicate what you want to delete, Format: delete name\n", output.toString());
    }

    @Test
    void testListContactsCommand() {
        HashMap<String, String> map = new HashMap<>(); //Read the file
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
                + LINE_BREAK + "\nNUS CONTACTS:\n", output.toString());
    }
}
