package CAPCalculatorTest;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.capCalculator.*;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainCAPCalculatorTest {
    private static final String LINEBREAK = "------------------------------\n";
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private ArrayList<Task> list = new ArrayList<>();
    private Stack<ArrayList<Task>> commandStack = new Stack<>();
    private ArrayList<Task> deletedTask = new ArrayList<>();
    private HashMap<String, ArrayList<CAPCommand>> map = new HashMap<>();
    private Map<String, ArrayList<CAPCommand>> CAPList = new TreeMap<>(map);

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
    void testMainCommand() throws IOException, ParseException, DukeException {
        TriviaManager triviaManager = new TriviaManager(storage);
        String moduleCode = "", grade = "";
        int moduleCredit = 0;
        CAPCommand test = new CAPCommand(moduleCode, moduleCredit, grade);
        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
        System.setIn(in);
        test.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
        assertEquals("Welcome to your CAP Calculator page! What would you like to do?\n\n"
                        + "__________________________________________________________\n"
                        + "1. Add module: add\n"
                        + "2. Find module: find moduleCode\n"
                        + "3. Delete a module: delete module\n"
                        + "4. See your CAP list: list\n"
                        + "5. Help Command: help\n"
                        + "6. Exit CAP page: esc\n"
                        + "__________________________________________________________\n"
                        + "\nGo back to Main Menu...\n" +
                        "Content Page:\n" +
                        "------------------ \n" +
                        "1. help\n" +
                        "2. contacts\n" +
                        "3. expenses\n" +
                        "4. places\n" +
                        "5. tasks\n" +
                        "6. cap\n" +
                        "7. spec\n" +
                        "8. moduleplanner\n" +
                        "9. notes\n\n"
                , output.toString()
        );
    }
}
