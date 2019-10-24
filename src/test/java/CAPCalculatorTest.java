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

public class CAPCalculatorTest {
    private static final String LINEBREAK = "------------------------------\n";
    Ui ui = new Ui();
    Storage storage = new Storage();
    TriviaManager triviaManager = new TriviaManager();
    ArrayList<Task> list = new ArrayList<>();
    Stack<String> commandStack = new Stack<>();
    ArrayList<Task> deletedTask = new ArrayList<>();
    HashMap<String, ArrayList<CAPCommand>> map = new HashMap<>();
    Map<String, ArrayList<CAPCommand>> CAPList = new TreeMap<>(map);

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
        String moduleCode = "", grade= "";
        int moduleCredit = 0;
        CAPCommand test = new CAPCommand(moduleCode,moduleCredit,grade);
        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
        System.setIn(in);
        test.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
        assertEquals("Welcome to your CAP Calculator page! What would you like to do?\n\n"
                        + "__________________________________________________________\n"
                        + "1. Add module: add\n"
                        + "2. Find module: find moduleCode/semNumber\n"
                        + "3. Delete a module: delete module\n"
                        + "4. See your CAP list: list\n"
                        + "5. Exit CAP page: esc\n"
                        + "__________________________________________________________\n"
                        + "\nGoing back to Main Menu\n"
                , output.toString()
        );
    }

    @Test
    void testAddCAPCommand () throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("1,CS1231,4,A".getBytes());
        System.setIn(in);
        AddCAPCommand test = new AddCAPCommand(ui, CAPList);
        assertEquals("Input in this format: semNumber,Module_Code,total_MC,CAP\n"
        + "Successfully added: CS1231\n", output.toString());
    }

    @Test
    void testListAllCAPCommand () throws IOException {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommand> list2 = new ArrayList<>();
        CAPCommand newCAP2 = new CAPCommand("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1",list);
        CAPList.put("2",list2);
        ByteArrayInputStream in = new ByteArrayInputStream("all".getBytes());
        System.setIn(in);
        ListCAPCommand test = new ListCAPCommand(ui,CAPList,LINEBREAK);
        assertEquals("Which sem do you want to list? all,1,2,3,4,5,6,7,8\n"
                + "Sem | Module code | MC | CAP\n" + LINEBREAK
                + "1   | CS1231      | 4  | A\n" +LINEBREAK
                + "2   | CG1112      | 6  | A\n"
                + LINEBREAK
                + "Total CAP: 5.0\n", output.toString());
    }

    @Test
    void testListSemCAPCommand () throws IOException {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommand> list2 = new ArrayList<>();
        CAPCommand newCAP2 = new CAPCommand("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1",list);
        CAPList.put("2",list2);
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        ListCAPCommand test = new ListCAPCommand(ui,CAPList,LINEBREAK);
        assertEquals("Which sem do you want to list? all,1,2,3,4,5,6,7,8\n"
                + "Sem | Module code | MC | CAP\n" + LINEBREAK
                + "1   | CS1231      | 4  | A\n"
                + LINEBREAK
                + "Sem 1 CAP: 5.0\n", output.toString());
    }

    @Test
    void testFindByModuleCodeCAPCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommand> list2 = new ArrayList<>();
        CAPCommand newCAP2 = new CAPCommand("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1",list);
        CAPList.put("2",list2);
        ui.fullCommand = "find CG1112";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "2   | CG1112      | 6  | A\n"
                + LINEBREAK, output.toString());
    }

    @Test
    void testFindBySemCAPCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommand> list2 = new ArrayList<>();
        CAPCommand newCAP2 = new CAPCommand("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1",list);
        CAPList.put("2",list2);
        ui.fullCommand = "find 1";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "1   | CS1231      | 4  | A\n"
                + LINEBREAK, output.toString());
    }

    @Test
    void testFindNotInTheCapListCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommand> list2 = new ArrayList<>();
        CAPCommand newCAP2 = new CAPCommand("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1",list);
        CAPList.put("2",list2);
        ui.fullCommand = "find CS2101";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("CS2101 is not found in the list.\n", output.toString());
    }



    @Test
    void testDeleteCAPCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1",list);
        ui.fullCommand = "delete CS1231";
        DeleteCAPCommand test = new DeleteCAPCommand(ui, CAPList);
        assertEquals("Successfully deleted: CS1231\n", output.toString());
    }

    @Test
    void testDeleteNotInCAPListCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        CAPList.put("1",list);
        ui.fullCommand = "delete CG1111";
        DeleteCAPCommand test = new DeleteCAPCommand(ui, CAPList);
        assertEquals("CG1111 is not found in the list.\n", output.toString());
    }

    @Test
    void testConvertGradetoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(5.0, test.converter("A"));
    }

}
