import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Contact.ContactsCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class ContactsCommandTest {
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
    void testwelcome() throws IOException, ParseException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ArrayList<Task> list = new ArrayList<>();
        Stack<String> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<>();
        ContactsCommand testc = new ContactsCommand();
        ByteArrayInputStream in = new ByteArrayInputStream("contact".getBytes());
        System.setIn(in);
        ByteArrayInputStream second = new ByteArrayInputStream("esc".getBytes());
        System.setIn(second);
        testc.execute(list,ui,storage,commandStack,deletedTask);
        assertEquals("CONTACTS PAGE\n\n" +
                "Name:                         | Number:\n------------------------------------------\n" +
                "RenHao                        | 8712 2345\n------------------------------------------\n" +
                "janel                         | 9123 4567\n------------------------------------------\n" +
                "jason                         | 9123 4567\n------------------------------------------\n" +
                "jess                          | 9123 4567\n------------------------------------------\n" +
                "yueyu                         | 9876 5432\n------------------------------------------\n" +
                "\nNUS CONTACTS:\n" +
                "NUS Campus.S(Bukit Timah)     | 6516 3636\n------------------------------------------\n" +
                "NUS Campus.S(KR)              | 6874 1616\n------------------------------------------\n" +
                "NUS Campus.S(Outram)          | 6222 5568\n------------------------------------------\n" +
                "NUS Lifeline                  | 6516 7777\n------------------------------------------\n" +
                "NUS OCS BTC                   | 6516 3636\n------------------------------------------\n" +
                "NUS OCS KRC                   | 6874 1616\n------------------------------------------\n" +
                "NUS OCS UTOWN                 | 6601 2004\n------------------------------------------\n" +
                "NUS OSHE                      | 6778 6304\n------------------------------------------\n",output.toString());

    }
}
