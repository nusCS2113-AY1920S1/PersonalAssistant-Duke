//@@author jessteoxizhi

package placestest;

import gazeeebo.ui.Ui;
import gazeeebo.commands.places.AddPlacesCommand;
import gazeeebo.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlacesAddCommandTest {
    private Ui ui = new Ui();
    private Storage storage = new Storage();
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
    void testAddPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        Stack<Map<String, String>> oldplaces = new Stack<>();
        oldplaces.push(places);
        ui.fullCommand = "add-Test,COM3";
        AddPlacesCommand test = new AddPlacesCommand(ui, places, oldplaces);
        assertEquals("Successfully added :Test,COM3\r\n", output.toString());
    }

    @Test
    void testAddWrongPlacesCommand() {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        Stack<Map<String, String>> oldplaces = new Stack<>();
        oldplaces.push(places);
        ui.fullCommand = "add-TestCOM3";
        AddPlacesCommand test = new AddPlacesCommand(ui, places, oldplaces);
        assertEquals("Please input add command in the correct format\r\n", output.toString());
    }
}

