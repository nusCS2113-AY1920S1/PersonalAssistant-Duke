import controlpanel.MoneyStorage;
import moneycommands.*;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import money.Account;
import java.text.ParseException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoalsTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;


    @Test
    public void testAddGoal()throws ParseException, DukeException {

        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
        String testInput = "goal buy Motorbike /amt 10000 /by 15/1/2050 /priority HIGH";
        MoneyCommand addGoalCommand =  new AddGoalCommand(testInput);
        ui.clearOutputString();
        addGoalCommand.execute(account, ui, moneyStorage);
        assertEquals(" Got it. I've added this Goal: \n"
                + "     [GS] buy Motorbike (target: $10000.0)\n (to achieve by: 15/1/2050) HIGH\n"
                , ui.getOutputString().split(" Now")[0]);
    }

    @Test
    public void testDeleteGoal()throws ParseException, DukeException {

        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
        MoneyCommand deleteGoalCommand =  new DeleteGoalCommand(8);
        ui.clearOutputString();
        deleteGoalCommand.execute(account, ui, moneyStorage);
        assertEquals(" Noted. I've removed this Goal:\n"
                        + "  [GS] buy Motorbike (target: $10000.0)\n (to achieve by: 15/1/2050) HIGH\n"
                , ui.getOutputString().split(" Now")[0]);
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

//    @Test
//    public void testDoneGoal()throws ParseException, DukeException {
//
//        ui = new Ui();
//        Path currentDir = Paths.get("data/account-test.txt");
//        String filePath = currentDir.toAbsolutePath().toString();
//        moneyStorage = new MoneyStorage(filePath);
//        account = new Account(moneyStorage.load());
//        String testInput = "done goal 4";
//        MoneyCommand doneGoalCommand =  new DoneGoalCommand(testInput);
//        ui.clearOutputString();
//        doneGoalCommand.execute(account, ui, moneyStorage);
//        assertEquals(" Noted. I've removed this Goal:\n"
//                        + "  [GS] buy Motorbike (target: $10000.0)\n (to achieve by: 15/1/2050) HIGH\n"
//                , ui.getOutputString().split(" Now")[0]);
//        MoneyCommand exitCommand =  new ExitMoneyCommand();
//        exitCommand.execute(account, ui, moneyStorage);
//    }
}
