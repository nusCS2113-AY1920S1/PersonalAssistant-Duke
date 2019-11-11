//@@author JasonLeeWeiHern

package contactcommandtest;



import gazeeebo.commands.contact.ListContactCommand;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
        String linebreak = "------------------------------------------\n";
        contact.put("janel", "9625 1722");
        contact.put("jason", "9825 1822");
        ListContactCommand test = new ListContactCommand(contact);
        assertEquals("Name:                         | Number:\n"
                + linebreak
                + "janel                         | 9625 1722\n"
                + linebreak
                + "jason                         | 9825 1822\n"
                + linebreak
                + "\nCEG CONTACTS:\n"
                + "\nNUS CONTACTS:\n", output.toString());
    }
}
