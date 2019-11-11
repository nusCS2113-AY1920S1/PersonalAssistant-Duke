//@@author jessteoxizhi

package placestest;

import gazeeebo.ui.Ui;
import gazeeebo.commands.places.FindPlacesCommand;
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

class PlacesFindCommandTest {
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
     * test find places command.
     * @throws IOException exception when there is an error reading the command
     */

    @Test
    void testFindPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> places = new TreeMap<String, String>(map);
        places.put("LT50", "COM6");
        places.put("LT20", "COM7");
        ui.fullCommand = "find-LT20";
        FindPlacesCommand test = new FindPlacesCommand(ui,places);
        assertEquals("LT20                                              | COM7\n"
                + "------------------------------------------\n", output.toString());
    }
}

