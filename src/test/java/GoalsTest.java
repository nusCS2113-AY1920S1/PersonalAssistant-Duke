import controlpanel.MoneyStorage;
import money.Expenditure;
import money.Goal;
import money.Income;
import moneycommands.*;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import money.Account;
import java.text.ParseException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoalsTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate = LocalDate.parse("9/10/2015", dateTimeFormatter);
    private LocalDate testDate2 = LocalDate.parse("9/10/2050", dateTimeFormatter);

    GoalsTest(){
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
                + "     [GS] buy Motorbike (target: $10000.0)\n (to achieve by: 15/1/2050) HIGH\n"
                , ui.getOutputString().split(" Now")[0]);
        account.getShortTermGoals().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testDeleteGoal()throws ParseException, DukeException {

        account.getShortTermGoals().clear();
        Goal g = new Goal(1000, "watch", "GS", testDate, "HIGH");
        account.getShortTermGoals().add(g);
        MoneyCommand deleteGoalCommand =  new DeleteGoalCommand(1);
        ui.clearOutputString();
        deleteGoalCommand.execute(account, ui, moneyStorage);
        assertEquals(" Noted. I've removed this Goal:\n"
                        + "  [GS] watch(target: $1000.0)\n (to achieve by: 9/10/2015) HIGH\n"
                , ui.getOutputString().split(" Now")[0]);
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
        assertEquals(" Nice! This Goal is Completed:\n"
                        + "  [GS] watch(target: $1000.0)\n (to achieve by: 9/10/2015) HIGH\n"
                , ui.getOutputString().split(" Now")[0]);

        assertEquals("[E]$1000.0 watch(on: 25/10/2019)", account.getExpListTotal().get(0).toString());
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
        assertEquals( " 1.[\u2713][GS] watch(target: $1000.0)\n (to achieve by: 9/10/2015) HIGH\n"
                        + " 2.[50%][GS] car(target: $2000.0)\n (to achieve by: 9/10/2015) MEDIUM\n"
                , ui.getGraphContainerString().split(" Now")[0]);

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
        MoneyCommand listGoalCommand =  new CommitGoalCommand(testInput);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        listGoalCommand.execute(account, ui, moneyStorage);
        assertEquals( " 1.[\u2713][GS] watch(target: $1000.0)\n (to achieve by: 9/10/2015) HIGH\n"
                        + " 2.[75%][GS] car(target: $2000.0)\n (to achieve by: 9/10/2015) MEDIUM\n"
                        + " 3.[\u2713][GS] pen(target: $100.0)\n (to achieve by: 9/10/2015) LOW\n"
                , ui.getGraphContainerString().split(" Now")[0]);

        assertEquals( " 1.[20%][GS] car(target: $2000.0)\n (to achieve by: 9/10/2015) MEDIUM\n"
                + "Goal Savings after commit: $400.0\n" +
                        "Target Savings for the Month after commit: $1600\n" +
                        "current Goal Savings: $1500.0\n" +
                        "Target Savings for the Month: $500\n" +
                        "Got it, list will be printed in the other pane!\n"
                , ui.getOutputString().split(" Now")[0]);

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
        MoneyCommand listGoalCommand =  new CommitGoalCommand(testInput);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        listGoalCommand.execute(account, ui, moneyStorage);
        assertEquals( " 1.[\u2713][GS] watch(target: $1000.0)\n (to achieve by: 9/10/2050) HIGH\n"
                        + " 2.[75%][GS] car(target: $2000.0)\n (to achieve by: 9/10/2050) MEDIUM\n"
                        + " 3.[\u2713][GS] pen(target: $100.0)\n (to achieve by: 9/10/2050) LOW\n"
                        + " 4.[\u2713][GS] computer(target: $300.0)\n (to achieve by: 9/10/2050) LOW\n"
                , ui.getGraphContainerString().split(" Now")[0]);

        assertEquals( " 1.[20%][GS] car(target: $2000.0)\n (to achieve by: 9/10/2050) MEDIUM\n"
                        + " 2.[\u2713][GS] computer(target: $300.0)\n" +
                        " (to achieve by: 9/10/2050) LOW\n"
                        + "Goal Savings after commit: $400.0\n" +
                        "Target Savings for the Month after commit: $4.32\n" +
                        "current Goal Savings: $1500.0\n" +
                        "Target Savings for the Month: $1.35\n" +
                        "Got it, list will be printed in the other pane!\n"
                , ui.getOutputString().split(" Now")[0]);

        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }
}
