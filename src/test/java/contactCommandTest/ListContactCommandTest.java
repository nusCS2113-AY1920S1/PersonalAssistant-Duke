
package contactCommandTest;

import gazeeebo.commands.contact.ListContactCommand;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListContactCommandTest {
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
        Map<String, String> contact = new TreeMap<>(map);
        String LINE_BREAK = "------------------------------------------\n";
        contact.put("janel", "9625 1722");
        contact.put("jason", "9825 1822");
        ListContactCommand test = new ListContactCommand(contact);
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
