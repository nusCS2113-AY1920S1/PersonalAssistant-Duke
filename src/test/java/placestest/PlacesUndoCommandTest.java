//@@author jessteoxizhi

package placestest;

import gazeeebo.ui.Ui;
import gazeeebo.commands.places.UndoPlacesCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Stack;



import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlacesUndoCommandTest {
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
    void testUndoPlacesCommand() throws IOException, ParseException, DukeException {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> places = new TreeMap<String, String>(map);
        Stack<Map<String, String>> oldplaces = new Stack<>();
        oldplaces.push(places);
        places.put("LT50", "COM6");
        UndoPlacesCommand undoTest = new UndoPlacesCommand();
        undoTest.undoPlaces(places,oldplaces);
        assertEquals("You have undo the previous command.\r\n", output.toString());
    }
}
