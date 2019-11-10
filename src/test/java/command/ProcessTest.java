package command;

import common.TaskList;
import org.junit.jupiter.api.Test;
import project.Fund;
import ui.Ui;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProcessTest {
    Fund fund = new Fund();
    TaskList tasklist = new TaskList();
    Ui ui = new Ui();
    Storage storage = new Storage();
    ArrayList<String> list = new ArrayList<>();
    String input;

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
            Parser.parse(input, tasklist, ui, fund, storage, list);
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
            Parser.parse(input, tasklist, ui, fund, storage, list);
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
            Parser.parse(input, tasklist, ui, fund, storage, list);
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
            Parser.parse(input, tasklist, ui, fund, storage, list);
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
            Parser.parse(input, tasklist, ui, fund, storage, list);
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
            Parser.parse(input, tasklist, ui, fund, storage, list);
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
            Fund fund = new Fund();
            fund.loadFund(2000, 0, 2000);
            TaskList tasklist = new TaskList();
            Ui ui = new Ui();
            Storage storage = new Storage();
            ArrayList<String> list = new ArrayList<>();
            String input = "add fund add/7000000";
            Parser.parse(input, tasklist, ui, fund, storage, list);
            assertEquals(fund.getFund(), 2000);
            assertTrue(fund.getFundTaken() == 0);
            assertEquals(fund.getFundRemaining(), fund.getFund());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void assignFund() {
    }

    @Test
    void showFund() {
    }

    @Test
    void resetFund() {
    }

    @Test
    void reduceBudget() {
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