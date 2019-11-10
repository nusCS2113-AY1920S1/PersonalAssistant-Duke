import gazeeebo.UI.Ui;
import gazeeebo.commands.places.DeletePlacesCommand;
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

public class PlacesDeleteCommandTest {
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
    void testDeleteInPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        places.put("LT19", "COM5");
        ui.fullCommand = "delete-LT19";
        DeletePlacesCommand test = new DeletePlacesCommand(ui, storage, places);
        assertEquals("Successfully deleted: LT19\r\n", output.toString());
    }

    @Test
    void testDeleteNotInPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        places.put("LT19", "COM5");
        ui.fullCommand = "delete-LT30";
        DeletePlacesCommand test = new DeletePlacesCommand(ui, storage, places);
        assertEquals("LT30 is not found in the list.\r\n", output.toString());
    }

    @Test
    void testDeleteWrongFormatPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        places.put("LT19", "COM5");
        ui.fullCommand = "delete";
        DeletePlacesCommand test = new DeletePlacesCommand(ui, storage, places);
        assertEquals("Incorrect format: delete-place\r\n", output.toString());
    }
}
