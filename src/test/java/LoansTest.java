import controlpanel.MoneyStorage;
import money.Loan;
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
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoansTest {
    private Ui ui;
    private Account account;
    private MoneyStorage storage;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate = LocalDate.parse("9/10/1997", dateTimeFormatter);


    public LoansTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new MoneyStorage(filePath);
        account = new Account();
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
    public void testListAllLoans() throws ParseException, DukeException {
        account.getLoans().clear();
        Loan outgoingLoan = new Loan(500, "my bros", testDate, Loan.Type.OUTGOING);
        Loan incomingLoan = new Loan(1000, "my mama", testDate, Loan.Type.INCOMING);
        account.getLoans().add(outgoingLoan);
        account.getLoans().add(incomingLoan);
        String listInput = "list all loans";
        MoneyCommand listAllLoansCommand = new ListLoansCommand(listInput);
        ui.clearOutputString();
        listAllLoansCommand.execute(account, ui, storage);
        assertEquals("Got it! List of ALL Loans printed in the other pane! \n", ui.getOutputString());
        assertEquals(" 1.[Outstanding] [O] my bros(loan: $500.0) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $500.0\n" +
                " 2.[Outstanding] [I] my mama(loan: $1000.0) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $1000.0\n" + "Total amount of ALL Loans: $1500.0\n", ui.getGraphContainerString());
    }

    @Test
    public void testListIncomingLoans() throws ParseException, DukeException {
        account.getLoans().clear();
        Loan outgoingLoan = new Loan(500, "my bros", testDate, Loan.Type.OUTGOING);
        Loan incomingLoan = new Loan(1000, "my bras", testDate, Loan.Type.INCOMING);
        account.getLoans().add(outgoingLoan);
        account.getLoans().add(incomingLoan);
        String listInput = "list incoming loans";
        MoneyCommand listAllLoansCommand = new ListLoansCommand(listInput);
        ui.clearOutputString();
        listAllLoansCommand.execute(account, ui, storage);
        assertEquals("Got it! List of INCOMING Loans printed in the other pane! \n", ui.getOutputString());
        assertEquals(" 1.[Outstanding] [I] my bras(loan: $1000.0) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $1000.0\n" + "Total amount of INCOMING Loans: $1000.0\n",
                ui.getGraphContainerString());
    }

    @Test
    public void testListOutgoingLoans() throws ParseException, DukeException {
        account.getLoans().clear();
        Loan outgoingLoan = new Loan(500, "my buds", testDate, Loan.Type.OUTGOING);
        Loan incomingLoan = new Loan(1000, "my mama", testDate, Loan.Type.INCOMING);
        account.getLoans().add(outgoingLoan);
        account.getLoans().add(incomingLoan);
        String listInput = "list outgoing loans";
        MoneyCommand listAllLoansCommand = new ListLoansCommand(listInput);
        ui.clearOutputString();
        listAllLoansCommand.execute(account, ui, storage);
        assertEquals("Got it! List of OUTGOING Loans printed in the other pane! \n", ui.getOutputString());
        assertEquals(" 1.[Outstanding] [O] my buds(loan: $500.0) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $500.0\n" + "Total amount of OUTGOING Loans: $500.0\n",
                ui.getGraphContainerString());
    }

    @Test
    public void testSettleOutgoingLoan() throws ParseException, DukeException {
        account.getLoans().clear();
        Loan settleLoan = new Loan(500, "my friends", testDate, Loan.Type.OUTGOING);
        account.getLoans().add(settleLoan);
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
        account.getLoans().clear();
        Loan settleLoan = new Loan(1000, "my daddy", testDate, Loan.Type.INCOMING);
        account.getLoans().add(settleLoan);
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
    public void testDeleteLoans() throws ParseException, DukeException {
        account.getLoans().clear();
        Loan outgoingLoan = new Loan(500, "my bros", testDate, Loan.Type.OUTGOING);
        Loan incomingLoan = new Loan(1000, "my daddy", testDate, Loan.Type.INCOMING);
        account.getLoans().add(outgoingLoan);
        account.getLoans().add(incomingLoan);
        String deleteFirstInput = "delete loan 2";
        MoneyCommand deleteLoanCommand = new DeleteLoanCommand(deleteFirstInput);
        ui.clearOutputString();
        deleteLoanCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now();
        String passDate = dateTimeFormatter.format(currDate);
        assertEquals(" Noted. I've removed this incoming loan:\n" +
                "  [Outstanding] [I] my daddy(loan: $1000.0) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $1000.0\n" +
                " Now you have 1 total loans.\n", ui.getOutputString());
        String deleteSecondInput = "delete loan 1";
        MoneyCommand deleteSecondLoanCommand = new DeleteLoanCommand(deleteSecondInput);
        ui.clearOutputString();
        deleteSecondLoanCommand.execute(account, ui, storage);
        assertEquals(" Noted. I've removed this outgoing loan:\n" +
                "  [Outstanding] [O] my bros(loan: $500.0) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $500.0\n" +
                " Now you have 0 total loans.\n", ui.getOutputString());
    }

    @Test
    public void testExceedAmount() throws ParseException {
        account.getLoans().clear();
        Loan settleLoan = new Loan(500, "my grandfather", testDate, Loan.Type.INCOMING);
        account.getLoans().add(settleLoan);
        String exceedInput = "paid 600 /to 1";
        MoneyCommand exceedSettleCommand = new SettleLoanCommand(exceedInput);
        ui.clearOutputString();
        try {
            exceedSettleCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("Whoa! The amount entered is more than debt! Type 'all' to settle the entire debt"));
        }

    }

    @Test
    public void testExceedSerialNumber() throws ParseException {
        account.getLoans().clear();
        Loan outgoingLoan = new Loan(500, "OutgoingLoan 1", testDate, Loan.Type.OUTGOING);
        Loan incomingLoan = new Loan(1000, "IncomingLoan 1", testDate, Loan.Type.INCOMING);
        account.getLoans().add(outgoingLoan);
        account.getLoans().add(incomingLoan);
        String settleOutgoingLoanInput = "received 20 /from -1";
        MoneyCommand exceedOutgoingCommand = new SettleLoanCommand(settleOutgoingLoanInput);
        ui.clearOutputString();
        try {
            exceedOutgoingCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The serial number of the loan is Out Of Bounds!"));
        }
        String settleIncomingLoanInput = "paid 2 /to 100";
        MoneyCommand exceedIncomingCommand = new SettleLoanCommand(settleIncomingLoanInput);
        ui.clearOutputString();
        try {
            exceedIncomingCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The serial number of the loan is Out Of Bounds!"));
        }
    }

    @Test
    public void testLoanDoesNotExist() {
        String notExistInput = "received 100 /from Brandon Frasier";
        MoneyCommand notExistOutgoingCommand = new SettleLoanCommand(notExistInput);
        ui.clearOutputString();
        try {
            notExistOutgoingCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException e) {
            assertThat(e.getMessage(), is("Brandon Frasier does not have a/an outgoing loan"));
        }
        String notExistSecondInput = "paid 400 /to Vivian Hsu";
        MoneyCommand notExistIncomingCommand = new SettleLoanCommand(notExistSecondInput);
        ui.clearOutputString();
        try {
            notExistIncomingCommand.execute(account, ui, storage);
        } catch (DukeException | ParseException e) {
            assertThat(e.getMessage(), is("Vivian Hsu does not have a/an incoming loan"));
        }
    }
}
