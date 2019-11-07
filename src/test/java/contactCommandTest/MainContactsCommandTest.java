//package contactCommandTest;
//
//import gazeeebo.TriviaManager.TriviaManager;
//import gazeeebo.UI.Ui;
//import gazeeebo.parsers.ContactCommandParser;
//import gazeeebo.exception.DukeException;
//import gazeeebo.storage.Storage;
//import gazeeebo.tasks.Task;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.util.ArrayList;
//import java.util.Stack;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class MainContactsCommandTest {
//    private Ui ui = new Ui();
//    private Storage storage = new Storage();
//    private ArrayList<Task> list = new ArrayList<>();
//    private Stack<ArrayList<Task>> commandStack = new Stack<>();
//    private ArrayList<Task> deletedTask = new ArrayList<>();
//
//    private ByteArrayOutputStream output = new ByteArrayOutputStream();
//    private PrintStream mine = new PrintStream(output);
//    private PrintStream original = System.out;
//
//    @BeforeEach
//    void setupStream() {
//        System.setOut(mine);
//    }
//
//    @AfterEach
//    void restoreStream() {
//        System.out.flush();
//        System.setOut(original);
//    }
//
//    @Test
//    void testWelcome() throws IOException, DukeException {
//        TriviaManager triviaManager = new TriviaManager(storage);
//        ContactCommandParser test = new ContactCommandParser();
//        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
//        System.setIn(in);
//        test.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
//        assertEquals("Welcome to your contacts page! What would you like to do?\n\n"
//                        + "__________________________________________________________\n"
//                        + "1. Add contacts: add name,number\n"
//                        + "2. Find contacts base on name: find name\n"
//                        + "3. Delete a contact: delete name\n"
//                        + "4. See your contacts list: list\n"
//                        + "5. Undo Command: undo\n"
//                        + "6. List of commands for contacts page: commands\n"
//                        + "7. Help page: help\n"
//                        + "8. Exit contact page: esc\n"
//                        + "__________________________________________________________\n\n"
//                        + "Go back to Main Menu...\n" +
//                        "Content Page:\n" +
//                        "------------------ \n" +
//                        "1. help\n" +
//                        "2. contacts\n" +
//                        "3. expenses\n" +
//                        "4. places\n" +
//                        "5. tasks\n" +
//                        "6. cap\n" +
//                        "7. spec\n" +
//                        "8. moduleplanner\n" +
//                        "9. notes\n"
//                , output.toString()
//        );
//    }
//}