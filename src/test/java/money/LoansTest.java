package money;

import controlpanel.MoneyStorage;
import moneycommands.*;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class LoansTest {
    private Ui ui;
    private Account account;
    private MoneyStorage storage;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate = LocalDate.parse("9/10/1997", dateTimeFormatter);


    LoansTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    void testAddOutgoingLoan() throws ParseException, DukeException {
        String addInput = "lent my friends /amt 500 /on 9/10/1997";
        MoneyCommand addOutgoingLoanCommand = new AddLoanCommand(addInput);
        ui.clearOutputString();
        addOutgoingLoanCommand.execute(account, ui, storage);
        assertEquals(" Got it. I've added this outgoing loan: \n" +
                "     [Outstanding] [O] my friends(loan: $500.00) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $500.00\n" + " Now you have " +
                account.getLoans().size() + " loans listed and " +
                account.getOutgoingLoans().size() + " outgoing loans\n", ui.getOutputString());
    }

    @Test
    void testAddIncomingLoan() throws ParseException, DukeException {
        String addInput = "borrowed my daddy /amt 1000 /on 9/10/1997";
        MoneyCommand addOutgoingLoanCommand = new AddLoanCommand(addInput);
        ui.clearOutputString();
        addOutgoingLoanCommand.execute(account, ui, storage);
        assertEquals(" Got it. I've added this incoming loan: \n" +
                "     [Outstanding] [I] my daddy(loan: $1000.00) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $1000.00\n" + " Now you have " +
                account.getLoans().size() + " loans listed and " +
                account.getIncomingLoans().size() + " incoming loans\n", ui.getOutputString());
    }

    @Test
    void testListAllLoans() throws ParseException, DukeException {
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
        assertEquals(" 1.[Outstanding] [O] my bros(loan: $500.00) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $500.00\n" +
                " 2.[Outstanding] [I] my mama(loan: $1000.00) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $1000.00\n" + "Total amount of ALL Loans: $1500.00\n", ui.getGraphContainerString());
    }

    @Test
    void testListIncomingLoans() throws ParseException, DukeException {
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
        assertEquals(" 1.[Outstanding] [I] my bras(loan: $1000.00) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $1000.00\n" + "Total amount of INCOMING Loans: $1000.00\n",
                ui.getGraphContainerString());
    }

    @Test
    void testListOutgoingLoans() throws ParseException, DukeException {
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
        assertEquals(" 1.[Outstanding] [O] my buds(loan: $500.00) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $500.00\n" + "Total amount of OUTGOING Loans: $500.00\n",
                ui.getGraphContainerString());
    }

    @Test
    void testSettleOutgoingLoan() throws ParseException, DukeException {
        account.getLoans().clear();
        Loan settleLoan = new Loan(500, "my friends", testDate, Loan.Type.OUTGOING);
        account.getLoans().add(settleLoan);
        String settleInput = "received 300 /from my friends";
        MoneyCommand settleOutgoingLoanCommand = new SettleLoanCommand(settleInput);
        ui.clearOutputString();
        settleOutgoingLoanCommand.execute(account, ui, storage);
        assertEquals(" Got it. An amount of $300.00 has been paid from my friends for the" +
                " following loan: \n" + "     [Outstanding] [O] my friends(loan: $500.00) " +
                "(Lent On: 9/10/1997) Outstanding Amount: $200.00\n", ui.getOutputString());
        String settleAllInput = "received all /from my friends";
        MoneyCommand settleEntireLoanCommand = new SettleLoanCommand(settleAllInput);
        ui.clearOutputString();
        settleEntireLoanCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now();
        String passDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. An amount of $200.00 has been paid from my friends for the" +
                " following loan: \n" + "     [Settled] [O] my friends(loan: $500.00) " +
                "(Lent On: 9/10/1997) (Paid Back On: " + passDate + ")\n" +
                "The outgoing loan has been settled\n", ui.getOutputString());
    }

    @Test
    void testSettleIncomingLoan() throws ParseException, DukeException {
        account.getLoans().clear();
        Loan settleLoan = new Loan(1000, "my daddy", testDate, Loan.Type.INCOMING);
        account.getLoans().add(settleLoan);
        String settleInput = "paid 400 /to my daddy";
        MoneyCommand settleOutgoingLoanCommand = new SettleLoanCommand(settleInput);
        ui.clearOutputString();
        settleOutgoingLoanCommand.execute(account, ui, storage);
        assertEquals(" Got it. An amount of $400.00 has been paid to my daddy for the" +
                " following loan: \n" + "     [Outstanding] [I] my daddy(loan: $1000.00) " +
                "(Lent On: 9/10/1997) Outstanding Amount: $600.00\n", ui.getOutputString());
        String settleAllInput = "paid all /to my daddy";
        MoneyCommand settleEntireLoanCommand = new SettleLoanCommand(settleAllInput);
        ui.clearOutputString();
        settleEntireLoanCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now();
        String passDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. An amount of $600.00 has been paid to my daddy for the" +
                " following loan: \n" + "     [Settled] [I] my daddy(loan: $1000.00) " +
                "(Lent On: 9/10/1997) (Paid Back On: " + passDate + ")\n" +
                "The incoming loan has been settled\n", ui.getOutputString());
    }

    @Test
    void testDeleteLoans() throws ParseException, DukeException {
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
                "  [Outstanding] [I] my daddy(loan: $1000.00) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $1000.00\n" +
                " Now you have 1 total loans.\n", ui.getOutputString());
        String deleteSecondInput = "delete loan 1";
        MoneyCommand deleteSecondLoanCommand = new DeleteLoanCommand(deleteSecondInput);
        ui.clearOutputString();
        deleteSecondLoanCommand.execute(account, ui, storage);
        assertEquals(" Noted. I've removed this outgoing loan:\n" +
                "  [Outstanding] [O] my bros(loan: $500.00) (Lent On: 9/10/1997) " +
                "Outstanding Amount: $500.00\n" +
                " Now you have 0 total loans.\n", ui.getOutputString());
    }

    @Test
    void testExceedAmount() throws ParseException {
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
            assertThat(e.getMessage(), is("Whoa! The amount entered is more than debt! Type 'all' to settle the entire debt\n"));
        }

    }

    @Test
    void testExceedSerialNumber() throws ParseException {
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
            assertThat(e.getMessage(), is("The serial number of the loan is Out Of Bounds!\n"));
        }
        String settleIncomingLoanInput = "paid 2 /to 100";
        MoneyCommand exceedIncomingCommand = new SettleLoanCommand(settleIncomingLoanInput);
        ui.clearOutputString();
        try {
            exceedIncomingCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The serial number of the loan is Out Of Bounds!\n"));
        }
    }

    @Test
    void testLoanDoesNotExist() {
        String notExistInput = "received 100 /from Brandon Frasier";
        MoneyCommand notExistOutgoingCommand = new SettleLoanCommand(notExistInput);
        ui.clearOutputString();
        try {
            notExistOutgoingCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException e) {
            assertThat(e.getMessage(), is("Brandon Frasier does not have an outgoing loan"));
        }
        String notExistSecondInput = "paid 400 /to Vivian Hsu";
        MoneyCommand notExistIncomingCommand = new SettleLoanCommand(notExistSecondInput);
        ui.clearOutputString();
        try {
            notExistIncomingCommand.execute(account, ui, storage);
        } catch (DukeException | ParseException e) {
            assertThat(e.getMessage(), is("Vivian Hsu does not have an incoming loan"));
        }
    }

    @Test
    void testInvalidAddInput() {
        String invalidInput = "borrowed from my daddy /amt 200000     ";
        MoneyCommand invalidAddCommand = new AddLoanCommand(invalidInput);
        ui.clearOutputString();
        try {
            invalidAddCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            assertThat(e.getMessage(), is("Please enter in the format: " +
                    "lent/borrowed <person> /amt <amount> /on <date>\n"));
        }
        String invalidDateInput = "lent my boys /amt 3000 /on blah blah blah";
        MoneyCommand invalidDateCommand = new AddLoanCommand(invalidDateInput);
        ui.clearOutputString();
        try {
            invalidDateCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException | DateTimeParseException e) {
            assertThat(e.getMessage(), is("Invalid date! Please enter date in the format: d/m/yyyy\n"));
        }
    }

    @Test
    void testInvalidDeleteInput() {
        String invalidInput = "delete loan whoa whoa";
        ui.clearOutputString();
        try {
            MoneyCommand invalidDeleteCommand = new DeleteLoanCommand(invalidInput);
            invalidDeleteCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException | NumberFormatException e) {
            assertThat(e.getMessage(),
                    is("Please enter a numerical number as the index of the loan to be deleted\n"));
        }
        String invalidIndexInput = "delete loan -2";
        ui.clearOutputString();
        try {
            MoneyCommand invalidIndexCommand = new DeleteLoanCommand(invalidIndexInput);
            invalidIndexCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException e) {
            assertThat(e.getMessage(), is("The serial number of the loan is Out Of Bounds!"));
        }
    }
}
