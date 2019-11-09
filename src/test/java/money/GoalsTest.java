package money;

import controlpanel.MoneyStorage;
import controlpanel.Parser;
import moneycommands.AddGoalCommand;
import moneycommands.CommitGoalCommand;
import moneycommands.DeleteGoalCommand;
import moneycommands.DoneGoalCommand;
import moneycommands.ListGoalsCommand;
import moneycommands.MoneyCommand;
import moneycommands.ExitMoneyCommand;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.text.ParseException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GoalsTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate = LocalDate.parse("9/10/2015", dateTimeFormatter);
    private LocalDate testDate2 = LocalDate.parse("9/10/2050", dateTimeFormatter);
    private static final int TICK_NO = 0x2713;

    GoalsTest() throws IOException {
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
    }


    @Test
    public void testAddGoal()throws ParseException, DukeException {

        String testInput = "goal buy Motorbike /amt 10000 /by 15/1/2050 /priority HIGH";
        MoneyCommand addGoalCommand =  new AddGoalCommand(testInput);
        ui.clearOutputString();
        addGoalCommand.execute(account, ui, moneyStorage);
        assertEquals(" Got it. I've added this Goal: \n"
                + "     [GS] buy Motorbike (target: $10000.00)\n (to achieve by: 15/1/2050) HIGH\n",
                ui.getOutputString().split(" Now")[0]);
        account.getShortTermGoals().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testDeleteGoal()throws ParseException, DukeException {

        account.getShortTermGoals().clear();
        Goal g = new Goal(1000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g);
        MoneyCommand deleteGoalCommand =  new DeleteGoalCommand("delete goal 1");
        ui.clearOutputString();
        deleteGoalCommand.execute(account, ui, moneyStorage);
        assertEquals(" Noted. I've removed this Goal:\n"
                        + "  [GS] watch(target: $1000.00)\n (to achieve by: 9/10/2015) HIGH\n",
                ui.getOutputString().split(" Now")[0]);
        account.getShortTermGoals().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testDoneGoal()throws ParseException, DukeException {
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        Income i = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i);
        Goal g = new Goal(1000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g);
        String testInput = "done goal 1";
        MoneyCommand doneGoalCommand =  new DoneGoalCommand(testInput);
        ui.clearOutputString();
        doneGoalCommand.execute(account, ui, moneyStorage);

        LocalDate doneDate = LocalDate.now();
        dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String todayDate = doneDate.format(dateTimeFormatter);
        assertEquals(" Nice! This Goal is Completed:\n"
                        + "  [GS] watch(target: $1000.00)\n (to achieve by: 9/10/2015) HIGH\n",
                ui.getOutputString().split(" Now")[0]);

        assertEquals("[E]$1000.00 watch(on: " + todayDate + ")", account.getExpListTotal().get(0).toString());
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testListGoal()throws ParseException, DukeException {
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        Income i = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i);
        Expenditure e = new Expenditure(1000, "straw", "E", testDate);
        account.getExpListTotal().add(e);
        Goal g1 = new Goal(1000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g1);
        Goal g2 = new Goal(2000, "car", "GS", testDate, "MEDIUM");
        account.getShortTermGoals().add(g2);
        MoneyCommand listGoalCommand =  new ListGoalsCommand();
        ui.clearOutputString();
        ui.clearGraphContainerString();
        listGoalCommand.execute(account, ui, moneyStorage);
        assertEquals(" 1." + "[" + Character.toString((char)TICK_NO) + "]"
                        + "[GS] watch(target: $1000.00)\n (to achieve by: 9/10/2015) HIGH\n"
                        + " 2.[50%][GS] car(target: $2000.00)\n (to achieve by: 9/10/2015) MEDIUM\n",
                ui.getGraphContainerString().split(" Now")[0]);

        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testCommitGoalOverdue()throws ParseException, DukeException {
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        Income i = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i);
        Expenditure e = new Expenditure(500, "straw", "E", testDate);
        account.getExpListTotal().add(e);
        Goal g1 = new Goal(1000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g1);
        Goal g2 = new Goal(2000, "car", "GS", testDate, "MEDIUM");
        account.getShortTermGoals().add(g2);
        Goal g3 = new Goal(100, "pen", "GS", testDate, "LOW");
        account.getShortTermGoals().add(g3);
        String testInput = "commit goal 1,3";
        MoneyCommand commitGoalCommand =  new CommitGoalCommand(testInput);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        commitGoalCommand.execute(account, ui, moneyStorage);
        assertEquals(" 1." + "[" + Character.toString((char)TICK_NO) + "]"
                        + "[GS] watch(target: $1000.00)\n (to achieve by: 9/10/2015) HIGH\n"
                        + " 2.[75%][GS] car(target: $2000.00)\n (to achieve by: 9/10/2015) MEDIUM\n"
                        + " 3." + "[" + Character.toString((char)TICK_NO) + "]"
                        + "[GS] pen(target: $100.00)\n (to achieve by: 9/10/2015) LOW\n",
                ui.getGraphContainerString().split(" Now")[0]);

        assertEquals(" 1.[20%][GS] car(target: $2000.00)\n (to achieve by: 9/10/2015) MEDIUM\n"
                + "Goal Savings after commit: $400.00\n"
                        + "Target Savings for the Month after commit: $1600.00\n"
                        + "current Goal Savings: $1500.00\n"
                        + "Target Savings for the Month: $500.00\n"
                        + "Got it, list will be printed in the other pane!\n",
                ui.getOutputString().split(" Now")[0]);

        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testCommitGoalUpcoming()throws ParseException, DukeException {
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        Income i = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i);
        Expenditure e = new Expenditure(500, "straw", "E", testDate);
        account.getExpListTotal().add(e);
        Goal g1 = new Goal(1000, "watch", "GS", testDate2, "HIGH");
        account.getShortTermGoals().add(g1);
        Goal g2 = new Goal(2000, "car", "GS", testDate2, "MEDIUM");
        account.getShortTermGoals().add(g2);
        Goal g3 = new Goal(100, "pen", "GS", testDate2, "LOW");
        account.getShortTermGoals().add(g3);
        Goal g4 = new Goal(300, "computer", "GS", testDate2, "LOW");
        account.getShortTermGoals().add(g4);
        String testInput = "commit goal 1,3";
        MoneyCommand commitGoalCommand =  new CommitGoalCommand(testInput);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        commitGoalCommand.execute(account, ui, moneyStorage);
        assertEquals(" 1." + "[" + Character.toString((char)TICK_NO) + "]"
                        + "[GS] watch(target: $1000.00)\n (to achieve by: 9/10/2050) HIGH\n"
                        + " 2.[75%][GS] car(target: $2000.00)\n (to achieve by: 9/10/2050) MEDIUM\n"
                        + " 3." + "[" + Character.toString((char)TICK_NO) + "]"
                        + "[GS] pen(target: $100.00)\n (to achieve by: 9/10/2050) LOW\n"
                        + " 4." + "[" + Character.toString((char)TICK_NO) + "]"
                        + "[GS] computer(target: $300.00)\n (to achieve by: 9/10/2050) LOW\n",
                ui.getGraphContainerString().split(" Now")[0]);

        assertEquals(" 1.[20%][GS] car(target: $2000.00)\n (to achieve by: 9/10/2050) MEDIUM\n"
                        + " 2." + "[" + Character.toString((char)TICK_NO) + "]"
                        + "[GS] computer(target: $300.00)\n"
                        + " (to achieve by: 9/10/2050) LOW\n"
                        + "Goal Savings after commit: $400.00\n"
                        + "Target Savings for the Month after commit: $4.31\n"
                        + "current Goal Savings: $1500.00\n"
                        + "Target Savings for the Month: $1.35\n"
                        + "Got it, list will be printed in the other pane!\n",
                ui.getOutputString().split(" Now")[0]);

        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testInvalidAmt()throws ParseException, DukeException {

        String testInput = "goal buy Motorbike /amt 1h2h2. /by 15/1/2050 /priority HIGH";
        MoneyCommand addGoalCommand =  new AddGoalCommand(testInput);
        ui.clearOutputString();

        try {
            addGoalCommand.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("Please enter in the format: "
                    + "goal <desc> /amt <amount> /by <date> /priority <HIGH/MEDIUM/LOW>\n"));
        }
        account.getShortTermGoals().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testInvalidAddGoalCommand()throws ParseException, DukeException {

        String testInput = "goalbuy a house/amt 100 /by 15/1/2050 /priority HIGH";

        try {
            MoneyCommand c = Parser.moneyParse(testInput, false);
            c.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("OOPS!!! I'm sorry, but I don't know what that means"));
        }
        account.getShortTermGoals().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testInvalidDate()throws ParseException, DukeException {

        String testInput = "goal buy Motorbike /amt 1000 /by 15 Jan 2050 /priority HIGH";
        MoneyCommand addGoalCommand =  new AddGoalCommand(testInput);
        ui.clearOutputString();

        try {
            addGoalCommand.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("Invalid date! Please enter date in the format: d/m/yyyy\n"));
        }
        account.getShortTermGoals().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testDeleteExceedSerial()throws ParseException, DukeException {

        account.getShortTermGoals().clear();
        Goal g = new Goal(1000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g);
        MoneyCommand deleteGoalCommand =  new DeleteGoalCommand("delete goal 2");
        ui.clearOutputString();

        try {
            deleteGoalCommand.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The serial number of the task is Out Of Bounds!"));
        }
        account.getShortTermGoals().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testDoneGoalExceedSavings()throws ParseException, DukeException {
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        Income i = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i);
        Goal g = new Goal(3000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g);
        String testInput = "done goal 1";
        MoneyCommand doneGoalCommand =  new DoneGoalCommand(testInput);
        ui.clearOutputString();

        try {
            doneGoalCommand.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("Goal Price exceeds Goal Savings"));
        }
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    void testUndoAddGoal() throws ParseException, DukeException {
        String testInput = "goal buy Motorbike /amt 10000 /by 9/10/2015 /priority HIGH";
        Goal g = new Goal(10000, "buy Motorbike ", "GS", testDate, "HIGH");
        MoneyCommand addGoalCommand =  new AddGoalCommand(testInput);
        addGoalCommand.execute(account, ui, moneyStorage);
        ui.clearOutputString();
        addGoalCommand.undo(account, ui, moneyStorage);
        assertEquals(" Last command undone: \n" + g.toString() + "\n Now you have "
                + account.getShortTermGoals().size() + " goals listed\ncurrent Goal Savings: $1000.00\n" +
                "Target Savings for the Month: $.00\n" +
                "Got it, list will be printed in the other pane!\n", ui.getOutputString());
        account.getShortTermGoals().clear();
        MoneyCommand exitCommand = new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    void testUndoDeleteGoal() throws DukeException, ParseException {
        account.getShortTermGoals().clear();
        Goal g = new Goal(1000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g);
        MoneyCommand deleteGoalCommand =  new DeleteGoalCommand("delete goal 1");
        deleteGoalCommand.execute(account, ui, moneyStorage);
        ui.clearOutputString();
        deleteGoalCommand.undo(account, ui, moneyStorage);
        assertEquals(" Last command undone: \n" + g.toString() + "\n Now you have "
                + account.getShortTermGoals().size() + " goals listed\ncurrent Goal Savings: $1000.00\n" +
                "Target Savings for the Month: $.00\n" +
                "Got it, list will be printed in the other pane!\n", ui.getOutputString());
        account.getShortTermGoals().clear();

        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    void testUndoDoneGoal() throws ParseException, DukeException {
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        Income i = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i);
        Goal g = new Goal(1000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g);
        String testInput = "done goal 1";
        MoneyCommand doneGoalCommand =  new DoneGoalCommand(testInput);
        doneGoalCommand.execute(account, ui, moneyStorage);
        ui.clearOutputString();
        doneGoalCommand.undo(account, ui, moneyStorage);
        assertEquals(" Last command undone: \n" + g.toString() + " added to goals\n Now you have "
        + account.getShortTermGoals().size() + " goals listed\nand " + account.getExpListTotal().size()
        + " expenses listed\n", ui.getOutputString());
        MoneyCommand exitCommand = new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }
}
