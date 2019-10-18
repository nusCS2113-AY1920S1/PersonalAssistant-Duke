import gazeeebo.commands.places.AddPlacesCommand;
import gazeeebo.commands.places.DeletePlacesCommand;
import gazeeebo.commands.places.ListPlacesCommand;
import gazeeebo.commands.places.PlacesCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.TriviaManager.TriviaManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlacesCommandTest {
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
    void testAddPlacesCommand() {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        ByteArrayInputStream in = new ByteArrayInputStream("Test,COM3".getBytes());
        System.setIn(in);
        AddPlacesCommand test = new AddPlacesCommand(ui, storage, places);
        assertEquals("Input in this format: Room,Location\r\n"
                + "Okay we have successfully added the new location :Test,COM3\r\n", output.toString());
    }

    @Test
    void testDeleteInPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        places.put("LT19", "COM5");
        ui.fullCommand = "delete-LT19";
        DeletePlacesCommand test = new DeletePlacesCommand(ui, storage, places);
        assertEquals("LT19 has been removed.\r\n", output.toString());
    }

    @Test
    void testDeleteNotInPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        places.put("LT19", "COM5");
        ui.fullCommand = "delete-LT30";
        DeletePlacesCommand test = new DeletePlacesCommand(ui, storage, places);
        assertEquals("LT30 is not in the list.\r\n", output.toString());
    }

    @Test
    void testDeleteWrongFormatPlacesCommand() throws IOException {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        places.put("LT19", "COM5");
        ui.fullCommand = "delete";
        DeletePlacesCommand test = new DeletePlacesCommand(ui, storage, places);
        assertEquals("You need to indicate what you want to delete, Format: delete-name\r\n", output.toString());
    }

    @Test
    void testListContactsCommand() {
        HashMap<String, String> map = new HashMap<>(); //Read the file
        Map<String, String> places = new TreeMap<String, String>(map);
        String linebreak = "------------------------------------------\n";
        places.put("LT19", "COM5");
        places.put("LT50", "COM6");
        ListPlacesCommand test = new ListPlacesCommand(places, linebreak);
        assertEquals("Room:                                             | Location:\n"
                + linebreak
                + "LT19                                              | COM5\n"
                + linebreak
                + "LT50                                              | COM6\n"
                + linebreak, output.toString());
    }
}
