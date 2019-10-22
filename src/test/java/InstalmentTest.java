import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import moneycommands.AddInstalmentCommand;
import moneycommands.MoneyCommand;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstalmentTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;

    @Test
    public void testAddInstalment() throws ParseException, DukeException{
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
        String testInput = "add instalment mortgage /amt 100000 /within 200 months /from 12/12/2010 @6%";
        MoneyCommand addInstalmentCommand =  new AddInstalmentCommand(testInput);
        ui.clearOutputString();
        addInstalmentCommand.execute(account, ui, moneyStorage);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        int last = account.getInstalments().size() - 1;

        assertEquals(" Got it. I've added this to your instalments: \n"
                + account.getInstalments().get(last).toString() + "\n"
                + " Now you have " + account.getInstalments().size() + " instalments listed\n"
                + " You are paying $" + df.format(account.getInstalments().get(last).EqualMonthlyInstalment())
                + " per month\n" + " For " + account.getInstalments().get(last).getNumOfPayments() + " months\n"
                + " Until " + account.getInstalments().get(last).getDateEndDate() + "\n"
                + " The total amount you will pay is $" + account.getInstalments().get(last).totalAmount() + "\n"
                , ui.getOutputString());
    }
}