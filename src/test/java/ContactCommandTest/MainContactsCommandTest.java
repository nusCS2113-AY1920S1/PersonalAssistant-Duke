package ContactCommandTest;

import gazeeebo.commands.contact.*;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainContactsCommandTest {
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private ArrayList<Task> list = new ArrayList<>();
    private Stack<ArrayList<Task>> commandStack = new Stack<>();
    private ArrayList<Task> deletedTask = new ArrayList<>();

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
    void testwelcome() throws IOException {
        TriviaManager triviaManager = new TriviaManager(storage);
        ContactCommand testc = new ContactCommand();
        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
        System.setIn(in);
        testc.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
        assertEquals("Welcome to your contacts page! What would you like to do?\n\n"
                        + "__________________________________________________________\n"
                        + "1. Add contacts: add\n"
                        + "2. Find contacts base on name: find name\n"
                        + "3. Delete a contact: delete name\n"
                        + "4. See your contacts list: list\n"
                        + "5. Help Command: help\n"
                        + "6. Exit contact page: esc\n"
                        + "__________________________________________________________\n\n"
                + "Going back to Main Menu\n"
                , output.toString()
        );
    }
}
