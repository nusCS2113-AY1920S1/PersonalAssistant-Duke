package ContactCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.contact.DeleteContactCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteContactCommandTest {
    private Ui ui = new Ui();

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
        Map<String, String> contact = new TreeMap<>(map);
        contact.put("jason", "9625 1722");
        ui.fullCommand = "delete jason";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("Successfully deleted: jason\n", output.toString());
    }

    @Test
    void testDeleteNotInContactsCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<>(map);
        contact.put("janel", "9625 1722");
        ui.fullCommand = "delete jason";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("jason is not found in the list.\n", output.toString());
    }

    @Test
    void testDeleteIncorrectFormatContactsCommand() {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> contact = new TreeMap<>(map);
        contact.put("janel", "9625 1722");
        ui.fullCommand = "delete jason and janel";
        DeleteContactCommand test = new DeleteContactCommand(ui, contact);
        assertEquals("Please Input in the correct format\n", output.toString());
    }
}
