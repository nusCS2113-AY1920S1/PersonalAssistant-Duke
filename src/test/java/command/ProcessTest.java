package command;

import common.AlphaNUSException;
import common.CommandFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import task.TaskList;
import org.junit.jupiter.api.Test;
import project.Fund;
import project.Project;
import project.ProjectManager;
import storage.Storage;
import task.Deadline;
import task.Task;
import ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ProcessTest {
    private Fund fund;
    private TaskList tasklist;
    private Ui ui;
    private Storage storage;
    private ArrayList<String> list;
    private String input;
    private Set<String> dict;
    public Process process;
    private CommandFormat commandFormat = new CommandFormat();
    private static final String line = "    ____________________________________________________________\n";

    //@@author leowyh
    @BeforeEach
    void initProcessTest() throws AlphaNUSException {
        fund = new Fund();
        tasklist = new TaskList();
        ui = new Ui();
        storage = new Storage();
        list = new ArrayList<>();
        input = null;
        dict = null;
        process = new Process();

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testaddProject_WithNoBudget_success() {
        input = "add project pr/Test am/";
        process.addProject(input, ui, storage, fund);
        Project expectedproject = new Project("Test");
        Project outputproject = process.projectManager.projectmap.get("Test");
        assertEquals(expectedproject.giveProject(), outputproject.giveProject());
    }

    @Test
    void testaddProject_WithBudget_success() {
        input = "set fund am/2000";
        process.setFund(input, ui, fund, storage);
        assertEquals(fund.getFund(), 2000);

        input = "add project pr/Flag am/100";
        process.addProject(input, ui, storage, fund);
        Project expectedproject = new Project("Flag", 100);
        Project outputproject = process.projectManager.projectmap.get("Flag");
        assertEquals(expectedproject.giveProject(), outputproject.giveProject());
    }

    @Test
    void testaddProject_InputNegative_NoBudget_fail() {
        input = "add project pr/Flag am/-100";
        process.addProject(input, ui, storage, fund);
        assertFalse(process.projectManager.projectmap.containsKey("Flag"));
    }

    @Test
    void testaddProject_BudgetExceeds_fail() {
        input = "set fund am/2000";
        process.setFund(input, ui, fund, storage);
        assertEquals(fund.getFund(), 2000);

        input = "add project pr/Flag am/3000";
        process.addProject(input, ui, storage, fund);
        assertFalse(process.projectManager.projectmap.containsKey("Flag"));
    }

    @Test
    void deleteProject_ProjectExists() throws AlphaNUSException {
        input = "add project pr/Test am/";
        process.addProject(input, ui, storage, fund);
        assertTrue(process.projectManager.projectmap.containsKey("Test"));
        input = "delete project pr/Test";
        process.deleteProject(input, ui, storage, fund);
        assertFalse(process.projectManager.projectmap.containsKey("Test"));
    }

    @Test
    void deleteProject_withouttag_fail() throws AlphaNUSException {
        input = "delete project";
        process.deleteProject(input, ui, storage, fund);
        assertEquals(line + "\t" + "Incorrect input" + "\n"
                + "\t" + "Correct Format: " + commandFormat.deleteProjectFormat() + "\r\n"
                + line, outContent.toString());
    }

    @Test
    void deleteProject_NoProjectExist() throws AlphaNUSException {
        assertFalse(process.projectManager.projectmap.containsKey("Test"));
        input = "delete project pr/Test";
        process.deleteProject(input, ui, storage, fund);
        assertEquals(line + "\t" + "Project does not exist!" + "\r\n"
                + line, outContent.toString());
    }

    @Test
    void deleteProject_EmptyProjectName_fail() throws AlphaNUSException {
        input = "delete project pr/";
        process.deleteProject(input, ui, storage, fund);
        String expected = line + "\t" + "Project name cannot be empty!" + "\n"
                + "\t" + "Correct Format: " + commandFormat.deleteProjectFormat() + "\r\n"
                + line;
        String output = outContent.toString();
        System.out.println(output);
        System.out.println(expected);
        assertTrue(expected.equals(output));
    }

    @Test
    void goToProject_WithExistingProject_success() {
        process.projectManager.projectmap = new LinkedHashMap<>();
        input = "add project pr/Test am/";
        process.addProject(input, ui, storage, fund);
        process.projectManager.currentprojectname = null;
        input = "goto 1";
        process.goToProject(input, ui);
        assertEquals("Test", process.projectManager.currentprojectname);
    }

    @Test
    void goToProject_WithNoExistingProject_fail() {
        process.projectManager.projectmap = new LinkedHashMap<>();
        input = "goto 1";
        process.goToProject(input, ui);
        assertEquals(line + "\t" + "There are no projects in the record." + "\r\n" + "\t"
                + "Please add a new project." + "\r\n" + "\t"
                + "Format: " + commandFormat.addProjectFormat() + "\r\n"
                + line, outContent.toString());
    }

    //@@author lijiayu980606
    @Test
    void testSetFund() {
        try {
            input = "set fund am/2000";
            process.setFund(input, ui, fund, storage);
            assertEquals(fund.getFund(), 2000);
            assertTrue(fund.getFundTaken() == 0);
            assertEquals(fund.getFundRemaining(), fund.getFund());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testSetFundWithNegaticeInput() {
        try {
            input = "set fund am/-2000";
            process.setFund(input, ui, fund, storage);
            assertEquals(fund.getFund(), 0);
            assertTrue(fund.getFundTaken() == 0);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testSetFundWithOversizeInput() {
        try {
            input = "set fund am/7000000";
            process.setFund(input, ui, fund, storage);
            assertEquals(fund.getFund(), 0);
            assertTrue(fund.getFundTaken() == 0);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testSetFundWhileFundExists() {
        try {
            fund.loadFund(1800, 0, 1800);
            input = "set fund am/2000";
            process.setFund(input, ui, fund, storage);
            assertEquals(fund.getFund(), 1800);
            assertTrue(fund.getFundTaken() == 0);
            assertEquals(fund.getFundRemaining(), fund.getFund());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testAddFundWithCorrectNumber() {
        try {
            fund.loadFund(2000, 0, 2000);
            input = "add fund add/200";
            process.addFund(input, ui, fund, storage);
            assertEquals(fund.getFund(), 2200);
            assertTrue(fund.getFundTaken() == 0);
            assertEquals(fund.getFundRemaining(), fund.getFund());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testAddFundWithNegativeNumber() {
        try {
            fund.loadFund(2000, 0, 2000);
            input = "add fund add/-200";
            process.addFund(input, ui, fund, storage);
            assertEquals(fund.getFund(), 2000);
            assertTrue(fund.getFundTaken() == 0);
            assertEquals(fund.getFundRemaining(), fund.getFund());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testAddFundWithOversizeNumber() {
        try {
            fund.loadFund(2000, 0, 2000);
            String input = "add fund add/7000000";
            process.addFund(input, ui, fund, storage);
            assertEquals(fund.getFund(), 2000);
            assertTrue(fund.getFundTaken() == 0);
            assertEquals(fund.getFundRemaining(), fund.getFund());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void assignFund() throws AlphaNUSException {
        fund.loadFund(2000, 0, 2000);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 0);
        input = "assign fund pr/rag am/100";
        process.assignFund(input, ui, fund);
        assertEquals(fund.getFund(), 2000);
        assertEquals(fund.getFundRemaining(), fund.getFund() - fund.getFundTaken());
    }

    @Test
    void assignFundMoreThanAffordableAmount() throws AlphaNUSException {
        fund.loadFund(2000, 0, 2000);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 0);
        input = "assign fund pr/rag add/3000";
        process.assignFund(input, ui, fund);
        assertEquals(fund.getFund(), 2000);
        assertTrue(fund.getFundTaken() == 0);
        assertEquals(fund.getFundRemaining(), fund.getFund() - fund.getFundTaken());
    }

    @Test
    void assignNegativeFund() throws AlphaNUSException {
        fund.loadFund(2000, 0, 2000);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 0);
        input = "assign fund pr/rag add/-300";
        process.assignFund(input, ui, fund);
        assertEquals(fund.getFund(), 2000);
        assertTrue(fund.getFundTaken() == 0);
        assertEquals(fund.getFundRemaining(), fund.getFund() - fund.getFundTaken());
    }

    @Test
    void assignFundWrongFormat() throws AlphaNUSException {
        fund.loadFund(2000, 0, 2000);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 0);
        input = "assign fund pr/rag add/";
        process.assignFund(input, ui, fund);
        assertEquals(fund.getFund(), 2000);
        assertTrue(fund.getFundTaken() == 0);
        assertEquals(fund.getFundRemaining(), fund.getFund() - fund.getFundTaken());
    }

    @Test
    void resetFund() throws AlphaNUSException {
        fund.loadFund(2000, 1500, 500);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 1500);
        input = "change fund new/1800";
        process.resetFund(input, ui, fund);
        assertEquals(fund.getFund(), 1800);
        assertTrue(fund.getFundTaken() == 1500);
        assertEquals(fund.getFundRemaining(), fund.getFund() - fund.getFundTaken());
    }

    @Test
    void resetFundLessThanTaken() throws AlphaNUSException {
        fund.loadFund(2000, 1500, 500);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 1500);
        input = "change fund new/1300";
        process.resetFund(input, ui, fund);
        assertEquals(fund.getFund(), 2000);
        assertTrue(fund.getFundTaken() == 1500);
        assertEquals(fund.getFundRemaining(), fund.getFund() - fund.getFundTaken());
    }

    @Test
    void resetFundWrongFormat() throws AlphaNUSException {
        fund.loadFund(2000, 1500, 500);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 1500);
        input = "change fund new/";
        process.resetFund(input, ui, fund);
        assertEquals(fund.getFund(), 2000);
        assertTrue(fund.getFundTaken() == 1500);
        assertEquals(fund.getFundRemaining(), fund.getFund() - fund.getFundTaken());
    }

    @Test
    void reduceBudget() throws AlphaNUSException {
        fund.loadFund(2000, 500, 1500);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("test", 1000);
        projectManager.projectmap.get("test").spending = 500;
        projectManager.projectmap.get("test").remaining = 500;
        input = "reduce budget pr/test am/300";
        process.reduceBudget(input, ui, fund);
        assertEquals((projectManager.projectmap.get("test").spending), 500);
        assertEquals(projectManager.projectmap.get("test").getBudget(),
                (projectManager.projectmap.get("test").getSpending()
                        + projectManager.projectmap.get("test").getRemaining()));
    }

    @Test
    void reduceBudgetNotEnoughRemaining() throws AlphaNUSException {
        fund.loadFund(2000, 1500, 500);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 1500);
        projectManager.projectmap.get("rag").spending = 1000;
        input = "reduce budget pr/rag am/700";
        process.reduceBudget(input, ui, fund);
        assertEquals((projectManager.projectmap.get("rag").spending), 1000);
        assertEquals((projectManager.projectmap.get("rag").budget), 1500);
    }


    @Test
    void addTodo() {
    }

    @Test
    void addDeadline() {
    }

    @Test
    void done() {
        Task td = new Task("test");
        tasklist.addTask(td);
        input = "done id/1";
        process.done(input, tasklist, ui);
        assertTrue(tasklist.get(0).getStatus() == "Done");
    }

    @Test
    void deleteTask() {
        Task td1 = new Task("test1");
        tasklist.addTask(td1);
        Task td2 = new Task("test2");
        tasklist.addTask(td2);
        input = "delete id/1";
        process.deleteTask(input, tasklist, ui);
        assertTrue(tasklist.size() == 1);
        assertTrue(tasklist.get(0) == td2);
    }

    @Test
    void findTask() {
    }

    @Test
    void listTasks() {
    }

    @Test
    void viewSchedule() {
    }

    @Test
    void snooze() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Task ddl = new Deadline("testddl", sdf.parse("11-11-2019"));
        tasklist.addTask(ddl);
        input = "snooze id/1";
        process.snooze(input, tasklist, ui);
        assertEquals(tasklist.get(0).getDate(), sdf.parse("12-11-2019"));
    }

    @Test
    void postpone() {
    }

    @Test
    void reschedule() {
    }

    @Test
    void doAfter() {
    }

    @Test
    void within() {
    }

    //@@karansarat
    @Test
    void edit() throws AlphaNUSException {
        addPayee();
        ProjectManager projectManager = new ProjectManager();
        input = "edit p/test f/PAYEE r/CHANGED";
        process.edit(input, ui);
        assertTrue(projectManager.projectmap.get("rag").managermap.containsKey("CHANGED"));
        input = "edit p/CHANGED f/PAYEE r/test";
        process.edit(input, ui);
        addPayment();
        input = "edit p/test i/CS2113T f/COST r/23.59";
        process.edit(input, ui);
        assertTrue(projectManager.projectmap.get("rag").managermap.get("test").payments.get(0).cost == 22.22);
    }

    @Test
    void deletePayment() throws AlphaNUSException {
        addPayee();
        addPayment();
        ProjectManager projectManager = new ProjectManager();        
        assertFalse(projectManager.projectmap.get("rag").managermap.get("test").payments.isEmpty());
        input = "delete payment p/test      i/CS2113T";
        process.deletePayment(input, ui, storage, dict);
        assertTrue(projectManager.projectmap.get("rag").managermap.get("test").payments.isEmpty());
    }

    @Test
    void addPayment() throws AlphaNUSException {
        ProjectManager projectManager = new ProjectManager();
        addPayee();
        input = "add payment p/   test i/ CS2113T  c/11.11 v/IM-DED";
        process.addPayment(input, ui, storage, dict);
        assertTrue(projectManager.projectmap.get("rag").managermap.get("test").payments.get(0).item.equals("CS2113T"));
        assertTrue(projectManager.projectmap.get("rag").managermap.get("test").payments.get(0).cost == 11.11);
        assertTrue(projectManager.projectmap.get("rag").managermap.get("test").payments.get(0).inv.equals("IM-DED"));

    }

    @Test
    void addPayee() throws AlphaNUSException {
        fund.loadFund(2000, 500, 1500);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 1500);
        input = "add payee p/   test e/  email@address.com    m/A100000A ph/838484904";
        process.addPayee(input, ui, storage);
        assertEquals(projectManager.projectmap.get("rag").managermap, "test");
        assertEquals(projectManager.projectmap.get("rag").managermap.get("test").email, "email@address.com");
        assertEquals(projectManager.projectmap.get("rag").managermap.get("test").matricNum, "A100000A");
        assertEquals(projectManager.projectmap.get("rag").managermap.get("test").phoneNum, "838484904");
    }

    @Test
    void deletePayee() throws AlphaNUSException {
        addPayee();
        ProjectManager projectManager = new ProjectManager();
        assertEquals(projectManager.projectmap.get("rag").managermap.size(), 1);
        input = "delete payee p/test";
        process.deletePayee(input, ui, storage);
        assertEquals(projectManager.projectmap.get("rag").managermap.size(), 0);
        assertFalse(projectManager.projectmap.get("rag").managermap.containsKey("test"));
    }

    //@@author lijiayu980606
    @Test
    void totalCost() {
    }

    @Test
    void reminder() {
    }

    @Test
    void showBudget() {
    }

    //@@author
    @Test
    void listAllPayments() {
    }

    @Test
    void commandHistory() {
    }

    @Test
    void history() {
    }

    @Test
    void viewhistory() {
    }

    @Test
    void undo() {
    }

    @Test
    void redo() {
    }
}