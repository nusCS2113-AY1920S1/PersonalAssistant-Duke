import controlpanel.MoneyStorage;
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

public class LoansTest {
    private Ui ui;
    private Account account;
    private MoneyStorage storage;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");


    public LoansTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new MoneyStorage(filePath);
        account = new Account(storage.load());
        ui = new Ui();
    }



    @Test
    public void testAddOutgoingLoan() throws ParseException, DukeException {
        String addInput = "lent my friends /amt 500 /on 9/10/1997";
        MoneyCommand addOutgoingLoanCommand = new AddLoanCommand(addInput);
        ui.clearOutputString();
        addOutgoingLoanCommand.execute(account, ui, storage);
        assertEquals(" Got it. I've added this outgoing loan: \n" +
                "     [Outstanding] [O] my friends(loan: $500.0) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $500.0\n" + " Now you have " +
                account.getLoans().size() + " loans listed and " +
                account.getOutgoingLoans().size() + " outgoing loans\n", ui.getOutputString());
    }

    @Test
    public void testAddIncomingLoan() throws ParseException, DukeException {
        String addInput = "borrowed my daddy /amt 1000 /on 9/10/1997";
        MoneyCommand addOutgoingLoanCommand = new AddLoanCommand(addInput);
        ui.clearOutputString();
        addOutgoingLoanCommand.execute(account, ui, storage);
        assertEquals(" Got it. I've added this incoming loan: \n" +
                "     [Outstanding] [I] my daddy(loan: $1000.0) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $1000.0\n" + " Now you have " +
                account.getLoans().size() + " loans listed and " +
                account.getIncomingLoans().size() + " incoming loans\n", ui.getOutputString());
    }

    @Test
    public void testSettleOutgoingLoan() throws ParseException, DukeException {
        String settleInput = "received 300 /from my friends";
        MoneyCommand settleOutgoingLoanCommand = new SettleLoanCommand(settleInput);
        ui.clearOutputString();
        settleOutgoingLoanCommand.execute(account, ui, storage);
        assertEquals(" Got it. An amount of $300.0 has been paid from my friends for the" +
                " following loan: \n" + "     [Outstanding] [O] my friends(loan: $500.0) " +
                "(Lent On: 9/10/1997) Outstanding Amount: $200.0\n", ui.getOutputString());
        String settleAllInput = "received all /from my friends";
        MoneyCommand settleEntireLoanCommand = new SettleLoanCommand(settleAllInput);
        ui.clearOutputString();
        settleEntireLoanCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now();
        String passDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. An amount of $200.0 has been paid from my friends for the" +
                " following loan: \n" + "     [Settled] [O] my friends(loan: $500.0) " +
                "(Lent On: 9/10/1997) (Paid Back On: " + passDate + ")\n" +
                "The outgoing loan has been settled\n", ui.getOutputString());
    }

    @Test
    public void testSettleIncomingLoan() throws ParseException, DukeException {
        String settleInput = "paid 400 /to my daddy";
        MoneyCommand settleOutgoingLoanCommand = new SettleLoanCommand(settleInput);
        ui.clearOutputString();
        settleOutgoingLoanCommand.execute(account, ui, storage);
        assertEquals(" Got it. An amount of $400.0 has been paid to my daddy for the" +
                " following loan: \n" + "     [Outstanding] [I] my daddy(loan: $1000.0) " +
                "(Lent On: 9/10/1997) Outstanding Amount: $600.0\n", ui.getOutputString());
        String settleAllInput = "paid all /to my daddy";
        MoneyCommand settleEntireLoanCommand = new SettleLoanCommand(settleAllInput);
        ui.clearOutputString();
        settleEntireLoanCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now();
        String passDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. An amount of $600.0 has been paid to my daddy for the" +
                " following loan: \n" + "     [Settled] [I] my daddy(loan: $1000.0) " +
                "(Lent On: 9/10/1997) (Paid Back On: " + passDate + ")\n" +
                "The incoming loan has been settled\n", ui.getOutputString());
    }

    @Test
    public void deleteLoans() {

    }
}
