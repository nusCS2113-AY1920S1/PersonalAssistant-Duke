package money;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Expenditure;
import money.Goal;
import money.Income;
import moneycommands.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate = LocalDate.parse("9/10/2015", dateTimeFormatter);
    private LocalDate testDate2 = LocalDate.parse("9/10/2050", dateTimeFormatter);

    IncomeTest(){
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
    }


    @Test
    public void testAddIncome()throws ParseException, DukeException {

        String testInput = "add income TA /amt 560 /payday 10/12/2040";
        MoneyCommand addIncomeCommand =  new AddIncomeCommand(testInput);
        ui.clearOutputString();
        addIncomeCommand.execute(account, ui, moneyStorage);
        assertEquals(" Got it. I've added this income source: \n" +
                        "     [I] TA (salary: $560.0) (Paid On: 10/12/2040)\n"
                , ui.getOutputString().split(" Now")[0]);
        account.getIncomeListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testDeleteIncome()throws ParseException, DukeException {

        account.getIncomeListTotal().clear();
        Income i = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i);
        String testInput = "delete income 1";
        MoneyCommand deleteIncomeCommand =  new DeleteIncomeCommand(testInput);
        ui.clearOutputString();
        deleteIncomeCommand.execute(account, ui, moneyStorage);
        assertEquals(" Noted. I've removed this income source:\n" +
                        "  [I] TA Pay(salary: $2000.0) (Paid On: 9/10/2015)\n"
                , ui.getOutputString().split(" Now")[0]);
        account.getIncomeListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testListAllIncome()throws ParseException, DukeException {
        account.getIncomeListTotal().clear();
        Income i1 = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i1);
        Income i2 = new Income(1000, "crypto money", testDate);
        account.getIncomeListTotal().add(i2);
        MoneyCommand listTotalIncomeCommand =  new ListTotalIncomeCommand();
        ui.clearOutputString();
        ui.clearGraphContainerString();
        listTotalIncomeCommand.execute(account, ui, moneyStorage);
        assertEquals( " 1.[I] TA Pay(salary: $2000.0) (Paid On: 9/10/2015)\n" +
                        " 2.[I] crypto money(salary: $1000.0) (Paid On: 9/10/2015)\n" +
                        "Total income so far: $3000.0\n"
                , ui.getGraphContainerString().split(" Now")[0]);
        assertEquals("Got it, list will be printed in the other pane!\n", ui.getOutputString());
        account.getIncomeListTotal().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

}
