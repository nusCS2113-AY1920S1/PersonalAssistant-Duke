//@@author jessteoxizhi

package placestest;

import gazeeebo.ui.Ui;
import gazeeebo.commands.places.ListPlacesCommand;
import gazeeebo.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlacesListCommandTest {
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

    /**
     * test list command.
     * @throws IOException Exception when file cannot be read
     */

    @Test
    void testListPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        String linebreak = "------------------------------------------\n";
        places.put("LT50", "COM6");
        places.put("LT20", "COM7");
        ListPlacesCommand test = new ListPlacesCommand(places);
        assertEquals("Room:                                             | Location:\n"
                + "------------------------------------------\n"
                + "LT20                                              | COM7\n"
                + "------------------------------------------\n"
                + "LT50                                              | COM6\n"
                + "------------------------------------------\n", output.toString());
    }
}

