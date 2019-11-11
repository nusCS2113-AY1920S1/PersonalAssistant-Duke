//@@author jessteoxizhi

package placestest;

import gazeeebo.storage.TriviaStorage;
import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.parser.PlacesCommandParser;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.ui.Ui;
import gazeeebo.tasks.Task;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlacesParserTest {
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
    void testPlaceCommand() throws IOException, ParseException, DukeException {
        PlacesCommandParser placesCommandParser = new PlacesCommandParser();
        ArrayList<Task> list = new ArrayList<>();
        Ui ui = new Ui();
        ArrayList<Task> deletedTask = new ArrayList<>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        Storage storage = new Storage();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
        System.setIn(in);
        placesCommandParser.execute(list,ui,storage,commandStack,deletedTask,triviaManager);
        assertEquals("Welcome to your places in SOC! What would you like to do?\r\n"
                + "__________________________________________________________\n"
                + "1. Add location: add-room,location\n"
                + "2. Find a place in SOC: find-place\n"
                + "3. Delete a place: delete-place\n"
                + "4. See all places in SOC: list\n"
                + "5. Undo previous command: undo\n"
                + "6. See all commands: commands\n"
                + "7. Help command: help\n"
                + "8. Exit places: esc\n"
                + "__________________________________________________________\n"
                + "\n"
                + "\r\n"
                + "Going back to Main Menu...\n"
                + "Content Page:\n"
                + "------------------ \n"
                + "1. help\n"
                + "2. contacts\n"
                + "3. expenses\n"
                + "4. places\n"
                + "5. tasks\n"
                + "6. cap\n"
                + "7. spec\n"
                + "8. moduleplanner\n"
                + "9. notes\n"
                + "10. change password\n"
                + "To exit: bye\n"
                + "\r\n",output.toString());
    }
}

