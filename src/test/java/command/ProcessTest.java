package command;

import common.AlphaNUSException;
import common.TaskList;
import org.junit.jupiter.api.Test;
import project.Fund;
import project.ProjectManager;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProcessTest {
    Fund fund = new Fund();
    TaskList tasklist = new TaskList();
    Ui ui = new Ui();
    Storage storage = new Storage();
    ArrayList<String> list = new ArrayList<>();
    String input;
    Set<String> dict;
    public Process process = new Process();

    ProcessTest() throws AlphaNUSException {
    }

    @Test
    void homePageMessage() {
    }

    @Test
    void listProjects() {
    }

    @Test
    void addProject() {
    }

    @Test
    void deleteProject() {
    }

    @Test
    void goToProject() {
    }

    @Test
    void testSetFund() {
        try {
            input = "set fund am/2000";
            process.setFund(input, ui, fund);
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
            process.setFund(input, ui, fund);
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
            process.setFund(input, ui, fund);
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
            process.setFund(input, ui, fund);
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
            process.addFund(input, ui, fund);
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
            process.addFund(input, ui, fund);
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
            process.addFund(input, ui, fund);
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
        input = "assign fund pr/rag add/100";
        process.assignFund(input, ui, fund);
        assertEquals(fund.getFund(), 2000);
        assertTrue(fund.getFundTaken() == 100);
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
        input = "reset fund new/1800";
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
        input = "reset fund new/1300";
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
        input = "reset fund new/";
        process.resetFund(input, ui, fund);
        assertEquals(fund.getFund(), 2000);
        assertTrue(fund.getFundTaken() == 1500);
        assertEquals(fund.getFundRemaining(), fund.getFund() - fund.getFundTaken());
    }

    @Test
    void reduceBudget() throws AlphaNUSException {
        fund.loadFund(2000, 1500, 500);
        ProjectManager projectManager = new ProjectManager();
        projectManager.addProject("rag", 1500);
        projectManager.projectmap.get("rag").spending = 1000;
        input = "reduce budget pr/rag am/300";
        process.reduceBudget(input, ui, fund);
        assertEquals((projectManager.projectmap.get("rag").spending), 1000);
        assertEquals((projectManager.projectmap.get("rag").budget), 1000);
        assertTrue(projectManager.projectmap.get("rag").getBudget() ==
                projectManager.projectmap.get("rag").getSpending()
                        + projectManager.projectmap.get("rag").getRemaining());
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
        assertTrue(projectManager.projectmap.get("rag").getBudget() ==
                projectManager.projectmap.get("rag").getSpending()
                        + projectManager.projectmap.get("rag").getRemaining());
    }


    @Test
    void addTodo() {
    }

    @Test
    void addDeadline() {
    }

    @Test
    void done() {
    }

    @Test
    void deleteTask() {
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
    void doAfter() {
    }

    @Test
    void within() {
    }

    @Test
    void snooze() {
    }

    @Test
    void postpone() {
    }

    @Test
    void reschedule() {
    }

    @Test
    void edit() {
    }

    @Test
    void deletePayment() {
    }

    @Test
    void addPayment() {
    }

    @Test
    void addPayee() {
    }

    @Test
    void deletePayee() {
    }

    @Test
    void findPayee() {
    }

    @Test
    void totalCost() {
    }

    @Test
    void reminder() {
    }

    @Test
    void listAllPayments() {
    }

    @Test
    void showBudget() {
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